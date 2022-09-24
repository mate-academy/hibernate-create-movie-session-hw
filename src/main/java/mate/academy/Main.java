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
    private static final MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie theTomorrowWar = new Movie("The Tomorrow War");
        theTomorrowWar.setDescription("A family man is drafted to fight in a future war "
                + "where the fate of humanity relies on his ability to confront the past.");
        movieService.add(theTomorrowWar);
        System.out.println(movieService.get(theTomorrowWar.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall bigHall = new CinemaHall(300, "Big Hall");
        cinemaHallService.add(bigHall);
        CinemaHall smallThreeDimHall = new CinemaHall(100, "3D Hall");
        cinemaHallService.add(smallThreeDimHall);
        System.out.println(cinemaHallService.get(bigHall.getId()));
        System.out.println(cinemaHallService.get(smallThreeDimHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession morningSession1 = new MovieSession();
        morningSession1.setCinemaHall(bigHall);
        morningSession1.setMovie(fastAndFurious);
        morningSession1.setShowTime(LocalDateTime.of(2022, 10, 30, 10, 0));

        MovieSession eveningSession1 = new MovieSession();
        eveningSession1.setCinemaHall(smallThreeDimHall);
        eveningSession1.setMovie(theTomorrowWar);
        eveningSession1.setShowTime(LocalDateTime.of(2022, 10, 30, 18, 0));
        movieSessionService.add(morningSession1);
        movieSessionService.add(eveningSession1);
        System.out.println(movieSessionService.get(eveningSession1.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, 10, 30)).forEach(System.out::println);
    }
}
