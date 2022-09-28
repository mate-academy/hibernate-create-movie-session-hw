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
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Couldn't add movie session: "
                    + movieSession + " to DB. ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get movie session by id: "
                    + id + " from DB. ", e);
        }
    }

    @Override
    public List<MovieSession> getAllByMovieIdAndDate(Long movieId, LocalDate localDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query =
                    session.createQuery("from MovieSession ms where ms.movie.id = :movieId "
                    + "AND YEAR(ms.showTime) = :dateYear AND MONTH(ms.showTime) = :dateMonth"
                            + " AND DAY(ms.showTime) = :dateDay", MovieSession.class);
            query.setParameter("movieId", movieId);
            query.setParameter("dateYear", localDate.getYear());
            query.setParameter("dateMonth", localDate.getMonthValue());
            query.setParameter("dateDay", localDate.getDayOfMonth());
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get all movie sessions by movieId: "
                    + movieId + " and date: " + localDate + " from DB. ", e);
        }
    }
}
