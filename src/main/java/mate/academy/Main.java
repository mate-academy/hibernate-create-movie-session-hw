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
        MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        System.out.println("Movie Service:");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        Movie ironMan = new Movie("Iron Man");
        ironMan.setDescription("the story of Tony Stark, a billionaire industrialist "
                + "and genius inventor who is kidnapped and forced to build a devastating weapon.");
        System.out.println("Add movie into DB:");
        movieService.add(ironMan);
        movieService.getAll().forEach(System.out::println);
        System.out.println("Cinema Hall service:");
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(80);
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("Movie Session Service service:");
        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setDateTime(LocalDateTime.now());
        fastAndFuriousSession.setCinemaHall(cinemaHall);
        MovieSessionService sessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        sessionService.add(fastAndFuriousSession);
        MovieSession ironManSession = new MovieSession();
        ironManSession.setCinemaHall(cinemaHall);
        ironManSession.setMovie(ironMan);
        ironManSession.setDateTime(LocalDateTime
                .of(2024, 7, 14, 22, 0,0,0));
        sessionService.add(ironManSession);
        System.out.println(sessionService.get(fastAndFuriousSession.getId()));
        sessionService.findAvailableSession(ironMan.getId(), LocalDate.of(2024,7,14))
                .forEach(System.out::println);
        System.out.println();
    }
}
