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
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast & Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println("Created movie: " + movieService.get(fastAndFurious.getId()));
        System.out.println("All movies: " + movieService.getAll());
        System.out.println("------------------------------------------------");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall hallOne = new CinemaHall();
        hallOne.setCapacity(50);
        hallOne.setDescription("IMAX Hall");
        cinemaHallService.add(hallOne);
        System.out.println("Created cinema hall: " + cinemaHallService.get(hallOne.getId()));
        System.out.println("All halls: " + cinemaHallService.getAll());
        System.out.println("------------------------------------------------");

        MovieSession session1 = new MovieSession();
        session1.setMovie(fastAndFurious);
        session1.setCinemaHall(hallOne);
        session1.setShowTime(LocalDateTime.now().plusDays(1).withHour(18).withMinute(0));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        movieSessionService.add(session1);

        MovieSession session2 = new MovieSession();
        session2.setMovie(fastAndFurious);
        session2.setCinemaHall(hallOne);
        session2.setShowTime(LocalDateTime.now().plusDays(1).withHour(21).withMinute(30));
        movieSessionService.add(session2);

        MovieSession session3 = new MovieSession();
        session3.setMovie(fastAndFurious);
        session3.setCinemaHall(hallOne);
        session3.setShowTime(LocalDateTime.now().plusDays(2).withHour(20).withMinute(0));
        movieSessionService.add(session3);

        LocalDate searchDate = LocalDate.now().plusDays(1);
        System.out.println("Available sessions for movie '" + fastAndFurious.getTitle()
                + "' on date " + searchDate + ":");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), searchDate)
                .forEach(System.out::println);
        System.out.println("------------------------------------------------");

        System.out.println("Get session by ID: " + movieSessionService.get(session1.getId()));
    }
}

