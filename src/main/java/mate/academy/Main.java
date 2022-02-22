package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    private static MovieService movieService;
    private static CinemaHallService cinemaHallService;
    private static MovieSessionService movieSessionService;

    static {
        Injector injector = Injector.getInstance("mate.academy");
        movieService = (MovieService) injector
                .getInstance(MovieService.class);
        cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
    }
    public static void main(String[] args) {
        System.out.println("Initializing DB");
        initDB();
        System.out.println("Getting all movies");
        movieService.getAll().forEach(System.out::println);
        System.out.println("Getting movie by id 1");
        System.out.println(movieService.get(1L));
        System.out.println("Getting all cinema halls");
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("Getting cinema hall by id 1");
        System.out.println(cinemaHallService.get(1L));
        System.out.println("Getting sessions by all init movies and today date");
        getMovieSessionsByIdAndLocalDate();
        System.out.println("Getting last init movie session");
        System.out.println(movieSessionService.get(3L));
    }

    private static void getMovieSessionsByIdAndLocalDate() {
        for (long i = 1; i <= 3L; i++) {
            System.out.println("Getting all movie sessions today by movie id " + i);
            movieSessionService.findAvailableSessions(i, LocalDate.now())
                    .forEach(System.out::println);
        }

    }

    private static void initDB() {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about "
                + "street racing, heists, and spies.");
        Movie lordOfRings = new Movie("Lord of Rings");
        lordOfRings.setDescription("Fantasy film");
        Movie ironMan = new Movie("Iron Man");
        ironMan.setDescription("Film based on Marvel's comics");
        movieService.add(fastAndFurious);
        movieService.add(lordOfRings);
        movieService.add(ironMan);

        CinemaHall luxHall = new CinemaHall();
        luxHall.setDescription("Hall for special guests");
        luxHall.setCapacity(10);
        CinemaHall standartHall = new CinemaHall();
        standartHall.setDescription("Main hall of the build");
        standartHall.setCapacity(100);
        cinemaHallService.add(luxHall);
        cinemaHallService.add(standartHall);

        MovieSession todaySession1 = new MovieSession();
        todaySession1.setMovie(ironMan);
        todaySession1.setCinemaHall(standartHall);
        todaySession1.setShowTime(LocalDate.now().atTime(9, 30));
        MovieSession todaySession2 = new MovieSession();
        todaySession2.setMovie(lordOfRings);
        todaySession2.setCinemaHall(standartHall);
        todaySession2.setShowTime(LocalDate.now().atTime(12, 0));
        MovieSession tomorrowSession = new MovieSession();
        tomorrowSession.setCinemaHall(luxHall);
        tomorrowSession.setMovie(fastAndFurious);
        tomorrowSession.setShowTime(LocalDate.now()
                .plusDays(1).atTime(9, 30));
        movieSessionService.add(todaySession1);
        movieSessionService.add(todaySession2);
        movieSessionService.add(tomorrowSession);
    }
}
