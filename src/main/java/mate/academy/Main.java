package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService = (MovieService)
            injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService)
            injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService)
            injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        System.out.println("------movies setup------");
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie dieHard = new Movie("Die Hard");
        dieHard.setDescription("An action film about NYC cop fighting against terrorists");
        movieService.add(dieHard);
        System.out.println(movieService.get(dieHard.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println("------halls setup------");
        CinemaHall hall3d = new CinemaHall();
        hall3d.setCapacity(100);
        hall3d.setDescription("3D screen with DolbyDigital sound");
        cinemaHallService.add(hall3d);
        System.out.println(cinemaHallService.get(hall3d.getId()));

        CinemaHall hallLuxury = new CinemaHall();
        hallLuxury.setCapacity(50);
        hallLuxury.setDescription("DolbyDigital sound and big personal leather sofas");
        cinemaHallService.add(hallLuxury);
        System.out.println(cinemaHallService.get(hallLuxury.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("------sessions setup------");
        MovieSession earlyBird = new MovieSession();
        earlyBird.setMovie(fastAndFurious);
        earlyBird.setCinemaHall(hall3d);
        earlyBird.setShowTime(LocalDateTime.parse("2021-06-10T12:00:00"));
        movieSessionService.add(earlyBird);
        System.out.println(movieSessionService.get(earlyBird.getId()));

        MovieSession lateShow = new MovieSession();
        lateShow.setMovie(dieHard);
        lateShow.setCinemaHall(hallLuxury);
        lateShow.setShowTime(LocalDateTime.parse("2021-06-10T20:00:00"));
        movieSessionService.add(lateShow);
        System.out.println(movieSessionService.get(lateShow.getId()));

        MovieSession latelateShow = new MovieSession();
        latelateShow.setMovie(dieHard);
        latelateShow.setCinemaHall(hall3d);
        latelateShow.setShowTime(LocalDateTime.parse("2021-06-10T23:59:59"));
        movieSessionService.add(latelateShow);
        System.out.println(movieSessionService.get(latelateShow.getId()));

        System.out.println("------sessions by demand result------");
        List<MovieSession> list = movieSessionService
                .findAvailableSessions(dieHard.getId(), LocalDate.parse("2021-06-10"));
        System.out.println(list.size());
        list.forEach(System.out::println);
    }
}
