package mate.academy.dao.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movie session "
                    + "into DB: " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate localDate) {
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> getMovieSessionQuery = session
                    .createQuery("FROM MovieSession m "
                            + "LEFT JOIN FETCH m.movie mvs "
                            + "where mvs.id = :id AND DATE(m.showTime) = :date",
                            MovieSession.class);
            getMovieSessionQuery.setParameter("id", movieId);
            getMovieSessionQuery.setParameter("date", Date.valueOf(localDate));
            return getMovieSessionQuery.list();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available movie sessions from DB "
                    + "for movie id: " + movieId + " and date: " + localDate, e);
        }
    }
}
