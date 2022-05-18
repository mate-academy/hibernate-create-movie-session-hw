package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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
        Movie theIceRoad = new Movie("The Ice Road");
        theIceRoad.setDescription("After a remote diamond mine collapses in far northern Canada, "
                + "a 'big-rig' ice road driver must lead an impossible rescue mission over "
                + "a frozen lake to save the trapped miners.");
        MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.add(theIceRoad);
        System.out.println(movieService.getAll());
        System.out.println(movieService.get(fastAndFurious.getId()));
        CinemaHall hall1 = new CinemaHall();
        hall1.setCapacity(100);
        hall1.setDescription("old hall");
        CinemaHall hall2 = new CinemaHall();
        hall2.setCapacity(200);
        hall2.setDescription("new hall");
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(hall1);
        cinemaHallService.add(hall2);
        cinemaHallService.getAll().forEach(System.out::println);
        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(hall1);
        movieSession1.setShowTime(LocalDateTime.of(2022, Month.JULY, 7, 12,0,0));
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(theIceRoad);
        movieSession2.setCinemaHall(hall2);
        movieSession2.setShowTime(LocalDateTime.of(2022, Month.MAY, 27, 22,0,0));
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        System.out.println(movieSessionService.get(movieSession1.getId()));
        System.out.println(movieSessionService.get(movieSession2.getId()));
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, Month.JULY, 7)));
        System.out.println(movieSessionService.findAvailableSessions(theIceRoad.getId(),
                LocalDate.of(2022, Month.MAY, 27)));
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, Month.MAY, 27)));
    }
}
