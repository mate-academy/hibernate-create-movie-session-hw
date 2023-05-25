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
        Movie fastAndFurious = new Movie("Fast and Furious");
        Movie terminator = new Movie("Terminator");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        terminator.setDescription("Awesome action film");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.add(terminator);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall bigHall = new CinemaHall();
        CinemaHall smallHall = new CinemaHall();
        bigHall.setDescription("Awesome and huge hall");
        smallHall.setDescription("Small and comfortable hall");
        bigHall.setCapacity(300);
        smallHall.setCapacity(50);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(bigHall);
        cinemaHallService.add(smallHall);
        System.out.println(cinemaHallService.get(bigHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession todaySession = new MovieSession();
        MovieSession tomorrowSession = new MovieSession();
        todaySession.setMovie(fastAndFurious);
        tomorrowSession.setMovie(fastAndFurious);
        todaySession.setCinemaHall(bigHall);
        tomorrowSession.setCinemaHall(bigHall);
        todaySession.setShowtime(LocalDateTime.now());
        tomorrowSession.setShowtime(LocalDateTime.now().plusDays(2));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(todaySession);
        movieSessionService.add(tomorrowSession);
        System.out.println(movieSessionService.get(todaySession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
