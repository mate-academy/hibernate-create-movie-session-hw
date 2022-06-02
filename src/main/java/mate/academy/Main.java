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
    static final Injector injector = Injector.getInstance("mate.academy");
    static final MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        Movie starWarsIV = new Movie("Star Wars. Episode IV: A New Hope");
        starWarsIV.setDescription("American epic space opera film written and "
                + "directed by George Lucas");
        movieService.add(starWarsIV);
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);

        CinemaHall greenHall = new CinemaHall();
        greenHall.setDescription("Ordinary hall");
        greenHall.setCapacity(200);
        cinemaHallService.add(greenHall);
        CinemaHall goldenHall = new CinemaHall();
        goldenHall.setDescription("lux hall");
        goldenHall.setCapacity(10);
        cinemaHallService.add(goldenHall);
        System.out.println(cinemaHallService.get(goldenHall.getId()));
        System.out.println(cinemaHallService.getAll());

        MovieSession fastAndFuriousMorningSession = new MovieSession();
        fastAndFuriousMorningSession.setMovie(fastAndFurious);
        fastAndFuriousMorningSession.setCinemaHall(greenHall);
        fastAndFuriousMorningSession.setShowTime(LocalDateTime.of(
                2022, 06, 01, 11, 00));
        movieSessionService.add(fastAndFuriousMorningSession);
        MovieSession fastAndFuriousDaySession = new MovieSession();
        fastAndFuriousMorningSession.setMovie(fastAndFurious);
        fastAndFuriousMorningSession.setCinemaHall(greenHall);
        fastAndFuriousMorningSession.setShowTime(LocalDateTime.of(
                2022, 06, 01, 15, 00));
        movieSessionService.add(fastAndFuriousDaySession);
        MovieSession fastAndFuriousEveningSession = new MovieSession();
        fastAndFuriousMorningSession.setMovie(fastAndFurious);
        fastAndFuriousMorningSession.setCinemaHall(goldenHall);
        fastAndFuriousMorningSession.setShowTime(LocalDateTime.of(
                2022, 06, 01, 20, 45));
        movieSessionService.add(fastAndFuriousEveningSession);
        MovieSession starWarsNightSession = new MovieSession();
        fastAndFuriousMorningSession.setMovie(starWarsIV);
        fastAndFuriousMorningSession.setCinemaHall(goldenHall);
        fastAndFuriousMorningSession.setShowTime(LocalDateTime.of(
                2022, 06, 01, 23, 00));
        movieSessionService.add(starWarsNightSession);
        System.out.println(movieSessionService.get(starWarsIV.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, 06,01));
    }
}
