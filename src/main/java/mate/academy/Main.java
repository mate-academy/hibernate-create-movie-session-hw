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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService hallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService sessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie gameOfThrones = new Movie("Game Of Thrones");
        gameOfThrones.setDescription("Awesome fantasy movie");
        movieService.add(gameOfThrones);
        System.out.println(movieService.get(gameOfThrones.getId()));

        Movie harryPotterAndTheCursedChild =
                new Movie("Harry Potter and the Cursed Child");
        harryPotterAndTheCursedChild.setDescription("New film about Harry Potter");
        movieService.add(harryPotterAndTheCursedChild);
        System.out.println(movieService.get(harryPotterAndTheCursedChild.getId()));
        System.out.println("====All movies====");
        movieService.getAll().forEach(System.out::println);

        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(100);
        redHall.setDescription("Red hall");
        hallService.add(redHall);
        System.out.println(hallService.get(redHall.getId()));

        CinemaHall greenHall = new CinemaHall();
        greenHall.setCapacity(50);
        greenHall.setDescription("Green Hall");
        hallService.add(greenHall);
        System.out.println(hallService.get(greenHall.getId()));
        System.out.println("====All halls====");
        hallService.getAll().forEach(System.out::println);

        MovieSession fastAndFuriousFirstSession = new MovieSession();
        fastAndFuriousFirstSession.setMovie(fastAndFurious);
        fastAndFuriousFirstSession.setCinemaHall(greenHall);
        fastAndFuriousFirstSession.setLocalDateTime(
                LocalDateTime.of(2021, 6, 10, 16, 30));
        sessionService.add(fastAndFuriousFirstSession);
        System.out.println(sessionService.get(fastAndFurious.getId()));

        MovieSession gameOfThronesFirstSession = new MovieSession();
        gameOfThronesFirstSession.setMovie(gameOfThrones);
        gameOfThronesFirstSession.setCinemaHall(redHall);
        gameOfThronesFirstSession.setLocalDateTime(
                LocalDateTime.of(2021, 6, 15, 16, 30));
        sessionService.add(gameOfThronesFirstSession);
        System.out.println(sessionService.get(gameOfThronesFirstSession.getId()));

        MovieSession gameOfThronesSecondSession = new MovieSession();
        gameOfThronesSecondSession.setMovie(gameOfThrones);
        gameOfThronesSecondSession.setCinemaHall(redHall);
        gameOfThronesSecondSession.setLocalDateTime(
                LocalDateTime.of(2021, 6, 15, 18, 30));
        sessionService.add(gameOfThronesSecondSession);
        System.out.println(sessionService.get(gameOfThronesSecondSession.getId()));

        MovieSession harryPotterFirstSession = new MovieSession();
        harryPotterFirstSession.setMovie(harryPotterAndTheCursedChild);
        harryPotterFirstSession.setCinemaHall(greenHall);
        harryPotterFirstSession.setLocalDateTime(
                LocalDateTime.of(2021, 6, 15, 16, 30));
        sessionService.add(harryPotterFirstSession);
        System.out.println(sessionService.get(harryPotterFirstSession.getId()));

        MovieSession harryPotterSecondSession = new MovieSession();
        harryPotterSecondSession.setMovie(harryPotterAndTheCursedChild);
        harryPotterSecondSession.setCinemaHall(greenHall);
        harryPotterSecondSession.setLocalDateTime(
                LocalDateTime.of(2021, 6, 15, 18, 30));
        sessionService.add(harryPotterSecondSession);
        System.out.println(sessionService.get(harryPotterSecondSession.getId()));

        MovieSession harryPotterThirdSession = new MovieSession();
        harryPotterThirdSession.setMovie(harryPotterAndTheCursedChild);
        harryPotterThirdSession.setCinemaHall(greenHall);
        harryPotterThirdSession.setLocalDateTime(
                LocalDateTime.of(2022, 6, 16, 18, 30));
        sessionService.add(harryPotterThirdSession);
        System.out.println(sessionService.get(harryPotterThirdSession.getId()));

        System.out.println("====Testing findAvailableSessions=====");
        sessionService.findAvailableSessions(harryPotterAndTheCursedChild.getId(),
                LocalDate.of(2021, 6, 15)).forEach(System.out::println);

    }
}
