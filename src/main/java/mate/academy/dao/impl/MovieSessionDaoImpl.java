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
public class MovieSessionDaoImpl extends AbstractDaoImpl<MovieSession>
        implements MovieSessionDao {
    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get movie session by id" + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery("from MovieSession ms "
                    + "where ms.movie.id = :movie "
                    + "and year(ms.showTime) = :year "
                    + "and month(ms.showTime) = :month "
                    + "and day(ms.showTime) = :day ", MovieSession.class);
            query.setParameter("movie", movieId);
            query.setParameter("year", date.getYear());
            query.setParameter("month", date.getMonthValue());
            query.setParameter("day", date.getDayOfMonth());
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get all movie sessions by date "
                    + date, e);
        }
    }
}
