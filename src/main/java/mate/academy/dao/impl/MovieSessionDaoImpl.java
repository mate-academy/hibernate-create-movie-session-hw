package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class MovieSessionDaoImpl implements MovieSessionDao {

    @Override
    public MovieSession add(MovieSession movieSession) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.persist(movieSession.getCinemaHall());
            session.persist(movieSession.getMovie());
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            throw new DataProcessingException("Can't add movieSession " + movieSession, e);
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movieSession hall by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllCinemaHalls = session.createQuery(
                    "from MovieSession ", MovieSession.class);
            List<MovieSession> allSessions = getAllCinemaHalls.getResultList();
            List<MovieSession> availableSessions = new ArrayList<>();
            for (MovieSession movieSession : allSessions) {
                if (movieId.equals(movieSession.getMovie().getId())
                        && date.equals(movieSession.getShowTime().toLocalDate())) {
                    availableSessions.add(movieSession);
                }
            }
            return availableSessions;
        } catch (Exception e) {
            throw new DataProcessingException("Can't get cinema halls", e);
        }
    }
}
