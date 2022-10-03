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
        movieService.getAll().forEach(System.out::println);
        Movie spy = new Movie("Spy");
        spy.setDescription("A desk-bound CIA analyst volunteers to go undercover to infiltrate "
                + "the world of a deadly arms dealer, and prevent diabolical global disaster.");
        movieService.add(spy);
        System.out.println(movieService.get(spy.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);

        CinemaHall planeta = new CinemaHall();
        planeta.setCapacity(60);
        planeta.setDescription("Cinema hall 'Planeta' with 60 sits.");
        cinemaHallService.add(planeta);
        System.out.println(cinemaHallService.get(planeta.getId()));
        CinemaHall zlata = new CinemaHall();
        zlata.setCapacity(30);
        zlata.setDescription("Cinema hall 'Zlata' with 30 sits.");
        cinemaHallService.add(zlata);
        System.out.println(cinemaHallService.get(zlata.getId()));

        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setCinemaHall(planeta);
        fastAndFuriousSession.setShowTime(LocalDateTime.of(LocalDate.of(2022,
                Month.OCTOBER, 30), LocalTime.of(21, 50)));
        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousSession);
        System.out.println(movieSessionService.get(fastAndFuriousSession.getId()));

        System.out.println("----------findAvailableSessions>>fastAndFurious>>>");
        movieSessionService.findAvailableSessions(fastAndFuriousSession.getId(), LocalDate.now())
                .forEach(System.out::println);
        System.out.println("<<fastAndFurious<<findAvailableSessions-----------");

        MovieSession spyMovieSession = new MovieSession();
        spyMovieSession.setMovie(spy);
        spyMovieSession.setCinemaHall(zlata);
        spyMovieSession.setShowTime(LocalDateTime.of(LocalDate.now(),
                LocalTime.of(20, 30)));
        movieSessionService.add(spyMovieSession);
        System.out.println(movieSessionService.get(spyMovieSession.getId()));

        System.out.println("----------findAvailableSessions>>>spyMovie>>>");
        movieSessionService.findAvailableSessions(spyMovieSession.getId(), LocalDate.now())
                .forEach(System.out::println);
        System.out.println("<<<spyMovie<<<<findAvailableSessions-----------");
    }
}
