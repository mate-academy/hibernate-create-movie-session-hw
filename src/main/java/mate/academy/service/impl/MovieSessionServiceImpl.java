package mate.academy.service.impl;

import java.time.LocalDate;
import java.util.List;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

@Service
public class MovieSessionServiceImpl implements MovieSessionService {
    @Inject
    private MovieSessionDao movieSessionDao;

    @Override
    public MovieSession add(MovieSession movieSession) {
        if (movieSession != null) {
            return movieSessionDao.add(movieSession);
        }
        throw new DataProcessingException("The argument (movie session) is null.");
    }

    @Override
    public MovieSession get(Long id) {
        if (id != null) {
            return movieSessionDao.get(id).orElseThrow(()
                    -> new DataProcessingException("Can't get movie session by id: " + id));
        }
        throw new DataProcessingException("The argument (id) is null.");
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        if (movieId != null && date != null) {
            return movieSessionDao.findAvailableSessions(movieId, date);
        }
        throw new DataProcessingException("The argument (movieID: " + movieId + " or date: "
                + date + ") is null.");
    }
}
