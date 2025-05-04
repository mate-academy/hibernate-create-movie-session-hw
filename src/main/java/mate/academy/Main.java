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
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Movie avengers = new Movie("Avengers");
        Movie starTrek = new Movie("Star Trek");
        Movie shrek = new Movie("Shrek");

        MovieService movieService = (MovieService)
                INJECTOR.getInstance(MovieService.class);
        movieService.add(avengers);
        movieService.add(starTrek);
        movieService.add(shrek);
        movieService.get(3L);
        movieService.getAll();

        CinemaHall firstHall = new CinemaHall();
        firstHall.setDescription("First Hall");
        CinemaHall secondHall = new CinemaHall();
        secondHall.setDescription("Second Hall");
        CinemaHall thirdHall = new CinemaHall();
        thirdHall.setDescription("Third Hall");

        CinemaHallService cinemaHallService = (CinemaHallService)
                INJECTOR.getInstance(CinemaHallService.class);
        cinemaHallService.add(firstHall);
        cinemaHallService.add(secondHall);
        cinemaHallService.add(thirdHall);
        cinemaHallService.get(2L);
        cinemaHallService.getAll();

        LocalDateTime currentTime = LocalDateTime.now();
        MovieSession shrekInFirstHall = new MovieSession();
        shrekInFirstHall.setMovie(shrek);
        shrekInFirstHall.setCinemaHall(firstHall);
        shrekInFirstHall.setShowTime(currentTime);
        MovieSession starTrekInSecondHall = new MovieSession();
        starTrekInSecondHall.setMovie(starTrek);
        starTrekInSecondHall.setCinemaHall(secondHall);
        starTrekInSecondHall.setShowTime(currentTime.plusDays(1));
        MovieSession avengersInThirdHall = new MovieSession();
        avengersInThirdHall.setMovie(avengers);
        avengersInThirdHall.setCinemaHall(thirdHall);
        avengersInThirdHall.setShowTime(currentTime.plusDays(2));

        MovieSessionService movieSessionService = (MovieSessionService)
                INJECTOR.getInstance(MovieSessionService.class);
        movieSessionService.add(shrekInFirstHall);
        movieSessionService.add(starTrekInSecondHall);
        movieSessionService.add(avengersInThirdHall);
        movieSessionService.get(1L);
        movieSessionService.findAvailableSessions(shrek.getId(), LocalDate.now());
    }
}
