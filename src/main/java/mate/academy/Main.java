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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie wallStreetWolf = new Movie("Wall street wolf");
        wallStreetWolf.setDescription("Based on the true story of Jordan Belfort");
        movieService.add(wallStreetWolf);
        movieService.get(wallStreetWolf.getId());

        movieService.getAll().forEach(System.out::println);

        CinemaHall bigCinemaHall = new CinemaHall();
        bigCinemaHall.setCapacity(300);
        bigCinemaHall.setDescription("Big screen, tasty cola, fresh popcorn");
        cinemaHallService.add(bigCinemaHall);

        CinemaHall smallCinemaHall = new CinemaHall();
        smallCinemaHall.setCapacity(100);
        smallCinemaHall.setDescription("small hall with big screen and comfortable seats");
        cinemaHallService.add(smallCinemaHall);

        MovieSession daySessionWallStreetWolf = new MovieSession();
        daySessionWallStreetWolf.setMovie(wallStreetWolf);
        daySessionWallStreetWolf.setCinemaHall(bigCinemaHall);
        daySessionWallStreetWolf.setShowTime(LocalDateTime.of(2022, 10, 3, 12, 00));
        movieSessionService.add(daySessionWallStreetWolf);

        MovieSession eveningSessionWallStreetWolf = new MovieSession();
        eveningSessionWallStreetWolf.setMovie(wallStreetWolf);
        eveningSessionWallStreetWolf.setCinemaHall(smallCinemaHall);
        eveningSessionWallStreetWolf.setShowTime(LocalDateTime.of(2022, 10, 3, 20, 00));
        movieSessionService.add(eveningSessionWallStreetWolf);

        MovieSession daySessionFastAndFurious = new MovieSession();
        daySessionFastAndFurious.setMovie(fastAndFurious);
        daySessionFastAndFurious.setCinemaHall(smallCinemaHall);
        daySessionFastAndFurious.setShowTime(LocalDateTime.of(2022, 10, 3, 12, 00));
        movieSessionService.add(daySessionFastAndFurious);

        MovieSession eveningSessionFastAndFurious = new MovieSession();
        eveningSessionFastAndFurious.setMovie(fastAndFurious);
        eveningSessionFastAndFurious.setCinemaHall(bigCinemaHall);
        eveningSessionFastAndFurious.setShowTime(LocalDateTime.of(2022, 10, 3, 20, 00));
        movieSessionService.add(eveningSessionFastAndFurious);

        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, 10, 3)).forEach(System.out::println);
    }
}
