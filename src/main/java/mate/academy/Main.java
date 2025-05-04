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

        Movie fastAndFurious = new Movie("Mad Max: Fury Road");
        fastAndFurious.setDescription("An action film about post-apocalyptic");
        System.out.println(movieService.add(fastAndFurious));
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println(movieService.get(3L));

        CinemaHall cinemaHallFirst = new CinemaHall();
        cinemaHallFirst.setCapacity(70);
        cinemaHallFirst.setDescription("Pink Hall");
        CinemaHall cinemaHallSecond = new CinemaHall();
        cinemaHallSecond.setCapacity(90);
        cinemaHallSecond.setDescription("Blue Hall");

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        System.out.println(cinemaHallService.get(1L));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movieService.get(3L));
        movieSession.setCinemaHall(cinemaHallService.get(2L));
        movieSession.setShowTime(LocalDateTime
                .of(2023, 03, 06, 20, 30));
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);

        System.out.println(movieSessionService.get(2L));

        LocalDate localDate = LocalDate.now();
        movieSessionService.findAvailableSessions(3L, localDate).forEach(System.out::println);

    }
}
