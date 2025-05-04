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
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(200);
        firstCinemaHall.setDescription("Usual");
        cinemaHallService.add(firstCinemaHall);
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));

        CinemaHall vipCinemaHall = new CinemaHall();
        vipCinemaHall.setCapacity(100);
        vipCinemaHall.setDescription("Vip");
        cinemaHallService.add(vipCinemaHall);
        System.out.println(cinemaHallService.get(vipCinemaHall.getId()));

        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession mondayMovieSession1 = new MovieSession();
        mondayMovieSession1.setMovie(fastAndFurious);
        mondayMovieSession1.setCinemaHall(firstCinemaHall);
        mondayMovieSession1.setShowTime(LocalDateTime
                .of(2023, 11, 13, 12,15));
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(mondayMovieSession1);
        System.out.println(movieSessionService.get(mondayMovieSession1.getId()));

        MovieSession mondayMovieSession2 = new MovieSession();
        mondayMovieSession2.setMovie(fastAndFurious);
        mondayMovieSession2.setCinemaHall(vipCinemaHall);
        mondayMovieSession2.setShowTime(LocalDateTime
                .of(2023,11,13, 20,15));
        movieSessionService.add(mondayMovieSession2);
        System.out.println(movieSessionService.get(mondayMovieSession2.getId()));

        MovieSession sundayMovieSession1 = new MovieSession();
        sundayMovieSession1.setMovie(fastAndFurious);
        sundayMovieSession1.setCinemaHall(vipCinemaHall);
        sundayMovieSession1.setShowTime(LocalDateTime
                .of(2023, 11, 19, 12, 30));
        movieSessionService.add(sundayMovieSession1);
        System.out.println(movieSessionService.get(sundayMovieSession1.getId()));

        MovieSession sundayMovieSession2 = new MovieSession();
        sundayMovieSession2.setMovie(fastAndFurious);
        sundayMovieSession2.setCinemaHall(firstCinemaHall);
        sundayMovieSession2.setShowTime(LocalDateTime
                .of(2023, 11, 19, 19, 15));
        movieSessionService.add(sundayMovieSession2);
        System.out.println(movieSessionService.get(sundayMovieSession2.getId()));

        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2023,11,19))
                .forEach(System.out::println);
    }
}
