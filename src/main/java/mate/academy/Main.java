package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import mate.academy.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public static void main(String[] args) {
        // Movie testing
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie movie = new Movie();
        movie.setTitle("The Matrix");
        movie.setDescription("A computer hacker learns from mysterious rebels about the true "
                + "nature of his reality and his role in the war against the controllers of it.");
        movieService.add(movie);

        System.out.println("Testing MovieService and MovieDao:");
        System.out.println("Added movie: " + movie);

        Movie retrievedMovie = movieService.get(movie.getId());
        System.out.println("Retrieved movie: " + retrievedMovie);

        List<Movie> allMovies = movieService.getAll();
        System.out.println("All movies: " + allMovies);
        System.out.println("------------------------");

        // CinemaHall testing
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setName("Red Hall");
        cinemaHall.setCapacity(100);
        cinemaHallService.add(cinemaHall);

        System.out.println("\nTesting CinemaHallService and CinemaHallDao:");
        System.out.println("Added cinema hall: " + cinemaHall);

        CinemaHall retrievedCinemaHall = null;
        try (Session session = sessionFactory.openSession()) {
            retrievedCinemaHall = session.get(CinemaHall.class, cinemaHall.getId());
            if (retrievedCinemaHall != null) { // Check if retrievedCinemaHall is not null
                Hibernate.initialize(retrievedCinemaHall.getMovieSessions());
            }
        } catch (Exception e) {
            throw new DataProcessingException("Error to avoid CinemaHall", e);
        }

        if (retrievedCinemaHall != null) {
            System.out.println("Retrieved cinema hall with initialized movieSessions: "
                    + retrievedCinemaHall);
        }

        List<CinemaHall> allCinemaHalls = cinemaHallService.getAll();
        System.out.println("All cinema halls: " + allCinemaHalls);
        System.out.println("------------------------");

        // MovieSession testing

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(retrievedMovie);
        movieSession.setCinemaHall(retrievedCinemaHall);
        String showTimeStr = "2023-12-25 10:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime showTime = LocalDateTime.parse(showTimeStr, formatter);
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSession.setShowTime(showTime);
        movieSessionService.add(movieSession);

        System.out.println("\nTesting MovieSessionService and MovieSessionDao:");
        System.out.println("Added movie session: " + movieSession);

        LocalDate date = LocalDate.parse("2023-12-25");
        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(retrievedMovie.getId(), date);
        //        System.out.println("Available sessions for movie "
        //        + retrievedMovie.getTitle() + " on "
        //                + date + ": " + availableSessions);
        System.out.println("------------------------");
    }
}
