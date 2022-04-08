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
        fastAndFurious.setDescription("An action film about street racing,"
                + " heists, and spies.");
        Movie beeMovie = new Movie("Bee Movie: Honey just got funny");
        beeMovie.setDescription(" computer-animated comedy film");
        Movie hulk = new Movie("Hulk");
        hulk.setDescription("Film based on Marvel's comics");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        movieService.add(beeMovie);
        movieService.add(hulk);

        CinemaHall firstClassHall = new CinemaHall();
        firstClassHall.setDescription("Hall for VIPs");
        firstClassHall.setCapacity(10);
        CinemaHall secondClassHall = new CinemaHall();
        secondClassHall.setDescription("Standard Hall");
        secondClassHall.setCapacity(100);
        cinemaHallService.add(firstClassHall);
        cinemaHallService.add(secondClassHall);

        MovieSession todaySession1 = new MovieSession();
        todaySession1.setMovie(beeMovie);
        todaySession1.setCinemaHall(secondClassHall);
        todaySession1.setShowTime(LocalDate.now().atTime(16, 30));
        MovieSession todaySession2 = new MovieSession();
        todaySession2.setMovie(hulk);
        todaySession2.setCinemaHall(secondClassHall);
        todaySession2.setShowTime(LocalDate.now().atTime(18, 50));
        MovieSession tomorrowSession = new MovieSession();
        tomorrowSession.setCinemaHall(firstClassHall);
        tomorrowSession.setMovie(fastAndFurious);
        tomorrowSession.setShowTime(LocalDate.now()
                .plusDays(1).atTime(21, 30));
        movieSessionService.add(todaySession1);
        movieSessionService.add(todaySession2);
        movieSessionService.add(tomorrowSession);
    }
}
