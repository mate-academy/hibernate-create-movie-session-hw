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

        System.out.println("--- Test Movie ---");
        System.out.println("Get Movie: " + movieService.get(fastAndFurious.getId()));
        System.out.println("All Movies: " + movieService.getAll());

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall imaxHall = new CinemaHall();
        imaxHall.setCapacity(200);
        imaxHall.setDescription("IMAX Hall with premium sound");
        cinemaHallService.add(imaxHall);

        System.out.println("\n--- Test CinemaHall ---");
        System.out.println("Get Cinema Hall: " + cinemaHallService.get(imaxHall.getId()));
        System.out.println("All Cinema Halls: " + cinemaHallService.getAll());

        MovieSession sessionToday = new MovieSession();
        sessionToday.setMovie(fastAndFurious);
        sessionToday.setCinemaHall(imaxHall);
        sessionToday.setShowTime(LocalDateTime.now().withHour(19).withMinute(30));

        MovieSession sessionTomorrow = new MovieSession();
        sessionTomorrow.setMovie(fastAndFurious);
        sessionTomorrow.setCinemaHall(imaxHall);
        sessionTomorrow.setShowTime(LocalDateTime.now().plusDays(1)
                .withHour(14).withMinute(0));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(sessionToday);
        movieSessionService.add(sessionTomorrow);

        System.out.println("\n--- Test MovieSession ---");
        System.out.println("Get Movie Session: " + movieSessionService.get(sessionToday.getId()));

        LocalDate queryDate = LocalDate.now();
        System.out.println("Available Sessions for Movie ID " + fastAndFurious.getId()
                + " on " + queryDate + ":");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), queryDate)
                .forEach(System.out::println);
    }
}


