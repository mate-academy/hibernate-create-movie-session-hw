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
        Movie starWars = new Movie("星際大戰");
        starWars.setDescription("星際大戰首部曲：威脅潛伏");
        movieService.add(starWars);
        CinemaHall englishHall = new CinemaHall(50);
        CinemaHall chineseHall = new CinemaHall(25);
        englishHall.setDescription("This hall is for people who studying English");
        chineseHall.setDescription("This hall is for people who studying 中文");
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                    .getInstance(CinemaHallService.class);
        cinemaHallService.add(englishHall);
        cinemaHallService.add(chineseHall);
        MovieSession morningChineseSession = new MovieSession();
        morningChineseSession.setCinemaHall(chineseHall);
        morningChineseSession.setMovie(starWars);
        morningChineseSession.setShowTime(LocalDateTime.of(2021, 11, 12, 10, 30));
        MovieSession dayEnglishSession = new MovieSession();
        dayEnglishSession.setCinemaHall(englishHall);
        dayEnglishSession.setMovie(fastAndFurious);
        dayEnglishSession.setShowTime(LocalDateTime.of(2021, 11, 12, 12, 30));
        MovieSession eveningMovieSession = new MovieSession();
        eveningMovieSession.setShowTime(LocalDateTime.of(2021, 11, 12, 19, 30));
        eveningMovieSession.setCinemaHall(chineseHall);
        eveningMovieSession.setMovie(starWars);
        MovieSessionService movieSessionService = (MovieSessionService) injector
                    .getInstance(MovieSessionService.class);
        movieSessionService.add(morningChineseSession);
        movieSessionService.add(dayEnglishSession);
        movieSessionService.add(eveningMovieSession);
        movieSessionService.findAvailableSessions(2L, LocalDate.of(2021, 11, 12))
                    .forEach(System.out::println);
    }
}
