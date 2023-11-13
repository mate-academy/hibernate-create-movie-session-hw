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
    private static final String HQL_QUERY = "from MovieSession ms "
            + "where ms.movie.id = :idValue "
            + "AND ms.showTime between :fromDate AND :toDate";
    private static final LocalTime TIME_FROM = LocalTime.of(0, 00);
    private static final LocalTime TIME_TO = LocalTime.of(23, 59);

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
            throw new DataProcessingException("Can't insert movie session "
                    + movieSession, e);
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
            throw new DataProcessingException("Can't get a movie session by id: "
                    + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime dateFrom = LocalDateTime.of(date, TIME_FROM);
        LocalDateTime dateTo = LocalDateTime.of(date, TIME_TO);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery(HQL_QUERY, MovieSession.class);
            query.setParameter("idValue", movieId);
            query.setParameter("fromDate", dateFrom);
            query.setParameter("toDate", dateTo);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie sessions by movieId"
                    + movieId + ", and date " + date, e);
        }
    }
}
