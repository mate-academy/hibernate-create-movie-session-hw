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
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);

        CinemaHall cinemaHall = new CinemaHall(300, "Black cinema hall");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(1L));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSessionTenOClock = new MovieSession();
        movieSessionTenOClock.setCinemaHall(cinemaHall);
        movieSessionTenOClock.setMovie(fastAndFurious);
        movieSessionTenOClock.setShowTime(LocalDateTime.of(2021, 04, 22, 10, 0));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSessionTenOClock);

        MovieSession movieSessionTwelveOClock = new MovieSession();
        movieSessionTwelveOClock.setCinemaHall(cinemaHall);
        movieSessionTwelveOClock.setMovie(fastAndFurious);
        movieSessionTwelveOClock.setShowTime(LocalDateTime.of(2021, 04, 22, 12, 0));
        movieSessionService.add(movieSessionTwelveOClock);

        MovieSession movieSessionTwoOClock = new MovieSession();
        movieSessionTwoOClock.setCinemaHall(cinemaHall);
        movieSessionTwoOClock.setMovie(fastAndFurious);
        movieSessionTwoOClock.setShowTime(LocalDateTime.of(2021, 04, 22, 14, 0));
        movieSessionService.add(movieSessionTwoOClock);

        MovieSession movieSessionAnotherDay = new MovieSession();
        movieSessionAnotherDay.setCinemaHall(cinemaHall);
        movieSessionAnotherDay.setMovie(fastAndFurious);
        movieSessionAnotherDay.setShowTime(LocalDateTime.of(2021, 04, 23, 14, 0));
        movieSessionService.add(movieSessionAnotherDay);

        movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.of(2021, 04, 22))
                .forEach(System.out::println);

    }
}
