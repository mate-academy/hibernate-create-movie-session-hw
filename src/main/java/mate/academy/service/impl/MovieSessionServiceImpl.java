package mate.academy.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.dao.MovieSessionDao;
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
        return movieSessionDao.add(movieSession);
    }

    @Override
    public MovieSession get(Long id) {
        return movieSessionDao.get(id);
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        List<MovieSession> movieSessionList = movieSessionDao.getAll();
        return movieSessionList.stream()
                .filter(ms -> ms.getId().equals(movieId)
                        && ms.getShowTime().getDayOfYear() == date.getDayOfYear())
                .collect(Collectors.toList());
    }
}
