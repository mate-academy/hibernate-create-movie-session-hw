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
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Dao
public class MovieSessionDaoImpl extends AbstractDao implements MovieSessionDao {
    protected MovieSessionDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public MovieSessionDaoImpl() {
        super(HibernateUtil.getSessionFactory());
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
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
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "from MovieSession ms "
                                    + "join fetch ms.movie m "
                                    + "join fetch ms.cinemaHall ch "
                                    + "where m.id = :movieId and date(ms.showTime) = :date",
                            MovieSession.class
                    )
                    .setParameter("movieId", movieId)
                    .setParameter("date", date)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available sessions for movie id "
                    + movieId + " on date " + date, e);
        }
    }

}
