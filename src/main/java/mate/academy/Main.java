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
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie spiderMan = new Movie("Spider-Man: 1");
        spiderMan.setDescription("Story about Piter Parker and how he became a hero");
        movieService.add(spiderMan);

        Movie fall = new Movie("Fall");
        fall.setDescription("Two girls stuck on a very high TV tower");
        movieService.add(fall);

        System.out.println("List of all Movies");
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(100);
        firstHall.setDescription("IMAX technology");
        cinemaHallService.add(firstHall);

        CinemaHall secondHall = new CinemaHall();
        secondHall.setCapacity(80);
        secondHall.setDescription("4DX technology");
        cinemaHallService.add(secondHall);

        CinemaHall thirdHall = new CinemaHall();
        thirdHall.setCapacity(130);
        thirdHall.setDescription("CINETECH+ technology");
        cinemaHallService.add(thirdHall);

        System.out.println("List of all Cinema Halls");
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstPartOfDay = new MovieSession();
        firstPartOfDay.setMovie(spiderMan);
        firstPartOfDay.setCinemaHall(firstHall);
        firstPartOfDay.setShowTime(LocalDateTime
                .of(2022, 9, 19, 11, 30));
        movieSessionService.add(firstPartOfDay);

        MovieSession middleOfDay = new MovieSession();
        middleOfDay.setMovie(fastAndFurious);
        middleOfDay.setCinemaHall(secondHall);
        middleOfDay.setShowTime(LocalDateTime
                .of(2022, 9, 19, 15, 15));
        movieSessionService.add(middleOfDay);

        MovieSession eveningOfDay = new MovieSession();
        eveningOfDay.setMovie(fall);
        eveningOfDay.setCinemaHall(thirdHall);
        eveningOfDay.setShowTime(LocalDateTime
                .of(2022, 9, 19, 20, 05));
        movieSessionService.add(eveningOfDay);

        System.out.println("Session of Spider-Man movie");
        System.out.println(movieSessionService.findAvailableSessions(
                spiderMan.getId(), LocalDate.of(2022, 9, 19)));
    }
}
