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
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (
                MovieService) injector.getInstance(MovieService.class);

        Movie movie1 = new Movie("Inception");
        movie1.setDescription("A mind-bending thriller by Christopher Nolan.");
        movie1 = movieService.add(movie1);

        Movie movie2 = new Movie("Avatar");
        movie2.setDescription("A sci-fi epic by James Cameron.");
        movie2 = movieService.add(movie2);

        Movie movie3 = new Movie("Joker");
        movie3.setDescription("Psychological drama with Joaquin Phoenix.");
        movie3 = movieService.add(movie3);

        Movie movie4 = new Movie("Dune");
        movie4.setDescription("Epic sci-fi saga based on Frank Herbert's novel.");
        movie4 = movieService.add(movie4);

        CinemaHallService cinemaHallService = (
                CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall hall1 = new CinemaHall();
        hall1.setCapacity(100);
        hall1.setDescription("Red Hall");
        hall1 = cinemaHallService.add(hall1);

        CinemaHall hall2 = new CinemaHall();
        hall2.setCapacity(80);
        hall2.setDescription("Green Hall");
        hall2 = cinemaHallService.add(hall2);

        MovieSessionService movieSessionService = (
                MovieSessionService) injector.getInstance(MovieSessionService.class);

        movieSessionService.add(createSession(movie1, hall1, "2025-04-01T14:00"));
        movieSessionService.add(createSession(movie1, hall2, "2025-04-01T18:00"));
        movieSessionService.add(createSession(movie2, hall1, "2025-04-02T15:00"));
        movieSessionService.add(createSession(movie3, hall2, "2025-04-01T20:00"));
        movieSessionService.add(createSession(movie4, hall1, "2025-04-03T13:00"));

        System.out.println("\nSeances for 'Inception' on 2025-04-01:");
        List<MovieSession> inceptionSessions = movieSessionService
                .findAvailableSessions(movie1.getId(), LocalDate.parse("2025-04-01"));
        inceptionSessions.forEach(System.out::println);

        System.out.println("\nSeances for 'Avatar' on 2025-04-02:");
        List<MovieSession> avatarSessions = movieSessionService
                .findAvailableSessions(movie2.getId(), LocalDate.parse("2025-04-02"));
        avatarSessions.forEach(System.out::println);
    }

    private static MovieSession createSession(Movie movie, CinemaHall hall, String dateTimeStr) {
        MovieSession session = new MovieSession();
        session.setMovie(movie);
        session.setCinemaHall(hall);
        session.setShowTime(LocalDateTime.parse(dateTimeStr));
        return session;
    }
}
