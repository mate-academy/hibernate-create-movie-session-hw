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

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        System.out.println("Movies");

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie cyberPank = new Movie("Cyber Pank 2077");
        cyberPank.setDescription("A sci-fi action about cyber humanity.");
        movieService.add(cyberPank);
        System.out.println(movieService.get(cyberPank.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println("CinemaHall");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall bigHall = new CinemaHall(350);
        bigHall.setDescription("biggest hall");
        cinemaHallService.add(bigHall);

        CinemaHall mediumHall = new CinemaHall(250);
        mediumHall.setDescription("medium hall");
        cinemaHallService.add(mediumHall);
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("MovieSession");

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        LocalDateTime terminatorDateTime = LocalDateTime.now();
        terminatorDateTime = terminatorDateTime.minusDays(1L);
        MovieSession terminatorSession = new MovieSession(cyberPank, bigHall, terminatorDateTime);
        movieSessionService.add(terminatorSession);

        LocalDateTime furiousDateTime = LocalDateTime.now();
        furiousDateTime = furiousDateTime.minusDays(2L);
        MovieSession furiousSession = new MovieSession(fastAndFurious, mediumHall, furiousDateTime);
        movieSessionService.add(furiousSession);
        movieSessionService.findAvailableSessions(cyberPank.getId(),
                        LocalDate.now().minusDays(1L))
                .forEach(System.out::println);
    }
}
