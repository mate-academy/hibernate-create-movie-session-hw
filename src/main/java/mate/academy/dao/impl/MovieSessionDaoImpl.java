package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String availableSessionsQueryFormat = """
                    from MovieSession ms
                    join fetch ms.movie
                    join fetch ms.cinemaHall
                    WHERE ms.movie.id = %s AND ms.showTime BETWEEN '%s' AND '%s'""";
            String availableSessionsQuery = String.format(
                    availableSessionsQueryFormat, movieId, date, date.plusDays(1)
            );
            return session.createQuery(availableSessionsQuery, MovieSession.class).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get movie sessions by movie and date from db", e
            );
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movie " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
