package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
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
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie shrek = new Movie("Shrek");
        shrek.setDescription("A humorous fairy tail about one green ogre and his adventures");
        movieService.add(shrek);
        System.out.println(movieService.get(shrek.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);

        CinemaHall venice = new CinemaHall();
        venice.setCapacity(100);
        venice.setDescription("Our new amazing cinema hall 'Venice' with 100 sits.");
        cinemaHallService.add(venice);
        System.out.println(cinemaHallService.get(venice.getId()));

        CinemaHall lasVegas = new CinemaHall();
        lasVegas.setCapacity(70);
        lasVegas.setDescription("The cinema hall 'Las Vegas' with 70 sits and IMAX screen");
        cinemaHallService.add(lasVegas);
        System.out.println(cinemaHallService.get(lasVegas.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);

        MovieSession fastAndFuriousSession = new MovieSession(fastAndFurious);
        fastAndFuriousSession.setCinemaHall(venice);
        fastAndFuriousSession.setShowTime(LocalDateTime.of(LocalDate.of(2022,
                        Month.MAY, 26), LocalTime.of(17, 50)));
        movieSessionService.add(fastAndFuriousSession);
        System.out.println(movieSessionService.get(fastAndFuriousSession.getId()));

        MovieSession shrekSession = new MovieSession(shrek);
        shrekSession.setCinemaHall(lasVegas);
        shrekSession.setShowTime(LocalDateTime.of(LocalDate.now(),
                LocalTime.of(20, 30)));
        movieSessionService.add(shrekSession);
        System.out.println(movieSessionService.get(shrekSession.getId()));

        movieSessionService.findAvailableSessions(fastAndFuriousSession.getId(), LocalDate.now())
                .forEach(System.out::println);
        movieSessionService.findAvailableSessions(shrekSession.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
