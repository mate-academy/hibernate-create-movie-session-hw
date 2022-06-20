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
    private static final Injector inject = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        final MovieService movieService =
                (MovieService) inject.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious 8");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        Movie forrestGump = new Movie("Forrest Gump");
        forrestGump.setDescription("Perfect movie to remind yourself and everyone else that "
                + "in any situation you need to remain human and move only forward.");
        movieService.add(forrestGump);
        movieService.get(forrestGump.getId());
        movieService.getAll().forEach(System.out::println);

        final CinemaHallService cinemaHallService =
                (CinemaHallService) inject.getInstance(CinemaHallService.class);

        CinemaHall standardHall = new CinemaHall();
        standardHall.setCapacity(50);
        standardHall.setDescription("Standard cinema hall.");
        cinemaHallService.add(standardHall);

        final MovieSessionService movieSessionService =
                (MovieSessionService) inject.getInstance(MovieSessionService.class);

        MovieSession movieSessionFastAndFurious = new MovieSession();
        movieSessionFastAndFurious.setMovie(fastAndFurious);
        movieSessionFastAndFurious.setCinemaHall(standardHall);
        movieSessionFastAndFurious.setShowTime(LocalDateTime.of(2017, 4, 13, 10, 30));
        movieSessionService.add(movieSessionFastAndFurious);
        System.out.println(movieSessionService.get(movieSessionFastAndFurious.getId()));

        MovieSession movieSessionFastAndFurious2 = new MovieSession();
        movieSessionFastAndFurious2.setMovie(fastAndFurious);
        movieSessionFastAndFurious2.setCinemaHall(standardHall);
        movieSessionFastAndFurious2.setShowTime(LocalDateTime.of(2017, 4, 13, 16, 30));
        movieSessionService.add(movieSessionFastAndFurious2);
        System.out.println(movieSessionService.get(movieSessionFastAndFurious2.getId()));

        MovieSession movieSessionFastAndFurious3 = new MovieSession();
        movieSessionFastAndFurious3.setMovie(fastAndFurious);
        movieSessionFastAndFurious3.setCinemaHall(standardHall);
        movieSessionFastAndFurious3.setShowTime(LocalDateTime.of(2017, 4, 13, 22, 30));
        movieSessionService.add(movieSessionFastAndFurious3);
        System.out.println(movieSessionService.get(movieSessionFastAndFurious3.getId()));

        MovieSession movieSessionForrestGump = new MovieSession();
        movieSessionForrestGump.setMovie(forrestGump);
        movieSessionForrestGump.setCinemaHall(standardHall);
        movieSessionForrestGump.setShowTime(LocalDateTime.of(1994, 7, 6, 18, 30));
        movieSessionService.add(movieSessionForrestGump);
        System.out.println(movieSessionService.get(movieSessionForrestGump.getId()));

        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.of(2017, 4, 13))
                .forEach(System.out::println);
        movieSessionService.findAvailableSessions(forrestGump.getId(), LocalDate.of(1994, 7, 6))
                .forEach(System.out::println);
    }
}
