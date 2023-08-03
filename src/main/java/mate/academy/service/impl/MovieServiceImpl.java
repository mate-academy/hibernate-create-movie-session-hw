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
        if (movie.isEmpty()) {
            throw new RuntimeException("Can't get movie by id " + id);
        }
        return movie.get();
    }

    @Override
    public List<Movie> getAll() {
        List<Movie> movies = movieDao.getAll();
        if (movies.isEmpty()) {
            throw new RuntimeException("Can't get all movies.");
        }
        return movies;
    }
}
