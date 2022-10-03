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
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        //create movie
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        //save movie
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        //test service
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        //create cinema hall
        CinemaHall vip = new CinemaHall();
        vip.setCapacity(50);
        vip.setDescription("Luxury vip hall");
        //save cinema hall
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(vip);
        //test service
        System.out.println(cinemaHallService.get(vip.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        //create movie session
        MovieSession hotWeekend = new MovieSession();
        hotWeekend.setMovie(fastAndFurious);
        hotWeekend.setCinemaHall(vip);
        hotWeekend.setShowTime(LocalDateTime.now());
        //save movie session
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(hotWeekend);
        //test service
        System.out.println(movieSessionService.get(hotWeekend.getId()));
        System.out.println(movieSessionService.findAvailableSessions(
                fastAndFurious.getId(),
                LocalDate.now()));
    }
}
