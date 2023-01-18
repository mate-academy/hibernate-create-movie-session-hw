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
        final MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        Movie forrestGump = new Movie("Forest Gump");
        forrestGump.setDescription("About man with an IQ of 75 and his childhood sweetheart.");
        movieService.add(forrestGump);
        System.out.println(movieService.get(forrestGump.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(150);
        firstCinemaHall.setDescription("IMAX hall");
        cinemaHallService.add(firstCinemaHall);
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));
        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(20);
        secondCinemaHall.setDescription("VR cinema hall");
        cinemaHallService.add(secondCinemaHall);
        System.out.println(cinemaHallService.get(secondCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession dayMovieSession = new MovieSession();
        dayMovieSession.setMovie(fastAndFurious);
        dayMovieSession.setCinemaHall(firstCinemaHall);
        dayMovieSession.setShowTime(LocalDateTime
                .of(2023, 5, 30, 12, 0));
        movieSessionService.add(dayMovieSession);
        System.out.println(movieSessionService.get(dayMovieSession.getId()));
        MovieSession afternoonMovieSession = new MovieSession();
        afternoonMovieSession.setMovie(forrestGump);
        afternoonMovieSession.setCinemaHall(firstCinemaHall);
        afternoonMovieSession.setShowTime(LocalDateTime
                .of(2023, 7, 24, 16, 0));
        movieSessionService.add(dayMovieSession);
        System.out.println(movieSessionService.get(dayMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2023, 5, 30)));
    }
}
