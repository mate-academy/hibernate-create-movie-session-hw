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
    private static final MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        //create movies
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie luck = new Movie("Luck");
        luck.setDescription("Comedy about bad luck girl");
        movieService.add(luck);
        System.out.println(movieService.get(luck.getId()));
        movieService.getAll().forEach(System.out::println);

        //create cinema halls
        CinemaHall redHall = new CinemaHall(200, "Red Hall");
        CinemaHall blueHall = new CinemaHall(300, "Blue Hall");
        cinemaHallService.add(redHall);
        cinemaHallService.add(blueHall);
        System.out.println(cinemaHallService.get(redHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        //create movie sessions
        MovieSession firstSession = new MovieSession();
        firstSession.setCinemaHall(redHall);
        firstSession.setMovie(fastAndFurious);
        firstSession.setShowtime(LocalDateTime.of(2022, 9, 22, 19, 0));
        MovieSession secondSession = new MovieSession();
        secondSession.setCinemaHall(blueHall);
        secondSession.setMovie(luck);
        secondSession.setShowtime(LocalDateTime.of(2022, 9, 22, 19, 0));
        movieSessionService.add(firstSession);
        movieSessionService.add(secondSession);
        System.out.println(movieSessionService.get(secondSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, 9, 22)).forEach(System.out::println);
    }
}
