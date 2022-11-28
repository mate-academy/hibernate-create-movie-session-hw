package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
public class MovieSessionDaoImpl extends AbstractDaoImpl<MovieSession> implements MovieSessionDao {

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session by id:" + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        String query = "select m from MovieSession m where m.movie.id = :movieId "
                + "and m.showTime between :startTime and :endTime";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> movieSessionQuery = session.createQuery(query, MovieSession.class);
            LocalDateTime startTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            movieSessionQuery.setParameter("movieId", movieId);
            movieSessionQuery.setParameter("startTime", startTime);
            movieSessionQuery.setParameter("endTime", endTime);
            return movieSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available sessions by movie id: "
                    + movieId + " and date: " + date, e);
        }
    }
}
