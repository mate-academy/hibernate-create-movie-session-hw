package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
        // Inject services
        final MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        // Test MovieService
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie inception = new Movie("Inception");
        inception.setDescription("A mind-bending thriller about dream manipulation.");
        movieService.add(inception);

        Movie avatar = new Movie("Avatar");
        avatar.setDescription("An epic science fiction film about a distant world.");
        movieService.add(avatar);

        System.out.println("Movies:");
        movieService.getAll().forEach(System.out::println);

        // Test CinemaHallService
        CinemaHall bigHall = new CinemaHall();
        bigHall.setCapacity(200);
        bigHall.setDescription("Large cinema hall with surround sound.");
        cinemaHallService.add(bigHall);

        CinemaHall smallHall = new CinemaHall();
        smallHall.setCapacity(50);
        smallHall.setDescription("Small cinema hall for private screenings.");
        cinemaHallService.add(smallHall);

        System.out.println("Cinema Halls:");
        cinemaHallService.getAll().forEach(System.out::println);

        // Test MovieSessionService
        MovieSession fastAndFuriousSession1 = new MovieSession();
        fastAndFuriousSession1.setMovie(fastAndFurious);
        fastAndFuriousSession1.setCinemaHall(bigHall);
        fastAndFuriousSession1.setShowTime(LocalDateTime.of(2024, 12, 10, 19, 0));
        movieSessionService.add(fastAndFuriousSession1);

        MovieSession fastAndFuriousSession2 = new MovieSession();
        fastAndFuriousSession2.setMovie(fastAndFurious);
        fastAndFuriousSession2.setCinemaHall(smallHall);
        fastAndFuriousSession2.setShowTime(LocalDateTime.of(2024, 12, 11, 20, 0));
        movieSessionService.add(fastAndFuriousSession2);

        MovieSession inceptionSession1 = new MovieSession();
        inceptionSession1.setMovie(inception);
        inceptionSession1.setCinemaHall(smallHall);
        inceptionSession1.setShowTime(LocalDateTime.of(2024, 12, 10, 21, 0));
        movieSessionService.add(inceptionSession1);

        MovieSession inceptionSession2 = new MovieSession();
        inceptionSession2.setMovie(inception);
        inceptionSession2.setCinemaHall(bigHall);
        inceptionSession2.setShowTime(LocalDateTime.of(2024, 12, 12, 18, 30));
        movieSessionService.add(inceptionSession2);

        MovieSession avatarSession1 = new MovieSession();
        avatarSession1.setMovie(avatar);
        avatarSession1.setCinemaHall(bigHall);
        avatarSession1.setShowTime(LocalDateTime.of(2024, 12, 10, 15, 30));
        movieSessionService.add(avatarSession1);

        MovieSession avatarSession2 = new MovieSession();
        avatarSession2.setMovie(avatar);
        avatarSession2.setCinemaHall(smallHall);
        avatarSession2.setShowTime(LocalDateTime.of(2024, 12, 11, 18, 30));
        movieSessionService.add(avatarSession2);

        // Test findAvailableSessions method
        System.out.println("Movie Sessions on 2024-12-10:");
        List<MovieSession> sessionsOn10 = movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.of(2024, 12, 10));
        sessionsOn10.forEach(System.out::println);

        System.out.println("Movie Sessions on 2024-12-11:");
        List<MovieSession> sessionsOn11 = movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.of(2024, 12, 11));
        sessionsOn11.forEach(System.out::println);

        System.out.println("Movie Sessions on 2024-12-12:");
        List<MovieSession> sessionsOn12 = movieSessionService.findAvailableSessions(
                inception.getId(), LocalDate.of(2024, 12, 12));
        sessionsOn12.forEach(System.out::println);
    }
}
