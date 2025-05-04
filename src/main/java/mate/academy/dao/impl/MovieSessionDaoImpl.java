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

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        try {
            HibernateUtil.getSessionFactory()
                    .inTransaction(session -> session.persist(movieSession));
            return movieSession;
        } catch (Exception e) {
            throw new DataProcessingException("Can't add movie session to db", e);
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
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session
                    .createQuery("""
                            from MovieSession ms
                            join fetch ms.movie
                            join fetch ms.cinemaHall
                            where ms.movie.id = :movieId 
                            and Date(ms.showTime) = :date
                            """, MovieSession.class)
                    .setParameter("movieId", movieId)
                    .setParameter("date", date)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie sessions from db", e);
        }
    }
}
