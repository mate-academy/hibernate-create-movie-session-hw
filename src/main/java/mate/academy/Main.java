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
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService hallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall greenHall = new CinemaHall(150, "Green Hall");
        CinemaHall whiteHall = new CinemaHall(20, "White Hall");
        hallService.add(greenHall);
        hallService.add(whiteHall);
        System.out.println(hallService.get(whiteHall.getId()));
        hallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        LocalDateTime firstTime = LocalDateTime.of(2023, 1, 1, 12, 0);
        LocalDateTime secondTime = LocalDateTime.of(2023, 1, 1, 19, 0);
        LocalDateTime thirdTime = LocalDateTime.of(2023, 1, 2, 13, 0);
        MovieSession firstGreenSession = new MovieSession(fastAndFurious, greenHall, firstTime);
        MovieSession firstWhiteSession = new MovieSession(fastAndFurious, whiteHall, firstTime);
        MovieSession secondWhiteSession = new MovieSession(fastAndFurious, whiteHall, secondTime);
        MovieSession thirdGreenSession = new MovieSession(fastAndFurious, greenHall, thirdTime);
        movieSessionService.add(firstGreenSession);
        movieSessionService.add(firstWhiteSession);
        movieSessionService.add(secondWhiteSession);
        movieSessionService.add(thirdGreenSession);

        System.out.println(movieSessionService.get(firstWhiteSession.getId()));

        LocalDate currentDate = LocalDate.of(2023, 1, 1);
        int size = movieSessionService.findAvailableSessions(fastAndFurious.getId(),currentDate)
                .size();
        System.out.println(size);

    }
}
