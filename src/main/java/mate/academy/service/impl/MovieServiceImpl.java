package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.MovieDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.Movie;
import mate.academy.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {
    private static final String CANT_GET_MOVIE_EXCEPTION_MESSAGE =
            "Can't get Movie with id: ";
    @Inject
    private MovieDao movieDao;

    @Override
    public Movie add(Movie movie) {
        return movieDao.add(movie);
    }

    @Override
    public Movie get(Long id) {
        return movieDao.get(id).orElseThrow(() ->
                new RuntimeException(CANT_GET_MOVIE_EXCEPTION_MESSAGE + id));
    }

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }
}
