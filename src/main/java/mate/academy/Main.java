package mate.academy;

import java.time.LocalDate;
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

        Movie pulpFiction = new Movie("Pulp Fiction");
        pulpFiction.setDescription("American black comedy crime film.");
        movieService.add(pulpFiction);
        System.out.println(movieService.get(pulpFiction.getId()));

        Movie starShipTroopers = new Movie("Starship troopers");
        starShipTroopers.setDescription("Fantastic science fiction action film");
        movieService.add(starShipTroopers);
        System.out.println(movieService.get(starShipTroopers.getId()));

        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHallRed = new CinemaHall();
        cinemaHallRed.setCapacity(164);
        cinemaHallRed.setDescription("big cinema hall on the first floor");
        cinemaHallService.add(cinemaHallRed);

        CinemaHall cinemaHallBlue = new CinemaHall();
        cinemaHallBlue.setCapacity(72);
        cinemaHallBlue.setDescription("small cinema hall on the second floor");
        cinemaHallService.add(cinemaHallBlue);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSessionTodayFirst = new MovieSession();
        movieSessionTodayFirst.setMovie(fastAndFurious);
        movieSessionTodayFirst.setCinemahall(cinemaHallRed);
        movieSessionTodayFirst.setSessionTime(LocalDate.now().atTime(19,00));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSessionTodayFirst);
        System.out.println(movieSessionService.get(movieSessionTodayFirst.getId()));

        MovieSession movieSessionTodaySecond = new MovieSession();
        movieSessionTodaySecond.setMovie(pulpFiction);
        movieSessionTodaySecond.setCinemahall(cinemaHallBlue);
        movieSessionTodaySecond.setSessionTime(LocalDate.now().atTime(19,00));
        movieSessionService.add(movieSessionTodaySecond);
        System.out.println(movieSessionService.get(movieSessionTodaySecond.getId()));

        MovieSession movieSessionTomorrowFirst = new MovieSession();
        movieSessionTomorrowFirst.setMovie(starShipTroopers);
        movieSessionTomorrowFirst.setCinemahall(cinemaHallRed);
        movieSessionTomorrowFirst.setSessionTime(
                LocalDate.now().plusDays(1).atTime(18,00));
        movieSessionService.add(movieSessionTomorrowFirst);
        System.out.println(movieSessionService.get(movieSessionTomorrowFirst.getId()));

        movieSessionService.findAvailableSessions(starShipTroopers.getId(),
                LocalDate.now().plusDays(1)).forEach(System.out::println);
    }
}
