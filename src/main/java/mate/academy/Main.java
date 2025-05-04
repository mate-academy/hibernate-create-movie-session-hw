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
        //movies
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie sonnik = new Movie("Sonnik");
        sonnik.setDescription("A cool film about fast blue hedgehog");
        movieService.add(sonnik);
        System.out.println(movieService.get(sonnik.getId()));

        Movie invisible = new Movie();
        movieService.add(invisible);
        System.out.println(movieService.get(invisible.getId()));
        movieService.getAll().forEach(System.out::println);

        //movie services
        CinemaHallService cinemaService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall cinemaDafi = new CinemaHall();
        cinemaDafi.setCapacity(120);
        cinemaDafi.setDescription("New 3D cinema");
        cinemaService.add(cinemaDafi);
        System.out.println(cinemaService.get(cinemaDafi.getId()));

        CinemaHall mostKino = new CinemaHall();
        cinemaService.add(mostKino);
        cinemaService.getAll().forEach(System.out::println);

        //movie sessions
        MovieSession movieSessionFastAndFuriousDafi = new MovieSession();
        movieSessionFastAndFuriousDafi.setMovie(fastAndFurious);
        movieSessionFastAndFuriousDafi.setCinemaHall(cinemaDafi);
        movieSessionFastAndFuriousDafi
                .setShowTime(LocalDateTime.of(2022,06,22,13,30));

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSessionFastAndFuriousDafi);
        System.out.println(movieSessionService.get(movieSessionFastAndFuriousDafi.getId()));

        MovieSession movieSessionSonnikDafi = new MovieSession();
        movieSessionSonnikDafi.setMovie(sonnik);
        movieSessionSonnikDafi.setCinemaHall(cinemaDafi);
        movieSessionSonnikDafi.setShowTime(LocalDateTime.of(2022,06,22,18,10));
        movieSessionService.add(movieSessionSonnikDafi);
        System.out.println(movieSessionService.get(movieSessionSonnikDafi.getId()));

        MovieSession movieSessionSonnikMost = new MovieSession();
        movieSessionSonnikMost.setMovie(sonnik);
        movieSessionSonnikMost.setCinemaHall(mostKino);
        movieSessionSonnikMost.setShowTime(LocalDateTime.of(2022,06,22,15,10));
        movieSessionService.add(movieSessionSonnikMost);
        System.out.println(movieSessionService.get(movieSessionSonnikMost.getId()));

        System.out.println(movieSessionService
                .findAvailableSessions(sonnik.getId(), LocalDate.of(2022, 06, 22)));
    }
}
