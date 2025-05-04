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

        Movie wolfFromWallStreet = new Movie("Wolf from Wall Street");
        wolfFromWallStreet.setDescription("Leonardo DiCaprio");
        movieService.add(wolfFromWallStreet);

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall blueHall = new CinemaHall(60, "Cute blue hall");
        cinemaHallService.add(blueHall);

        CinemaHall yellowHall = new CinemaHall(60, "Cute yellow hall");
        cinemaHallService.add(yellowHall);

        LocalDateTime filmsShowTime = LocalDateTime.of(2023, 8, 15, 10, 30);
        LocalDateTime filmsShowTime1 = LocalDateTime.of(2023, 8, 15, 14, 30);
        LocalDateTime filmsShowTime2 = LocalDateTime.of(2023, 8, 15, 18, 25);

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);

        MovieSession today = new MovieSession(fastAndFurious, blueHall, filmsShowTime);
        movieSessionService.add(today);
        MovieSession today1 = new MovieSession(fastAndFurious, blueHall, filmsShowTime1);
        movieSessionService.add(today1);
        MovieSession today2 = new MovieSession(fastAndFurious, blueHall, filmsShowTime2);
        movieSessionService.add(today2);

        filmsShowTime = LocalDateTime.of(2023, 8, 16, 10, 30);
        filmsShowTime1 = LocalDateTime.of(2023, 8, 16, 14, 30);
        filmsShowTime2 = LocalDateTime.of(2023, 8, 16, 18, 25);

        MovieSession tomorrow = new MovieSession(wolfFromWallStreet, yellowHall, filmsShowTime);
        movieSessionService.add(tomorrow);
        MovieSession tomorrow1 = new MovieSession(wolfFromWallStreet, yellowHall, filmsShowTime1);
        movieSessionService.add(tomorrow1);
        MovieSession tomorrow2 = new MovieSession(wolfFromWallStreet, yellowHall, filmsShowTime2);
        movieSessionService.add(tomorrow2);

        LocalDate localDate = LocalDate.of(2023, 8, 15);

        for (long l = 1L; l < 7L; l++) {
            System.out.println(movieSessionService.get(l));
        }
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), localDate)
                .forEach(System.out::println);
        localDate = LocalDate.of(2023, 8, 16);
        movieSessionService.findAvailableSessions(wolfFromWallStreet.getId(), localDate)
                .forEach(System.out::println);
        movieSessionService.get(0L);
    }
}
