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
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        Movie bearAdventure = new Movie("Hungry bear adventure");
        bearAdventure.setDescription("About hungry bear. Only for children!");
        movieService.add(bearAdventure);
        System.out.println(movieService.get(bearAdventure.getId()));
        System.out.println("All movies in db : ");
        movieService.getAll().forEach(System.out::println);
        System.out.println("-------------------------------------------");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall hall4dx = new CinemaHall();
        hall4dx.setCapacity(400);
        hall4dx.setDescription("IMax4DX");
        System.out.println("cinema hall hall4dx after adding to db"
                + cinemaHallService.add(hall4dx));
        System.out.println("cinema hall " + hall4dx
                + " in db is " + cinemaHallService.get(hall4dx.getId()));
        CinemaHall hall3d = new CinemaHall();
        hall3d.setCapacity(200);
        hall3d.setDescription("3Dhall");
        System.out.println("cinema hall 3Dhall after adding to db "
                + cinemaHallService.add(hall3d));
        System.out.println("cinema hall " + hall3d + " in db is "
                + cinemaHallService.get(hall3d.getId()));
        System.out.println("All cinema halls in db : ");
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("-------------------------------------------");

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setShowTime(LocalDateTime.of(2021,12,12,12,12));
        movieSession1.setCinemaHall(hall4dx);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(bearAdventure);
        movieSession2.setShowTime(LocalDateTime.of(2021,12,12,15, 0));
        movieSession2.setCinemaHall(hall3d);
        movieSessionService.add(movieSession2);
        MovieSession movieSession3 = new MovieSession();
        movieSession3.setMovie(bearAdventure);
        movieSession3.setShowTime(LocalDateTime.of(2021,12,13,15, 0));
        movieSession3.setCinemaHall(hall3d);
        movieSessionService.add(movieSession3);
        MovieSession movieSession4 = new MovieSession();
        movieSession4.setMovie(bearAdventure);
        movieSession4.setShowTime(LocalDateTime.of(2021,12,12,23, 59));
        movieSession4.setCinemaHall(hall3d);
        System.out.println("movie session 4 before adding to db " + movieSession4);
        System.out.println("movie session 4 after adding to db "
                + movieSessionService.add(movieSession4));
        System.out.println("movie session 1 " + movieSession1 + " in db : "
                + movieSessionService.get(movieSession1.getId()));
        System.out.println("sessions for bearAdventure(2021.12.12) : "
                + movieSessionService.findAvailableSessions(
                bearAdventure.getId(), LocalDate.of(2021, 12, 12)));
        System.out.println("sessions for bearAdventure(2021.12.13) : "
                + movieSessionService.findAvailableSessions(
                bearAdventure.getId(), LocalDate.of(2021, 12, 13)));
    }
}
