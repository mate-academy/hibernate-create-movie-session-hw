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
        fastAndFurious.setDescription("Fast & Furious (also known as The Fast and the Furious) "
                + "is an American media franchise centered on a series of action films "
                + "that are largely concerned with street racing, heists, spies, and family");
        movieService.add(fastAndFurious);

        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(200);
        firstHall.setDescription("First hall");

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(firstHall);

        MovieSession sundaySession = new MovieSession();
        sundaySession.setMovie(fastAndFurious);
        sundaySession.setCinemaHall(firstHall);
        sundaySession.setLocalDateTime(LocalDateTime
                .of(2024, 1, 2, 14, 0, 0));

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(sundaySession);
        System.out.println("Get movie by id!" + movieService.get(fastAndFurious.getId()));
        System.out.println("Get all movies!" + movieService.getAll());

        System.out.println("Get cinema hall by id!" + cinemaHallService.get(firstHall.getId()));
        System.out.println("Get all cinema halls!" + cinemaHallService.getAll());

        System.out.println("Get movie session by id!"
                + movieSessionService.get(sundaySession.getId()));
        System.out.println("Get all movie sessions for sundaySession! Date: 2024-01-02");
        movieSessionService.findAvailableSessions(sundaySession.getId(),
                LocalDate.of(2024, 1, 2)).forEach(System.out::println);
        System.out.println("Get all movie sessions for sundaySession! Date: 2024-01-03");
        movieSessionService.findAvailableSessions(sundaySession.getId(),
                LocalDate.of(2024, 1, 23)).forEach(System.out::println);
        System.out.println("Thank`s for watching!!!");
    }
}
