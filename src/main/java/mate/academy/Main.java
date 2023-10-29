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
    private static Injector injector
            = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie fromDuskTillDown = new Movie("From Dusk Till Down");
        fromDuskTillDown.setDescription("Awesome movie!");
        movieService.add(fromDuskTillDown);

        Movie nextMovie = new Movie("Unknown Movie");
        nextMovie.setDescription("Nobody knows what this movie about");
        movieService.add(nextMovie);
        System.out.println(movieService.get(2L));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHallWhite = new CinemaHall();
        cinemaHallWhite.setCapacity(50);
        cinemaHallWhite.setDescription("Square hall");
        cinemaHallService.add(cinemaHallWhite);

        CinemaHall cinemaHallBlack = new CinemaHall();
        cinemaHallBlack.setCapacity(100);
        cinemaHallBlack.setDescription("Round hall");
        cinemaHallService.add(cinemaHallBlack);

        CinemaHall cinemaHallNext = new CinemaHall();
        cinemaHallNext.setCapacity(40);
        cinemaHallNext.setDescription("Magic Hall");
        cinemaHallService.add(cinemaHallNext);

        System.out.println(cinemaHallService.get(1L));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSessionOne = new MovieSession();
        movieSessionOne.setMovie(fastAndFurious);
        movieSessionOne.setCinemaHall(cinemaHallWhite);
        movieSessionOne.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSessionOne);

        MovieSession movieSessionFour = new MovieSession();
        movieSessionFour.setMovie(fastAndFurious);
        movieSessionFour.setCinemaHall(cinemaHallWhite);
        movieSessionFour.setShowTime(LocalDateTime.now().plusHours(1L));
        movieSessionService.add(movieSessionFour);

        MovieSession movieSessionFifth = new MovieSession();
        movieSessionFifth.setMovie(fastAndFurious);
        movieSessionFifth.setCinemaHall(cinemaHallWhite);
        movieSessionFifth.setShowTime(LocalDateTime.now().plusDays(1L));
        movieSessionService.add(movieSessionFifth);

        MovieSession movieSessionTwo = new MovieSession();
        movieSessionTwo.setMovie(fromDuskTillDown);
        movieSessionTwo.setCinemaHall(cinemaHallBlack);
        movieSessionTwo.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSessionTwo);

        MovieSession movieSessionThree = new MovieSession();
        movieSessionThree.setMovie(nextMovie);
        movieSessionThree.setCinemaHall(cinemaHallNext);
        movieSessionThree.setShowTime(LocalDateTime.now().plusDays(3));
        movieSessionService.add(movieSessionThree);
        System.out.println(movieSessionService.get(3L));
        movieSessionService.findAvailableSessions(1L, LocalDate.now())
                .forEach(System.out::println);
    }
}
