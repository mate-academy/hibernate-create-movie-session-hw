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
    public static void main(String[] args) {

        Movie firstMovie = new Movie();
        firstMovie.setTitle("First Movie");
        firstMovie.setDescription("First Movie description");
        Movie secondMovie = new Movie();
        secondMovie.setTitle("Second Movie");
        secondMovie.setDescription("Second Movie description");
        Movie thirdMovie = new Movie();
        thirdMovie.setTitle("Third Movie");
        thirdMovie.setDescription("Third Movie description");

        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService)
                injector.getInstance(MovieService.class);
        movieService.add(firstMovie);
        movieService.add(secondMovie);
        movieService.add(thirdMovie);
        System.out.println(movieService.get(firstMovie.getId()));
        System.out.println(movieService.get(secondMovie.getId()));
        System.out.println(movieService.get(thirdMovie.getId()));
        System.out.println(movieService.getAll());

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(20);
        firstCinemaHall.setDescription("First cinema Hall description");
        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(30);
        secondCinemaHall.setDescription("Second cinema Hall description");
        CinemaHall thirdCinemaHall = new CinemaHall();
        thirdCinemaHall.setCapacity(40);
        thirdCinemaHall.setDescription("Third cinema Hall description");
        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(firstCinemaHall);
        cinemaHallService.add(secondCinemaHall);
        cinemaHallService.add(thirdCinemaHall);
        System.out.println(cinemaHallService
                .get(firstCinemaHall.getId()));
        System.out.println(cinemaHallService
                .get(secondCinemaHall.getId()));
        System.out.println(cinemaHallService
                .get(thirdCinemaHall.getId()));
        System.out.println(cinemaHallService
                .getAll());

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(firstMovie);
        firstMovieSession.setCinemaHall(firstCinemaHall);
        LocalDateTime date1 = LocalDateTime.now();
        firstMovieSession.setShowTime(date1);
        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(secondMovie);
        secondMovieSession.setCinemaHall(secondCinemaHall);
        LocalDateTime date2 = LocalDateTime.now().plusDays(1);
        secondMovieSession.setShowTime(date2);
        MovieSession thirdMovieSession = new MovieSession();
        thirdMovieSession.setMovie(thirdMovie);
        thirdMovieSession.setCinemaHall(thirdCinemaHall);
        LocalDateTime date3 = LocalDateTime.now().plusHours(2);
        thirdMovieSession.setShowTime(date3);

        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(firstMovieSession);
        movieSessionService.add(secondMovieSession);
        movieSessionService.add(thirdMovieSession);
        System.out.println(movieSessionService
                .get(firstMovieSession.getId()));
        System.out.println(movieSessionService
                .get(secondMovieSession.getId()));
        System.out.println(movieSessionService
                .get(thirdMovieSession.getId()));
        LocalDate checkDate1 = LocalDate.now();
        LocalDate checkDate2 = LocalDate.now().plusDays(1);
        System.out.println(movieSessionService
                .findAvailableSessions(firstMovieSession.getId(), checkDate1));
        System.out.println(movieSessionService
                .findAvailableSessions(secondMovieSession.getId(), checkDate2));
        System.out.println(movieSessionService
                .findAvailableSessions(thirdMovieSession.getId(), checkDate2));

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
    }
}
