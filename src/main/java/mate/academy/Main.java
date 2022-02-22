package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private static final MovieService movieService = (MovieService)
            injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService)
            injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService)
            injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie frenchDispatch = new Movie("The French Dispatch");
        frenchDispatch.setDescription("Great stylized comedy");
        movieService.add(frenchDispatch);
        Movie spiderManAnotherReturn = new Movie("SpiderMan Legacy League Seven");
        spiderManAnotherReturn.setDescription("Comics and comedy");
        movieService.add(spiderManAnotherReturn);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHall cinemaHallOne = new CinemaHall();
        cinemaHallOne.setCapacity(75);
        cinemaHallOne.setDescription("First movie hall, medium");
        cinemaHallService.add(cinemaHallOne);
        System.out.println(cinemaHallService.get(cinemaHallOne.getId()));
        CinemaHall cinemaHallTwo = new CinemaHall();
        cinemaHallTwo.setCapacity(25);
        cinemaHallTwo.setDescription("Second movie hall, small");
        cinemaHallService.add(cinemaHallTwo);
        System.out.println(cinemaHallService.get(cinemaHallTwo.getId()));
        CinemaHall cinemaHallThree = new CinemaHall();
        cinemaHallThree.setCapacity(100);
        cinemaHallThree.setDescription("Third movie hall, large");
        cinemaHallService.add(cinemaHallThree);
        System.out.println(cinemaHallService.get(cinemaHallThree.getId()));
        MovieSession fastAndFuriousMovieSessionAt14 = new MovieSession();
        fastAndFuriousMovieSessionAt14.setMovie(fastAndFurious);
        fastAndFuriousMovieSessionAt14.setCinemaHall(cinemaHallOne);
        fastAndFuriousMovieSessionAt14.setShowTime(LocalDateTime
                .of(2022, Month.FEBRUARY, 23, 14, 0));
        movieSessionService.add(fastAndFuriousMovieSessionAt14);
        System.out.println("Fast and Furious movie session at 14:00 info");
        System.out.println(movieSessionService.get(fastAndFuriousMovieSessionAt14.getId()));
        MovieSession fastAndFuriousMovieSessionAt19 = new MovieSession();
        fastAndFuriousMovieSessionAt19.setMovie(fastAndFurious);
        fastAndFuriousMovieSessionAt19.setCinemaHall(cinemaHallTwo);
        fastAndFuriousMovieSessionAt19.setShowTime(LocalDateTime
                .of(2022, Month.FEBRUARY, 23, 19, 0));
        movieSessionService.add(fastAndFuriousMovieSessionAt19);
        System.out.println("Fast and Furious movie session at 19:00 info");
        System.out.println(movieSessionService.get(fastAndFuriousMovieSessionAt19.getId()));
        MovieSession frenchDispatchMovieSessionAt17 = new MovieSession();
        frenchDispatchMovieSessionAt17.setMovie(frenchDispatch);
        frenchDispatchMovieSessionAt17.setCinemaHall(cinemaHallThree);
        frenchDispatchMovieSessionAt17.setShowTime(LocalDateTime
                .of(2022, Month.FEBRUARY, 27, 17, 0));
        movieSessionService.add(frenchDispatchMovieSessionAt17);
        System.out.println("The French Dispatch movie session at 17:00 info");
        System.out.println(movieSessionService.get(frenchDispatchMovieSessionAt17.getId()));
        System.out.println("Get all movie session with Fast and Furious movie at 2022-02-23");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate
                        .of(2022, Month.FEBRUARY, 23))
                .forEach(System.out::println);
    }
}
