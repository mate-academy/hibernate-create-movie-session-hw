package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.lib.Inject;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieService;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private final SessionFactory factory = HibernateUtil.getSessionFactory();
    @Inject
    private MovieService movieService;

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw new DataProcessingException(String.format("Can't add "
                    + "movie session: %s to DB", movieSession), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return movieSession;
    }

    @Override
    public MovieSession get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(MovieSession.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can't get "
                    + "movie session with id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = factory.openSession()) {
            Movie checkMovie = movieService.get(movieId);

            Query<MovieSession> getAllAvailableSessions = session.createQuery(
                    "from MovieSession ms where ms.movie = :movie",
                    MovieSession.class);
            getAllAvailableSessions.setParameter("movie", checkMovie);

            List<MovieSession> sessionByMovieId = getAllAvailableSessions.getResultList();

            return sessionByMovieId.stream()
                    .filter(ms -> ms.getShowTime().toLocalDate().isEqual(date))
                    .collect(Collectors.toList());
        }
    }
}
