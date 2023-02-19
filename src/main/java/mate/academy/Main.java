package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final int SHOWTIME_DAYS = 3;
    private static final int MOVIE_SESSIONS_PER_DAY = 2;
    private static final int INITIAL_SHOWTIME_HOURS = 16;
    private static final int INITIAL_SHOWTIME_MINUTES = 30;
    private static final int HOURS_BETWEEN_MOVIE_SESSIONS = 2;
    private static final int MINUTES_BETWEEN_MOVIE_SESSIONS = 30;

    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and FAMILY. "
                + "Family is everything! Salute mi Familia!");
        System.out.println();
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("All movies from DB:");
        movieService.getAll().forEach(System.out::println);

        CinemaHall imaxHall = new CinemaHall();
        imaxHall.setCapacity(350);
        imaxHall.setDescription("Go deeper into the world’s most innovative movie-going "
                + "experience");
        System.out.println();
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(imaxHall);
        System.out.println(movieService.get(imaxHall.getId()));
        System.out.println("All cinema halls from DB:");
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println();
        MovieSession movieSession;
        LocalDateTime initialShowTime = LocalDateTime.now()
                .toLocalDate()
                .atTime(LocalTime.MIN)
                .plusHours(INITIAL_SHOWTIME_HOURS)
                .plusMinutes(INITIAL_SHOWTIME_MINUTES);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        for (int i = 0; i < SHOWTIME_DAYS; i++) {
            initialShowTime = initialShowTime.plusDays(i == 0 ? 0 : 1);
            for (int j = 0; j < MOVIE_SESSIONS_PER_DAY; j++) {
                movieSession = new MovieSession();
                movieSession.setMovie(fastAndFurious);
                movieSession.setCinemaHall(imaxHall);
                movieSession.setShowTime(initialShowTime
                        .plusHours(j * HOURS_BETWEEN_MOVIE_SESSIONS)
                        .plusMinutes(j == 0 ? 0 : MINUTES_BETWEEN_MOVIE_SESSIONS));
                movieSessionService.add(movieSession);
                System.out.println(movieSessionService.get(movieSession.getId()));
            }
        }
        System.out.println("All available movie sessions from DB:");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                        LocalDate.now().plusDays(1))
                .forEach(System.out::println);
    }
}
