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
        final MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);

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

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(100);
        firstCinemaHall.setDescription("2D cinema hall");
        cinemaHallService.add(firstCinemaHall);
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));
        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(200);
        secondCinemaHall.setDescription("3D cinema hall");
        cinemaHallService.add(secondCinemaHall);
        System.out.println(cinemaHallService.get(secondCinemaHall.getId()));
        CinemaHall thirdCinemaHall = new CinemaHall();
        thirdCinemaHall.setCapacity(280);
        thirdCinemaHall.setDescription("IMAX cinema hall");
        cinemaHallService.add(thirdCinemaHall);
        System.out.println(cinemaHallService.get(thirdCinemaHall.getId()));
        CinemaHall fourthCinemaHall = new CinemaHall();
        fourthCinemaHall.setCapacity(50);
        fourthCinemaHall.setDescription("VIP cinema hall");
        cinemaHallService.add(fourthCinemaHall);
        System.out.println(cinemaHallService.get(fourthCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession morningMovieSession = new MovieSession();
        morningMovieSession.setMovie(lordOfTheRings);
        morningMovieSession.setCinemaHall(thirdCinemaHall);
        morningMovieSession.setShowTime(LocalDateTime
                .of(2020, 10, 15, 10, 0));
        movieSessionService.add(morningMovieSession);
        System.out.println(movieSessionService.get(morningMovieSession.getId()));
        MovieSession dayMovieSession = new MovieSession();
        dayMovieSession.setMovie(fastAndFurious);
        dayMovieSession.setCinemaHall(firstCinemaHall);
        dayMovieSession.setShowTime(LocalDateTime
                .of(2020, 10, 15, 12, 0));
        movieSessionService.add(dayMovieSession);
        System.out.println(movieSessionService.get(dayMovieSession.getId()));
        MovieSession afternoonMovieSession = new MovieSession();
        afternoonMovieSession.setMovie(taxi);
        afternoonMovieSession.setCinemaHall(firstCinemaHall);
        afternoonMovieSession.setShowTime(LocalDateTime
                .of(2020, 11, 24, 14, 0));
        movieSessionService.add(afternoonMovieSession);
        System.out.println(movieSessionService.get(afternoonMovieSession.getId()));
        MovieSession eveningMovieSession = new MovieSession();
        eveningMovieSession.setMovie(rambo);
        eveningMovieSession.setCinemaHall(secondCinemaHall);
        eveningMovieSession.setShowTime(LocalDateTime
                .of(2020, 12, 3, 20, 0));
        movieSessionService.add(eveningMovieSession);
        System.out.println(movieSessionService.get(eveningMovieSession.getId()));
        MovieSession nightMovieSession = new MovieSession();
        nightMovieSession.setMovie(fastAndFurious);
        nightMovieSession.setCinemaHall(fourthCinemaHall);
        nightMovieSession.setShowTime(LocalDateTime
                .of(2020, 10, 15, 23, 0));
        movieSessionService.add(nightMovieSession);
        System.out.println(movieSessionService.get(nightMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2020, 10, 15)));
    }
}
