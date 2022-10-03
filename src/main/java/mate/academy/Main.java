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
    private static final Injector injector
            = Injector.getInstance("mate.academy");
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie black_adam = new Movie("Black Adam");
        black_adam.setDescription("Nearly 5,000 years after he was bestowed with the almighty powers of the Egyptian gods-and imprisoned just as quickly-Black Adam (Johnson) is freed from his earthly tomb, ready to unleash his unique form of justice on the modern world.");
        Movie the_enforcer = new Movie("The Enforcer");
        the_enforcer.setDescription("An enforcer has to sacrifice everything to save a young girl he has befriended from his femme fatale boss who is involved in cybersex trafficking.");
        movieService.add(black_adam);
        System.out.println(movieService.get(black_adam.getId()));
        movieService.add(the_enforcer);
        movieService.getAll().forEach(System.out::println);

        CinemaHall hallImax = new CinemaHall();
        hallImax.setCapacity(100);
        hallImax.setDescription("IMAX hall");
        CinemaHall luxHall = new CinemaHall();
        luxHall.setCapacity(10);
        luxHall.setDescription("LUX hall");
        cinemaHallService.add(hallImax);
        cinemaHallService.add(luxHall);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession morningSession = new MovieSession();
        morningSession.setCinemaHall(hallImax);
        morningSession.setMovie(the_enforcer);
        morningSession.setShowTime(LocalDateTime.of(2022, 10,
                3, 8, 30));
        MovieSession daySession = new MovieSession();
        daySession.setCinemaHall(hallImax);
        daySession.setMovie(black_adam);
        daySession.setShowTime(LocalDateTime.of(2022, 10,
                3, 13, 00));
        MovieSession eveningSession = new MovieSession();
        eveningSession.setCinemaHall(luxHall);
        eveningSession.setMovie(the_enforcer);
        eveningSession.setShowTime(LocalDateTime.of(2022, 10,
                3, 17, 20));
        MovieSession nightSession = new MovieSession();
        nightSession.setCinemaHall(luxHall);
        nightSession.setMovie(black_adam);
        nightSession.setShowTime(LocalDateTime.of(2022, 10,
                3, 22, 40));
        movieSessionService.add(morningSession);
        movieSessionService.add(daySession);
        movieSessionService.add(eveningSession);
        movieSessionService.add(nightSession);

        System.out.println(movieSessionService.get(daySession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(the_enforcer.getId(),
                LocalDate.of(2022, 10, 3)));
    }
}
