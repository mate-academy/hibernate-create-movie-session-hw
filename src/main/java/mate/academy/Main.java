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
    private static final int HALL_CAPACITY = 20;
    private static final Injector injector = Injector.getInstance("mate.academy");
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

        CinemaHall redVipHall = new CinemaHall();
        redVipHall.setCapacity(HALL_CAPACITY);
        redVipHall.setDescription("Hall #7 on schema");
        cinemaHallService.add(redVipHall);
        System.out.println(cinemaHallService.get(redVipHall.getId()));
        CinemaHall blue3DHall = new CinemaHall();
        blue3DHall.setCapacity(HALL_CAPACITY);
        blue3DHall.setDescription("Hall #2 on schema");
        cinemaHallService.add(blue3DHall);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSessionFirst = new MovieSession();
        LocalDateTime showTime = LocalDateTime.now();
        movieSessionFirst.setMovie(fastAndFurious);
        movieSessionFirst.setCinemaHall(redVipHall);
        movieSessionFirst.setShowTime(showTime);
        movieSessionService.add(movieSessionFirst);
        movieSessionService.get(movieSessionFirst.getId());
        MovieSession movieSessionSecond = new MovieSession();
        movieSessionSecond.setMovie(fastAndFurious);
        movieSessionSecond.setCinemaHall(blue3DHall);
        movieSessionSecond.setShowTime(showTime.plusHours(2));
        movieSessionService.add(movieSessionSecond);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
