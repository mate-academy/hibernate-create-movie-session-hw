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
    public static final int FIRST_CINEMA_HALL_CAPACITY = 105;
    public static final int SECOND_CINEMA_HALL_CAPACITY = 130;
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        // Movie
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie fury = new Movie("Fury");
        fury.setDescription("An action film about war.");
        movieService.add(fury);
        Movie drive = new Movie("Drive");
        drive.setDescription("An action film about ryan gosling.");
        movieService.add(drive);
        Movie scarface = new Movie("Scarface");
        scarface.setDescription("An action film about crimes.");
        movieService.add(scarface);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        // Cinema Hall
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall cinemaHall1 = new CinemaHall();
        cinemaHall1.setDescription("Hall number one");
        cinemaHall1.setCapacity(FIRST_CINEMA_HALL_CAPACITY);
        cinemaHallService.add(cinemaHall1);
        CinemaHall cinemaHall2 = new CinemaHall();
        cinemaHall2.setDescription("Hall number two");
        cinemaHall2.setCapacity(SECOND_CINEMA_HALL_CAPACITY);
        cinemaHallService.add(cinemaHall2);
        System.out.println(cinemaHallService.get(cinemaHall2.getId()));
        System.out.println("All Cinema Halls: ");
        cinemaHallService.getAll().forEach(System.out::println);

        // Movie Session
        final MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(scarface);
        firstMovieSession.setCinemaHall(cinemaHall1);
        firstMovieSession.setShowTime(LocalDateTime.now().plusDays(2));
        movieSessionService.add(firstMovieSession);
        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(fastAndFurious);
        secondMovieSession.setCinemaHall(cinemaHall1);
        secondMovieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(secondMovieSession);
        MovieSession thirdMovieSession = new MovieSession();
        thirdMovieSession.setMovie(scarface);
        thirdMovieSession.setCinemaHall(cinemaHall2);
        thirdMovieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(thirdMovieSession);

        System.out.println(movieSessionService.findAvailableSessions(scarface.getId(),
                LocalDate.now()));
    }
}
