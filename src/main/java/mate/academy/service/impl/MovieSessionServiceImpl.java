package mate.academy.service.impl;

import java.time.LocalDate;
import java.util.List;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class MovieSessionServiceImpl implements MovieSessionService {
    private MovieSessionDao movieSessionDao;

    public MovieSessionServiceImpl() {
    }

    public MovieSessionDao getMovieSessionDao() {
        return movieSessionDao;
    }

    public void setMovieSessionDao(MovieSessionDao movieSessionDao) {
        this.movieSessionDao = movieSessionDao;
    }

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
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> getSessionQuery = session.createQuery("from Movie с where с.id = "
                    + ":id and current_date = :date", MovieSession.class);
            getSessionQuery.setParameter("id", movieId).setParameter("date", date);
            return getSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can`t get movie seesion", e);
        }
    }
}
