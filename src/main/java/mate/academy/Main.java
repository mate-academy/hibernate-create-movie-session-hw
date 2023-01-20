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
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie avatarTwo = new Movie("Avatar 2");
        avatarTwo.setDescription("An action film with a great visual effects.");
        Movie madMax = new Movie("Mad Max");
        madMax.setDescription("Pure madness on wheels.");

        CinemaHall bigCinemaHall = new CinemaHall();
        bigCinemaHall.setDescription("Big hall");
        bigCinemaHall.setCapacity(150);
        CinemaHall middleCinemaHall = new CinemaHall();
        middleCinemaHall.setDescription("Middle hall");
        middleCinemaHall.setCapacity(75);
        CinemaHall smallCinemaHall = new CinemaHall();
        smallCinemaHall.setDescription("Small hall");
        smallCinemaHall.setCapacity(30);

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.add(avatarTwo);
        movieService.add(madMax);

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(bigCinemaHall);
        cinemaHallService.add(middleCinemaHall);
        cinemaHallService.add(smallCinemaHall);

        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setCinemaHall(middleCinemaHall);
        fastAndFuriousSession.setShowTime(LocalDateTime.of(2023, 1,21,11,0));

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousSession);
        fastAndFuriousSession.setShowTime(LocalDateTime.of(2023, 1,21,14,0));
        movieSessionService.add(fastAndFuriousSession);
        fastAndFuriousSession.setShowTime(LocalDateTime.of(2023, 1,22,18,30));
        movieSessionService.add(fastAndFuriousSession);

        MovieSession avatarSession = new MovieSession();
        avatarSession.setMovie(avatarTwo);
        avatarSession.setCinemaHall(bigCinemaHall);
        avatarSession.setShowTime(LocalDateTime.of(2023, 1,21,13,0));
        movieSessionService.add(avatarSession);
        avatarSession.setShowTime(LocalDateTime.of(2023, 1,21,17,0));
        movieSessionService.add(avatarSession);
        avatarSession.setShowTime(LocalDateTime.of(2023, 1,22,13,0));
        movieSessionService.add(avatarSession);

        MovieSession madMaxSession = new MovieSession();
        madMaxSession.setMovie(madMax);
        madMaxSession.setCinemaHall(smallCinemaHall);
        madMaxSession.setShowTime(LocalDateTime.of(2023, 1,21,19,0));
        movieSessionService.add(madMaxSession);
        madMaxSession.setShowTime(LocalDateTime.of(2023, 1,21,21,30));
        movieSessionService.add(madMaxSession);
        madMaxSession.setShowTime(LocalDateTime.of(2023, 1,22,19,00));
        movieSessionService.add(madMaxSession);

        System.out.println(movieService.get(1L));
        System.out.println(cinemaHallService.get(2L));
        System.out.println(movieSessionService.get(1L));

        System.out.println(movieService.getAll());
        System.out.println(cinemaHallService.getAll());
        System.out.println(movieSessionService
                .findAvailableSessions(1L, LocalDate.parse("2023-01-21")));
    }
}
