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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie mulan = new Movie("Mulan");
        mulan.setDescription("Cartoon Mulan");
        movieService.add(mulan);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHallKiev = new CinemaHall();
        cinemaHallKiev.setCapacity(200);
        cinemaHallKiev.setDescription("Big hall");
        cinemaHallService.add(cinemaHallKiev);
        CinemaHall cinemaHallLviv = new CinemaHall();
        cinemaHallLviv.setCapacity(30);
        cinemaHallLviv.setDescription("Small hall");
        cinemaHallService.add(cinemaHallLviv);

        MovieSession movieSessionToday = new MovieSession();
        movieSessionToday.setMovie(mulan);
        movieSessionToday.setCinemaHall(cinemaHallLviv);
        movieSessionToday.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSessionToday);
        MovieSession movieSessionTomorrow = new MovieSession();
        movieSessionTomorrow.setMovie(fastAndFurious);
        movieSessionTomorrow.setCinemaHall(cinemaHallKiev);
        movieSessionTomorrow.setShowTime(LocalDateTime.now().plusDays(2));
        movieSessionService.add(movieSessionTomorrow);
        MovieSession movieSessionTodayEvening = new MovieSession();
        movieSessionTodayEvening.setMovie(fastAndFurious);
        movieSessionTodayEvening.setCinemaHall(cinemaHallKiev);
        movieSessionTodayEvening.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSessionTodayEvening);
        MovieSession movieSessionYesterday = new MovieSession();
        movieSessionYesterday.setMovie(fastAndFurious);
        movieSessionYesterday.setCinemaHall(cinemaHallLviv);
        movieSessionYesterday.setShowTime(LocalDateTime.now().minusDays(1));
        movieSessionService.add(movieSessionYesterday);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println(cinemaHallService.get(cinemaHallKiev.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println(movieSessionService.get(movieSessionToday.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()).forEach(System.out::println);
    }
}
