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
public class MovieSessionDaoImpl extends AbstractDao implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        return super.add(movieSession);
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return getByIdQuery(session)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("The search for movie sessions was failed", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return getSessionsByDateQuery(session)
                    .setParameter("movieId", movieId)
                    .setParameter("date", date)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("The search for movie sessions was failed", e);
        }
    }

    private static Query<MovieSession> getByIdQuery(Session session) {
        return session.createQuery("""
                    from MovieSession m
                    join fetch m.cinemaHall join fetch m.movie
                    where m.id = :id""", MovieSession.class
        );
    }

    private Query<MovieSession> getSessionsByDateQuery(Session session) {
        return session.createQuery("""
                        from MovieSession s
                        join fetch s.movie
                        join fetch s.cinemaHall
                        where s.movie.id = :movieId and Date(showTime) = :date""",
                MovieSession.class
        );
    }
}
