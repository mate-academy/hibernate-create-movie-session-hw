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
import org.hibernate.query.Query;

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
            Query<MovieSession> getMovieSession = session
                    .createQuery("""
                            from MovieSession ms 
                            where ms.movie.id = :movieId 
                            and Date(ms.showTime) = :date
                            """, MovieSession.class);
            getMovieSession.setParameter("movieId", movieId);
            getMovieSession.setParameter("date", date);
            return getMovieSession.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie sessions from db", e);
        }
    }
}
