package mate.dao.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.dao.MovieSessionDao;
import mate.exception.DataProcessingException;
import mate.lib.Dao;
import mate.model.MovieSession;
import mate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movieSession " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> movieSessionQueryQuery = session
                    .createQuery("from MovieSession ms "
                    + "left join fetch ms.cinemaHall where ms.id = :id", MovieSession.class);
            movieSessionQueryQuery.setParameter("id", id);
            return Optional.ofNullable(movieSessionQueryQuery.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a MovieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String queryString = "from MovieSession "
                    + "where id = :movieId and date_format(showTime, '%Y-%m-%d') = :dateId";
            Query<MovieSession> movieSessionQueryQuery = session
                    .createQuery(queryString, MovieSession.class);
            movieSessionQueryQuery.setParameter("movieId", movieId);
            movieSessionQueryQuery.setParameter("dateId", date.toString());
            return movieSessionQueryQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available sessions", e);
        }
    }
}
