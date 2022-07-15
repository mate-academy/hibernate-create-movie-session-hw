package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) INJECTOR.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie driver = new Movie("Driver");
        driver.setDescription("An action film about racer, banks robberies");
        movieService.add(driver);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(driver.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("TRC French Boulevard");
        cinemaHall.setCapacity(500);
        CinemaHallService cinemaHallService =
                (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));

        MovieSession session15July2015 = new MovieSession();
        session15July2015.setCinemaHall(cinemaHall);
        session15July2015.setMovie(fastAndFurious);
        session15July2015.setShowTime(LocalDateTime.of(2022, Month.JULY, 15, 20, 15));
        MovieSessionService sessionService =
                (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);
        sessionService.add(session15July2015);

        MovieSession session15July1320 = new MovieSession();
        session15July1320.setShowTime(LocalDateTime.of(2022, Month.JULY, 15, 13, 20));
        session15July1320.setMovie(fastAndFurious);
        session15July1320.setCinemaHall(cinemaHall);
        sessionService.add(session15July1320);

        System.out.println(sessionService.get(session15July1320.getId()));
        System.out.println(sessionService.get(session15July2015.getId()));
        System.out.println(sessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now()));
    }
}
