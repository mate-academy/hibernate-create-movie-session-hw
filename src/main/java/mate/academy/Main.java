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

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie wow = new Movie("World of Warcraft: The Beginning");
        wow.setDescription("A fantasy film about the war between humans and orcs");
        movieService.add(wow);
        System.out.println(movieService.get(wow.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall blueHall = new CinemaHall();
        blueHall.setCapacity(50);
        blueHall.setDescription("A movie screen for glasses-free 3D");
        cinemaHallService.add(blueHall);

        System.out.println(cinemaHallService.get(blueHall.getId()));

        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(200);
        redHall.setDescription("It is a large cinema hall");
        cinemaHallService.add(redHall);

        System.out.println(cinemaHallService.get(redHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        LocalDateTime firstTime = LocalDateTime.of(2021, 10, 26, 12, 0);
        MovieSession firstSession = new MovieSession();
        firstSession.setMovie(fastAndFurious);
        firstSession.setCinemaHall(blueHall);
        firstSession.setShowTime(firstTime);

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(firstSession);

        System.out.println(movieSessionService.get(firstSession.getId()));
        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.from(firstTime)));

        LocalDateTime secondTime = LocalDateTime.of(2021, 10, 27, 16, 0);
        MovieSession secondSession = new MovieSession();
        secondSession.setMovie(wow);
        secondSession.setCinemaHall(redHall);
        secondSession.setShowTime(secondTime);
        movieSessionService.add(secondSession);

        System.out.println(movieSessionService.get(secondSession.getId()));
        System.out.println(movieSessionService
                .findAvailableSessions(wow.getId(), LocalDate.from(secondTime)));
    }
}
