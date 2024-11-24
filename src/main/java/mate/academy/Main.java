package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final String DATE = "2019-09-10";

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie united93 = new Movie("United 93");
        united93.setDescription("A real-time account of the events on United Flight 93.");
        movieService.add(united93);

        Movie neerja = new Movie("Neerja");
        neerja.setDescription("A biographical thriller film, based on a real-life event.");
        movieService.add(neerja);

        Movie endOfWatch = new Movie("End of Watch");
        endOfWatch.setDescription("An action film about Los Angeles Police Department officers.");
        movieService.add(endOfWatch);

        Movie spotlight = new Movie("Spotlight");
        spotlight.setDescription("A biographical drama film about investigative journalist unit.");

        CinemaHall firstHall = new CinemaHall();
        cinemaHallService.add(firstHall);
        CinemaHall secondHall = new CinemaHall();
        cinemaHallService.add(secondHall);

        LocalDateTime firstMorningSession = LocalDateTime.parse("2019-09-06T11:00");
        LocalDateTime secondMorningSession = LocalDateTime.parse("2019-09-10T11:00");
        LocalDateTime firstAfternoonSession = LocalDateTime.parse("2019-09-06T16:00");
        LocalDateTime secondAfternoonSession = LocalDateTime.parse("2019-09-10T16:00");
        LocalDateTime firstEveningSession = LocalDateTime.parse("2019-09-06T19:00");
        LocalDateTime secondEveningSession = LocalDateTime.parse("2019-09-10T19:00");

        MovieSession fastAndFuriousMorningSession = new MovieSession(
                fastAndFurious, firstHall, firstMorningSession);
        MovieSession fastAndFuriousEveningSession = new MovieSession(
                fastAndFurious, secondHall, firstEveningSession);
        MovieSession neerjaAfternoonSession = new MovieSession(
                neerja, firstHall, firstAfternoonSession);
        MovieSession united93MorningSession = new MovieSession(
                united93, firstHall, secondMorningSession);
        MovieSession endOfWatchAfternoonSession = new MovieSession(
                endOfWatch, secondHall, secondAfternoonSession);
        MovieSession endOfWatchEveningSession = new MovieSession(
                endOfWatch, firstHall, secondEveningSession);

        movieSessionService.add(fastAndFuriousMorningSession);
        movieSessionService.add(fastAndFuriousEveningSession);
        movieSessionService.add(neerjaAfternoonSession);
        movieSessionService.add(united93MorningSession);
        movieSessionService.add(endOfWatchAfternoonSession);
        movieSessionService.add(endOfWatchEveningSession);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println(cinemaHallService.get(1L));
        cinemaHallService.getAll().forEach(System.out::println);

        movieSessionService.get(4L);
        LocalDate date = LocalDate.parse(DATE);
        movieSessionService.findAvailableSessions(4L, date)
                .forEach(System.out::println);
    }
}
