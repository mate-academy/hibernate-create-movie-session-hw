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

        Movie matrix = new Movie("Matrix");
        matrix.setDescription("When computer programmer Thomas Anderson, "
                + "under the hacker alias \"Neo\", uncovers the truth, "
                + "he \"is drawn into a rebellion against the machines");

        movieService.add(matrix);
        System.out.println(movieService.get(matrix.getId()));
        System.out.println("All movies in db : ");
        movieService.getAll().forEach(System.out::println);
        System.out.println("-------------------------------------------");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHall1 = new CinemaHall();
        cinemaHall1.setCapacity(50);
        cinemaHall1.setDescription("hall 1");
        cinemaHallService.add(cinemaHall1);
        CinemaHall cinemaHall2 = new CinemaHall();
        cinemaHall2.setCapacity(70);
        cinemaHall2.setDescription("hall 2");
        cinemaHallService.add(cinemaHall2);
        System.out.println("All cinema halls in db : ");
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("-------------------------------------------");

        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setShowTime(LocalDateTime.parse("2021-12-27T15:00:00"));
        movieSession1.setCinemaHall(cinemaHall1);
        movieSessionService.add(movieSession1);
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(fastAndFurious);
        movieSession2.setShowTime(LocalDateTime.parse("2021-12-27T15:00:00"));
        movieSession2.setCinemaHall(cinemaHall2);
        movieSessionService.add(movieSession2);
        MovieSession movieSession3 = new MovieSession();
        movieSession3.setMovie(matrix);
        movieSession3.setShowTime(LocalDateTime.parse("2021-12-28T15:00:00"));
        movieSession3.setCinemaHall(cinemaHall2);
        movieSessionService.add(movieSession3);
        MovieSession movieSession4 = new MovieSession();
        movieSession4.setMovie(fastAndFurious);
        movieSession4.setShowTime(LocalDateTime.parse("2021-12-28T15:00:00"));
        movieSession4.setCinemaHall(cinemaHall2);
        System.out.println("movie session 4 before adding to db " + movieSession4);
        System.out.println("movie session 4 after adding to db "
                + movieSessionService.add(movieSession4));
        System.out.println("movie session 1 " + movieSession1 + " in db : "
                + movieSessionService.get(movieSession1.getId()));
        System.out.println("sessions for matrix(2021.12.27) : "
                + movieSessionService.findAvailableSessions(
                matrix.getId(), LocalDate.parse("2021-12-27")));
        System.out.println("sessions for fast and furious(2021.12.28) : "
                + movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.parse("2021-12-28")));
    }
}
