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
        return super.get(MovieSession.class, id);
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> moviesByDateQuery = session.createQuery(
                    "from MovieSession s where s.movie.id = :movieId and Date(showTime) = :date",
                    MovieSession.class
            );
            moviesByDateQuery.setParameter("movieId", movieId);
            moviesByDateQuery.setParameter("date", date);
            return moviesByDateQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("The search for movie sessions was failed", e);
        }
    }
}
