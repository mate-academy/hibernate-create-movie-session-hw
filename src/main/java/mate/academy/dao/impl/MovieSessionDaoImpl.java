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
            throw new DataProcessingException("Can't insert movie session " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> movieSessionQuery = session
                    .createQuery("from MovieSession ms"
                                    + " left join fetch ms.movie "
                                    + "left join fetch ms.cinemaHall "
                                    + "where ms.id= :id and ms.showTime between :after and :before",
                            MovieSession.class);
            movieSessionQuery.setParameter("id", movieId);
            movieSessionQuery.setParameter("before", LocalDateTime.of(date, LocalTime.MAX));
            movieSessionQuery.setParameter("after", LocalDateTime.of(date, LocalTime.MIN));
            return movieSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available movie by id: " + movieId
                         + " for session time: " + date, e);
        }
    }
}
