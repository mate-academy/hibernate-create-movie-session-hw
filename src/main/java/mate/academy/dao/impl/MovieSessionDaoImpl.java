package mate.academy.dao.impl;

import mate.academy.dao.MovieSessionDao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MovieSessionDaoImpl extends AbstractDao<MovieSession> implements MovieSessionDao {
    public MovieSessionDaoImpl() {
        super(MovieSession.class);
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        persistEntity(movieSession);
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        return findEntity(id);
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM MovieSession ms WHERE ms.id = :id AND DATE(ms.showTime) = :date";
            Query<MovieSession> query = session.createQuery(hql, MovieSession.class);
            query.setParameter("id", movieId);
            query.setParameter("date", date);
            return query.getResultList();
        }
    }
}
