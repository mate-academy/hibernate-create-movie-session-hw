package mate.academy;

import java.time.LocalDate;
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

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        Movie cyborgs = new Movie("Cyborgs");
        cyborgs.setDescription("War drama film about the Second Battle "
                + "of Donetsk Airport during the war in Donbas.");
        movieService.add(cyborgs);
        System.out.println(movieService.get(cyborgs.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService hallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall redHall = new CinemaHall(25, "Small red hall");
        CinemaHall blackHall = new CinemaHall(73, "Big black hall");
        hallService.add(redHall);
        System.out.println(hallService.get(redHall.getId()));
        hallService.add(blackHall);
        System.out.println(hallService.get(blackHall.getId()));
        hallService.getAll().forEach(System.out::println);

        MovieSessionService sessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        LocalDate firstOctober = LocalDate.of(2022, 10, 1);
        LocalDate thirdDecember = LocalDate.of(2022, 12, 3);
        final MovieSession firstOctoberSession = new MovieSession(firstOctober);
        final MovieSession thirdDecemberSession = new MovieSession(thirdDecember);
        firstOctoberSession.setMovie(cyborgs);
        firstOctoberSession.setCinemaHall(blackHall);
        sessionService.add(firstOctoberSession);
        System.out.println(sessionService.get(firstOctoberSession.getId()));
        thirdDecemberSession.setMovie(fastAndFurious);
        thirdDecemberSession.setCinemaHall(redHall);
        sessionService.add(thirdDecemberSession);
        System.out.println(sessionService.get(thirdDecemberSession.getId()));
        sessionService.findAvailableSessions(cyborgs.getId(),
                firstOctober).forEach(System.out::println);
        sessionService.findAvailableSessions(fastAndFurious.getId(),
                thirdDecember).forEach(System.out::println);
    }
}
