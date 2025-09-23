package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
import mate.academy.dao.AbstractDao;
import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;

@Dao
public class MovieSessionDaoImpl
        extends AbstractDao<MovieSession, Long>
        implements MovieSessionDao {

    public MovieSessionDaoImpl() {
        super(MovieSession.class);
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        return HibernateUtil.getSessionFactory().openSession().createQuery(
                        "SELECT ms FROM MovieSession ms "
                                + "WHERE function('DATE', ms.showTime) = :date "
                                + "AND ms.movie.id = :movieId "
                                + "ORDER BY ms.showTime ASC", MovieSession.class)
                .setParameter("date", date)
                .setParameter("movieId", movieId)
                .getResultList();
    }
}
