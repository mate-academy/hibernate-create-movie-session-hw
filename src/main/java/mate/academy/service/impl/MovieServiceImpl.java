package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.MovieDao;
import mate.academy.exception.DataProcessingException;
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
        if (movie != null) {
            return movieDao.add(movie);
        }
        throw new DataProcessingException("The argument (movie) is null.");
    }

    @Override
    public Movie get(Long id) {
        if (id != null) {
            return movieDao.get(id).orElseThrow(() ->
                    new DataProcessingException("Can't get movie by id, result is null. " + id));
        }
        throw new DataProcessingException("The argument (id) is null.");
    }

    @Override
    public List<Movie> getAll() {
        List<Movie> movies = movieDao.getAll();
        if (!movies.isEmpty()) {
            return movies;
        }
        throw new DataProcessingException("Can't get list of all movies, list is empty.");
    }
}
