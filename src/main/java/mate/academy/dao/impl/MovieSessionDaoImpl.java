package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.model.dto.MovieSessionDto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl extends AbstractDao implements MovieSessionDao {
    protected MovieSessionDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public MovieSessionDaoImpl() {
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        return persistEntity(movieSession);
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = factory.openSession()) {
            Query<MovieSession> getMovieSessionQuery = session.createQuery(
                    "FROM MovieSession m "
                            + "JOIN FETCH m.movie "
                            + "JOIN FETCH m.cinemaHall "
                            + "WHERE m.id = :id", MovieSession.class
            );
            getMovieSessionQuery.setParameter("id", id);
            return getMovieSessionQuery.uniqueResultOptional();
        }
    }

    @Override
    public List<MovieSessionDto> findAvailableSession(Long movieId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        try (Session session = factory.openSession()) {
            Query<MovieSessionDto> findAvailableMovieSessionQuery = session.createQuery(
                    "SELECT new mate.academy.model.dto.MovieSessionDto("
                            + "movie.title, "
                            + "cinemaHall.description, "
                            + "m.showTime) "
                            + "FROM MovieSession m "
                            + "JOIN m.movie movie "
                            + "JOIN m.cinemaHall cinemaHall "
                            + "WHERE m.movie.id = :movieId "
                            + "AND m.showTime BETWEEN :startOfDay AND :endOfDay",
                    MovieSessionDto.class);
            findAvailableMovieSessionQuery.setParameter("startOfDay", startOfDay);
            findAvailableMovieSessionQuery.setParameter("endOfDay", endOfDay);
            findAvailableMovieSessionQuery.setParameter("movieId", movieId);
            return findAvailableMovieSessionQuery.getResultList();
        }
    }
}
