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
    private static Injector injector = Injector.getInstance("mate.academy");
    private static MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie terminator1 = new Movie("The Terminator");
        terminator1.setDescription(
                "Actors: Arnold Schwarzenegger, Linda Hamilton; year: 1984; country: USA; "
                        + "genres: action, sci-fi");
        movieService.add(terminator1);
        System.out.println(movieService.get(terminator1.getId()));

        Movie terminator2 = new Movie("Terminator 2: Judgment Day");
        terminator2.setDescription(
                "Actors: Arnold Schwarzenegger, Linda Hamilton; year: 1991; country: USA; "
                        + "genres: action, sci-fi");
        movieService.add(terminator2);
        System.out.println(movieService.get(terminator2.getId()));

        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(120);
        firstHall.setDescription("Cinema hall in the first flow");
        cinemaHallService.add(firstHall);

        MovieSession terminator1Session = new MovieSession();
        terminator1Session.setMovie(terminator1);
        terminator1Session.setCinemaHall(firstHall);
        terminator1Session.setShowTime(LocalDateTime.now().plusHours(1));
        movieSessionService.add(terminator1Session);

        MovieSession terminator2Session = new MovieSession();
        terminator2Session.setMovie(terminator2);
        terminator2Session.setCinemaHall(firstHall);
        terminator2Session.setShowTime(LocalDateTime.now().plusHours(2).plusMinutes(30));
        movieSessionService.add(terminator2Session);

        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setCinemaHall(firstHall);
        fastAndFuriousSession.setShowTime(LocalDateTime.now().plusDays(1));
        movieSessionService.add(fastAndFuriousSession);

        movieService.getAll().forEach(System.out::println);
        movieService.getAll().forEach(System.out::println);
        movieSessionService.findAvailableSessions(terminator1.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
