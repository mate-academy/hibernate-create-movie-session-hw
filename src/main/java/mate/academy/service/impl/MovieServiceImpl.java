package mate.academy.service.impl;

import java.util.List;
import javax.persistence.Query;
import mate.academy.dao.MovieDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.Movie;
import mate.academy.service.MovieService;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;

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
        return movieDao.get(id).orElseThrow(() ->
                new RuntimeException("Can't get movie with id " + id));
    }

    @Override
    public List<Movie> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query getAllSmilesQuery = session.createQuery("FROM Movie", Movie.class);
            return getAllSmilesQuery.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Unable to get all movies", e);
        }
    }
}
