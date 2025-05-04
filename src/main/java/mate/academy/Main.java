package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate");
        final MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie titanic = new Movie("Titanic");
        titanic.setDescription("Drama");
        movieService.add(titanic);

        Movie prestige = new Movie("Prestige");
        prestige.setDescription("Mysterious film.");
        movieService.add(prestige);

        CinemaHall cinemaHallSmall = new CinemaHall();
        cinemaHallSmall.setCapacity(100);
        cinemaHallSmall.setDescription("Small hall");
        cinemaHallService.add(cinemaHallSmall);

        CinemaHall cinemaHallMedium = new CinemaHall();
        cinemaHallMedium.setCapacity(150);
        cinemaHallMedium.setDescription("Medium hall");
        cinemaHallService.add(cinemaHallMedium);

        MovieSession movieSessionFast = new MovieSession();
        movieSessionFast.setMovie(fastAndFurious);
        movieSessionFast.setCinemaHall(cinemaHallSmall);
        movieSessionFast.setShowTime(LocalDateTime.parse("2022-10-25T18:00"));
        movieSessionService.add(movieSessionFast);

        MovieSession movieSessionTitanic = new MovieSession();
        movieSessionTitanic.setMovie(titanic);
        movieSessionTitanic.setCinemaHall(cinemaHallMedium);
        movieSessionTitanic.setShowTime(LocalDateTime.parse("2022-10-25T23:00"));
        movieSessionService.add(movieSessionTitanic);

        movieSessionFast.setCinemaHall(cinemaHallMedium);
        movieSessionFast.setShowTime(LocalDateTime.parse("2022-10-25T13:00"));
        movieSessionService.add(movieSessionFast);
        
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println(cinemaHallService.get(cinemaHallMedium.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        List<MovieSession> resultMovieSession = movieSessionService.findAvailableSessions(
                1L, LocalDate.parse("2022-10-25"));
        System.out.println(resultMovieSession);
    }
}
