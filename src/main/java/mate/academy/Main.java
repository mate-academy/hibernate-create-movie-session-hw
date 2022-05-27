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
        //****************************************
        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists and spies.");
        Movie taxi = new Movie();
        taxi.setTitle("Taxi");
        taxi.setDescription("An action film about street racing, police and humor.");
        //----------------------------------------
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.add(taxi);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(taxi.getId()));
        movieService.getAll().forEach(System.out::println);
        //****************************************
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(150);
        cinemaHall.setDescription("IMAX");
        CinemaHall cinemaHall2 = new CinemaHall();
        cinemaHall2.setCapacity(50);
        cinemaHall2.setDescription("CINEMAX");
        //----------------------------------------
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        cinemaHallService.add(cinemaHall2);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        //****************************************
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(taxi);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.now());
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(taxi);
        movieSession2.setCinemaHall(cinemaHall2);
        movieSession2.setShowTime(LocalDateTime.now().plusHours(3));
        //----------------------------------------
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.add(movieSession2);
        System.out.println(movieSessionService.get(movieSession.getId()));
        movieSessionService.findAvailableSession(2L, LocalDate.now()).forEach(System.out::println);
        movieSessionService.findAvailableSession(2L, LocalDate.now().plusDays(1))
                .forEach(System.out::println);
        //****************************************
    }
}
