package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie avatar = new Movie("Avatar");
        avatar.setDescription("About man in other planet");
        movieService.add(avatar);
        System.out.println(movieService.get(avatar.getId()));

        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("Hall number 1");
        cinemaHall.setCapacity(10);
        cinemaHallService.add(cinemaHall);

        CinemaHall cinemaHall2 = new CinemaHall();
        cinemaHall2.setDescription("Hall number 2");
        cinemaHall2.setCapacity(12);
        cinemaHallService.add(cinemaHall2);

        CinemaHall cinemaHall3 = new CinemaHall();
        cinemaHall3.setDescription("Hall number 3");
        cinemaHall3.setCapacity(8);
        cinemaHallService.add(cinemaHall3);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movieService.get(fastAndFurious.getId()));
        movieSession.setCinemaHall(cinemaHallService.get(cinemaHall.getId()));
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2022, 12, 2), LocalTime.now());
        movieSession.setShowTime(localDateTime);
        movieSessionService.add(movieSession);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(movieService.get(avatar.getId()));
        movieSession1.setCinemaHall(cinemaHallService.get(cinemaHall2.getId()));
        LocalDateTime localDateTime1 = LocalDateTime.of(LocalDate.of(2022, 12, 3), LocalTime.now());
        movieSession1.setShowTime(localDateTime1);
        movieSessionService.add(movieSession1);

        List<MovieSession> availableSessions =
                movieSessionService.findAvailableSessions(2L, LocalDate.of(2022, 12, 3));
        availableSessions.forEach(System.out::println);
    }
}
