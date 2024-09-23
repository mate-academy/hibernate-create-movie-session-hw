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

        Movie fastAndStupid = new Movie("Fast and Stupid");
        fastAndStupid.setDescription("An action comedy movie about romantic relationships");
        Movie javaMovie = new Movie("MyJavaJourney");
        javaMovie.setDescription("A thriller about setting out on a career in java");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall extraHall = new CinemaHall(11,"extraHall");
        cinemaHallService.add(extraHall);

        System.out.println("------cinemaHallService:get1L: " + cinemaHallService.get(1L));
        System.out.println("-------cinemaHallService:getAll(BEFORE CASCADES): "
                + cinemaHallService.getAll());

        MovieSession javaMovieSession = new MovieSession();
        javaMovieSession.setMovie(javaMovie);
        CinemaHall cinemaHallBig = new CinemaHall(100, "big");
        javaMovieSession.setCinemaHall(cinemaHallBig);
        javaMovieSession.setShowTime(LocalDateTime.of(2024,9,19,20,25));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(javaMovieSession);

        MovieSession fastAndStmovieSession = new MovieSession();
        fastAndStmovieSession.setMovie(fastAndStupid);
        CinemaHall cinemaHallSmall = new CinemaHall(20, "small");
        fastAndStmovieSession.setCinemaHall(cinemaHallSmall);
        fastAndStmovieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(fastAndStmovieSession);

        System.out.println("-------cinemaHallService:getAll(AFTER CASCADES): "
                + cinemaHallService.getAll());
        System.out.println("--------movieSerivce:get1L: " + movieService.get(1L));
        System.out.println("--------movieSerivce:get2L: " + movieService.get(2L));
        System.out.println("-------Today sessions (movie=Javamovie): "
                + movieSessionService.findAvailableSessions(2L, LocalDate.now()));
        System.out.println("-------Today sessions: (movie-fastandStupid)"
                + movieSessionService.findAvailableSessions(3L, LocalDate.now()));
        System.out.println("-------Today sessions(movie=fastandFurious): "
                + movieSessionService.findAvailableSessions(1L, LocalDate.now()));
        System.out.println("-------movieService:getALL: " + movieService.getAll());
    }
}
