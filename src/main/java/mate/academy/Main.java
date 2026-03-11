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
        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);

        Movie movie1 = new Movie();
        movie1.setTitle("Matrix");
        movie1.setDescription("Sci-fi action");
        movieService.add(movie1);

        Movie movie2 = new Movie();
        movie2.setTitle("Inception");
        movie2.setDescription("Mind-bending thriller");
        movieService.add(movie2);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);

        CinemaHall hall1 = new CinemaHall();
        hall1.setCapacity(100);
        hall1.setDescription("Big hall with 3D");
        cinemaHallService.add(hall1);

        CinemaHall hall2 = new CinemaHall();
        hall2.setCapacity(50);
        hall2.setDescription("Small cozy hall");
        cinemaHallService.add(hall2);

        MovieSession session1 = new MovieSession();
        session1.setMovie(movie1);
        session1.setCinemaHall(hall1);
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        session1.setShowtime(LocalDateTime.now().plusDays(1)
                .withHour(10).withMinute(0));
        movieSessionService.add(session1);

        MovieSession session2 = new MovieSession();
        session2.setMovie(movie1);
        session2.setCinemaHall(hall2);
        session2.setShowtime(LocalDateTime.now().plusDays(1)
                .withHour(14).withMinute(30));
        movieSessionService.add(session2);

        MovieSession session3 = new MovieSession();
        session3.setMovie(movie2);
        session3.setCinemaHall(hall1);
        session3.setShowtime(LocalDateTime.now().plusDays(1)
                .withHour(18).withMinute(0));
        movieSessionService.add(session3);

        System.out.println("All movies: " + movieService.getAll());
        System.out.println("All halls: " + cinemaHallService.getAll());

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(movie1.getId(), tomorrow);
        System.out.println("Available sessions for Matrix tomorrow: "
                + availableSessions);
    }
}
