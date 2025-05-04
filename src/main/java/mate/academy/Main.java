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
    private static final String PURPLE_COLOUR_FOR_LOGGING = "\u001B[35m";
    private static final String RESET_COLOUR = "\u001B[0m";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final int BIG_CINEMA_HALL_CAPACITY = 100;
    private static final int SMALL_CINEMA_HALL_CAPACITY = 25;
    private static final int AMOUNT_OF_DAYS_IN_WEEK = 7;

    public static void main(String[] args) {
        //TEST MOVIE
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(PURPLE_COLOUR_FOR_LOGGING
                + LINE_SEPARATOR + "Fast and Furious successfully added to DB");
        System.out.println(
                LINE_SEPARATOR + "Get Fast and Furious from DB" + LINE_SEPARATOR + RESET_COLOUR);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie transformers = new Movie("Transformers");
        transformers.setDescription("A film about robots capable of being "
                + "either a humanoid robot or transport");
        movieService.add(transformers);
        System.out.println(PURPLE_COLOUR_FOR_LOGGING + LINE_SEPARATOR
                + "Transformers successfully added to DB");
        System.out.println(
                LINE_SEPARATOR + "Get Transformers from DB" + LINE_SEPARATOR + RESET_COLOUR);
        System.out.println(movieService.get(transformers.getId()));

        System.out.println(PURPLE_COLOUR_FOR_LOGGING + LINE_SEPARATOR + "Get all movies from DB"
                + LINE_SEPARATOR + RESET_COLOUR);
        movieService.getAll().forEach(System.out::println);

        //TEST CINEMA HALL
        CinemaHallService cinemaHallService = (CinemaHallService) injector.getInstance(
                CinemaHallService.class);

        CinemaHall bigCinemaHall = new CinemaHall();
        bigCinemaHall.setCapacity(BIG_CINEMA_HALL_CAPACITY);
        bigCinemaHall.setDescription("A big cinema hall");
        cinemaHallService.add(bigCinemaHall);
        System.out.println(PURPLE_COLOUR_FOR_LOGGING
                + LINE_SEPARATOR + "A big cinema hall successfully added to DB");
        System.out.println(
                LINE_SEPARATOR + "Get the big cinema hall from DB" + LINE_SEPARATOR + RESET_COLOUR);
        System.out.println(cinemaHallService.get(bigCinemaHall.getId()));

        CinemaHall smallCinemaHall = new CinemaHall();
        smallCinemaHall.setCapacity(SMALL_CINEMA_HALL_CAPACITY);
        smallCinemaHall.setDescription("A small cinema hall");
        cinemaHallService.add(smallCinemaHall);
        System.out.println(PURPLE_COLOUR_FOR_LOGGING
                + LINE_SEPARATOR + "A small cinema hall successfully added to DB");
        System.out.println(
                LINE_SEPARATOR + "Get the small cinema hall from DB" + LINE_SEPARATOR
                        + RESET_COLOUR);
        System.out.println(cinemaHallService.get(smallCinemaHall.getId()));

        System.out.println(PURPLE_COLOUR_FOR_LOGGING + LINE_SEPARATOR
                + "Get all cinema halls from DB" + LINE_SEPARATOR + RESET_COLOUR);
        cinemaHallService.getAll().forEach(System.out::println);

        //TEST movie session
        LocalDateTime now = LocalDateTime.now();
        MovieSession movieSessionNow = new MovieSession();
        movieSessionNow.setMovie(fastAndFurious);
        movieSessionNow.setCinemaHall(bigCinemaHall);
        movieSessionNow.setShowTime(now);

        LocalDateTime sevenDaysFromNow = LocalDateTime.now().plusDays(AMOUNT_OF_DAYS_IN_WEEK);
        MovieSession movieSessionThen = new MovieSession();
        movieSessionThen.setMovie(transformers);
        movieSessionThen.setCinemaHall(smallCinemaHall);
        movieSessionThen.setShowTime(sevenDaysFromNow);

        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);

        movieSessionService.add(movieSessionNow);
        System.out.println(PURPLE_COLOUR_FOR_LOGGING
                + LINE_SEPARATOR + "A current time movie session successfully added to DB");
        System.out.println(
                LINE_SEPARATOR + "Get the current time movie session from DB"
                        + LINE_SEPARATOR + RESET_COLOUR);
        System.out.println(movieSessionService.get(movieSessionNow.getId()));

        movieSessionService.add(movieSessionThen);
        System.out.println(PURPLE_COLOUR_FOR_LOGGING
                + LINE_SEPARATOR + "A next week movie session successfully added to DB");
        System.out.println(
                LINE_SEPARATOR + "Get the next week movie from DB"
                        + LINE_SEPARATOR + RESET_COLOUR);
        System.out.println(movieSessionService.get(movieSessionThen.getId()));

        System.out.println(PURPLE_COLOUR_FOR_LOGGING
                + "Show available 'Fast and Furious' session for now" + RESET_COLOUR);
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.from(now)));
        System.out.println(LINE_SEPARATOR + PURPLE_COLOUR_FOR_LOGGING
                + "Show available 'Transformers' session for the same weekday next week"
                + RESET_COLOUR);
        System.out.println(movieSessionService.findAvailableSessions(transformers.getId(),
                LocalDate.from(sevenDaysFromNow)));
    }
}
