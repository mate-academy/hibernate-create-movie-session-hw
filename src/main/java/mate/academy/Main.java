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
        MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie terminator = new Movie("Terminator 2");
        terminator.setDescription("An action film about androids and robots");
        movieService.add(terminator);
        System.out.println(movieService.get(terminator.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall imaxHall = new CinemaHall();
        imaxHall.setDescription("IMAX hall");
        imaxHall.setCapacity(250);
        cinemaHallService.add(imaxHall);
        System.out.println(cinemaHallService.get(imaxHall.getId()));

        CinemaHall smallHall = new CinemaHall();
        smallHall.setDescription("Small hall");
        smallHall.setCapacity(100);
        cinemaHallService.add(smallHall);
        System.out.println(cinemaHallService.get(smallHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession fastAndFuriousSession = new MovieSession();
        LocalDateTime fastAndFuriousShowTime
                = LocalDateTime.of(2022, 7, 4, 15,0);
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setCinemaHall(imaxHall);
        fastAndFuriousSession.setShowTime(fastAndFuriousShowTime);
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousSession);
        System.out.println(movieSessionService.get(fastAndFuriousSession.getId()));

        MovieSession terminatorSession = new MovieSession();
        terminatorSession.setMovie(terminator);
        terminatorSession.setCinemaHall(smallHall);
        LocalDateTime terminatorSessionShowTime
                = LocalDateTime.of(2022, 7, 4, 17,0);
        terminatorSession.setShowTime(terminatorSessionShowTime);
        movieSessionService.add(terminatorSession);
        System.out.println(movieSessionService.get(terminatorSession.getId()));
        System.out.println(movieSessionService
                .findAvailableSessions(terminator.getId(), LocalDate.of(2022, 7, 4)));

    }
}
