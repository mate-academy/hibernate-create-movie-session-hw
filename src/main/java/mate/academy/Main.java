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

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie theMatrix = new Movie("The Matrix");
        theMatrix.setDescription("Cool movie about Matrix");
        movieService.add(theMatrix);

        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(50);
        cinemaHall.setDescription("Cinema hall number 1");
        cinemaHallService.add(cinemaHall);

        MovieSession firstFastAndFuriousSession = new MovieSession();
        firstFastAndFuriousSession.setMovie(fastAndFurious);
        firstFastAndFuriousSession.setCinemaHall(cinemaHall);
        firstFastAndFuriousSession.setShowTime(LocalDateTime.of(2024, 1,
                20, 2, 0));
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(firstFastAndFuriousSession);

        MovieSession secondFastAndFuriousSession = new MovieSession();
        secondFastAndFuriousSession.setMovie(theMatrix);
        secondFastAndFuriousSession.setCinemaHall(cinemaHall);
        secondFastAndFuriousSession.setShowTime(LocalDateTime.of(2024, 1,
                20, 5, 0));
        movieSessionService.add(secondFastAndFuriousSession);

        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        System.out.println(movieSessionService.get(firstFastAndFuriousSession.getId()));
        System.out.println(movieSessionService.get(secondFastAndFuriousSession.getId()));

        movieService.getAll().forEach(System.out::println);
        cinemaHallService.getAll().forEach(System.out::println);

        LocalDate firstLocalDate = LocalDate.of(2024, 1, 20);
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                firstLocalDate));
        LocalDate secondLocalDate = LocalDate.of(2024, 1, 20);
        System.out.println(movieSessionService.findAvailableSessions(theMatrix.getId(),
                secondLocalDate));
    }
}
