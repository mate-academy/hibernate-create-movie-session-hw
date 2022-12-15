package mate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.lib.Injector;
import mate.model.CinemaHall;
import mate.model.Movie;
import mate.model.MovieSession;
import mate.service.CinemaHallService;
import mate.service.MovieService;
import mate.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        MovieService movieService = (MovieService)injector
                .getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie superMan = new Movie("SuperMan Forever");
        superMan.setDescription("This film is about Hero and his adventure");
        movieService.add(superMan);

        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(superMan.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println("__________________________________");

        CinemaHallService cinemaHallService = (CinemaHallService)injector
                .getInstance(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("Red Room");
        cinemaHall.setCapacity(500);
        cinemaHallService.add(cinemaHall);

        // expected in result list ( after findAvailableSessions(1L,LocalDateTime.now()))
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService = (MovieSessionService)injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);

        // don't expect in result list
        movieSession.setMovie(superMan);
        cinemaHall.setDescription("Black Room");
        cinemaHallService.add(cinemaHall);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSession);

        // don't expect in result list( because LocalDateTime.now().minusDays(2L))
        movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.now().minusDays(2L));
        movieSessionService.add(movieSession);

        // expected in result list ( after findAvailableSessions(1L,LocalDateTime.now()))
        movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        cinemaHall.setDescription("White Room");
        cinemaHallService.add(cinemaHall);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.now().minusHours(6L));
        movieSessionService.add(movieSession);

        // don't expect in result list( because movieId = 2)
        movieSession = new MovieSession();
        movieSession.setMovie(superMan);
        movieSession.setShowTime(LocalDateTime.now().minusHours(2));
        movieSessionService.add(movieSession);

        System.out.println("____________________________________");
        System.out.println(movieSessionService.findAvailableSessions(1L, LocalDate.now()));
    }
}
