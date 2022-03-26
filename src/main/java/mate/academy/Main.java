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
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall vipCinemaHall = new CinemaHall();
        vipCinemaHall.setCapacity(100);
        vipCinemaHall.setDescription("VIP hall");
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(vipCinemaHall);
        System.out.println(cinemaHallService.get(vipCinemaHall.getId()));

        CinemaHall threeDCinemaHall = new CinemaHall();
        threeDCinemaHall.setCapacity(200);
        threeDCinemaHall.setDescription("3D hall");
        cinemaHallService.add(threeDCinemaHall);
        System.out.println(cinemaHallService.get(threeDCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession sessionInVipHall = new MovieSession();
        sessionInVipHall.setMovie(fastAndFurious);
        sessionInVipHall.setCinemaHall(vipCinemaHall);
        sessionInVipHall.setShowTime(LocalDateTime
                .of(2022, 1, 22, 20, 30));
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(sessionInVipHall);
        System.out.println(movieSessionService.get(sessionInVipHall.getId()));

        MovieSession sessionInThreeDHall = new MovieSession();
        sessionInThreeDHall.setMovie(fastAndFurious);
        sessionInThreeDHall.setCinemaHall(threeDCinemaHall);
        sessionInThreeDHall.setShowTime(LocalDateTime
                .of(2022, 2, 22, 18, 20));
        movieSessionService.add(sessionInThreeDHall);
        System.out.println(movieSessionService.get(sessionInThreeDHall.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, 1, 22)).forEach(System.out::println);
    }
}
