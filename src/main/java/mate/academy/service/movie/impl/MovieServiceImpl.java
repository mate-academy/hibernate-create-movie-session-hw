package mate.academy.service.movie.impl;

import java.util.List;
import mate.academy.dao.movie.MovieDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.Movie;
import mate.academy.service.movie.MovieService;

@Service
public class MovieServiceImpl implements MovieService {
    @Inject
    private MovieDao movieDao;

    @Override
    public Movie add(Movie movie) {
        return movieDao.add(movie);
    }

    @Override
    public Movie get(Long id) {
        return movieDao.get(id).orElseThrow(() -> new RuntimeException(
                "Can't get Movie with id: " + id + " from Dao."
        ));
    }

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }
}
