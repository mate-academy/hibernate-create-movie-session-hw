package mate.academy.dao.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.Query;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
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
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long id, LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.movie "
                    + "where ms.id = :inputId and date(ms.showTime) = :inputDate");
            query.setParameter("inputId", id);
            query.setParameter("inputDate", Date.valueOf(date));
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all cinema halls", e);
        }
    }
}
