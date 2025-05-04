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
    private static final String SEPARATOR = System.lineSeparator();

    public static void main(String[] args) {
        Movie sherlock = new Movie("Sherlock");
        sherlock.setDescription("Movie about detective Sherlock Holmes.");
        Movie sherlockTwo = new Movie("Sherlock 2");
        sherlockTwo.setDescription("Second part of the movie about detective Sherlock Holmes.");
        Movie sherlockThree = new Movie("Sherlock");
        sherlockThree.setDescription("Third part of the movie about detective Sherlock Holmes.");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(sherlock);
        movieService.add(sherlockTwo);
        movieService.add(sherlockThree);
        System.out.println("Get movie by id: " + movieService.get(sherlock.getId())
                + SEPARATOR + SEPARATOR + "Get all movies: ");
        movieService.getAll().forEach(System.out::println);
        System.out.println(SEPARATOR);

        CinemaHall someCinemaHall = new CinemaHall();
        someCinemaHall.setCapacity(50);
        someCinemaHall.setDescription("Some description.");
        CinemaHall someSecondCinemaHall = new CinemaHall();
        someSecondCinemaHall.setCapacity(100);
        someSecondCinemaHall.setDescription("Some second cinema hall description.");
        CinemaHall someThirdCinemaHall = new CinemaHall();
        someThirdCinemaHall.setCapacity(150);
        someThirdCinemaHall.setDescription("Some third cinema hall description.");
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(someCinemaHall);
        System.out.println("Get cinema hall by id: "
                + cinemaHallService.get(someCinemaHall.getId())
                + SEPARATOR + SEPARATOR + "Get all cinema halls: ");
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println(SEPARATOR);

        MovieSession someCinemaHallSession = new MovieSession();
        someCinemaHallSession.setMovie(sherlock);
        someCinemaHallSession.setCinemaHall(someCinemaHall);
        someCinemaHallSession.setShowTime(LocalDateTime.parse("2021-06-05T12:00:00"));
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(someCinemaHallSession);
        System.out.println("Get cinema hall session by id: "
                + movieSessionService.get(someCinemaHallSession.getId())
                + SEPARATOR + SEPARATOR + "Get all all available movies: ");
        movieSessionService.findAvailableSessions(sherlock.getId(),
                LocalDate.of(2021, 6, 5)).forEach(System.out::println);
        System.out.println(SEPARATOR);
    }
}
