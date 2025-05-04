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

        System.out.println("Creating movies:");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie homeAlone = new Movie("Home alone");
        homeAlone.setDescription("Best christmas movie");
        movieService.add(homeAlone);
        System.out.println(movieService.get(homeAlone.getId()));

        System.out.println("All movies:");
        movieService.getAll().forEach(System.out::println);

        System.out.println("Creating halls:");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall hall1 = new CinemaHall(100, "Hall #1");
        cinemaHallService.add(hall1);
        System.out.println(cinemaHallService.get(hall1.getId()));

        CinemaHall hall2 = new CinemaHall(150, "Hall # 2");
        cinemaHallService.add(hall2);
        System.out.println(cinemaHallService.get(hall2.getId()));

        CinemaHall hall3 = new CinemaHall(20, "VIP hall");
        cinemaHallService.add(hall3);
        System.out.println(cinemaHallService.get(hall3.getId()));

        System.out.println("All halls:");
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("Creating sessions:");
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession session1 = new MovieSession(fastAndFurious, hall1,
                LocalDateTime.of(2024,1,16,19,0,0));
        movieSessionService.add(session1);
        System.out.println(movieSessionService.get(session1.getId()));

        MovieSession session2 = new MovieSession(fastAndFurious, hall3,
                LocalDateTime.of(2024,1,16,22,0,0));
        movieSessionService.add(session2);
        System.out.println(movieSessionService.get(session2.getId()));

        MovieSession session3 = new MovieSession(homeAlone, hall2,
                LocalDateTime.of(2024,1,16,17,0,0));
        movieSessionService.add(session3);
        System.out.println(movieSessionService.get(session3.getId()));

        MovieSession session4 = new MovieSession(homeAlone, hall2,
                LocalDateTime.of(2024,1,17,17,0,0));
        movieSessionService.add(session4);
        System.out.println(movieSessionService.get(session4.getId()));

        MovieSession session5 = new MovieSession(fastAndFurious, hall1,
                LocalDateTime.of(2024,1,17,21,30,0));
        movieSessionService.add(session5);
        System.out.println(movieSessionService.get(session5.getId()));

        System.out.println("Find sessions:");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2024,1,16)).forEach(System.out::println);

        System.out.println("Find sessions:");
        movieSessionService.findAvailableSessions(homeAlone.getId(),
                LocalDate.of(2024,1,17)).forEach(System.out::println);

    }
}
