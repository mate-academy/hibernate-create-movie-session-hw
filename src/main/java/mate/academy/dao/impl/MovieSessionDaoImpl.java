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
            final Query<MovieSession> query
                    = session.createQuery("""
                    from MovieSession ms
                    left join fetch ms.movie
                    left join fetch ms.cinemaHall
                    where ms.id = :id
                    """, MovieSession.class);
            query.setParameter("id", id);
            return Optional.ofNullable(query.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movies session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> getByIdAndDate(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            final Query<MovieSession> query
                    = session.createQuery("""
                    from MovieSession ms
                    left join fetch ms.movie
                    left join fetch ms.cinemaHall
                    where ms.movie.id = :id
                    and cast(ms.showTime AS date) = :date
                    """, MovieSession.class);
            query.setParameter("id", movieId);
            query.setParameter("date", date);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movies sessions from DB ", e);
        }
    }
}
