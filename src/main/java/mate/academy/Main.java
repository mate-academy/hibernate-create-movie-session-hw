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
        Movie birdman = new Movie();
        birdman.setTitle("Birdman");
        birdman.setDescription("Oscar 2015 - Best Picture");
        Movie threeBillboards = new Movie();
        threeBillboards.setTitle("Three Billboards Outside Ebbing, Missouri");
        threeBillboards.setDescription("Oscar 2018 - Best Actress in a Leading Role");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(birdman);
        movieService.add(threeBillboards);
        movieService.getAll().forEach(System.out::println);

        CinemaHall mercury = new CinemaHall();
        mercury.setCapacity(20);
        mercury.setDescription("Small hall");
        CinemaHall jupiter = new CinemaHall();
        jupiter.setCapacity(300);
        jupiter.setDescription("Big hall");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(mercury);
        cinemaHallService.add(jupiter);
        System.out.println(cinemaHallService.get(jupiter.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstSession = new MovieSession();
        firstSession.setMovie(birdman);
        firstSession.setCinemaHall(mercury);
        firstSession.setShowTime(LocalDateTime.of(2021, 12, 28, 15, 00));

        MovieSession secondSession = new MovieSession();
        secondSession.setMovie(threeBillboards);
        secondSession.setCinemaHall(jupiter);
        secondSession.setShowTime(LocalDateTime.of(2021, 12, 29, 15, 00));

        MovieSession thirdSession = new MovieSession();
        thirdSession.setMovie(threeBillboards);
        thirdSession.setCinemaHall(mercury);
        thirdSession.setShowTime(LocalDateTime.of(2021, 12, 29, 18, 00));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(firstSession);
        movieSessionService.add(secondSession);
        movieSessionService.add(thirdSession);
        System.out.println(movieSessionService.get(firstSession.getId()));
        movieSessionService.findAvailableSessions(threeBillboards.getId(),
                LocalDate.of(2021, 12, 29)).forEach(System.out::println);
    }
}
