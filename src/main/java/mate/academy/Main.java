package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println("Fast and Furious info");
        System.out.println(movieService.get(fastAndFurious.getId()));
        Movie scarface = new Movie("Scarface");
        scarface.setDescription("Scarface description");
        movieService.add(scarface);
        Movie terminator = new Movie("Terminator");
        terminator.setDescription("Terminator description");
        movieService.add(terminator);
        System.out.println("All movies list:");
        movieService.getAll().forEach(System.out::println);
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall redCinemaHall = new CinemaHall();
        redCinemaHall.setCapacity(50);
        redCinemaHall.setDescription("Red movie hall, medium");
        cinemaHallService.add(redCinemaHall);
        System.out.println("Red movie hall info:");
        System.out.println(cinemaHallService.get(redCinemaHall.getId()));
        CinemaHall greenCinemaHall = new CinemaHall();
        greenCinemaHall.setCapacity(60);
        greenCinemaHall.setDescription("Green cinema hall, big");
        cinemaHallService.add(greenCinemaHall);
        CinemaHall blackCinemaHall = new CinemaHall();
        blackCinemaHall.setCapacity(20);
        blackCinemaHall.setDescription("Black cinema hall, small");
        cinemaHallService.add(blackCinemaHall);
        cinemaHallService.getAll().forEach(System.out::println);
        MovieSession scarfaceMovieSessionAt12 = new MovieSession();
        scarfaceMovieSessionAt12.setMovie(scarface);
        scarfaceMovieSessionAt12.setCinemaHall(greenCinemaHall);
        scarfaceMovieSessionAt12.setShowTime(LocalDateTime
                .of(2021, Month.OCTOBER, 28, 12, 0));
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(scarfaceMovieSessionAt12);
        System.out.println("Scarface movie session at 12:00 info");
        System.out.println(movieSessionService.get(scarfaceMovieSessionAt12.getId()));
        MovieSession scarfaceMovieSessionAt18 = new MovieSession();
        scarfaceMovieSessionAt18.setMovie(scarface);
        scarfaceMovieSessionAt18.setCinemaHall(blackCinemaHall);
        scarfaceMovieSessionAt18.setShowTime(LocalDateTime
                .of(2021, Month.OCTOBER, 28, 18, 30));
        movieSessionService.add(scarfaceMovieSessionAt18);
        MovieSession terminatorMovieSessionAt15 = new MovieSession();
        terminatorMovieSessionAt15.setMovie(terminator);
        terminatorMovieSessionAt15.setCinemaHall(redCinemaHall);
        terminatorMovieSessionAt15.setShowTime(LocalDateTime
                .of(2021, Month.OCTOBER, 28, 15, 15));
        movieSessionService.add(terminatorMovieSessionAt15);
        System.out.println("Get all movie session with scarface movie at 2021-10-28");
        movieSessionService.findAvailableSessions(scarface.getId(), LocalDate
                        .of(2021, Month.OCTOBER, 28))
                .forEach(System.out::println);
    }
}
