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
    private static final CinemaHallService cinemaHalService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        addFirstMovie();
        addSecondMovie();

        addFirstCinemaHall();
        addSecondCinemaHall();

        LocalDateTime firstSessionDateTime = LocalDateTime.of(2023, 12, 1, 18, 30);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setCinemaHall(cinemaHalService.get(1L));
        firstMovieSession.setMovie(movieService.get(1L));
        firstMovieSession.setShowTime(firstSessionDateTime);
        movieSessionService.add(firstMovieSession);

        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setCinemaHall(cinemaHalService.get(2L));
        secondMovieSession.setMovie(movieService.get(1L));
        secondMovieSession.setShowTime(firstSessionDateTime);
        movieSessionService.add(secondMovieSession);

        LocalDateTime thirdSessionDate = LocalDateTime.of(2020,12,12,2,56);

        MovieSession thirdMovieSession = new MovieSession();
        thirdMovieSession.setCinemaHall(cinemaHalService.get(2L));
        thirdMovieSession.setMovie(movieService.get(1L));
        thirdMovieSession.setShowTime(thirdSessionDate);//OTHER DATE
        movieSessionService.add(thirdMovieSession);

        System.out.println(movieSessionService.get(1L));
        LocalDate firstSessionDateSelect = LocalDate.of(2023, 12, 1);

        System.out.println(movieSessionService.findAvailableSessions(1L, firstSessionDateSelect));
    }

    private static Movie addFirstMovie() {
        Movie firstMovie = new Movie("FirstMovie");
        firstMovie.setDescription("1_description");
        movieService.getAll().forEach(System.out::println);
        return movieService.add(firstMovie);
    }

    private static Movie addSecondMovie() {
        Movie secondMovie = new Movie("SecondMovie");
        secondMovie.setDescription("2_description");
        movieService.getAll().forEach(System.out::println);
        return movieService.add(secondMovie);
    }

    private static CinemaHall addFirstCinemaHall() {
        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(40);
        firstCinemaHall.setDescription("firstCinemaHall_description");
        return cinemaHalService.add(firstCinemaHall);
    }

    private static CinemaHall addSecondCinemaHall() {
        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(20);
        secondCinemaHall.setDescription("secondCinemaHall_description");
        return cinemaHalService.add(secondCinemaHall);
    }
}
