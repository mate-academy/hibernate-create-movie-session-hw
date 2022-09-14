package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {

    @Override
    public MovieSession add(MovieSession movieSession) {
        return MovieSessionDao.super.add(movieSession);
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        return get(MovieSession.class, id);
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        String query = "from MovieSession"
                + " where movie.id = :id"
                + " and showTime between :startTime and :endTime";
        Map<String, Object> queryParameters = Map.of(
                "id", movieId,
                "startTime", LocalDateTime.of(date, LocalTime.of(0, 0, 0)),
                "endTime", LocalDateTime.of(date, LocalTime.of(23, 59, 59)));
        return getList(query, queryParameters, MovieSession.class);
    }
}
