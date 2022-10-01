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
        Movie superDuperMovie = new Movie("Super movie");
        superDuperMovie.setDescription("So goood");
        movieService.add(superDuperMovie);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("___________________________________");
        movieService.getAll().forEach(System.out::println);

        CinemaHall redRoom = new CinemaHall();
        redRoom.setCapacity(250);
        redRoom.setDescription("Have a dress-code");
        cinemaHallService.add(redRoom);
        CinemaHall comfortableHall = new CinemaHall();
        comfortableHall.setCapacity(50);
        comfortableHall.setDescription(
                "Jacuzzi vibro chairs and free beer for our lovely consumers");
        cinemaHallService.add(comfortableHall);

        MovieSession daySessionForsage = new MovieSession();
        daySessionForsage.setMovie(fastAndFurious);
        daySessionForsage.setCinemaHall(redRoom);
        daySessionForsage.setShowTime(LocalDateTime.of(2022, 9, 20, 12, 00));
        movieSessionService.add(daySessionForsage);

        MovieSession eveningSessionForsage = new MovieSession();
        eveningSessionForsage.setMovie(fastAndFurious);
        eveningSessionForsage.setCinemaHall(comfortableHall);
        eveningSessionForsage.setShowTime(LocalDateTime.of(2022, 9, 20, 20, 00));
        movieSessionService.add(eveningSessionForsage);

        MovieSession daySessionSuperDuperMovie = new MovieSession();
        daySessionSuperDuperMovie.setMovie(superDuperMovie);
        daySessionSuperDuperMovie.setCinemaHall(comfortableHall);
        daySessionSuperDuperMovie.setShowTime(LocalDateTime.of(2022, 9, 20, 12, 00));
        movieSessionService.add(daySessionSuperDuperMovie);

        MovieSession eveningSessionSuperDuperMovie = new MovieSession();
        eveningSessionSuperDuperMovie.setMovie(superDuperMovie);
        eveningSessionSuperDuperMovie.setCinemaHall(redRoom);
        eveningSessionSuperDuperMovie.setShowTime(LocalDateTime.of(2022, 9, 20, 20, 00));
        movieSessionService.add(eveningSessionSuperDuperMovie);

        System.out.println("________________FIND_SESSION___________________");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, 9, 20)).forEach(System.out::println);
    }
}
