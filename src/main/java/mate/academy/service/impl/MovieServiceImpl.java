package mate.academy.service.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.Movie;
import mate.academy.service.MovieService;

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
        Optional<Movie> movie = movieDao.get(id);
        if (movie.isPresent()) {
            return movie.get();
        }
        throw new RuntimeException("Movie with id " + id + " is not present");
    }

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }
}
