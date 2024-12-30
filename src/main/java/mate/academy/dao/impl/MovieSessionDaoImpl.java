package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.ArrayList;
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

    private static final String CAN_T_GET_MOVIE_SESSION_BY_ID_MSG = "Can't get movieSession by id ";
    private static final String CAN_T_ADD_MOVIE_SESSION_MSG = "Can't add movieSession ";

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(CAN_T_ADD_MOVIE_SESSION_MSG + movieSession, e);
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
            Query<MovieSession> query =
                    session.createQuery("SELECT ms FROM MovieSession ms "
                                    + "LEFT JOIN FETCH ms.movie m "
                                    + "LEFT JOIN FETCH ms.cinemaHall cnh",
                            MovieSession.class);
            return Optional.of(query.getSingleResult());
        } catch (RuntimeException e) {
            throw new DataProcessingException(CAN_T_GET_MOVIE_SESSION_BY_ID_MSG + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        return get(movieId).filter(movieSession ->
                        movieSession.getLocalDateTime().toLocalDate().equals(date))
                .map(List::of)
                .orElseGet(ArrayList::new);
    }
}
