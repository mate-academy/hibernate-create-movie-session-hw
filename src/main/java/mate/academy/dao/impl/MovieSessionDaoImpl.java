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
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final String MOVIE_ID_PARAMETER = "movieId";
    private static final String DAY_PARAMETER = "day";
    private static final String MONTH_PARAMETER = "month";
    private static final String YEAR_PARAMETER = "year";

    private final SessionFactory factory = HibernateUtil.getSessionFactory();

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            movieSession.setId((Long) session.getIdentifier(movieSession));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movie session. "
                    + "Movie session: " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = factory.openSession()) {
            return session.createQuery("from MovieSession ms "
                            + "left join fetch ms.movie m "
                            + "where m.id = :movieId "
                            + "and extract(day from ms.showTime) = :day "
                            + "and extract(month from ms.showTime) = :month "
                            + "and extract(year from ms.showTime) = :year ",
                            MovieSession.class)
                    .setParameter(MOVIE_ID_PARAMETER, movieId)
                    .setParameter(DAY_PARAMETER, date.getDayOfMonth())
                    .setParameter(MONTH_PARAMETER, date.getMonthValue())
                    .setParameter(YEAR_PARAMETER, date.getYear())
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available movie sessions."
                    + " Movie Id: " + movieId
                    + " Date: " + date, e);
        }
    }
}
