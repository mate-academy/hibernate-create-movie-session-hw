package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");
    private static final int THIRD_ELEMENT = 2;
    private static final List<Movie> movies = List.of(
            new Movie("Avatar", "Alien world, human conflict"),
            new Movie("Titanic", "Love story on doomed ship"),
            new Movie("The Avengers", "Superheroes unite to save Earth"),
            new Movie("Harry Potter", "Magic, friendship, epic battles"),
            new Movie("The Lord of the Rings", "Epic quest to save Middle-earth"),
            new Movie("Fast and Furious", "An action film about street racing, heists, and spies")
    );
    private static final List<CinemaHall> cinemaHalls = List.of(
            new CinemaHall(30, "Standard 3D hall"),
            new CinemaHall(15, "Small 3D hall"),
            new CinemaHall(50, "Big 3D hall"),
            new CinemaHall(30, "Standard 2D hall"),
            new CinemaHall(10, "VIP 5D hall")
    );

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        for (Movie movie : movies) {
            System.out.println("Insert into movies: " + movieService.add(movie));
        }
        System.out.println("Movie by id 3: " + movieService.get(movies.get(THIRD_ELEMENT).getId()));

        for (CinemaHall cinemaHall : cinemaHalls) {
            System.out.println("Insert into cinemaHalls: " + cinemaHallService.add(cinemaHall));
        }
        System.out.println("Cinema hall by id 3: " + cinemaHallService
                .get(cinemaHalls.get(THIRD_ELEMENT).getId()));

        MovieSession movieSession = new MovieSession();
        List<MovieSession> sessions = createMovieSessions();
        for (MovieSession session : sessions) {
            System.out.println("Insert into movieSession: " + movieSessionService.add(session));
        }
        System.out.println("Movie session by id 3: " + movieSessionService
                .get(sessions.get(THIRD_ELEMENT).getId()));

        List<MovieSession> availableSessions = movieSessionService.findAvailableSessions(1L,
                LocalDate.now());
        for (MovieSession availableSession : availableSessions) {
            System.out.println("Available today: " + availableSession);
        }
    }

    private static List<MovieSession> createMovieSessions() {
        List<MovieSession> sessions = new ArrayList<>();
        for (int i = 0; i < cinemaHalls.size(); i++) {
            sessions.add(new MovieSession(movies.get(i), cinemaHalls.get(i), LocalDateTime.now()
                    .plusDays(i)
                    .withSecond(0)
                    .withNano(0)));
        }
        return sessions;
    }
}
