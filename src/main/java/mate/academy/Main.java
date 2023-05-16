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

        // Movies
        Movie fastAndFurious = new Movie("Fast and Furious");
        //fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        fastAndFurious.setDescription("Fast and Furious description.");

        Movie theGodfather = new Movie("The Godfather");
        //theGodfather.setDescription("The Godfather \"Don\" Vito Corleone is the head of "
        //+ "the Corleone mafia family in New York. He is at the event of his "
        //+ "daughter's wedding.");
        theGodfather.setDescription("The Godfather description");

        Movie underTheSkin = new Movie("Under the Skin");
        //underTheSkin.setDescription("The story of an alien in human form.");
        underTheSkin.setDescription("Under the Skin description");

        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.add(theGodfather);
        movieService.add(underTheSkin);

        System.out.println(System.lineSeparator());
        System.out.println("Get movie:");
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(theGodfather.getId()));
        System.out.println(movieService.get(underTheSkin.getId()));
        System.out.println(System.lineSeparator());

        System.out.println("All movies:");
        movieService.getAll().forEach(System.out::println);
        System.out.println(System.lineSeparator());

        // Cinema halls
        CinemaHall spaciousHall = new CinemaHall();
        spaciousHall.setCapacity(179);
        //spaciousHall.setDescription("A spacious cinema hall with state-of-the-art facilities, "
        //+ "perfect for enjoying the latest blockbuster movies in comfort and style.");
        spaciousHall.setDescription("Spacious hall description.");

        CinemaHall intimateHall = new CinemaHall();
        intimateHall.setCapacity(60);
        //intimateHall.setDescription("An intimate cinema hall designed for film enthusiasts "
        //+ "seeking a cozy and immersive movie-watching experience.");
        intimateHall.setDescription("Intimate hall description.");

        CinemaHall modernHall = new CinemaHall();
        modernHall.setCapacity(105);
        //modernHall.setDescription("A modern cinema hall featuring cutting-edge technology "
        //+ "and a vibrant ambiance, providing an unforgettable cinematic journey for "
        //+ "movie lovers of all ages.");
        modernHall.setDescription("Modern hall description.");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(spaciousHall);
        cinemaHallService.add(intimateHall);
        cinemaHallService.add(modernHall);

        System.out.println("Get cinema hall:");
        System.out.println(cinemaHallService.get(spaciousHall.getId()));
        System.out.println(cinemaHallService.get(intimateHall.getId()));
        System.out.println(cinemaHallService.get(modernHall.getId()));
        System.out.println(System.lineSeparator());

        System.out.println("All cinema halls:");
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println(System.lineSeparator());

        // Movie sessions
        MovieSession firstMovieSessionOnFirstDay = new MovieSession();
        firstMovieSessionOnFirstDay.setMovie(fastAndFurious);
        firstMovieSessionOnFirstDay.setCinemaHall(spaciousHall);
        firstMovieSessionOnFirstDay.setShowTime(LocalDateTime.of(2023, 5, 8, 10, 0));

        MovieSession secondMovieSessionOnFirstDay = new MovieSession();
        secondMovieSessionOnFirstDay.setMovie(theGodfather);
        secondMovieSessionOnFirstDay.setCinemaHall(intimateHall);
        secondMovieSessionOnFirstDay.setShowTime(LocalDateTime.of(2023, 5, 8, 14, 0));

        MovieSession thirdMovieSessionOnFirstDay = new MovieSession();
        thirdMovieSessionOnFirstDay.setMovie(underTheSkin);
        thirdMovieSessionOnFirstDay.setCinemaHall(modernHall);
        thirdMovieSessionOnFirstDay.setShowTime(LocalDateTime.of(2023, 5, 8, 18, 0));

        MovieSession firstMovieSessionOnSecondDay = new MovieSession();
        firstMovieSessionOnSecondDay.setMovie(fastAndFurious);
        firstMovieSessionOnSecondDay.setCinemaHall(spaciousHall);
        firstMovieSessionOnSecondDay.setShowTime(LocalDateTime.of(2023, 5, 9, 10, 0));

        MovieSession secondMovieSessionOnSecondDay = new MovieSession();
        secondMovieSessionOnSecondDay.setMovie(underTheSkin);
        secondMovieSessionOnSecondDay.setCinemaHall(modernHall);
        secondMovieSessionOnSecondDay.setShowTime(LocalDateTime.of(2023, 5, 9, 14, 0));

        MovieSession thirdMovieSessionOnSecondDay = new MovieSession();
        thirdMovieSessionOnSecondDay.setMovie(fastAndFurious);
        thirdMovieSessionOnSecondDay.setCinemaHall(spaciousHall);
        thirdMovieSessionOnSecondDay.setShowTime(LocalDateTime.of(2023, 5, 9, 18, 0));

        MovieSession fourthMovieSessionOnSecondDay = new MovieSession();
        fourthMovieSessionOnSecondDay.setMovie(fastAndFurious);
        fourthMovieSessionOnSecondDay.setCinemaHall(modernHall);
        fourthMovieSessionOnSecondDay.setShowTime(LocalDateTime.of(2023, 5, 9, 22, 0));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(firstMovieSessionOnFirstDay);
        movieSessionService.add(secondMovieSessionOnFirstDay);
        movieSessionService.add(thirdMovieSessionOnFirstDay);
        movieSessionService.add(firstMovieSessionOnSecondDay);
        movieSessionService.add(secondMovieSessionOnSecondDay);
        movieSessionService.add(thirdMovieSessionOnSecondDay);
        movieSessionService.add(fourthMovieSessionOnSecondDay);

        System.out.println("Get movie session:");
        System.out.println(movieSessionService.get(firstMovieSessionOnFirstDay.getId()));
        System.out.println(movieSessionService.get(secondMovieSessionOnFirstDay.getId()));
        System.out.println(movieSessionService.get(thirdMovieSessionOnFirstDay.getId()));
        System.out.println(movieSessionService.get(firstMovieSessionOnSecondDay.getId()));
        System.out.println(movieSessionService.get(secondMovieSessionOnSecondDay.getId()));
        System.out.println(movieSessionService.get(thirdMovieSessionOnSecondDay.getId()));
        System.out.println(movieSessionService.get(fourthMovieSessionOnSecondDay.getId()));
        System.out.println(System.lineSeparator());

        // Find available sessions
        System.out.println("Find available session:");
        System.out.println("First day:");
        System.out.println(movieSessionService.findAvailableSessions(
                fastAndFurious.getId(),
                LocalDate.of(2023, 5, 8))); // 1 session
        System.out.println(movieSessionService.findAvailableSessions(
                theGodfather.getId(),
                LocalDate.of(2023, 5, 8))); // 1 session
        System.out.println(movieSessionService.findAvailableSessions(
                underTheSkin.getId(),
                LocalDate.of(2023, 5, 8))); // 1 session
        System.out.println(System.lineSeparator());
        System.out.println("Second day:");
        System.out.println(movieSessionService.findAvailableSessions(
                fastAndFurious.getId(),
                LocalDate.of(2023, 5, 9))); // 3 sessions
        System.out.println(movieSessionService.findAvailableSessions(
                underTheSkin.getId(),
                LocalDate.of(2023, 5, 9))); // 1 sessions
    }
}
