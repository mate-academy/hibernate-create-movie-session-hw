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
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println("________Get_movie________");
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("________Get_all_movies________");
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(80);
        cinemaHall.setDescription("A special feature of this cinema "
                + "hall is the sofas in the back row");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);

        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setCinemaHall(cinemaHall);
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setShowTime(LocalDateTime.of(
                LocalDate.now(), LocalTime.of(19, 30)));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousSession);
        movieSessionService.findAvailableSessions(
                fastAndFuriousSession.getId(), LocalDate.of(2022, 7, 22));
        movieSessionService.findAvailableSessions(fastAndFuriousSession.getId(), LocalDate.now());
        System.out.println("________Get_cinema_hall________");
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("________Get_all_cinema_halls________");
        System.out.println(movieSessionService.get(fastAndFuriousSession.getId()));
    }
}
