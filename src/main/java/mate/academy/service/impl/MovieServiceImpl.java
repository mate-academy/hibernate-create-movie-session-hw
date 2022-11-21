package mate.academy.service.impl;

import mate.academy.dao.GenericDao;
import mate.academy.dao.MovieDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.Movie;
import mate.academy.service.MovieService;

@Service
public class MovieServiceImpl extends GenericServiceImpl<Movie>
        implements MovieService {
    @Inject
    private MovieDao movieDao;

    @Override
    protected GenericDao<Movie> getDao() {
        return movieDao;
    }
}
