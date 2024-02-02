package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final SessionFactory SESSION_FACTORY = HibernateUtil.getSessionFactory();

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = SESSION_FACTORY.openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = SESSION_FACTORY.openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        String query = """
                FROM MovieSession movieSession
                LEFT JOIN FETCH movieSession.movie movie
                LEFT JOIN FETCH movieSession.cinemaHall
                WHERE movie.id = :idValue
                """;
        try (Session session = SESSION_FACTORY.openSession()) {
            return session.createQuery(query, MovieSession.class)
                    .setParameter("idValue", movieId)
                    .list()
                    .stream()
                    .filter(movieSession ->
                            movieSession.getShowTime().getDayOfMonth()
                                    == date.getDayOfMonth())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't find available sessions by id and date: " + movieId + " " + date, e);
        }
    }
}
