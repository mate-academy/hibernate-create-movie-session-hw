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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        // ---- Test MovieService ----
        Movie myFavoriteFilm = new Movie("The Shawshank Redemption");
        myFavoriteFilm.setDescription("American drama film written and directed by Frank Darabont,"
                + "based on the 1982 Stephen King novella Rita Hayworth and Shawshank Redemption. "
                + "The film tells the story of banker Andy Dufresne, who is sentenced to life in"
                + " Shawshank State Penitentiary for the murders of his wife and her lover, "
                + "despite his claims of innocence.");
        movieService.add(myFavoriteFilm);
        System.out.println("---- List of MovieService entities ----");
        System.out.println(movieService.get(myFavoriteFilm.getId()));
        movieService.getAll().forEach(System.out::println);
        System.out.println("---- End of List ----");
        //  ---- Test CinemaHallService ----
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("My local cinema hall");
        cinemaHall.setCapacity(40);
        cinemaHallService.add(cinemaHall);
        System.out.println("---- List of CinemaHallService entities ----");
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("---- End of List ----");
        // ---- Test MovieSessionService ----
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(myFavoriteFilm);
        movieSession.setShowTime(LocalDateTime.now());
        movieSession.setCinemaHall(cinemaHall);
        movieSessionService.add(movieSession);
        System.out.println("---- List of MovieSessionService entities ----");
        System.out.println(movieSessionService.get(movieSession.getId()));
        movieSessionService
                .findAvailableSessions(myFavoriteFilm.getId(), LocalDate.now())
                .forEach(System.out::println);
        System.out.println("---- End of List ----");
    }
}
