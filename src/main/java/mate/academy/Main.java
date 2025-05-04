package mate.academy;

import java.time.LocalDate;
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

        // 0. INJECTING
        final var cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final var movieService
                = (MovieService) injector.getInstance(MovieService.class);
        final var movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);

        // 1. CREATING
        // Movies
        Movie movie1 = new Movie();
        movie1.setTitle("The Godfather");
        movie1.setDescription("The Godfather");

        Movie movie2 = new Movie();
        movie2.setTitle("The Godfather");
        movie2.setDescription("The Godfather");

        Movie movie3 = new Movie();
        movie3.setTitle("The Godfather");
        movie3.setDescription("The Godfather");

        // CinemaHalls
        CinemaHall cinemaHall1 = new CinemaHall();
        cinemaHall1.setCapacity(100);
        cinemaHall1.setDescription("Hall 1");

        CinemaHall cinemaHall2 = new CinemaHall();
        cinemaHall2.setCapacity(100);
        cinemaHall2.setDescription("Hall 1");

        CinemaHall cinemaHall3 = new CinemaHall();
        cinemaHall3.setCapacity(100);
        cinemaHall3.setDescription("Hall 1");

        //MovieSessions
        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(movie1);
        movieSession1.setCinemaHall(cinemaHall1);
        movieSession1.setShowTime(2024, 12,1,10, 15);

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(movie2);
        movieSession2.setCinemaHall(cinemaHall2);
        movieSession2.setShowTime(2024, 12,1,10, 15);

        MovieSession movieSession3 = new MovieSession();
        movieSession3.setMovie(movie3);
        movieSession3.setCinemaHall(cinemaHall3);
        movieSession3.setShowTime(2024, 12,1,10, 15);

        // 2. ADDING
        // Movies
        movieService.add(movie1);
        movieService.add(movie2);
        movieService.add(movie3);

        // CinemaHalls
        cinemaHallService.add(cinemaHall1);
        cinemaHallService.add(cinemaHall2);
        cinemaHallService.add(cinemaHall3);

        //MovieSessions
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        movieSessionService.add(movieSession3);

        // 3. GETTING
        System.out.println("\n *** CREATE AND READ FUNCTIONS TEST *** \n");

        // Movies
        System.out.println(movieService.get(1L));
        System.out.println(movieService.get(2L));
        System.out.println(movieService.get(3L));

        // CinemaHalls
        System.out.println(cinemaHallService.get(1L));
        System.out.println(cinemaHallService.get(2L));
        System.out.println(cinemaHallService.get(3L));

        //MovieSessions
        System.out.println(movieSessionService.get(1L));
        System.out.println(movieSessionService.get(2L));
        System.out.println(movieSessionService.get(3L));

        System.out.println("\n ****** \n");

        // 4. UPDATING
        movie1.setTitle("The Godfather");
        movie1.setDescription("Cinema classics");
        movieService.update(movie1);

        movie2.setTitle("Up");
        movie2.setDescription("Touching");
        movieService.update(movie2);

        movie3.setTitle("Harold Crick");
        movie3.setDescription("Original");
        movieService.update(movie3);

        // CinemaHalls
        cinemaHall1.setCapacity(100);
        cinemaHall1.setDescription("Small Hall");
        cinemaHallService.update(cinemaHall1);

        cinemaHall2.setCapacity(200);
        cinemaHall2.setDescription("Medium Hall");
        cinemaHallService.update(cinemaHall2);

        cinemaHall3.setCapacity(300);
        cinemaHall3.setDescription("Large Hall");
        cinemaHallService.update(cinemaHall3);

        //MovieSessions
        movieSession1.setMovie(movie1);
        movieSession1.setCinemaHall(cinemaHall1);
        movieSession1.setShowTime(2024, 12,1,10, 15);
        movieSessionService.update(movieSession1);

        movieSession2.setMovie(movie2);
        movieSession2.setCinemaHall(cinemaHall2);
        movieSession2.setShowTime(2024, 12,2,10, 15);
        movieSessionService.update(movieSession2);

        movieSession3.setMovie(movie3);
        movieSession3.setCinemaHall(cinemaHall3);
        movieSession3.setShowTime(2024, 12,2,23, 15);
        movieSessionService.update(movieSession3);

        // 5. GETTING ALL
        System.out.println("\n *** UPDATE AND READ ALL FUNCTIONS TEST *** \n");

        // Movies
        System.out.println(movieService.getAll());

        // CinemaHalls
        System.out.println(cinemaHallService.getAll());

        System.out.println("\n ****** \n");

        // 6. GETTING BY DATE
        System.out.println("\n *** UPDATE AND READ WITH FILTER FUNCTIONS TEST *** \n");
        System.out.println(
                movieSessionService.findAvailableSessions(1L, LocalDate.parse("2024-12-01")));
    }
}
