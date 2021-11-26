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
        MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie taxi3 = new Movie("Taxi 3");
        taxi3.setDescription("Awesome film.");
        movieService.add(taxi3);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall1 = new CinemaHall();
        cinemaHall1.setDescription("Hall 1");
        cinemaHall1.setCapacity(100);
        CinemaHall cinemaHall2 = new CinemaHall();
        cinemaHall2.setDescription("Hall 2");
        cinemaHall2.setCapacity(50);
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall1);
        cinemaHallService.add(cinemaHall2);

        MovieSession session1 = new MovieSession();
        session1.setCinemaHall(cinemaHall1);
        session1.setMovie(fastAndFurious);
        session1.setShowTime(LocalDateTime.now().plusHours(6));
        System.out.println(session1.getShowTime());

        MovieSession session2 = new MovieSession();
        session2.setCinemaHall(cinemaHall1);
        session2.setMovie(fastAndFurious);
        session2.setShowTime(LocalDateTime.now().plusDays(1));
        System.out.println(session2.getShowTime());

        MovieSession session3 = new MovieSession();
        session3.setCinemaHall(cinemaHall2);
        session3.setMovie(taxi3);
        session3.setShowTime(LocalDateTime.now().plusHours(1));
        System.out.println(session3.getShowTime());

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(session1);
        movieSessionService.add(session2);
        movieSessionService.add(session3);
        //System.out.println(movieSessionService.get(1L));
        movieSessionService.findAvailableSessions(1L, LocalDate.now()).forEach(System.out::println);
    }
}
