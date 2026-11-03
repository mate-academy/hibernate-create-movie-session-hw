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
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        //add a few movies to db and getting them by id,
        //then showing a list with all movies
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println("Added a new movie: " + fastAndFurious.getId());

        Movie harryPotter = new Movie("Harry Potter");
        harryPotter.setDescription("The Harry Potter boy who lived.");
        movieService.add(harryPotter);
        System.out.println("Added a new movie: " + movieService.get(harryPotter.getId()));

        movieService.getAll().forEach(System.out::println);

        //add a few cinema halls to db and getting them by id,
        // then showing a list with all cinema halls.
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);

        CinemaHall bigCinemaHall = new CinemaHall(300);
        bigCinemaHall.setDescription("The Big cinema hall has capacity for 300 people.");
        cinemaHallService.add(bigCinemaHall);
        System.out.println("Added a new cinema hall: "
                + cinemaHallService.get(bigCinemaHall.getId()));

        CinemaHall vipCinemaHall = new CinemaHall(50);
        vipCinemaHall.setDescription("The Vip cinema hall has very comfortable armchairs.");
        cinemaHallService.add(vipCinemaHall);
        System.out.println("Added a new cinema hall: "
                + cinemaHallService.get(vipCinemaHall.getId()));

        cinemaHallService.getAll().forEach(System.out::println);

        //add a few movie sessions to db and getting them by id
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        MovieSession harryPotterSessionBigHall = new MovieSession(harryPotter, bigCinemaHall);
        harryPotterSessionBigHall.setShowTime(LocalDateTime.now());
        movieSessionService.add(harryPotterSessionBigHall);
        System.out.println("Added a new movie session: "
                + movieSessionService.get(harryPotterSessionBigHall.getId()));

        MovieSession harryPotterSessionVipHall = new MovieSession(harryPotter, vipCinemaHall);
        harryPotterSessionVipHall.setShowTime(LocalDateTime.now());
        movieSessionService.add(harryPotterSessionVipHall);
        System.out.println("Added a new movie session: "
                + movieSessionService.get(harryPotterSessionVipHall.getId()));

        MovieSession fastAndFuriousBigHall = new MovieSession(fastAndFurious, bigCinemaHall);
        fastAndFuriousBigHall.setShowTime(LocalDateTime.now());
        movieSessionService.add(fastAndFuriousBigHall);
        System.out.println("Added a new movie session: "
                + movieSessionService.get(fastAndFuriousBigHall.getId()));

        MovieSession fastAndFuriousVipHall = new MovieSession(fastAndFurious, vipCinemaHall);
        fastAndFuriousVipHall.setShowTime(LocalDateTime.now());
        movieSessionService.add(fastAndFuriousVipHall);
        System.out.println("Added a new movie session: "
                + movieSessionService.get(fastAndFuriousVipHall.getId()));

        //find movie sessions by id and date
        System.out.println("Movie sessions for Harry Potter: ");
        movieSessionService.findAvailableSessions(harryPotter.getId(),
                LocalDate.now()).forEach(System.out::println);
        System.out.println("Movie sessions for Fast and Furious: ");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()).forEach(System.out::println);

    }
}
