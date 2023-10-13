package mate.academy;

import java.time.LocalDate;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieService;
import mate.academy.service.impl.CinemaHallService;
import mate.academy.service.impl.MovieSessionService;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);
        CinemaHallService cinemaHallService =
                (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);
        MovieSessionService movieSessionService =
                (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);

        insertMovies(movieService);
        insertCinemaHalls(cinemaHallService);
        insertSessions(cinemaHallService, movieService, movieSessionService);

        System.out.println(movieService.get(2L));
        movieService.getAll().forEach(System.out::println);
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println(movieSessionService.get(1L));
        System.out.println(movieSessionService.findAvailableSessions(1L, LocalDate.now()));
    }

    private static void insertSessions(CinemaHallService cinemaHallService,
                                       MovieService movieService,
                                       MovieSessionService movieSessionService) {
        MovieSession session1 = new MovieSession();
        session1.setCinemaHall(cinemaHallService.get(1L));
        session1.setMovie(movieService.get(1L));
        session1.setShowTime(LocalDate.now().atTime(18, 0));
        movieSessionService.add(session1);

        MovieSession session2 = new MovieSession();
        session2.setCinemaHall(cinemaHallService.get(1L));
        session2.setMovie(movieService.get(1L));
        session2.setShowTime(LocalDate.now().atTime(20, 0));
        movieSessionService.add(session2);

        MovieSession session3 = new MovieSession();
        session3.setCinemaHall(cinemaHallService.get(2L));
        session3.setMovie(movieService.get(3L));
        session3.setShowTime(LocalDate.now().minusDays(2).atTime(22, 0));
        movieSessionService.add(session3);
    }

    private static void insertCinemaHalls(CinemaHallService cinemaHallService) {
        CinemaHall happyCinemaHall = new CinemaHall();
        happyCinemaHall.setCapacity(30);
        happyCinemaHall.setDescription("Cinema hall for children");
        cinemaHallService.add(happyCinemaHall);

        CinemaHall redCinemaHall = new CinemaHall();
        redCinemaHall.setCapacity(200);
        redCinemaHall.setDescription("It's the biggest cinema hall");
        cinemaHallService.add(redCinemaHall);

        CinemaHall blueCinemaHall = new CinemaHall();
        blueCinemaHall.setCapacity(50);
        blueCinemaHall.setDescription("It's small cinema hall");
        cinemaHallService.add(blueCinemaHall);
    }

    private static void insertMovies(MovieService movieService) {
        Movie harryPotter = new Movie("Harry Potter");
        harryPotter.setDescription("A film for children about wizards.");
        movieService.add(harryPotter);

        Movie titanic = new Movie("Titanic");
        titanic.setDescription("Epic romantic drama. Disaster movie.");
        movieService.add(titanic);

        Movie theDayAfterTomorrow = new Movie("The day after tomorrow");
        theDayAfterTomorrow.setDescription("Fantasy.Action.Thrill.Drama.Adventure.");
        movieService.add(theDayAfterTomorrow);
    }
}
