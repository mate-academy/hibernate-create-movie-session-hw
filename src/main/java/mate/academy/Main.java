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

        Movie intouchables = new Movie("Intouchables");
        intouchables.setDescription("Awesome movie");
        movieService.add(intouchables);

        Movie theGreenMile = new Movie("The Green Mile");
        theGreenMile.setDescription("Awesome movie");
        movieService.add(theGreenMile);

        System.out.println("_______get_movie_by_id_________");
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("_______get_all_movies_________");
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(200);
        firstHall.setDescription("IMAX");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(firstHall);

        CinemaHall secondHall = new CinemaHall();
        secondHall.setCapacity(300);
        secondHall.setDescription("3D");
        cinemaHallService.add(secondHall);

        System.out.println("_______get_cinema_hall_by_id_________");
        System.out.println(cinemaHallService.get(secondHall.getId()));
        System.out.println("_______get_all_cinema_halls_________");
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession eveningSession = new MovieSession();
        eveningSession.setMovie(fastAndFurious);
        eveningSession.setCinemaHall(firstHall);
        eveningSession.setShowTime(LocalDateTime.of(2022, 4, 10, 21, 30));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(eveningSession);

        MovieSession nightSession = new MovieSession();
        nightSession.setMovie(intouchables);
        nightSession.setCinemaHall(secondHall);
        nightSession.setShowTime(LocalDateTime.of(2022, 4, 10, 0, 30));
        movieSessionService.add(nightSession);

        System.out.println("_______get_movie_session_by_id_________");
        System.out.println(movieSessionService.get(eveningSession.getId()));
        System.out.println("_______get_available_session_________");
        movieSessionService.findAvailableSessions(intouchables.getId(),
                LocalDate.of(2022, 4, 10)).forEach(System.out::println);
    }
}
