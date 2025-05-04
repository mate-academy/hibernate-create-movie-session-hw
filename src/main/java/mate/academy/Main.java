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
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        Movie watchmen = new Movie("Watchmen");
        watchmen.setDescription("Watchmen is an American superhero drama limited series based on "
                + "the 1986 DC Comics series of the same title "
                + "created by Alan Moore and Dave Gibbons.");
        movieService.add(watchmen);
        movieService.get(watchmen.getId());
        movieService.getAll().forEach(System.out::println);

        System.out.println(
                System.lineSeparator() + "Start of cinema hall service block"
                        + System.lineSeparator());
        CinemaHallService cinemaHallService = (CinemaHallService) injector.getInstance(
                CinemaHallService.class);
        CinemaHall classicHall = new CinemaHall(125, "classic");
        CinemaHall vipHall = new CinemaHall(36, "VIP");
        CinemaHall dolbyDigitalHall = new CinemaHall(90, "Dolby 3d Digital Cinema Hall");
        cinemaHallService.add(classicHall);
        cinemaHallService.add(vipHall);
        cinemaHallService.add(dolbyDigitalHall);
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println(cinemaHallService.get(vipHall.getId()));

        System.out.println(
                System.lineSeparator() + "Start of movie session service block"
                        + System.lineSeparator());
        MovieSessionService movieSessionService = (MovieSessionService) injector.getInstance(
                MovieSessionService.class);
        MovieSession watchmenSessionToday = new MovieSession(watchmen, dolbyDigitalHall,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 15)));
        MovieSession watchmenSessionToday2 = new MovieSession(watchmen, dolbyDigitalHall,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(20, 15)));
        movieSessionService.add(watchmenSessionToday);
        movieSessionService.add(watchmenSessionToday2);
        System.out.println(movieSessionService.get(watchmenSessionToday.getId()));
        MovieSession crapSessionToday = new MovieSession(fastAndFurious, classicHall,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(21, 30)));
        MovieSession crapSession = new MovieSession(fastAndFurious, classicHall,
                LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(9, 30)));
        movieSessionService.add(crapSession);
        movieSessionService.add(crapSessionToday);
        movieSessionService.get(crapSession.getId());
        System.out.println(System.lineSeparator() + "Find available sessions for today: "
                + System.lineSeparator());
        movieSessionService.findAvailableSessions(watchmen.getId(), LocalDate.now()).forEach(
                System.out::println);
    }
}
