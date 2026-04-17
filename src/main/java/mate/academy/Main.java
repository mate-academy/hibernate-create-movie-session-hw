package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        movieService.add(fastAndFurious);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall goldHall = new CinemaHall();
        goldHall.setCapacity(100);
        goldHall.setDescription("Gold Hall");
        cinemaHallService.add(goldHall);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(goldHall);
        movieSession.setShowTime(LocalDateTime.now().plusDays(1));

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);

        System.out.println("Available sessions: "
                + movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                        LocalDate.now().plusDays(1)));
    }
}
