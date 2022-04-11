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
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        final CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        Movie hardNut = new Movie();
        hardNut.setTitle("a hard nut");
        hardNut.setDescription("film about New York City policeman John McClane");
        movieService.add(hardNut);
        CinemaHall hallNumberOne = new CinemaHall();
        hallNumberOne.setCapacity(50);
        hallNumberOne.setDescription("I MAX hall with DOLBY ATMOS AUDIO");
        cinemaHallService.add(hallNumberOne);
        cinemaHallService.getAll().forEach(System.out::println);

        final MovieSessionService movieSessionService = (MovieSessionService) injector
                 .getInstance(MovieSessionService.class);
        LocalDateTime timeHardNutSession = LocalDateTime.of(2022, 4, 12, 21, 35);
        MovieSession movieSessionHardNut = new MovieSession();
        movieSessionHardNut.setMovie(hardNut);
        movieSessionHardNut.setShowTime(timeHardNutSession);
        movieSessionHardNut.setCinemaHall(hallNumberOne);
        movieSessionService.add(movieSessionHardNut);
        System.out.println(movieSessionService.get(movieSessionHardNut.getId()));
        LocalDate localDate = LocalDate.of(2022, 4, 11);
        System.out.println(movieSessionService.findAvailableSessions(hardNut.getId(), localDate));
    }
}
