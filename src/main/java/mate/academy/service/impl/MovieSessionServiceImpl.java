package mate.academy.service.impl;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

@Service
@AllArgsConstructor
public class MovieSessionServiceImpl implements MovieSessionService {

    private final MovieSessionDao movieSessionDao;

    @Override
    public MovieSession add(MovieSession movieSession) {
        return movieSessionDao.add(movieSession);
    }

    @Override
    public MovieSession get(Long id) {
        return movieSessionDao.get(id);
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        return movieSessionDao.findAvailableSessions(movieId, date);
    }
}
