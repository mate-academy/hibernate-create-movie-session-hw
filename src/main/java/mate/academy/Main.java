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

        Movie saw = new Movie("SAW");
        saw.setDescription("An action film about killed");
        movieService.add(saw);

        Movie mazeRunner = new Movie("Maze Runner");
        mazeRunner.setDescription("An action film about labyrinth");
        movieService.add(mazeRunner);

        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(30);
        redHall.setDescription("This one has Dolby Atoms sound and red color!");
        cinemaHallService.add(redHall);

        CinemaHall blueHall = new CinemaHall();
        blueHall.setCapacity(50);
        blueHall.setDescription("This hall has a very large screen and "
                + "more seats than any other hall!");
        cinemaHallService.add(blueHall);

        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession sessionOne = new MovieSession();
        sessionOne.setMovie(fastAndFurious);
        sessionOne.setCinemaHall(redHall);
        sessionOne.setShowTime(LocalDateTime.of(2023, 8, 17, 10, 0));
        MovieSessionService sessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        sessionService.add(sessionOne);

        MovieSession sessionTwo = new MovieSession();
        sessionTwo.setMovie(saw);
        sessionTwo.setCinemaHall(blueHall);
        sessionTwo.setShowTime(LocalDateTime.of(2023, 8, 17, 15, 30));
        sessionService.add(sessionTwo);

        MovieSession sessionThree = new MovieSession();
        sessionThree.setMovie(mazeRunner);
        sessionThree.setCinemaHall(redHall);
        sessionThree.setShowTime(LocalDateTime.of(2023, 8, 18, 16, 30));
        sessionService.add(sessionThree);

        MovieSession sessionFor = new MovieSession();
        sessionFor.setMovie(fastAndFurious);
        sessionFor.setCinemaHall(redHall);
        sessionFor.setShowTime(LocalDateTime.of(2023, 8, 17, 18, 30));
        sessionService.add(sessionFor);

        LocalDate date = LocalDate.of(2023, 8, 17);
        LocalDate date2 = LocalDate.of(2023, 8, 18);
        LocalDate date3 = LocalDate.of(2023, 8, 19);

        System.out.println("Valid day: " + sessionService.findAvailableSessions(1L, date));
        System.out.println("Invalid day: " + sessionService.findAvailableSessions(1L, date2));
        System.out.println("Valid day: " + sessionService.findAvailableSessions(2L, date));
        System.out.println("Valid day: " + sessionService.findAvailableSessions(3L, date2));
        System.out.println("Invalid day: " + sessionService.findAvailableSessions(3L, date3));

    }
}
