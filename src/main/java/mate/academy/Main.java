package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieService;
import mate.academy.service.impl.CinemaHallServicel;
import mate.academy.service.impl.MovieSessionService;

public class Main {
    public static void main(String[] args) {

        Injector injector = Injector.getInstance("mate.academy");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.getAll());

        CinemaHallServicel cinemaHallService = (CinemaHallServicel) injector
                .getInstance(CinemaHallServicel.class);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(50);
        cinemaHall.setDescription("A cinemaHall for 50 viewers.");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        System.out.println(cinemaHallService.getAll());

        MovieSession session = new MovieSession();
        LocalDateTime time = LocalDateTime.now();
        session.setMovie(fastAndFurious);
        session.setCinemaHall(cinemaHall);
        session.setShowTime(time);
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(session);
        System.out.println(movieSessionService.get(session.getId()));
        System.out.println(movieSessionService.findAvaibleSessions(fastAndFurious.getId(),
                LocalDate.from(time)));

    }
}
