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
        Movie fastAndFurious9 = new Movie("Fast and Furious 9");
        fastAndFurious9.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious9);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        CinemaHall liniaKino = new CinemaHall();
        liniaKino.setCapacity(350);
        liniaKino.setDescription("Bla bla bla liniaKino");
        cinemaHallService.add(liniaKino);
        System.out.println(cinemaHallService.get(liniaKino.getId()));
        CinemaHall magelan = new CinemaHall();
        magelan.setCapacity(450);
        magelan.setDescription("Bla bla bla magelan");
        cinemaHallService.add(magelan);
        System.out.println(cinemaHallService.get(magelan.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession sessionFirst = new MovieSession();
        sessionFirst.setMovie(fastAndFurious);
        sessionFirst.setCinemaHall(liniaKino);
        sessionFirst.setShowTime(LocalDateTime.now());
        MovieSession sessionSecond = new MovieSession();
        sessionSecond.setMovie(fastAndFurious9);
        sessionSecond.setCinemaHall(magelan);
        sessionSecond.setShowTime(LocalDateTime.now().minusDays(1));
        MovieSession sessionThird = new MovieSession();
        sessionThird.setMovie(fastAndFurious);
        sessionThird.setCinemaHall(magelan);
        sessionThird.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(sessionFirst);
        movieSessionService.add(sessionSecond);
        movieSessionService.add(sessionThird);
        System.out.println(movieSessionService.get(sessionSecond.getId()));
        System.out.println("test");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()).forEach(System.out::println);
    }
}
