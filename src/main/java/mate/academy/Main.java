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
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cronverkCinemaVipHall = new CinemaHall();
        cronverkCinemaVipHall.setCapacity(8);
        cronverkCinemaVipHall.setDescription("VIP cinema hall");
        cinemaHallService.add(cronverkCinemaVipHall);
        System.out.println(cinemaHallService.get(cronverkCinemaVipHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        LocalDate dateForCinema = LocalDate.of(2022, 2, 24);
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cronverkCinemaVipHall);
        LocalDateTime showTime = dateForCinema.atTime(4,30);
        movieSession.setShowTime(showTime);

        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), dateForCinema)
                .forEach(System.out::println);
    }
}
