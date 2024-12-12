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
        return movieDao.add(movie);
    }

    @Override
    public Movie get(Long id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("Invalid cinema hall ID: " + id);
        }
        return movieDao
                .get(id)
                .orElseThrow(() ->
                new DataProcessingException("Movie service not found with ID:" + id));
    }

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }
}
