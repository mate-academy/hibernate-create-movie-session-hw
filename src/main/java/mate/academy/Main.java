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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie oppenheimer = new Movie("Oppenheimer");
        oppenheimer.setDescription("Oppenheimer as he and his team race to develop the first"
                + " Nuclear weapon.");
        movieService.add(oppenheimer);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall redCinemaHall = new CinemaHall();
        redCinemaHall.setDescription("Red Cinema Hall");
        redCinemaHall.setCapacity(300);
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(redCinemaHall);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setCinemaHall(redCinemaHall);
        firstMovieSession.setMovie(fastAndFurious);
        firstMovieSession.setShowTime(LocalDateTime.of(2024, 9, 16, 18, 30));
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(firstMovieSession);

        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setCinemaHall(redCinemaHall);
        secondMovieSession.setMovie(oppenheimer);
        secondMovieSession.setShowTime(LocalDateTime.of(2024, 9, 16, 18, 30));
        movieSessionService.add(secondMovieSession);

        System.out.println(movieSessionService.findAvailableSessions(oppenheimer.getId(),
                LocalDate.now()));
    }
}
