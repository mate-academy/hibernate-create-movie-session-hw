package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

        //services initializing
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        //models creating
        List<Movie> movies = injectMovies();
        List<CinemaHall> cinemaHalls = injectCinemaHalls();
        List<MovieSession> movieSessions = injectMovieSessions(movies, cinemaHalls);

        //adding to DB
        movies.forEach(movieService::add);
        cinemaHalls.forEach(cinemaHallService::add);
        movieSessions.forEach(movieSessionService::add);

        //getting all from DB
        System.out.println("All movies:");
        movieService.getAll().forEach(System.out::println);
        System.out.println("\nMovie with id 1: " + movieService.get(1L));
        System.out.println("Movie with id 2: " + movieService.get(2L));

        System.out.println("\nAll cinema halls:");
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("\nCinema hall with id 1: " + cinemaHallService.get(1L));
        System.out.println("Cinema hall with id 2: " + cinemaHallService.get(2L));

        System.out.println("\nMovie session with id 1:");
        System.out.println(movieSessionService.get(1L));
        System.out.println("Movie session with id 8:");
        System.out.println(movieSessionService.get(8L));

        System.out.println("\nMovie sessions of movie with id 1 on 08/24:");
        List<MovieSession> availableSessions20210824 = movieSessionService
                .findAvailableSessions(1L, LocalDate.of(2021, 8, 24));
        availableSessions20210824.forEach(System.out::println);

        System.out.println("\nMovie sessions of movie with id 2 on 08/25:");
        List<MovieSession> availableSessions20210825 = movieSessionService
                .findAvailableSessions(2L, LocalDate.of(2021, 8, 25));
        availableSessions20210825.forEach(System.out::println);
    }

    private static List<Movie> injectMovies() {
        Movie fromDuskTillDawn = new Movie("From Dusk till Dawn");
        fromDuskTillDawn.setDescription("The story about criminal brothers who take a family as "
                + " hostages in order to cross into Mexico, but ultimately find themselves trapped"
                + " in a saloon frequented by vampires");

        Movie pulpFiction = new Movie("Pulp Fiction");
        pulpFiction.setDescription("The movie tells several stories of criminal Los Angeles.");

        return List.of(fromDuskTillDawn, pulpFiction);
    }

    private static List<CinemaHall> injectCinemaHalls() {
        CinemaHall alpha = new CinemaHall();
        alpha.setCapacity(300);
        alpha.setDescription("The hall for 2D and 3D movies");

        CinemaHall vip = new CinemaHall();
        vip.setCapacity(12);
        vip.setDescription("VIP hall for limited quantity of visitors. There are comfortable "
                + "leather armchairs and a possibility to call the waiter and order champagne.");

        return List.of(alpha, vip);
    }

    private static List<MovieSession> injectMovieSessions(List<Movie> movies,
                                                          List<CinemaHall> cinemaHalls) {
        // 08/24/2021 - ALPHA
        MovieSession fromDuskTillDawnAlpha202108241500 = new MovieSession();
        fromDuskTillDawnAlpha202108241500.setMovie(movies.get(0));
        fromDuskTillDawnAlpha202108241500.setCinemaHall(cinemaHalls.get(0));
        fromDuskTillDawnAlpha202108241500.setShowTime(LocalDateTime.of(2021, 8, 24, 15, 0));

        MovieSession fromDuskTillDawnAlpha202108241845 = new MovieSession();
        fromDuskTillDawnAlpha202108241845.setMovie(movies.get(0));
        fromDuskTillDawnAlpha202108241845.setCinemaHall(cinemaHalls.get(0));
        fromDuskTillDawnAlpha202108241845.setShowTime(LocalDateTime.of(2021, 8, 24, 18, 45));

        // 08/24/2021 - VIP
        MovieSession pulpFictionVip202108241030 = new MovieSession();
        pulpFictionVip202108241030.setMovie(movies.get(1));
        pulpFictionVip202108241030.setCinemaHall(cinemaHalls.get(1));
        pulpFictionVip202108241030.setShowTime(LocalDateTime.of(2021, 8, 24, 10, 30));

        MovieSession pulpFictionVip202108242010 = new MovieSession();
        pulpFictionVip202108242010.setMovie(movies.get(1));
        pulpFictionVip202108242010.setCinemaHall(cinemaHalls.get(1));
        pulpFictionVip202108242010.setShowTime(LocalDateTime.of(2021, 8, 24, 20, 10));

        // 08/25/2021 - ALPHA
        MovieSession fromDuskTillDawnAlpha202108251500 = new MovieSession();
        fromDuskTillDawnAlpha202108251500.setMovie(movies.get(0));
        fromDuskTillDawnAlpha202108251500.setCinemaHall(cinemaHalls.get(0));
        fromDuskTillDawnAlpha202108251500.setShowTime(LocalDateTime.of(2021, 8, 25, 15, 0));

        MovieSession fromDuskTillDawnAlpha202108251845 = new MovieSession();
        fromDuskTillDawnAlpha202108251845.setMovie(movies.get(0));
        fromDuskTillDawnAlpha202108251845.setCinemaHall(cinemaHalls.get(0));
        fromDuskTillDawnAlpha202108251845.setShowTime(LocalDateTime.of(2021, 8, 25, 18, 45));

        // 08/25/2021 - VIP
        MovieSession pulpFictionVip202108251030 = new MovieSession();
        pulpFictionVip202108251030.setMovie(movies.get(1));
        pulpFictionVip202108251030.setCinemaHall(cinemaHalls.get(1));
        pulpFictionVip202108251030.setShowTime(LocalDateTime.of(2021, 8, 25, 10, 30));

        MovieSession pulpFictionVip202108252010 = new MovieSession();
        pulpFictionVip202108252010.setMovie(movies.get(1));
        pulpFictionVip202108252010.setCinemaHall(cinemaHalls.get(1));
        pulpFictionVip202108252010.setShowTime(LocalDateTime.of(2021, 8, 25, 20, 10));

        return List.of(fromDuskTillDawnAlpha202108241500, fromDuskTillDawnAlpha202108241845,
                pulpFictionVip202108241030, pulpFictionVip202108242010,
                fromDuskTillDawnAlpha202108251500, fromDuskTillDawnAlpha202108251845,
                pulpFictionVip202108251030, pulpFictionVip202108252010);
    }
}
