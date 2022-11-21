package mate.academy.service.impl;

import mate.academy.dao.CinemaHallDao;
import mate.academy.dao.GenericDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.CinemaHall;
import mate.academy.service.CinemaHallService;

@Service
public class CinemaHallServiceImpl extends GenericServiceImpl<CinemaHall>
        implements CinemaHallService {
    @Inject
    private CinemaHallDao cinemaHallDao;
    @Override
    protected GenericDao<CinemaHall> getDao() {
        return cinemaHallDao;
    }
}
