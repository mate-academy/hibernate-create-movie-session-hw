package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
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
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie pandaKungFu = new Movie("Panda Kung-Fu");
        pandaKungFu.setDescription("Funny cartoon about panda and kung-fu");
        movieService.add(pandaKungFu);
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall cinemaHall3 = new CinemaHall(30);
        cinemaHall3.setDescription("Hall #3 for 30 people");
        cinemaHallService.add(cinemaHall3);
        System.out.println(cinemaHallService.get(cinemaHall3.getId()));

        CinemaHall cinemaHall6 = new CinemaHall(15);
        cinemaHall6.setDescription("Hall #6 for 15 people");
        cinemaHallService.add(cinemaHall6);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        MovieSession ms01MarchAt21 = new MovieSession(LocalDateTime
                .of(2024, Month.MARCH, 1, 21, 0, 0));
        ms01MarchAt21.setMovie(fastAndFurious);
        ms01MarchAt21.setCinemaHall(cinemaHall3);
        movieSessionService.add(ms01MarchAt21);
        System.out.println(movieSessionService.get(ms01MarchAt21.getId()));

        MovieSession ms01MarchAt13 = new MovieSession(LocalDateTime
                .of(2024, Month.MARCH, 1, 13, 0, 0));
        ms01MarchAt13.setMovie(pandaKungFu);
        ms01MarchAt13.setCinemaHall(cinemaHall6);
        movieSessionService.add(ms01MarchAt13);

        MovieSession ms01MarchAt16 = new MovieSession(LocalDateTime
                .of(2024, Month.MARCH, 1, 16, 0, 0));
        ms01MarchAt16.setMovie(pandaKungFu);
        ms01MarchAt16.setCinemaHall(cinemaHall3);
        movieSessionService.add(ms01MarchAt16);

        MovieSession ms02MarchAt19 = new MovieSession(LocalDateTime
                .of(2024, Month.MARCH, 2, 19, 0, 0));
        ms02MarchAt19.setMovie(fastAndFurious);
        ms02MarchAt19.setCinemaHall(cinemaHall6);
        movieSessionService.add(ms02MarchAt19);
        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2024, Month.MARCH, 1));
        availableSessions.forEach(System.out::println);
    }
}
