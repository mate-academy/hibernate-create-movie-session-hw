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
    private static final int SUPER_CINEMA_HALL_CAPACITY = 250;
    private static final int NOT_SUPER_CINEMA_HALL_CAPACITY = 500;

    public static void main(String[] args) {
        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie kimiNoNawa = new Movie("Kimi no Nawa");
        kimiNoNawa.setDescription("An action animation film about time travel and romantic");
        movieService.add(kimiNoNawa);

        Movie fiveCentimetersPerSecond = new Movie("5 Centimeters per Second");
        fiveCentimetersPerSecond.setDescription("An action animation dramatic film "
                + "about two lovers that can't be together");
        movieService.add(fiveCentimetersPerSecond);

        CinemaHall superCinemaHall = new CinemaHall();
        superCinemaHall.setCapacity(SUPER_CINEMA_HALL_CAPACITY);
        superCinemaHall.setDescription("Super Cinema Hall!");
        cinemaHallService.add(superCinemaHall);

        CinemaHall notSuperCinemaHall = new CinemaHall();
        notSuperCinemaHall.setCapacity(NOT_SUPER_CINEMA_HALL_CAPACITY);
        notSuperCinemaHall.setDescription("Not Super Cinema Hall...");
        cinemaHallService.add(notSuperCinemaHall);

        MovieSession firstSession = new MovieSession();
        firstSession.setMovie(fastAndFurious);
        firstSession.setCinemaHall(superCinemaHall);
        firstSession.setShowTime(LocalDateTime.now().plusDays(2).plusHours(1));
        movieSessionService.add(firstSession);

        MovieSession secondSession = new MovieSession();
        secondSession.setMovie(kimiNoNawa);
        secondSession.setCinemaHall(notSuperCinemaHall);
        secondSession.setShowTime(LocalDateTime.now().plusDays(2).plusHours(1));
        movieSessionService.add(secondSession);

        MovieSession thirdSession = new MovieSession();
        thirdSession.setMovie(fiveCentimetersPerSecond);
        thirdSession.setCinemaHall(superCinemaHall);
        thirdSession.setShowTime(LocalDateTime.now().plusDays(2).plusHours(4));
        movieSessionService.add(thirdSession);

        MovieSession fourthSession = new MovieSession();
        fourthSession.setMovie(kimiNoNawa);
        fourthSession.setCinemaHall(superCinemaHall);
        fourthSession.setShowTime(LocalDateTime.now().plusDays(3).plusHours(1));
        movieSessionService.add(fourthSession);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println(movieSessionService.get(firstSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(kimiNoNawa.getId(),
                LocalDate.now().plusDays(2)));
        System.out.println(movieSessionService.findAvailableSessions(kimiNoNawa.getId(),
                LocalDate.now().plusDays(3)));
    }
}
