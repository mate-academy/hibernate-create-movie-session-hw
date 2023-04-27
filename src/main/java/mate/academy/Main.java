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
        MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie fast5 = new Movie("Fast 5");
        fast5.setDescription("real fast 5");
        movieService.add(fastAndFurious);
        movieService.add(fast5);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(10);
        firstHall.setDescription("Comfort");
        CinemaHall secondHall = new CinemaHall();
        secondHall.setDescription("Lux");
        secondHall.setCapacity(20);
        cinemaHallService.add(firstHall);
        cinemaHallService.add(secondHall);
        System.out.println(cinemaHallService.get(1L));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession session1 = new MovieSession();
        session1.setShowTime(LocalDateTime.of(2023, 4, 27, 12, 30));
        session1.setCinemaHall(cinemaHallService.get(1L));
        session1.setMovie(movieService.get(1L));
        MovieSession session2 = new MovieSession();
        session2.setShowTime(LocalDateTime.of(2023, 4, 30, 12, 30));
        session2.setCinemaHall(cinemaHallService.get(2L));
        session2.setMovie(movieService.get(2L));
        movieSessionService.add(session1);
        movieSessionService.add(session2);
        movieSessionService.get(1L);
        System.out.println(movieSessionService
                .findAvailableSessions(1L, LocalDate.of(2023, 4, 27)));

    }
}
