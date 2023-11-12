package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.DaoOperation;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private final DaoOperation<MovieSession> daoOperation = new DaoOperation<>("movie session");

    @Override
    public MovieSession add(MovieSession movieSession) {
        return daoOperation.add(movieSession);
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        return daoOperation.get(id, MovieSession.class);
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> allMoviesQuery =
                    session.createQuery("from MovieSession "
                            + "where year(showTime) = year(:date)"
                                    + "and month(showTime) = month(:date)"
                                    + "and day(showTime) = day(:date)",
                            MovieSession.class);
            allMoviesQuery.setParameter("date", date);
            return allMoviesQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get list of all movies.", e);
        }
    }
}
