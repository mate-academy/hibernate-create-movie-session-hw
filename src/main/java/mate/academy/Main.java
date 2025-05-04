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
    private static final Injector injector = Injector
            .getInstance("mate.academy");
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector
                    .getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        System.out.println("--------------");
        CinemaHall oscar = new CinemaHall();
        oscar.setCapacity(100);
        oscar.setDescription("Dolby Atmos sound");
        CinemaHall planetaKino = new CinemaHall();
        planetaKino.setCapacity(130);
        planetaKino.setDescription("Imax");
        cinemaHallService.add(oscar);
        cinemaHallService.add(planetaKino);
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("--------------");
        MovieSession fastAndFuriousOscar = new MovieSession();
        fastAndFuriousOscar.setShowTime(LocalDateTime.of(2021, 10, 27, 12, 30));
        fastAndFuriousOscar.setCinemaHall(oscar);
        fastAndFuriousOscar.setMovie(fastAndFurious);
        MovieSession fastAndFuriousPlaneta = new MovieSession();
        fastAndFuriousPlaneta.setShowTime(LocalDateTime.of(2021, 10, 28, 13, 30));
        fastAndFuriousPlaneta.setMovie(fastAndFurious);
        fastAndFuriousPlaneta.setCinemaHall(planetaKino);
        movieSessionService.add(fastAndFuriousOscar);
        movieSessionService.add(fastAndFuriousPlaneta);
        System.out.println("--------");
        movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.of(2021, 10, 27))
                .forEach(System.out::println);
    }
}
