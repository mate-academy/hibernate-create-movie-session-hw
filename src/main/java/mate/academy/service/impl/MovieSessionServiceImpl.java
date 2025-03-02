package mate.academy.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

@Service
public class MovieSessionServiceImpl implements MovieSessionService {
    public static final String THERE_IS_NO_MOVIE_SESSION_WITH_SUCH_ID =
            "There is no movie session with such id -> %d";
    @Inject
    private MovieSessionDao movieSessionDao;

    @Override
    public MovieSession add(MovieSession movieSession) {
        return movieSessionDao.add(movieSession);
    }

    @Override
    public MovieSession get(Long id) {
        return movieSessionDao.get(id).orElseThrow(() -> new EntityNotFoundException(
                THERE_IS_NO_MOVIE_SESSION_WITH_SUCH_ID.formatted(id)));
    }

    @Override
    public List<MovieSession> findAllAvailableSessions(Long movieId, LocalDate localDate) {
        return movieSessionDao.findAllAvailableSessions(movieId, localDate);
    }
}
