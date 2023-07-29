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
        /*Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie terminator2 = new Movie("Terminator 2");
        terminator2.setDescription("The war between robots and humans continues.");
        movieService.add(fastAndFurious);
        movieService.add(terminator2);
        System.out.println(fastAndFurious.getId() + " - "
        + movieService.get(fastAndFurious.getId()));
        System.out.println(terminator2.getId() + " - "
        + movieService.get(terminator2.getId()));
        movieService.getAll().forEach(System.out::println);
        System.out.println("getByParam: " + movieService.getByParam("F"));*/

        Movie fastAndFuriousMovie = new Movie("Fast and Furious");
        fastAndFuriousMovie
                .setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFuriousMovie);
        System.out.println(movieService.get(fastAndFuriousMovie.getId()));

        Movie terminatorMovie = new Movie("Terminator 2");
        terminatorMovie.setDescription("The war between robots and humans continues.");
        movieService.add(terminatorMovie);
        System.out.println(movieService.get(terminatorMovie.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall smallCinemaHall = new CinemaHall();
        smallCinemaHall.setCapacity(80);
        smallCinemaHall.setDescription("Small hall");
        cinemaHallService.add(smallCinemaHall);
        System.out.println(cinemaHallService.get(smallCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        CinemaHall largeCinemaHall = new CinemaHall();
        largeCinemaHall.setCapacity(160);
        largeCinemaHall.setDescription("Large hall");
        cinemaHallService.add(largeCinemaHall);
        System.out.println(cinemaHallService.get(largeCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(fastAndFuriousMovie);
        firstMovieSession.setCinemaHall(largeCinemaHall);
        firstMovieSession.setShowTime(LocalDateTime
                .of(2023, 7, 30, 12, 0));
        movieSessionService.add(firstMovieSession);

        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(terminatorMovie);
        secondMovieSession.setCinemaHall(smallCinemaHall);
        secondMovieSession.setShowTime(LocalDateTime
                .of(2023, 7, 30, 14, 0));
        movieSessionService.add(secondMovieSession);

        MovieSession thirdMovieSession = new MovieSession();
        thirdMovieSession.setMovie(terminatorMovie);
        thirdMovieSession.setCinemaHall(largeCinemaHall);
        thirdMovieSession.setShowTime(LocalDateTime
                .of(2023, 7, 30, 16, 0));
        movieSessionService.add(thirdMovieSession);

        MovieSession fourthMovieSession = new MovieSession();
        fourthMovieSession.setMovie(fastAndFuriousMovie);
        fourthMovieSession.setCinemaHall(smallCinemaHall);
        fourthMovieSession.setShowTime(LocalDateTime
                .of(2023, 7, 30, 18, 0));
        movieSessionService.add(fourthMovieSession);

        System.out.println(movieSessionService.get(firstMovieSession.getId()));
        System.out.println(movieSessionService.get(secondMovieSession.getId()));
        System.out.println(movieSessionService.get(thirdMovieSession.getId()));
        System.out.println(movieSessionService.get(fourthMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(fastAndFuriousMovie
                .getId(), LocalDate.of(2023, 7, 30)));
    }
}
