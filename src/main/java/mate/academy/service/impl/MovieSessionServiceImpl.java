package mate.academy.service.impl;

import mate.academy.lib.Service;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovieSessionServiceImpl implements MovieSessionService {
    @Override
    public MovieSession add(MovieSession movieSession) {
        return null;
    }

    @Override
    public MovieSession get(Long id) {
        return null;
    }

    @Override
    public List<MovieSession> findAvailableSession(Long movieId, LocalDate date) {
        return null;
    }
}
