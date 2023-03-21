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

    private static MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);

    private static MovieSessionService sessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    private static CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {

        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        Movie imRobot = new Movie();
        imRobot.setTitle("I, Robot");
        imRobot.setDescription("In 2035, a technophobic cop investigates a crime that may have "
                + "been perpetrated by a robot, which leads to a larger threat "
                + "to humanity.");

        movieService.add(fastAndFurious);
        movieService.add(imRobot);

        CinemaHall red = new CinemaHall();
        red.setCapacity(100);
        red.setDescription("Red Cinema Hall");
        cinemaHallService.add(red);

        CinemaHall blue = new CinemaHall();
        blue.setCapacity(80);
        blue.setDescription("Blue Cinema Hall");
        cinemaHallService.add(blue);

        MovieSession movieSessionOne = new MovieSession();
        movieSessionOne.setMovie(movieService.get(1L));
        movieSessionOne.setCinemaHall(cinemaHallService.get(1L));
        movieSessionOne.setShowTime(LocalDateTime.parse("2023-03-21T21:15:00"));
        sessionService.add(movieSessionOne);

        MovieSession movieSessionTwo = new MovieSession();
        movieSessionTwo.setMovie(movieService.get(2L));
        movieSessionTwo.setCinemaHall(cinemaHallService.get(2L));
        movieSessionTwo.setShowTime(LocalDateTime.parse("2023-03-21T22:00:00"));
        sessionService.add(movieSessionTwo);

        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(imRobot.getId()));

        System.out.println("Information about all movies");
        movieService.getAll().forEach(System.out::println);

        System.out.println("Information about all Cinema Hall");
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("Information about all movie sessions");
        sessionService.getAll().forEach(System.out::println);

        System.out.println("Information about today movie sessions");
        sessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.parse("2023-03-21"))
                .forEach(System.out::println);
        sessionService
                .findAvailableSessions(imRobot.getId(), LocalDate.parse("2023-03-21"))
                .forEach(System.out::println);
    }
}
