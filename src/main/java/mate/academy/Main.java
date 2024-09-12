package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("dd MMM yyyy HH:mm", Locale.ENGLISH);
    private static final LocalDate NECESSARY_DAY = LocalDate.of(2024, 9, 12);

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie johnCarter = new Movie("John Carter");
        johnCarter.setDescription("Civil War vet John Carter is transplanted to Mars");
        movieService.add(johnCarter);
        Movie greenBook = new Movie("Green Book");
        greenBook.setDescription("A working-class Italian-American bouncer becomes the driver "
                + "for an African-American classical pianist on a tour of venues through the 1960s"
                + " American South.");
        movieService.add(greenBook);
        movieService.get(fastAndFurious.getId());
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector.getInstance(
                CinemaHallService.class);
        CinemaHall imax = new CinemaHall(150, "IMAX");
        cinemaHallService.add(imax);
        CinemaHall driveIn = new CinemaHall(250,
                "A drive-in movie theater is an outdoor parking area");
        cinemaHallService.add(driveIn);
        CinemaHall multiplexes = new CinemaHall(75,
                "a cinema with several separate screens.");
        cinemaHallService.add(multiplexes);
        cinemaHallService.get(multiplexes.getId());
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService) injector.getInstance(
                MovieSessionService.class);
        MovieSession firstMovieSession = new MovieSession(fastAndFurious, imax,
                LocalDateTime.parse("12 Sep 2024 14:11", DATE_TIME_FORMATTER));
        movieSessionService.add(firstMovieSession);
        MovieSession secondMovieSession = new MovieSession(johnCarter, driveIn,
                LocalDateTime.parse("12 Sep 2024 15:12", DATE_TIME_FORMATTER));
        movieSessionService.add(secondMovieSession);
        MovieSession thridMovieSession = new MovieSession(greenBook, multiplexes,
                LocalDateTime.parse("13 Sep 2024 12:00", DATE_TIME_FORMATTER));
        movieSessionService.add(thridMovieSession);
        MovieSession fourthMovieSession = new MovieSession(fastAndFurious, multiplexes,
                LocalDateTime.parse("12 Sep 2024 12:00", DATE_TIME_FORMATTER));
        movieSessionService.add(fourthMovieSession);
        MovieSession fifthMovieSession = new MovieSession(fastAndFurious, multiplexes,
                LocalDateTime.parse("13 Sep 2024 13:00", DATE_TIME_FORMATTER));
        movieSessionService.add(fifthMovieSession);
        movieSessionService.get(firstMovieSession.getId());
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), NECESSARY_DAY)
                .forEach(System.out::println);
    }
}
