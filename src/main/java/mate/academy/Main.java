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
    private static MovieService movieService;
    private static CinemaHallService cinemaHallService;
    private static MovieSessionService movieSessionService;

    public static void main(String[] args) {
        movieService = (MovieService) injector
                .getInstance(MovieService.class);
        cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        moviesCreator();
        cinemaHallCreator();
        movieSessionCreator();

        System.out.println(movieSessionService.get(3L));
        System.out.println(movieSessionService.get(1L));
        System.out.println(movieSessionService.get(2L));

        System.out.println("----findAvailableSessions----");
        System.out.println(movieSessionService
                .findAvailableSessions(1L, LocalDate.now().plusDays(1L)));
    }

    private static void movieSessionCreator() {
        MovieSession movieSessionFirst = new MovieSession();
        movieSessionFirst.setMovie(movieService.get(1L));
        movieSessionFirst.setCinemaHall(cinemaHallService.get(2L));
        movieSessionFirst.setShowTime(LocalDateTime.now().plusDays(1L).minusHours(3L));
        movieSessionService.add(movieSessionFirst);

        MovieSession movieSessionFirstOneMore = new MovieSession();
        movieSessionFirstOneMore.setMovie(movieService.get(1L));
        movieSessionFirstOneMore.setCinemaHall(cinemaHallService.get(3L));
        movieSessionFirstOneMore.setShowTime(LocalDateTime.now().plusDays(1L).minusHours(4L));
        movieSessionService.add(movieSessionFirstOneMore);

        MovieSession movieSessionSecond = new MovieSession();
        movieSessionSecond.setMovie(movieService.get(2L));
        movieSessionSecond.setCinemaHall(cinemaHallService.get(3L));
        movieSessionSecond.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSessionSecond);

        MovieSession movieSessionThird = new MovieSession();
        movieSessionThird.setMovie(movieService.get(3L));
        movieSessionThird.setCinemaHall(cinemaHallService.get(1L));
        movieSessionThird.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSessionThird);
    }

    private static void moviesCreator() {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie fastAndFuriousSecond = new Movie("Fast and Furious 2");
        fastAndFuriousSecond.setDescription("An action film about street racing, "
                + "heists, and spies.");
        movieService.add(fastAndFuriousSecond);

        Movie fastAndFuriousThird = new Movie("Fast and Furious 3");
        fastAndFuriousThird.setDescription("An action film about street racing, "
                + "heists, and spies.");
        movieService.add(fastAndFuriousThird);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
    }

    private static void cinemaHallCreator() {
        CinemaHall cinemaHallA = new CinemaHall(100, "Hall A");
        cinemaHallService.add(cinemaHallA);

        CinemaHall cinemaHallB = new CinemaHall(150, "Hall B");
        cinemaHallService.add(cinemaHallB);

        CinemaHall cinemaHallC = new CinemaHall(50, "Hall C");
        cinemaHallService.add(cinemaHallC);

        System.out.println(cinemaHallService.get(cinemaHallA.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
    }
}
