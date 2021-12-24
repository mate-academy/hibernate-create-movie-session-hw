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
    private static final MovieService movieService = (MovieService)
            injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService)
            injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService)
            injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie redNotice = new Movie("Red Notice");
        redNotice.setDescription("An FBI profiler pursuing the world's most wanted art thief "
                + "becomes his reluctant partner in crime to catch an elusive crook "
                + "who's always one step ahead.");
        movieService.add(redNotice);
        Movie extraction = new Movie("Extraction");
        extraction.setDescription("A hardened mercenary's mission "
                + "becomes a soul-searching race to survive when he's sent into Bangladesh "
                + "to rescue a drug lord's kidnapped son.");
        movieService.add(extraction);
        Movie theKing = new Movie("The King");
        theKing.setDescription("Wayward Prince Hal must turn from carouser to warrior king "
                + "as he faces hostilities from inside and outside the castle walls "
                + "in the battle for England.");
        movieService.add(theKing);
        System.out.println(movieService.get(theKing.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall smallCinemaHall = new CinemaHall(60);
        smallCinemaHall.setDescription("Small cinema hall");
        cinemaHallService.add(smallCinemaHall);
        CinemaHall bigCinemaHall = new CinemaHall(90);
        bigCinemaHall.setDescription("Big cinema hall");
        cinemaHallService.add(bigCinemaHall);
        System.out.println(cinemaHallService.get(bigCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstMovieSession = new MovieSession(LocalDateTime
                .of(2021, 10, 20, 11, 50));
        firstMovieSession.setMovie(theKing);
        firstMovieSession.setCinemaHall(smallCinemaHall);
        movieSessionService.add(firstMovieSession);
        MovieSession secondMovieSession = new MovieSession(LocalDateTime
                .of(2021, 10, 20, 15, 25));
        secondMovieSession.setMovie(redNotice);
        secondMovieSession.setCinemaHall(bigCinemaHall);
        movieSessionService.add(secondMovieSession);
        MovieSession thirdMovieSession = new MovieSession(LocalDateTime
                .of(2021, 10, 21, 19, 30));
        thirdMovieSession.setMovie(extraction);
        thirdMovieSession.setCinemaHall(bigCinemaHall);
        movieSessionService.add(thirdMovieSession);
        System.out.println(movieSessionService.get(thirdMovieSession.getId()));
        movieSessionService.findAvailableSessions(1L, LocalDate.of(2021, 10, 20))
                .forEach(System.out::println);
    }
}
