package mate.academy.dao.impl;

import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        return null;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        return null;
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        return null;
    }
}
