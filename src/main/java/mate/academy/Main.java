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

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie movie2 = new Movie("Movie2 title");
        movie2.setDescription("Movie2 description");
        movieService.add(movie2);

        movieService.getAll().forEach(System.out::println);
        System.out.println();

        CinemaHall cinemaHall1 = new CinemaHall(100,"CinemaHall_1 description");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall1);
        System.out.println(cinemaHallService.get(cinemaHall1.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println();

        MovieSession movieSessionToday = new MovieSession();
        movieSessionToday.setMovie(fastAndFurious);
        movieSessionToday.setCinemaHall(cinemaHall1);
        LocalDateTime nowDateTime = LocalDateTime.now();
        movieSessionToday.setShowTime(nowDateTime);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        movieSessionService.add(movieSessionToday);

        MovieSession movieSessionTomorrow = new MovieSession();
        movieSessionTomorrow.setMovie(fastAndFurious);
        movieSessionTomorrow.setCinemaHall(cinemaHall1);
        movieSessionTomorrow.setShowTime(nowDateTime.plusDays(1L));
        movieSessionService.add(movieSessionTomorrow);
        System.out.println();

        MovieSession movie2SessionToday = new MovieSession();
        movie2SessionToday.setMovie(movie2);
        movie2SessionToday.setCinemaHall(cinemaHall1);
        movie2SessionToday.setShowTime(nowDateTime);
        movieSessionService.add(movie2SessionToday);

        System.out.println(movieSessionService.get(movieSessionToday.getId()));
        System.out.println(movieSessionService.get(movieSessionTomorrow.getId()));
        System.out.println(movieSessionService.get(movie2SessionToday.getId()));
        System.out.println();

        System.out.println("Today cinema sessions:");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
        System.out.println();

        System.out.println("Today movie2 sessions:");
        movieSessionService.findAvailableSessions(movie2.getId(), LocalDate.now())
                .forEach(System.out::println);
        System.out.println();

        System.out.println("Tomorrow cinema sessions:");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                        LocalDate.now().plusDays(1L))
                .forEach(System.out::println);
        System.out.println();

        System.out.println("Уesterday cinema sessions:");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                        LocalDate.now().minusDays(1L))
                .forEach(System.out::println);
        System.out.println();
    }
}
