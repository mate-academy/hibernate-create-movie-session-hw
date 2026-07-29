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
        MovieService movieService = (MovieService)
                injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println("\n--- Testing CinemaHall ---");
        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        CinemaHall premiumHall = new CinemaHall();
        premiumHall.setCapacity(50);
        premiumHall.setDescription("Premium IMAX Hall with VIP seats");
        cinemaHallService.add(premiumHall);

        System.out.println("Get cinema hall by id: " + cinemaHallService.get(premiumHall.getId()));
        System.out.println("All cinema halls:");
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession sessionToday = new MovieSession();
        sessionToday.setMovie(fastAndFurious);
        sessionToday.setCinemaHall(premiumHall);
        sessionToday.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(sessionToday);

        MovieSession sessionTomorrow = new MovieSession();
        sessionTomorrow.setMovie(fastAndFurious);
        sessionTomorrow.setCinemaHall(premiumHall);
        sessionTomorrow.setShowTime(LocalDateTime.now().plusDays(1));
        movieSessionService.add(sessionTomorrow);

        System.out.println("Available sessions for TODAY:");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
