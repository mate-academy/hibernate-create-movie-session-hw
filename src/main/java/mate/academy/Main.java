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
    public static final Injector injector = Injector.getInstance("mate.academy");
    public static final MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);
    public static final CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);
    public static final MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        Movie terminator = new Movie("Terminator 2");
        terminator.setDescription("An action film about a war in the real time for the future.");
        movieService.add(terminator);
        System.out.println(movieService.get(terminator.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHall cinemaHall = new CinemaHall("Port City");
        cinemaHall.setCapacity(1000);
        cinemaHall.setDescription("New cinema hall");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        CinemaHall cinemaHallLucomorie = new CinemaHall("Lucomorie");
        cinemaHallLucomorie.setCapacity(2000);
        cinemaHallLucomorie.setDescription("Very popular cinema hall");
        cinemaHallService.add(cinemaHallLucomorie);
        System.out.println(cinemaHallService.get(cinemaHallLucomorie.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        MovieSession movieSessionEvening = new MovieSession();
        movieSessionEvening.setMovie(fastAndFurious);
        movieSessionEvening.setCinemaHall(cinemaHallLucomorie);
        movieSessionEvening.setShowTime(LocalDateTime.of(2022, 9, 20, 19, 0, 0));
        movieSessionService.add(movieSessionEvening);
        MovieSession movieSessionMorning = new MovieSession();
        movieSessionMorning.setMovie(terminator);
        movieSessionMorning.setCinemaHall(cinemaHall);
        movieSessionMorning.setShowTime(LocalDateTime.of(2022, 9, 20, 10, 0, 0));
        movieSessionService.add(movieSessionMorning);
        System.out.println(movieSessionService.findAvailableSessions(terminator.getId(),
                LocalDate.of(2022,9,20)));
    }
}
