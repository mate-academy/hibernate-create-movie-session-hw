package mate.academy.service.impl;

import java.time.LocalDate;
import java.util.List;

import mate.academy.lib.Service;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

@Service
public class MovieSessionServiceImpl implements MovieSessionService {

    @Override
    public MovieSession add(MovieSession movieSession) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MovieSession get(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
    
//  Keep in mind that method `findAvailableSessions()` expects a `LocalDate` that represents the day 
//  chosen by a customer to visit our cinema, so your task here is to return all MovieSessions 
//  that will be running between 00:00 and 23:59:59 on that particular day.
    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        // TODO Auto-generated method stub
        return null;
    }
}
