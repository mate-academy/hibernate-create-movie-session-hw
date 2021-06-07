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
    private static Injector injector = Injector.getInstance("mate");
    private static MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie avatar = new Movie("Avatar");
        avatar.setTitle("resource corporation threatens the existence of"
                + " a local tribe of humanoid sentient beings");
        movieService.add(avatar);

        CinemaHall multiplexLavina = new CinemaHall();
        multiplexLavina.setCapacity(120);
        multiplexLavina.setDescription("Berkovetska Street, 6D, Kyiv");
        cinemaHallService.add(multiplexLavina);

        CinemaHall multiplex = new CinemaHall();
        multiplex.setCapacity(60);
        multiplex.setDescription("Berkovetska Street, 5D, Kyiv");
        cinemaHallService.add(multiplex);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(avatar);
        firstMovieSession.setCinemaHall(multiplexLavina);
        firstMovieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(firstMovieSession);

        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(fastAndFurious);
        secondMovieSession.setCinemaHall(multiplexLavina);
        secondMovieSession.setShowTime(LocalDateTime.of(2021,6, 15, 15, 0));
        movieSessionService.add(secondMovieSession);

        System.out.println("Test.....cinemaHall");
        System.out.println(cinemaHallService.get(multiplex.getId()));
        System.out.println(cinemaHallService.get(multiplexLavina.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("Test....movieService");
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(avatar.getId()));
        movieService.getAll().forEach(System.out::println);
        System.out.println("Test.....movieSession");
        System.out.println(movieSessionService.get(firstMovieSession.getId()));
        System.out.println(movieSessionService.get(secondMovieSession.getId()));
        movieSessionService.findAvailableSessions(avatar.getId(),
                LocalDate.of(2021,06,15)).forEach(System.out::println);
    }
}
