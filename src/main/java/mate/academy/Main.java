package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        Movie lordOfTheRings = new Movie("The Lord of the Rings");
        lordOfTheRings.setDescription("А fantasy adventure film.");
        movieService.add(lordOfTheRings);
        System.out.println(movieService.get(lordOfTheRings.getId()));
        Movie taxi = new Movie("Taxi");
        taxi.setDescription("А comedy film about a taxi driver and his friends.");
        movieService.add(taxi);
        System.out.println(movieService.get(taxi.getId()));
        Movie rambo = new Movie("Rambo");
        rambo.setDescription("Series of action films");
        movieService.add(rambo);
        System.out.println(movieService.get(rambo.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHall_2D = new CinemaHall();
        cinemaHall_2D.setCapacity(100);
        cinemaHall_2D.setDescription("2D cinema hall");
        cinemaHallService.add(cinemaHall_2D);
        System.out.println(cinemaHallService.get(cinemaHall_2D.getId()));
        CinemaHall cinemaHall_3D = new CinemaHall();
        cinemaHall_3D.setCapacity(200);
        cinemaHall_3D.setDescription("3D cinema hall");
        cinemaHallService.add(cinemaHall_3D);
        System.out.println(cinemaHallService.get(cinemaHall_3D.getId()));
        CinemaHall cinemaHall_IMAX = new CinemaHall();
        cinemaHall_IMAX.setCapacity(280);
        cinemaHall_IMAX.setDescription("IMAX cinema hall");
        cinemaHallService.add(cinemaHall_IMAX);
        System.out.println(cinemaHallService.get(cinemaHall_IMAX.getId()));
        CinemaHall cinemaHall_VIP = new CinemaHall();
        cinemaHall_VIP.setCapacity(50);
        cinemaHall_VIP.setDescription("VIP cinema hall");
        cinemaHallService.add(cinemaHall_VIP);
        System.out.println(cinemaHallService.get(cinemaHall_VIP.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession morning_MovieSession = new MovieSession();
        morning_MovieSession.setMovie(lordOfTheRings);
        morning_MovieSession.setCinemaHall(cinemaHall_IMAX);
        morning_MovieSession.setShowTime(LocalDateTime
                .of(2020, 10, 15, 10, 0));
        movieSessionService.add(morning_MovieSession);
        System.out.println(movieSessionService.get(morning_MovieSession.getId()));
        MovieSession day_MovieSession = new MovieSession();
        day_MovieSession.setMovie(fastAndFurious);
        day_MovieSession.setCinemaHall(cinemaHall_2D);
        day_MovieSession.setShowTime(LocalDateTime
                .of(2020, 10, 15, 12, 0));
        movieSessionService.add(day_MovieSession);
        System.out.println(movieSessionService.get(day_MovieSession.getId()));
        MovieSession afternoon_MovieSession = new MovieSession();
        afternoon_MovieSession.setMovie(taxi);
        afternoon_MovieSession.setCinemaHall(cinemaHall_2D);
        afternoon_MovieSession.setShowTime(LocalDateTime
                .of(2020, 11, 24, 14, 0));
        movieSessionService.add(afternoon_MovieSession);
        System.out.println(movieSessionService.get(afternoon_MovieSession.getId()));
        MovieSession evening_MovieSession = new MovieSession();
        evening_MovieSession.setMovie(rambo);
        evening_MovieSession.setCinemaHall(cinemaHall_VIP);
        evening_MovieSession.setShowTime(LocalDateTime
                .of(2020, 12, 3, 20, 0));
        movieSessionService.add(evening_MovieSession);
        System.out.println(movieSessionService.get(evening_MovieSession.getId()));
        MovieSession night_MovieSession = new MovieSession();
        night_MovieSession.setMovie(fastAndFurious);
        night_MovieSession.setCinemaHall(cinemaHall_VIP);
        night_MovieSession.setShowTime(LocalDateTime
                .of(2020, 10, 15, 23, 0));
        movieSessionService.add(night_MovieSession);
        System.out.println(movieSessionService.get(night_MovieSession.getId()));

        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2020, 10, 15)));
    }
}
