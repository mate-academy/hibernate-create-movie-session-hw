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
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService)
                INJECTOR.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        Movie lostArk = new Movie("Raiders of the Lost Ark");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        lostArk.setDescription("Raiders of the Lost Ark is a adventure film and the first "
                + "entry in the Indiana Jones series.");
        movieService.add(fastAndFurious);
        movieService.add(lostArk);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall smallHall = new CinemaHall();
        CinemaHall largeHall = new CinemaHall();
        smallHall.setCapacity(30);
        smallHall.setDescription("Small cinema hall");
        largeHall.setCapacity(100);
        largeHall.setDescription("Large cinema hall");

        CinemaHallService cinemaHallService = (CinemaHallService) INJECTOR
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(smallHall);
        cinemaHallService.add(largeHall);
        System.out.println(cinemaHallService.get(smallHall.getId()));
        System.out.println(cinemaHallService.getAll());

        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setCinemaHall(smallHall);
        fastAndFuriousSession.setShowTime(LocalDateTime.now().plusDays(3));

        MovieSessionService movieSessionService = (MovieSessionService) INJECTOR
                .getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousSession);
        movieSessionService.add(fastAndFuriousSession);
        movieSessionService.get(fastAndFuriousSession.getId());
        movieSessionService.findAvailableSessions(fastAndFuriousSession.getId(), LocalDate.now());
    }
}
