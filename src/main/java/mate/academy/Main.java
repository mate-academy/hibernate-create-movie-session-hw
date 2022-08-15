package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final String TIME_ZONE = "UTC";
    private static final Injector instance = Injector.getInstance("mate.academy");
    private static final MovieService movieService = (MovieService) instance
            .getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService) instance
            .getInstance(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService) instance
            .getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        instanceOfCinemaHalls();
        instanceOfMovies();

        System.out.println("List of movies:");
        movieService.getAll().forEach(System.out::println);
        System.out.println("List of cinema halls:");
        cinemaHallService.getAll().forEach(System.out::println);

        // Fast and Furious in BigHall at 20:00 for four days
        addMovieToSessionFromDatetimeForDays(movieService.get(1L), cinemaHallService.get(1L),
                getDateInSpecificTimeZone("2022-09-10T20:00:00"), 4);
        // Fast and Furious in BigHall at 22:00 for six days
        addMovieToSessionFromDatetimeForDays(movieService.get(1L), cinemaHallService.get(1L),
                getDateInSpecificTimeZone("2022-09-10T22:00:00"), 6);
        // The Die Hard in MediumHall at 20:00 for four six
        addMovieToSessionFromDatetimeForDays(movieService.get(2L), cinemaHallService.get(2L),
                getDateInSpecificTimeZone("2022-09-10T20:00:00"), 6);
        // Intolerable Cruelty in SmallHall at 19:00 for six days
        addMovieToSessionFromDatetimeForDays(movieService.get(3L), cinemaHallService.get(3L),
                getDateInSpecificTimeZone("2022-09-10T19:00:00"), 6);
        // Intolerable Cruelty in BigHall at 22:00 for two days
        addMovieToSessionFromDatetimeForDays(movieService.get(3L), cinemaHallService.get(1L),
                getDateInSpecificTimeZone("2022-09-14T20:00:00"), 2);

        System.out.println("List of movie sessions:");
        movieSessionService.findAvailableSessions(1L,
                LocalDate.of(2022, 9, 12))
                .forEach(System.out::println);
    }
    
    private static void instanceOfCinemaHalls() {
        CinemaHall bigCinemaHall = new CinemaHall();
        bigCinemaHall.setCapacity(5000);
        bigCinemaHall.setDescription("Biggest cinema hall in Ukraine");
        cinemaHallService.add(bigCinemaHall);

        CinemaHall mediumCinemaHall = new CinemaHall();
        mediumCinemaHall.setCapacity(2000);
        mediumCinemaHall.setDescription("Not biggest, but best comfort cinema hall in Ukraine");
        cinemaHallService.add(mediumCinemaHall);

        CinemaHall smallCinemaHall = new CinemaHall();
        smallCinemaHall.setCapacity(250);
        smallCinemaHall.setDescription("Small cinema hall for almost family movie-day");
        cinemaHallService.add(smallCinemaHall);
    }

    private static void instanceOfMovies() {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie dieHard = new Movie("The Die Hard");
        dieHard.setDescription("Classic film about policeman and terrorists. Humor, guns.");
        movieService.add(dieHard);

        Movie intolerableCruelty = new Movie("Intolerable Cruelty");
        intolerableCruelty.setDescription("A beautiful gold digger matches wits with a shrewd "
                + "Beverly Hills divorce lawyer.");
        movieService.add(intolerableCruelty);
    }

    private static LocalDateTime getDateInSpecificTimeZone(String dateInString) {
        LocalDateTime parseLocalDate = LocalDateTime.parse(dateInString.substring(0, 19),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(parseLocalDate,
                ZoneId.of(TIME_ZONE));
        return zonedDateTime.withZoneSameInstant(ZoneId.of(TIME_ZONE)).toLocalDateTime();
    }

    private static void addMovieToSessionFromDatetimeForDays(
            Movie movie, CinemaHall cinemaHall, LocalDateTime start, int days) {
        List<MovieSession> movieSessions = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            movieSessions.add(new MovieSession(movie, cinemaHall, start.plusDays(i)));
        }
        movieSessions.forEach(movieSessionService::add);
    }
}
