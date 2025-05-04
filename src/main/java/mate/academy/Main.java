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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        // Adding data to DB
        Movie movie1 = new Movie("Fast and Furious");
        movie1.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(movie1);
        Movie movie2 = new Movie("The Last Samurai");
        movie2.setDescription("A drama film about British soldier in Japan.");
        movieService.add(movie2);
        System.out.println(System.lineSeparator());

        CinemaHall cinemaHall1 = new CinemaHall(35, "4DX");
        cinemaHallService.add(cinemaHall1);
        CinemaHall cinemaHall2 = new CinemaHall(150, "IMAX");
        cinemaHallService.add(cinemaHall2);
        System.out.println(System.lineSeparator());

        LocalDateTime showTime1 = LocalDateTime.now().minusDays(1).minusHours(3);
        MovieSession movieSession1 = new MovieSession(movie1, cinemaHall1, showTime1);
        movieSessionService.add(movieSession1);
        LocalDateTime showTime2 = LocalDateTime.now().minusDays(1);
        MovieSession movieSession2 = new MovieSession(movie2, cinemaHall2, showTime2);
        movieSessionService.add(movieSession2);
        LocalDateTime showTime3 = LocalDateTime.now().minusDays(1).plusHours(3);
        MovieSession movieSession3 = new MovieSession(movie1, cinemaHall2, showTime3);
        movieSessionService.add(movieSession3);
        LocalDateTime showTime4 = LocalDateTime.now().minusHours(3);
        MovieSession movieSession4 = new MovieSession(movie1, cinemaHall1, showTime4);
        movieSessionService.add(movieSession4);
        LocalDateTime showTime5 = LocalDateTime.now();
        MovieSession movieSession5 = new MovieSession(movie2, cinemaHall2, showTime5);
        movieSessionService.add(movieSession5);
        LocalDateTime showTime6 = LocalDateTime.now().plusHours(3);
        MovieSession movieSession6 = new MovieSession(movie2, cinemaHall1, showTime6);
        movieSessionService.add(movieSession6);
        LocalDateTime showTime7 = LocalDateTime.now().plusDays(1).minusHours(3);
        MovieSession movieSession7 = new MovieSession(movie1, cinemaHall1, showTime7);
        movieSessionService.add(movieSession7);
        LocalDateTime showTime8 = LocalDateTime.now().plusDays(1);
        MovieSession movieSession8 = new MovieSession(movie2, cinemaHall2, showTime8);
        movieSessionService.add(movieSession8);
        LocalDateTime showTime9 = LocalDateTime.now().plusDays(1).plusHours(3);
        MovieSession movieSession9 = new MovieSession(movie1, cinemaHall2, showTime9);
        movieSessionService.add(movieSession9);
        System.out.println(System.lineSeparator());

        // Getting all data with get by ID
        System.out.println(movieService.get(movie1.getId()));
        System.out.println(movieService.get(movie2.getId()));
        System.out.println(System.lineSeparator());
        System.out.println(cinemaHallService.get(cinemaHall1.getId()));
        System.out.println(cinemaHallService.get(cinemaHall2.getId()));
        System.out.println(System.lineSeparator());
        System.out.println(movieSessionService.get(movieSession1.getId()));
        System.out.println(movieSessionService.get(movieSession2.getId()));
        System.out.println(movieSessionService.get(movieSession3.getId()));
        System.out.println(movieSessionService.get(movieSession4.getId()));
        System.out.println(movieSessionService.get(movieSession5.getId()));
        System.out.println(movieSessionService.get(movieSession6.getId()));
        System.out.println(movieSessionService.get(movieSession7.getId()));
        System.out.println(movieSessionService.get(movieSession8.getId()));
        System.out.println(movieSessionService.get(movieSession9.getId()));
        System.out.println(System.lineSeparator());

        // Getting data with getAll
        movieService.getAll().forEach(System.out::println);
        System.out.println(System.lineSeparator());
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println(System.lineSeparator());

        // Getting available movie sessions for yesterday
        movieSessionService.findAvailableSessions(movie1.getId(),
                LocalDate.now().minusDays(1)).forEach(System.out::println);
        System.out.println(System.lineSeparator());
        movieSessionService.findAvailableSessions(movie2.getId(),
                LocalDate.now().minusDays(1)).forEach(System.out::println);
        System.out.println(System.lineSeparator());

        // Getting available movie sessions for today
        movieSessionService.findAvailableSessions(movie1.getId(), LocalDate.now())
                .forEach(System.out::println);
        System.out.println(System.lineSeparator());
        movieSessionService.findAvailableSessions(movie2.getId(), LocalDate.now())
                .forEach(System.out::println);
        System.out.println(System.lineSeparator());

        // Getting available movie sessions for tomorrow
        movieSessionService.findAvailableSessions(movie1.getId(), LocalDate.now().plusDays(1))
                .forEach(System.out::println);
        System.out.println(System.lineSeparator());
        movieSessionService.findAvailableSessions(movie2.getId(), LocalDate.now().plusDays(1))
                .forEach(System.out::println);
        System.out.println(System.lineSeparator());

        // Getting available movie sessions for day after tomorrow (empty list assumed)
        System.out.println("attempt1");
        movieSessionService.findAvailableSessions(movie1.getId(), LocalDate.now().plusDays(2))
                .forEach(System.out::println);
        System.out.println("attempt2");
        movieSessionService.findAvailableSessions(movie2.getId(), LocalDate.now().plusDays(2))
                .forEach(System.out::println);
    }
}
