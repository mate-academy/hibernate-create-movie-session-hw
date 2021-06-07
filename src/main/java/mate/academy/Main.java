package mate.academy;

import java.time.LocalDate;
import java.time.LocalTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie scaryMovie = new Movie("Scary movie");
        scaryMovie.setDescription("Funny movie");
        movieService.add(scaryMovie);

        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(scaryMovie.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall superHall = new CinemaHall();
        superHall.setCapacity(100);
        superHall.setDescription("Super hall");
        cinemaHallService.add(superHall);

        CinemaHall megaHall = new CinemaHall();
        megaHall.setCapacity(202);
        megaHall.setDescription("Mega hall");
        cinemaHallService.add(megaHall);

        System.out.println(cinemaHallService.get(superHall.getId()));
        System.out.println(cinemaHallService.get(megaHall.getId()));
        System.out.println(cinemaHallService.getAll());

        MovieSession tomorrowAfternoonFastAndFuriousSession = new MovieSession();
        tomorrowAfternoonFastAndFuriousSession.setCinemaHall(superHall);
        tomorrowAfternoonFastAndFuriousSession.setMovie(fastAndFurious);
        tomorrowAfternoonFastAndFuriousSession.setShowTime(LocalDate.now().plusDays(1)
                .atTime(LocalTime.of(12, 10)));

        MovieSession tomorrowNightFastAndFuriousSession = new MovieSession();
        tomorrowNightFastAndFuriousSession.setCinemaHall(megaHall);
        tomorrowNightFastAndFuriousSession.setMovie(fastAndFurious);
        tomorrowNightFastAndFuriousSession.setShowTime(LocalDate.now().plusDays(1)
                .atTime(LocalTime.of(22, 20)));

        MovieSession tomorrowNightScaryMovieSession = new MovieSession();
        tomorrowNightScaryMovieSession.setCinemaHall(megaHall);
        tomorrowNightScaryMovieSession.setMovie(scaryMovie);
        tomorrowNightScaryMovieSession.setShowTime(LocalDate.now().plusDays(1)
                .atTime(LocalTime.of(21, 1)));

        MovieSession todayAfternoonFastAndFuriousSession = new MovieSession();
        todayAfternoonFastAndFuriousSession.setCinemaHall(superHall);
        todayAfternoonFastAndFuriousSession.setMovie(fastAndFurious);
        todayAfternoonFastAndFuriousSession.setShowTime(LocalDate.now()
                .atTime(LocalTime.of(12, 50)));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(tomorrowAfternoonFastAndFuriousSession);
        movieSessionService.add(tomorrowNightScaryMovieSession);
        movieSessionService.add(todayAfternoonFastAndFuriousSession);
        movieSessionService.add(tomorrowNightFastAndFuriousSession);

        System.out.println(movieSessionService.get(todayAfternoonFastAndFuriousSession.getId()));
        System.out.println(movieSessionService.get(tomorrowAfternoonFastAndFuriousSession.getId()));
        System.out.println(movieSessionService.get(tomorrowNightScaryMovieSession.getId()));
        System.out.println(movieSessionService.get(tomorrowNightFastAndFuriousSession.getId()));

        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now().plusDays(1)));
        System.out.println(movieSessionService
                .findAvailableSessions(scaryMovie.getId(), LocalDate.now().plusDays(1)));
        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now()));
    }
}
