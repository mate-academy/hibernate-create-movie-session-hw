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
    public static final Injector injector = Injector.getInstance("mate.academy");
    public static final MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);
    public static final CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);
    public static final MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie ironMan = new Movie();
        ironMan.setTitle("Iron Man 1");
        ironMan.setDescription("About super heroes");
        movieService.add(ironMan);

        Movie war = new Movie();
        war.setTitle("War");
        war.setDescription("About war");
        movieService.add(war);

        Movie onePlusOne = new Movie();
        onePlusOne.setTitle("1 + 1");
        onePlusOne.setDescription("About good history in London");
        movieService.add(onePlusOne);

        CinemaHall bigHall = new CinemaHall();
        bigHall.setCapacity(100);
        bigHall.setDescription("This is BIG HALL for family and big company babyes");
        cinemaHallService.add(bigHall);

        CinemaHall smallHall = new CinemaHall();
        smallHall.setCapacity(10);
        smallHall.setDescription("This is SMALL HALL for family and small company babyes");
        cinemaHallService.add(smallHall);

        MovieSession movieSessionIronMan = new MovieSession();
        movieSessionIronMan.setMovie(ironMan);
        movieSessionIronMan.setCinemaHall(bigHall);
        LocalDateTime fourDate = LocalDateTime.of(2021, 9, 15, 10, 30);
        movieSessionIronMan.setShowDate(fourDate);
        movieSessionService.add(movieSessionIronMan);

        MovieSession movieSessionTwo = new MovieSession();
        movieSessionTwo.setMovie(ironMan);
        movieSessionTwo.setCinemaHall(bigHall);
        LocalDateTime firstDate = LocalDateTime.of(2021, 7, 10, 10, 0);
        movieSessionTwo.setShowDate(firstDate);
        movieSessionService.add(movieSessionTwo);

        MovieSession movieSessionOnePlusOne = new MovieSession();
        movieSessionOnePlusOne.setMovie(onePlusOne);
        movieSessionOnePlusOne.setCinemaHall(smallHall);
        LocalDateTime fifeDate = LocalDateTime.of(2021, 9, 15, 14, 30);
        movieSessionOnePlusOne.setShowDate(fifeDate);
        movieSessionService.add(movieSessionOnePlusOne);

        System.out
                .println(movieSessionService.findAvailableSessions(1L, LocalDate.of(2021, 9, 15)));
    }
}
