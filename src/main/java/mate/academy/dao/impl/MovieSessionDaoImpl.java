package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
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
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime startDate = LocalDateTime.of(date.getYear(), date.getMonth(),
                date.getDayOfMonth(), 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(date.getYear(), date.getMonth(),
                date.getDayOfMonth(), 23, 59, 59);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from MovieSession ms"
                                    + " left join ms.movie where ms.id =: movieId"
                                    + " and showTime between :startDate and :endDate",
                            MovieSession.class)
                            .setParameter("movieId", movieId)
                            .setParameter("startDate", startDate)
                            .setParameter("endDate", endDate)
                    .getResultList();
        } catch (Exception ex) {
            throw new DataProcessingException("Could not get available movie sessions ", ex);
        }
    }
}
