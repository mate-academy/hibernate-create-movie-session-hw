package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.cinema.hall.CinemaHallService;
import mate.academy.service.movie.MovieService;
import mate.academy.service.movie.session.MovieSessionService;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie incredible = new Movie("Incredible");
        incredible.setDescription("Some uniq incredible movie.");
        movieService.add(incredible);
        System.out.println(movieService.get(incredible.getId()));

        System.out.println(movieService.getAll());

        CinemaHallService cinemaHallService =
                (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);

        CinemaHall bestKyivCinema = new CinemaHall();
        bestKyivCinema.setCapacity(100);
        bestKyivCinema.setDescription("Cinema Hall with middle level of service");
        cinemaHallService.add(bestKyivCinema);
        System.out.println(cinemaHallService.get(bestKyivCinema.getId()));

        CinemaHall flyingStar = new CinemaHall();
        flyingStar.setCapacity(75);
        flyingStar.setDescription("Cinema Hall with good level of service");
        cinemaHallService.add(flyingStar);
        System.out.println(cinemaHallService.get(flyingStar.getId()));

        System.out.println(cinemaHallService.getAll());

        MovieSession firstSession = new MovieSession();
        firstSession.setMovie(fastAndFurious);
        firstSession.setCinemaHall(bestKyivCinema);
        LocalDateTime firstSessionTime = LocalDateTime.of(
                2023, 6, 27, 18, 40);
        firstSession.setShowTime(firstSessionTime);
        MovieSessionService movieSessionService = (MovieSessionService)
                INJECTOR.getInstance(MovieSessionService.class);
        movieSessionService.add(firstSession);
        System.out.println(movieSessionService.get(firstSession.getId()));

        MovieSession secondSession = new MovieSession();
        secondSession.setMovie(incredible);
        secondSession.setCinemaHall(flyingStar);
        LocalDateTime secondSessionTime = LocalDateTime.of(
                2022, 1, 15, 12, 0);
        secondSession.setShowTime(secondSessionTime);
        movieSessionService.add(secondSession);
        System.out.println(movieSessionService.get(secondSession.getId()));

        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2023, 6, 27)));

        System.out.println(movieSessionService.findAvailableSessions(incredible.getId(),
                LocalDate.of(2023, 6, 15)));

    }
}
