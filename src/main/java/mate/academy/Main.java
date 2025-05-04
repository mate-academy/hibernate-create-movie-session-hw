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

        Movie wrongTurn = new Movie("Wrong turn");
        wrongTurn.setDescription("Horror");
        movieService.add(wrongTurn);

        Movie finalDestination = new Movie("Final destination");
        finalDestination.setDescription("Excited movie about travel");
        movieService.add(finalDestination);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall multiplex = new CinemaHall();
        multiplex.setDescription("Laser IMAX");
        multiplex.setCapacity(300);
        cinemaHallService.add(multiplex);

        CinemaHall planetaKino = new CinemaHall();
        planetaKino.setDescription("RE'LUX");
        planetaKino.setCapacity(230);
        cinemaHallService.add(planetaKino);

        cinemaHallService.getAll().forEach(movie -> System.out.println(movie));

        MovieSession morningSessionFaf = new MovieSession();
        morningSessionFaf.setMovie(fastAndFurious);
        morningSessionFaf.setCinemaHall(multiplex);
        morningSessionFaf.setShowTime(LocalDateTime.of(2024, 1, 21, 9, 30));

        MovieSession eveningSessionWt = new MovieSession();
        eveningSessionWt.setMovie(wrongTurn);
        eveningSessionWt.setCinemaHall(planetaKino);
        eveningSessionWt.setShowTime(LocalDateTime.of(2024, 1, 21, 20, 20));

        MovieSession nightSessionFd = new MovieSession();
        nightSessionFd.setMovie(finalDestination);
        nightSessionFd.setCinemaHall(multiplex);
        nightSessionFd.setShowTime(LocalDateTime.of(2024, 1, 21, 23, 55));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(morningSessionFaf);
        movieSessionService.add(eveningSessionWt);
        movieSessionService.add(nightSessionFd);

        movieSessionService.findAvailableSessions(wrongTurn.getId(), LocalDate.now())
                .forEach(movieSession -> System.out.println(movieSession));
    }
}
