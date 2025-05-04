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
public class MovieSessionDaoImpl extends AbstractDao implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        return super.create(movieSession);
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        return super.get(MovieSession.class, id);
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
            Query<MovieSession> query = session.createQuery("from MovieSession "
                            + "where movie.id = :movieId and "
                            + "showTime between :startOfDay and :endOfDay "
                            + "order by showTime",
                    MovieSession.class);
            query.setParameter("movieId", movieId);
            query.setParameter("startOfDay", startOfDay);
            query.setParameter("endOfDay", endOfDay);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available sessions for "
                    + "movieId = [" + movieId + "] and date = [" + date + "]", e);
        }
    }
}
