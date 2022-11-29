package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall bigHall = new CinemaHall(300, "Big cinema hall");
        CinemaHall vipCinemaHall = new CinemaHall(50, "Small and "
                + "comfortable cinema hall");
        cinemaHallService.add(bigHall);
        cinemaHallService.add(vipCinemaHall);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        LocalDateTime firstSession = LocalDateTime.parse("2022-11-25T21:30:00",
                        DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime secondSession = LocalDateTime.parse("2022-11-25T22:45:00",
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        MovieSession regularFastAndFuriousSession = new MovieSession(fastAndFurious,
                        bigHall, firstSession);
        MovieSession vipFastAndFuriousSession = new MovieSession(fastAndFurious,
                        vipCinemaHall, secondSession);
        movieSessionService.add(regularFastAndFuriousSession);
        movieSessionService.add(vipFastAndFuriousSession);

        LocalDate today = LocalDateTime.now().toLocalDate();
        System.out.println("Today sessions");
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious
                        .getId(), today));
        LocalDateTime tomorrow = LocalDateTime.parse("2022-11-26T21:45:00",
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        vipFastAndFuriousSession.setId(null);
        vipFastAndFuriousSession.setTimeOfBeginning(tomorrow);
        movieSessionService.add(vipFastAndFuriousSession);
        System.out.println("Tomorrow sessions");
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                        tomorrow.toLocalDate()));
    }
}
