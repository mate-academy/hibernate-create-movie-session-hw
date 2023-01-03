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

    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie howToTrainYourDragon = new Movie("How to train your Dragon");
        howToTrainYourDragon.setDescription("Movie about doy and a dragon");
        movieService.add(fastAndFurious);
        movieService.add(howToTrainYourDragon);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(20);
        firstCinemaHall.setDescription("the smallest cinema hall");
        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(60);
        secondCinemaHall.setDescription("the biggest cinema hall");
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(firstCinemaHall);
        cinemaHallService.add(secondCinemaHall);
        System.out.printf(cinemaHallService.get(firstCinemaHall.getId()).toString());
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(fastAndFurious);
        firstMovieSession.setCinemaHall(firstCinemaHall);
        firstMovieSession.setShowTime(LocalDateTime.of(LocalDate.of(2022,12,31),
                LocalTime.of(11, 0)));
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(firstMovieSession);
        System.out.println(movieSessionService.get(firstMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, 12, 31)));
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()));
    }
}
