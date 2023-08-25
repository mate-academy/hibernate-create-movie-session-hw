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
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        //MOVIES
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie barbie = new Movie("The Barbie");
        barbie.setDescription("It's a really nice film for only being an example.");
        movieService.add(barbie);

        Movie wrongTurn = new Movie("The Wrong Turn");
        wrongTurn.setDescription("It's a definitely nice horror for the main class.");
        movieService.add(wrongTurn);

        Movie wrongTurn2 = new Movie("The Wrong Turn2");
        wrongTurn.setDescription("It's a definitely nice second part of "
                + "famous horror for the main class.");
        movieService.add(wrongTurn2);

        //CINEMA HALLS
        CinemaHallService cinemaHallService = (CinemaHallService) INJECTOR
                .getInstance(CinemaHallService.class);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("This is the premium cinema hall in the cinema");
        cinemaHall.setCapacity(100);
        cinemaHallService.add(cinemaHall);

        //CINEMA SESSIONS
        MovieSession todayMovieSession = new MovieSession();
        todayMovieSession.setMovie(barbie);
        todayMovieSession.setMovie(fastAndFurious);
        todayMovieSession.setCinemaHall(cinemaHall);
        todayMovieSession.setShowTime(LocalDateTime.now());

        MovieSession anotherMovieSession = new MovieSession();
        anotherMovieSession.setMovie(wrongTurn);
        anotherMovieSession.setMovie(wrongTurn2);
        anotherMovieSession.setMovie(barbie);
        anotherMovieSession.setCinemaHall(cinemaHall);
        anotherMovieSession.setShowTime(LocalDateTime.of(2023, 8, 27, 19, 30));

        MovieSessionService movieSessionService = (MovieSessionService) INJECTOR
                .getInstance(MovieSessionService.class);
        movieSessionService.add(todayMovieSession);
        movieSessionService.add(anotherMovieSession);
        movieSessionService.findAvailableSessions(1L, LocalDate.now()).forEach(System.out::println);
    }
}
