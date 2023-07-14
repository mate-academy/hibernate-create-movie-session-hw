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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie anotherFilm = new Movie("SomeFilm");
        fastAndFurious.setDescription("Something happens in this film.");
        movieService.add(anotherFilm);
        System.out.println(movieService.get(anotherFilm.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall greenHall = new CinemaHall();
        greenHall.setCapacity(200);
        greenHall.setDescription("Green hall");
        cinemaHallService.add(greenHall);
        System.out.println(cinemaHallService.get(greenHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(fastAndFurious);
        firstMovieSession.setCinemaHall(greenHall);
        firstMovieSession.setShowTime(LocalDateTime.of(2023, 8, 12, 10, 0));

        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(anotherFilm);
        secondMovieSession.setCinemaHall(greenHall);
        secondMovieSession.setShowTime(LocalDateTime.of(2023, 8, 12, 14, 0));

        MovieSession thirdMovieSession = new MovieSession();
        thirdMovieSession.setMovie(fastAndFurious);
        thirdMovieSession.setCinemaHall(greenHall);
        thirdMovieSession.setShowTime(LocalDateTime.of(2023, 8, 12, 18, 0));

        MovieSession fourthMovieSession = new MovieSession();
        fourthMovieSession.setMovie(fastAndFurious);
        fourthMovieSession.setCinemaHall(greenHall);
        fourthMovieSession.setShowTime(LocalDateTime.of(2023, 8, 13, 10, 0));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        movieSessionService.add(firstMovieSession);
        movieSessionService.add(secondMovieSession);
        movieSessionService.add(thirdMovieSession);
        movieSessionService.add(fourthMovieSession);

        System.out.println(movieSessionService.get(firstMovieSession.getId()));
        System.out.println(movieSessionService.get(secondMovieSession.getId()));
        System.out.println(movieSessionService.get(thirdMovieSession.getId()));
        System.out.println(movieSessionService.get(fourthMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(
                fastAndFurious.getId(),
                LocalDate.of(2023, 8, 12)));
    }
}
