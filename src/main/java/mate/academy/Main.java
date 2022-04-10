package mate.academy;

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
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie needForSpeed = new Movie("Need for speed");
        needForSpeed.setDescription("A film about racing");
        movieService.add(needForSpeed);
        System.out.println(movieService.get(needForSpeed.getId()));

        Movie theLastWitchHunter = new Movie(" The last witch hunter");
        theLastWitchHunter.setDescription("fantasy, action");
        movieService.add(theLastWitchHunter);
        System.out.println(movieService.get(theLastWitchHunter.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("4DX");
        cinemaHall.setCapacity(80);
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession fastAndFuriousMovieSession = new MovieSession();
        fastAndFuriousMovieSession.setMovie(fastAndFurious);
        fastAndFuriousMovieSession.setCinemaHall(cinemaHall);
        fastAndFuriousMovieSession.setShowTime(LocalDateTime.now().plusHours(1L));
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousMovieSession);

        MovieSession theLastWitchHunterMovieSession = new MovieSession();
        theLastWitchHunterMovieSession.setMovie(theLastWitchHunter);
        theLastWitchHunterMovieSession.setCinemaHall(cinemaHall);
        theLastWitchHunterMovieSession.setShowTime(LocalDateTime.now().minusHours(3L));
        movieSessionService.add(theLastWitchHunterMovieSession);
    }
}
