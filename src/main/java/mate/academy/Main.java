package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    private static final Injector inject = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService
                = (MovieService) inject.getInstance(MovieService.class);
        Movie avengers = new Movie("Avengers", "First film of Avengers tetralogy");
        movieService.add(avengers);
        Movie avatar2 = new Movie("Avatar2", "Sequel of great film Avatar");
        movieService.add(avatar2);
        Movie starWars3 = new Movie("Star Wars Episode 3", "Third part of Star Wars series");
        movieService.add(starWars3);
        Movie titanic = new Movie("Titanic", "Dramatic popular love story film.");
        movieService.add(titanic);
        Movie avengersEndgame = new Movie("Avengers Endgame", "Last film of Avengers tetralogy");
        movieService.add(avengersEndgame);
        movieService.getAll().forEach(System.out::println);
        System.out.println();

        CinemaHallService cinemaHallService
                = (CinemaHallService) inject.getInstance(CinemaHallService.class);
        CinemaHall blackHall = new CinemaHall(200, "Big Black Hall");
        cinemaHallService.add(blackHall);
        CinemaHall greenHall = new CinemaHall(100, "Middle Green Hall");
        cinemaHallService.add(greenHall);
        CinemaHall redHall = new CinemaHall(30, "VIP Red Hall");
        cinemaHallService.add(redHall);
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println();

        MovieSessionService movieSessionService
                = (MovieSessionService) inject.getInstance(MovieSessionService.class);
        LocalDateTime firstTime = LocalDateTime.of(2021, 10, 1, 12, 30);
        LocalDateTime secondTime = LocalDateTime.of(2021, 10, 1, 15, 30);
        LocalDateTime thirdTime = LocalDateTime.of(2021, 10, 1, 19, 0);
        MovieSession session1 = new MovieSession(avengers, blackHall, firstTime);
        movieSessionService.add(session1);
        MovieSession session2 = new MovieSession(avengersEndgame, blackHall, secondTime);
        movieSessionService.add(session2);
        MovieSession session3 = new MovieSession(titanic, blackHall, thirdTime);
        movieSessionService.add(session3);
        MovieSession session4 = new MovieSession(starWars3, greenHall, firstTime);
        movieSessionService.add(session4);
        MovieSession session5 = new MovieSession(titanic, greenHall, thirdTime);
        movieSessionService.add(session5);
        MovieSession session6 = new MovieSession(avatar2, redHall, thirdTime);
        movieSessionService.add(session6);
        MovieSession session7 = new MovieSession(avengersEndgame, redHall, secondTime);
        movieSessionService.add(session7);
        List<MovieSession> availableSessions
                = movieSessionService.findAvailableSessions(avengersEndgame.getId(), secondTime.toLocalDate());
        availableSessions.forEach(System.out::println);
    }
}
