package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        Movie batman = new Movie("Batman");
        batman.setDescription("Action film about hero....");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        movieService.add(batman);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(batman.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall hallLux = new CinemaHall(15, "Lux hall");
        CinemaHall hallStandard = new CinemaHall(50, "Standard hall");

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(hallLux);
        cinemaHallService.add(hallStandard);

        LocalDateTime sessionMorning = LocalDateTime.of(LocalDate.now(),
                LocalTime.of(10, 0));
        LocalDateTime sessionDay = LocalDateTime.of(LocalDate.now(),
                LocalTime.of(15, 0));
        LocalDateTime sessionEvening = LocalDateTime.of(LocalDate.now(),
                LocalTime.of(19, 0));

        MovieSession movieSessionMorningHallLux = new MovieSession(batman, hallLux, sessionMorning);
        MovieSession movieSessionDayHallLux = new MovieSession(fastAndFurious, hallLux, sessionDay);
        MovieSession movieSessionEveningHallLux = new MovieSession(batman, hallLux, sessionEvening);
        MovieSession movieSessionMorningHallStandard = new MovieSession(fastAndFurious,
                hallStandard, sessionMorning);
        MovieSession movieSessionDayHallStandard = new MovieSession(batman, hallStandard,
                sessionDay);

        MovieSession movieSessionEveningHallStandard = new MovieSession(fastAndFurious,hallLux,
                sessionEvening);

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(movieSessionDayHallLux);
        movieSessionService.add(movieSessionEveningHallLux);
        movieSessionService.add(movieSessionMorningHallLux);
        movieSessionService.add(movieSessionMorningHallStandard);
        movieSessionService.add(movieSessionDayHallStandard);
        movieSessionService.add(movieSessionEveningHallStandard);

        MovieSession movieSession = movieSessionService.get(movieSessionDayHallLux.getId());
        System.out.println(movieSession.getCinemaHall());
        System.out.println(movieSession.getMovie());
        List<MovieSession> availableSessions
                = movieSessionService.findAvailableSessions(batman.getId(), LocalDate.now());
        availableSessions.forEach(System.out::println);
    }
}
