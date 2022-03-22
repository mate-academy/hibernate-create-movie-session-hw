package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie residentEvil6 = new Movie("Resident Evil 6");
        residentEvil6.setDescription("It's a very famous horror movie about walking dead.");
        movieService.add(fastAndFurious);
        movieService.add(residentEvil6);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(residentEvil6.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(80);
        cinemaHall.setDescription("4dx IMAX");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));

        MovieSession movieSession1 = new MovieSession();
        LocalDateTime localDateTime1 = LocalDateTime.parse("2022-03-23 13:00", formatter);
        movieSession1.setShowTime(localDateTime1);
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(cinemaHall);
        MovieSession movieSession2 = new MovieSession();
        LocalDateTime localDateTime2 = LocalDateTime.parse("2022-03-23 19:00", formatter);
        movieSession2.setShowTime(localDateTime2);
        movieSession2.setMovie(residentEvil6);
        movieSession2.setCinemaHall(cinemaHall);
        MovieSession movieSession3 = new MovieSession();
        LocalDateTime localDateTime3 = LocalDateTime.parse("2022-03-23 20:00", formatter);
        movieSession3.setShowTime(localDateTime3);
        movieSession3.setMovie(fastAndFurious);
        movieSession3.setCinemaHall(cinemaHall);
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        movieSessionService.add(movieSession3);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now().withDayOfMonth(23)).forEach(System.out::println);
        movieSessionService.findAvailableSessions(residentEvil6.getId(),
                LocalDate.now().withDayOfMonth(23)).forEach(System.out::println);
    }
}
