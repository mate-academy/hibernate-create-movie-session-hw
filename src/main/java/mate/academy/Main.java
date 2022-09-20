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
        matrix.setDescription("An action film about illusions of our life."
                + "The Wachowski brothers (now sisters) probably read too much Castaneda.");
        movieService.add(matrix);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("________________All Movies___________________");
        movieService.getAll().forEach(System.out::println);

        CinemaHall bigHall = new CinemaHall();
        bigHall.setCapacity(250);
        bigHall.setDescription("The big hall is located on the first floor");
        cinemaHallService.add(bigHall);
        CinemaHall comfortableHall = new CinemaHall();
        comfortableHall.setCapacity(50);
        comfortableHall.setDescription("The hall with excellent seats"
                + " is located on the ground floor near the bar.");
        cinemaHallService.add(comfortableHall);

        MovieSession daySessionForsage = new MovieSession();
        daySessionForsage.setMovie(fastAndFurious);
        daySessionForsage.setCinemaHall(bigHall);
        daySessionForsage.setShowTime(LocalDateTime.of(2022, 9, 20, 12, 00));
        movieSessionService.add(daySessionForsage);

        MovieSession eveningSessionForsage = new MovieSession();
        eveningSessionForsage.setMovie(fastAndFurious);
        eveningSessionForsage.setCinemaHall(comfortableHall);
        eveningSessionForsage.setShowTime(LocalDateTime.of(2022, 9, 20, 20, 00));
        movieSessionService.add(eveningSessionForsage);

        MovieSession daySessionMatrix = new MovieSession();
        daySessionMatrix.setMovie(matrix);
        daySessionMatrix.setCinemaHall(comfortableHall);
        daySessionMatrix.setShowTime(LocalDateTime.of(2022, 9, 20, 12, 00));
        movieSessionService.add(daySessionMatrix);

        MovieSession eveningSessionMatrix = new MovieSession();
        eveningSessionMatrix.setMovie(matrix);
        eveningSessionMatrix.setCinemaHall(bigHall);
        eveningSessionMatrix.setShowTime(LocalDateTime.of(2022, 9, 20, 20, 00));
        movieSessionService.add(eveningSessionMatrix);

        System.out.println("________________Sessions of the Matrix Movie___________________");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, 9, 20)).forEach(System.out::println);
    }
}
