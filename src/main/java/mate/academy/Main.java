package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall imaxHall = new CinemaHall();
        imaxHall.setDescription("B1 IMAX Hall");
        imaxHall.setCapacity(60);

        CinemaHallService cinemaHallService =
                ((CinemaHallService) INJECTOR.getInstance(CinemaHallService.class));
        cinemaHallService.add(imaxHall);
        System.out.println(cinemaHallService.get(imaxHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        LocalDate dateToday = LocalDate.now();

        MovieSession midnightSession = new MovieSession();
        midnightSession.setMovie(fastAndFurious);
        midnightSession.setCinemaHall(imaxHall);
        midnightSession.setShowTime(LocalDateTime.of(dateToday, LocalTime.MIN));

        MovieSession lastSessionForToday = new MovieSession();
        lastSessionForToday.setMovie(fastAndFurious);
        lastSessionForToday.setCinemaHall(imaxHall);
        lastSessionForToday.setShowTime(LocalDateTime.of(dateToday, LocalTime.MAX));

        MovieSession yesterdaySession = new MovieSession();
        yesterdaySession.setMovie(fastAndFurious);
        yesterdaySession.setCinemaHall(imaxHall);
        yesterdaySession.setShowTime(dateToday.minusDays(1).atStartOfDay());

        MovieSessionService movieSessionService =
                ((MovieSessionService) INJECTOR.getInstance(MovieSessionService.class));
        movieSessionService.add(midnightSession);
        movieSessionService.add(lastSessionForToday);
        movieSessionService.add(yesterdaySession);
        System.out.println(movieSessionService.get(midnightSession.getId()));
        System.out.println(movieSessionService.get(lastSessionForToday.getId()));
        System.out.println(movieSessionService.get(yesterdaySession.getId()));
        movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), dateToday)
                .forEach(System.out::println);
    }
}
