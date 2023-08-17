package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final String MOVIE_ID = "movieId";
    private static final String START_OF_DATE = "startOfDate";
    private static final String END_OF_DATE = "endOfDate";

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
            MovieSession movieSession = session.get(MovieSession.class, id);
            if (movieSession != null) {
                Hibernate.initialize(movieSession.getMovie());
            }
            return Optional.ofNullable(movieSession);
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        String query = String.format("from MovieSession m where m.movie.id = :%s"
                        + " and m.showTime >= :%s and m.showTime <= :%s",
                MOVIE_ID, START_OF_DATE, END_OF_DATE);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAll = session.createQuery(query, MovieSession.class);
            getAll.setParameter(MOVIE_ID, movieId);
            getAll.setParameter(START_OF_DATE, date.atStartOfDay());
            getAll.setParameter(END_OF_DATE, date.atTime(LocalTime.MAX));
            return getAll.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get movie session with id " + movieId
                    + " on required date: " + date, e);
        }
    }
}
