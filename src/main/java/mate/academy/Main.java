package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
                = (mate.academy.service.MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        Movie fifthElement = new Movie("Fifth Element");
        fifthElement.setDescription("Abt future.");
        movieService.add(fifthElement);
        System.out.println(movieService.get(fifthElement.getId()));
        System.out.println(movieService.getAll());
        CinemaHall cinemaHallRed = new CinemaHall();
        cinemaHallRed.setDescription("Red");
        cinemaHallRed.setCapacity(200);
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHallRed);
        System.out.println(cinemaHallService.get(cinemaHallRed.getId()));
        CinemaHall cinemaHallGreen = new CinemaHall();
        cinemaHallGreen.setDescription("Green");
        cinemaHallGreen.setCapacity(150);
        cinemaHallService.add(cinemaHallGreen);
        System.out.println(cinemaHallService.get(cinemaHallGreen.getId()));
        System.out.println(cinemaHallService.getAll());
        MovieSession movieSessionMorning = new MovieSession();
        movieSessionMorning.setMovie(fifthElement);
        movieSessionMorning.setCinemaHall(cinemaHallRed);
        movieSessionMorning.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSessionMorning);
        MovieSession movieSessionDay = new MovieSession();
        movieSessionDay.setMovie(fastAndFurious);
        movieSessionDay.setCinemaHall(cinemaHallGreen);
        movieSessionDay.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSessionDay);
        MovieSession movieSessionEvening = new MovieSession();
        movieSessionEvening.setMovie(fifthElement);
        movieSessionEvening.setCinemaHall(cinemaHallRed);
        movieSessionEvening.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSessionEvening);
        MovieSession movieSessionOtherDay = new MovieSession();
        movieSessionOtherDay.setMovie(fastAndFurious);
        movieSessionOtherDay.setCinemaHall(cinemaHallRed);
        movieSessionOtherDay.setShowTime(LocalDateTime.now().minusDays(1));
        movieSessionService.add(movieSessionOtherDay);
        MovieSession movieSession = movieSessionService.get(movieSessionMorning.getId());
        System.out.println(movieSession.getCinemaHall());
        System.out.println(movieSession.getMovie());
        List<MovieSession> availableSessions
                = movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now().minusDays(1));
        availableSessions.forEach(System.out::println);
    }
}
