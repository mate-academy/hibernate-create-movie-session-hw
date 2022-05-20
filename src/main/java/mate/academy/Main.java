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
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie theLostCity = new Movie("The Lost City");
        theLostCity.setDescription("A reclusive romance novelist on a book tour with her...");
        movieService.add(theLostCity);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall smallCinemaHall = new CinemaHall();
        smallCinemaHall.setCapacity(24);
        smallCinemaHall.setDescription("Small DX3D");
        cinemaHallService.add(smallCinemaHall);
        System.out.println(cinemaHallService.get(smallCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession movieSession = new MovieSession();
        LocalDateTime currentDate1 = LocalDateTime.of(2022,5,20,19,0);
        movieSession.setMovie(theLostCity);
        movieSession.setCinemaHall(smallCinemaHall);
        movieSession.setShowTime(currentDate1);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.findAvailableSessions(theLostCity.getId(),
                LocalDate.now()));
    }
}
