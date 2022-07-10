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
        System.out.println("___________________Add movies___________________");
        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        System.out.println(movieService.add(fastAndFurious));
        Movie backToTheFuture = new Movie("Back to the Future");
        backToTheFuture.setDescription("Film about traveling in the time");
        System.out.println(movieService.add(backToTheFuture));

        System.out.println("_______________Add cinema halls_________________");
        final CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        CinemaHall leftCinemaHall = new CinemaHall();
        leftCinemaHall.setCapacity(35);
        leftCinemaHall.setDescription("Left cinema hall");
        System.out.println(cinemaHallService.add(leftCinemaHall));
        CinemaHall rightCinemaHall = new CinemaHall();
        rightCinemaHall.setCapacity(40);
        rightCinemaHall.setDescription("right cinema hall");
        System.out.println(cinemaHallService.add(rightCinemaHall));

        System.out.println("_______________Add Movie sessions_______________");
        final MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        MovieSession firstSession = new MovieSession();
        firstSession.setCinemaHall(leftCinemaHall);
        firstSession.setMovie(fastAndFurious);
        firstSession.setShowTime(LocalDateTime.now().plusDays(2));
        System.out.println(movieSessionService.add(firstSession));
        MovieSession secondSession = new MovieSession();
        secondSession.setMovie(backToTheFuture);
        secondSession.setCinemaHall(rightCinemaHall);
        secondSession.setShowTime(LocalDateTime.now().plusDays(1));
        System.out.println(movieSessionService.add(secondSession));

        System.out.println("________________Get cinema hall_________________");
        System.out.println(cinemaHallService.get(1L));
        System.out.println("_____________Get all cinema halls_______________");
        System.out.println(cinemaHallService.getAll());

        System.out.println("________________Get movie session_______________");
        System.out.println(movieSessionService.get(1L));
        System.out.println("____________Get available sessions______________");
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now().plusDays(2)));
    }
}
