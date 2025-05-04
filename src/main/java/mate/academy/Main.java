package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance(
            "mate.academy");
    private static final MovieService movieService = (MovieService)
            injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription(
                "An action film about street racing, heists, and spies.");
        Movie bigLebowski = new Movie("Big Lebowski");
        bigLebowski.setDescription("Where is my money, Lebowski?");
        movieService.add(fastAndFurious);
        movieService.add(bigLebowski);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(bigLebowski.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setDescription("We show bad films here");
        firstCinemaHall.setCapacity(25);
        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setDescription("We show great films here");
        secondCinemaHall.setCapacity(25);
        cinemaHallService.add(firstCinemaHall);
        cinemaHallService.add(secondCinemaHall);
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));
        System.out.println(cinemaHallService.get(secondCinemaHall.getId()));
        cinemaHallService.getALl().forEach(System.out::println);

        MovieSession goodTimeSpending = new MovieSession();
        goodTimeSpending.setMovie(bigLebowski);
        goodTimeSpending.setCinemaHall(secondCinemaHall);
        LocalDate bigLebowskiDate = LocalDate.of(2024, 4,25);
        LocalTime bigLebowskiTime = LocalTime.of(19, 00, 00);
        goodTimeSpending.setShowtime(LocalDateTime.of(bigLebowskiDate, bigLebowskiTime));
        movieSessionService.add(goodTimeSpending);
        System.out.println(movieSessionService.get(goodTimeSpending.getId()));
        movieSessionService.findAvailableSessions(goodTimeSpending.getId(), bigLebowskiDate)
                .forEach(System.out::println);
        MovieSession badTimeSpending = new MovieSession();
        badTimeSpending.setMovie(fastAndFurious);
        badTimeSpending.setCinemaHall(firstCinemaHall);
        LocalDate fastAndFuriousDate = LocalDate.of(2024, 4,25);
        LocalTime fastAndFuriousTime = LocalTime.of(21, 00, 00);
        badTimeSpending.setShowtime(LocalDateTime.of(fastAndFuriousDate, fastAndFuriousTime));
        movieSessionService.add(badTimeSpending);
        movieSessionService.findAvailableSessions(badTimeSpending.getId(), fastAndFuriousDate)
                .forEach(System.out::println);
    }
}
