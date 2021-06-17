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
        Movie dieHard = new Movie("Die Hard");
        dieHard.setDescription("Twelve terrorists. One cop. The odds are against John McClane... "
                + "That's just the way he likes it.");
        movieService.add(dieHard);
        System.out.println(movieService.get(dieHard.getId()));

        Movie dieHard2 = new Movie("Die Hard 2");
        dieHard2.setDescription("Die harder.");
        movieService.add(dieHard2);
        System.out.println(movieService.get(dieHard2.getId()));

        Movie dieHard3 = new Movie("Die Hard: With a Vengeance");
        dieHard3.setDescription("On a good day he's a great cop. On a bad day he's the best there "
                + "is.");
        movieService.add(dieHard3);
        System.out.println(movieService.get(dieHard3.getId()));

        System.out.println("A list of movies to watch!");
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(30);
        firstHall.setDescription("Cinema hall New York");
        cinemaHallService.add(firstHall);
        System.out.println(cinemaHallService.get(firstHall.getId()));

        CinemaHall secondHall = new CinemaHall();
        secondHall.setCapacity(15);
        secondHall.setDescription("Cinema hall London");
        cinemaHallService.add(secondHall);
        System.out.println(cinemaHallService.get(secondHall.getId()));

        CinemaHall thirdHall = new CinemaHall();
        thirdHall.setCapacity(50);
        thirdHall.setDescription("Cinema hall Kyiv");
        cinemaHallService.add(thirdHall);
        System.out.println(cinemaHallService.get(thirdHall.getId()));

        System.out.println("A list of cinema halls!");
        System.out.println(cinemaHallService.getAll());

        MovieSession morningSession = new MovieSession();
        morningSession.setMovie(dieHard);
        morningSession.setCinemaHall(firstHall);
        morningSession.setShowtime(LocalDateTime.of(2021, 10, 18, 11, 0));
        movieSessionService.add(morningSession);
        System.out.println(movieSessionService.get(morningSession.getId()));

        MovieSession noonSession = new MovieSession();
        noonSession.setMovie(dieHard2);
        noonSession.setCinemaHall(secondHall);
        noonSession.setShowtime(LocalDateTime.of(2021, 10, 18, 15, 0));
        movieSessionService.add(noonSession);
        System.out.println(movieSessionService.get(noonSession.getId()));

        MovieSession eveningSession = new MovieSession();
        eveningSession.setMovie(dieHard3);
        eveningSession.setCinemaHall(thirdHall);
        eveningSession.setShowtime(LocalDateTime.of(2021, 10, 18, 20, 0));
        movieSessionService.add(eveningSession);
        System.out.println(movieSessionService.get(eveningSession.getId()));

        System.out.println("Movie sessions!");
        System.out.println(movieSessionService.getAvailableSessions(dieHard.getId(),
                LocalDate.of(2021, 10, 18)));
    }
}
