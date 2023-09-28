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
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try {
            return sessionFactory.fromTransaction(session -> {
                session.persist(movieSession);
                return movieSession;
            });
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't save movie session to DB: " + movieSession.getId(), e);
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        String query = """
                from MovieSession ms
                left join fetch ms.cinemaHalls
                left join fetch ms.movies
                where ms.id = :id
                """;
        try (Session session = HibernateUtil.getSessionFactory()
                .openSession()) {
            Query<MovieSession> sessionQuery = session.createQuery(query, MovieSession.class);
            sessionQuery.setParameter("id", id);
            return sessionQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new RuntimeException("Can't get movie session by id");
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        String query = """
                from MovieSession ms
                left join fetch ms.cinemaHalls
                left join fetch ms.movies
                WHERE ms.id = :id
                AND ms.showTime >= :startDay
                AND ms.showTime < :endDay
                """;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> movieSessionQuery = session.createQuery(query, MovieSession.class);
            movieSessionQuery.setParameter("id", movieId);
            movieSessionQuery.setParameter("startDay", date.atStartOfDay());
            movieSessionQuery.setParameter("endDay", date.atStartOfDay().plusDays(1L));
            return movieSessionQuery.getResultList();
        }
    }
}
