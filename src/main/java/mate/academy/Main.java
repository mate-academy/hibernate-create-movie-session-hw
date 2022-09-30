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
    private static final Injector injector
            = Injector.getInstance("mate.academy");
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing");
        Movie harryPotter = new Movie("Harry Potter");
        harryPotter.setDescription("Is a series fantasy films");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.add(harryPotter);
        movieService.getAll().forEach(System.out::println);

        CinemaHall hall3D = new CinemaHall();
        hall3D.setCapacity(50);
        hall3D.setDescription("3D hall");
        CinemaHall vipHall = new CinemaHall();
        vipHall.setCapacity(10);
        vipHall.setDescription("VIP hall");
        cinemaHallService.add(hall3D);
        cinemaHallService.add(vipHall);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession morningSession = new MovieSession();
        morningSession.setCinemaHall(hall3D);
        morningSession.setMovie(harryPotter);
        morningSession.setShowTime(LocalDateTime.of(2022, 9,
                30, 8, 30));
        MovieSession daySession = new MovieSession();
        daySession.setCinemaHall(hall3D);
        daySession.setMovie(fastAndFurious);
        daySession.setShowTime(LocalDateTime.of(2022, 9,
                30, 13, 00));
        MovieSession eveningSession = new MovieSession();
        eveningSession.setCinemaHall(vipHall);
        eveningSession.setMovie(harryPotter);
        eveningSession.setShowTime(LocalDateTime.of(2022, 9,
                30, 17, 20));
        MovieSession nightSession = new MovieSession();
        nightSession.setCinemaHall(vipHall);
        nightSession.setMovie(fastAndFurious);
        nightSession.setShowTime(LocalDateTime.of(2022, 9,
                30, 22, 40));
        movieSessionService.add(morningSession);
        movieSessionService.add(daySession);
        movieSessionService.add(eveningSession);
        movieSessionService.add(nightSession);

        System.out.println(movieSessionService.get(daySession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(harryPotter.getId(),
                LocalDate.of(2022, 9, 30)));
    }
}
