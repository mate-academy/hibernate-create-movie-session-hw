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

        Movie matrix = new Movie("Matrix");
        matrix.setDescription("Have you ever had a dream that you were so sure was real?");
        movieService.add(matrix);

        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(matrix.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall multiplex = new CinemaHall();
        multiplex.setCapacity(150);
        multiplex.setDescription("Halls with places of increased comfort");
        cinemaHallService.add(multiplex);

        CinemaHall multiplex3D = new CinemaHall();
        multiplex3D.setCapacity(50);
        multiplex3D.setDescription("Halls with places of increased comfort and 3D");
        cinemaHallService.add(multiplex3D);

        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setCinemaHall(multiplex);
        fastAndFuriousSession.setShowTime(LocalDateTime.now().plusDays(3L).plusHours(5L));
        movieSessionService.add(fastAndFuriousSession);

        MovieSession matrixSession = new MovieSession();
        matrixSession.setMovie(matrix);
        matrixSession.setCinemaHall(multiplex);
        matrixSession.setShowTime(LocalDateTime.now().plusDays(14L).plusHours(5L));
        movieSessionService.add(matrixSession);

        MovieSession matrixSession3D = new MovieSession();
        matrixSession3D.setMovie(matrix);
        matrixSession3D.setCinemaHall(multiplex3D);
        matrixSession3D.setShowTime(LocalDateTime.now().plusDays(14L).plusHours(3L));
        movieSessionService.add(matrixSession3D);

        movieSessionService.findAvailableSessions(matrix.getId(), LocalDate.now().plusDays(14L))
                .forEach(System.out::println);
    }
}
