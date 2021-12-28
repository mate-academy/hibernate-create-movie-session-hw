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
        Movie machoAndNerd = new Movie("Macho and nerd");
        machoAndNerd.setDescription("Film about two cops who infiltrated a school to prevent drug trafficking");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.add(machoAndNerd);

        CinemaHall bigHall = new CinemaHall();
        bigHall.setDescription("Big cinema hall");
        bigHall.setCapacity(150);
        CinemaHall smallHall = new CinemaHall();
        smallHall.setDescription("Small cinema hall");
        smallHall.setCapacity(50);
        CinemaHallService cinemaHallService = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(bigHall);
        cinemaHallService.add(smallHall);

        MovieSession action = new MovieSession();
        action.setMovie(fastAndFurious);
        action.setCinemaHall(bigHall);
        action.setShowTime(LocalDateTime.of(2021, 12, 27, 10, 0));
        MovieSession comedy = new MovieSession();
        comedy.setMovie(machoAndNerd);
        comedy.setCinemaHall(smallHall);
        comedy.setShowTime(LocalDateTime.of(2021, 12, 28, 15, 0));
        MovieSessionService movieSessionService = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(action);
        movieSessionService.add(comedy);

        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2021, 12, 27)));
    }
}
