package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        try {
            HibernateUtil.getFactory().inTransaction(session ->
                    session.persist(movieSession));
            return movieSession;
        } catch (Exception e) {
            throw new DataProcessingException("Can't add movieSession " + movieSession, e);
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try {
            return HibernateUtil.getFactory().fromSession(session ->
                    Optional.ofNullable(session.get(MovieSession.class, id)));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try {
            return HibernateUtil.getFactory()
                    .fromSession(session -> session.createQuery("FROM MovieSession ms "
            + "WHERE ms.movie.id = :movieId AND DATE(showTime) = :date", MovieSession.class)
                    .setParameter("movieId", movieId).setParameter("date", date).getResultList());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available movie's sessions for movieID "
                    + movieId + " on date " + date, e);
        }
    }
}
