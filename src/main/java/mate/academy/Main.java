package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie titanic = new Movie("Titanic");
        titanic.setDescription("Film about ferryboat, romantic, tragic");
        movieService.add(titanic);
        movieService.get(titanic.getId());
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall bigCinemaHall = new CinemaHall(400, "Big cinema hall with row of couches");
        cinemaHallService.add(bigCinemaHall);
        System.out.println(cinemaHallService.get(bigCinemaHall.getId()));
        CinemaHall familyCinemaHall = new CinemaHall(20, "Small cinema hall for few families");
        cinemaHallService.add(familyCinemaHall);
        System.out.println(cinemaHallService.get(familyCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);


        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setCinemaHall(familyCinemaHall);
        Period period = Period.ofDays(1);
        fastAndFuriousSession.setShowTime(LocalDateTime.now().plus(period));
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousSession);
        System.out.println(movieSessionService.get(fastAndFuriousSession.getId()));

        MovieSession titanicSession = new MovieSession();
        titanicSession.setMovie(titanic);
        titanicSession.setCinemaHall(bigCinemaHall);
        titanicSession.setShowTime(LocalDateTime.now().plus(period));
        movieSessionService.add(titanicSession);
        movieSessionService.findAvailableSessions(2L,
                LocalDate.now().plus(period)).forEach(System.out::println);
    }
}
