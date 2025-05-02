package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.MovieDao;
import mate.academy.dao.impl.MovieDaoImpl;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.Movie;
import mate.academy.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {
    @Inject
    private MovieDao movieDao = new MovieDaoImpl();

    @Override
    public Movie add(Movie movie) {
        return movieDao.add(movie);
    }

    @Override
    public Movie get(Long id) {
        Movie movie = new Movie();
        try {
            movie = movieDao.get(id).get();
        } catch (Exception e) {
            throw new DataProcessingException("Movie with id: "
                    + id + " dosen't exist in the database", e);
        }
        return movie;
    }

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }
}
