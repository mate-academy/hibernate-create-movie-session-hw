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

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie homeALone = new Movie("Home ALone");
        homeALone.setDescription("An eight-year-old troublemaker, mistakenly left home alone");
        movieService.add(homeALone);

        Movie harryPotter = new Movie("Harry Potter");
        harryPotter.setDescription("An orphaned boy enrolls in a school of wizardry");
        movieService.add(harryPotter);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);

        CinemaHall multiplex = new CinemaHall(250, "Multiplex");
        cinemaHallService.add(multiplex);

        CinemaHall kinoLand = new CinemaHall(300, "KinoLand");
        cinemaHallService.add(kinoLand);

        CinemaHall cinemaciti = new CinemaHall(350, "Сінема сіті");
        cinemaHallService.add(cinemaciti);

        System.out.println(cinemaHallService.get(kinoLand.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession mondaySessionAM = new MovieSession();
        mondaySessionAM.setCinemaHall(kinoLand);
        mondaySessionAM.setMovie(harryPotter);
        mondaySessionAM.setLocalDateTime(LocalDateTime.of(2024, 02, 12, 9, 30));

        MovieSession mondaySessionPM = new MovieSession();
        mondaySessionPM.setCinemaHall(kinoLand);
        mondaySessionPM.setMovie(harryPotter);
        mondaySessionPM.setLocalDateTime(LocalDateTime.of(2024, 02, 12, 16, 30));

        MovieSession sundaySessionPM = new MovieSession();
        sundaySessionPM.setCinemaHall(multiplex);
        sundaySessionPM.setMovie(harryPotter);
        sundaySessionPM.setLocalDateTime(LocalDateTime.of(2024, 02, 11, 16, 30));

        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(mondaySessionAM);
        movieSessionService.add(mondaySessionPM);
        movieSessionService.add(sundaySessionPM);

        System.out.println(movieSessionService.get(mondaySessionPM.getId()));
        movieSessionService.findAvailableSessions(harryPotter.getId(),
                LocalDate.of(2024, 02, 12)).forEach(System.out::println);
    }
}
