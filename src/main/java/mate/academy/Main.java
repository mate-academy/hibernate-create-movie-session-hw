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
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        Movie killBill = new Movie("Kill Bill");
        killBill.setDescription("The fourth film by Quentin Tarantino");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(killBill);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(killBill.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall zirka = new CinemaHall();
        zirka.setCapacity(200);
        zirka.setDescription("Zirka placed at Kyiv");

        CinemaHall pobeda = new CinemaHall();
        pobeda.setCapacity(300);
        pobeda.setDescription("Pobeda placed at Lviv");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(pobeda);
        cinemaHallService.add(zirka);
        System.out.println(cinemaHallService.get(killBill.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession zirkaBill = new MovieSession();
        zirkaBill.setMovie(killBill);
        zirkaBill.setCinemaHall(zirka);
        zirkaBill.setShowTime(LocalDateTime.of(2022, 7, 7, 16, 0));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(zirkaBill);

        MovieSession zirkaFast = new MovieSession();
        zirkaFast.setMovie(fastAndFurious);
        zirkaFast.setCinemaHall(zirka);
        zirkaFast.setShowTime(LocalDateTime.of(2022, 7, 7, 18, 0));
        movieSessionService.add(zirkaFast);

        MovieSession pobedaBill = new MovieSession();
        pobedaBill.setMovie(killBill);
        pobedaBill.setCinemaHall(pobeda);
        pobedaBill.setShowTime(LocalDateTime.of(2022, 7, 8, 17, 0));
        movieSessionService.add(pobedaBill);

        MovieSession pobedaFast = new MovieSession();
        pobedaFast.setMovie(fastAndFurious);
        pobedaFast.setCinemaHall(pobeda);
        pobedaFast.setShowTime(LocalDateTime.of(2022, 7, 7, 17, 0));
        movieSessionService.add(pobedaFast);

        System.out.println(movieSessionService.get(zirkaBill.getId()));
        movieSessionService.findAvailableSessions(
                killBill.getId(), LocalDate.now()).forEach(System.out::println);
    }
}
