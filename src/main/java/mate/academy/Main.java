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

        Movie trolls = new Movie("Trolls");
        trolls.setDescription("Trolls are small, colorful, perpetually-happy creatures who sing,"
                + " dance, and hug all day.");
        movieService.add(trolls);
        System.out.println(movieService.get(trolls.getId()));

        Movie harryPotter = new Movie("Harry Potter and the Goblet of Fire");
        harryPotter.setDescription("About Harry!");
        movieService.add(harryPotter);
        System.out.println(movieService.get(harryPotter.getId()));

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall cinemaHall1 = new CinemaHall(130, "Without 3D");
        cinemaHallService.add(cinemaHall1);
        System.out.println(cinemaHallService.get(cinemaHall1.getId()));

        CinemaHall cinemaHall2 = new CinemaHall(60, "With 3D");
        cinemaHallService.add(cinemaHall2);
        System.out.println(cinemaHallService.get(cinemaHall2.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        MovieSession session1 = new MovieSession(trolls, cinemaHall1);
        LocalDateTime showTime1 = LocalDateTime.now().plusHours(3);
        session1.setShowTime(showTime1);
        movieSessionService.add(session1);
        System.out.println(movieSessionService.get(session1.getId()));

        MovieSession session2 = new MovieSession(trolls, cinemaHall2);
        LocalDateTime showTime2 = LocalDateTime.now().plusDays(3);
        session2.setShowTime(showTime2);
        movieSessionService.add(session2);
        System.out.println(movieSessionService.get(session2.getId()));

        MovieSession session3 = new MovieSession(trolls, cinemaHall2);
        LocalDateTime showTime3 = LocalDateTime.now();
        session3.setShowTime(showTime3);
        movieSessionService.add(session3);
        System.out.println(movieSessionService.get(session3.getId()));
        movieSessionService.findAvailableSessions(trolls.getId(), LocalDate.now())
                .forEach(System.out::println);
        movieSessionService.findAvailableSessions(8L, LocalDate.now())
                .forEach(System.out::println);
    }
}
