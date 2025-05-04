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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        final MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        final CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie drStrange2 = new Movie("Doctor Strange in the Multiverse of Madness");
        drStrange2.setDescription("Dr Strange casts a spell that opens the door to the multiverse");
        movieService.add(drStrange2);
        System.out.println(movieService.get(drStrange2.getId()));

        movieService.getAll().forEach(System.out::println);

        CinemaHall t1Hall = new CinemaHall();
        t1Hall.setCapacity(100);
        t1Hall.setDescription("Cinamon Kino in T1 Mall of Tallinn");
        cinemaHallService.add(t1Hall);
        System.out.println(cinemaHallService.get(t1Hall.getId()));

        CinemaHall cosmosHall = new CinemaHall();
        cosmosHall.setCapacity(150);
        cosmosHall.setDescription("Cinamon Kosmos");
        cinemaHallService.add(cosmosHall);
        System.out.println(cinemaHallService.get(cosmosHall.getId()));

        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession sessionFfT12022June20 = new MovieSession();
        sessionFfT12022June20.setMovie(fastAndFurious);
        sessionFfT12022June20.setCinemaHall(t1Hall);
        sessionFfT12022June20.setShowTime(LocalDateTime.of(2022, 6, 20, 22, 0, 0));
        movieSessionService.add(sessionFfT12022June20);

        MovieSession sessionDsT12022June20 = new MovieSession();
        sessionDsT12022June20.setMovie(drStrange2);
        sessionDsT12022June20.setCinemaHall(t1Hall);
        sessionDsT12022June20.setShowTime(LocalDateTime.of(2022, 6, 20, 19, 0, 0));
        movieSessionService.add(sessionDsT12022June20);

        MovieSession sessionFfT12022June21 = new MovieSession();
        sessionFfT12022June21.setMovie(fastAndFurious);
        sessionFfT12022June21.setCinemaHall(t1Hall);
        sessionFfT12022June21.setShowTime(LocalDateTime.of(2022, 6, 21, 23, 59, 59));
        movieSessionService.add(sessionFfT12022June21);

        MovieSession sessionDsT12022June21 = new MovieSession();
        sessionDsT12022June21.setMovie(drStrange2);
        sessionDsT12022June21.setCinemaHall(t1Hall);
        sessionDsT12022June21.setShowTime(LocalDateTime.of(2022, 6, 21, 12, 0, 0));
        movieSessionService.add(sessionDsT12022June21);

        System.out.println(movieSessionService.get(3L));
        System.out.println(movieSessionService.findAvailableSessions(drStrange2.getId(),
                LocalDate.of(2022, 6, 20)));
    }
}
