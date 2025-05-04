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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall cinemaHall = new CinemaHall(150, "Cinema in LavinaMoll");
        CinemaHall cinemaOscar = new CinemaHall(100, "Cinema in Plaza");
        cinemaHallService.add(cinemaHall);
        cinemaHallService.add(cinemaOscar);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        Movie granTorino = new Movie("Gran Torino", "Retired auto mechanic Walt "
                + "Kowalski spends his days fixing things around the house, "
                + "drinking beer, and going to the barber");
        movieService.add(granTorino);
        MovieSession movieSession16 = new MovieSession(LocalDateTime.parse(
                "2023-11-30T16:00:00", DateTimeFormatter.ISO_DATE_TIME),
                fastAndFurious, cinemaHall);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession16);
        MovieSession movieSession18 = new MovieSession(LocalDateTime.parse(
                "2023-11-30T18:00:00", DateTimeFormatter.ISO_DATE_TIME), granTorino, cinemaHall);
        movieSessionService.add(movieSession18);
        MovieSession movieSession15 = new MovieSession(LocalDateTime.parse(
                "2023-11-30T15:00:00", DateTimeFormatter.ISO_DATE_TIME), granTorino, cinemaOscar);
        movieSessionService.add(movieSession15);

        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println();
        movieService.getAll().forEach(System.out::println);
        System.out.println();
        movieSessionService.findAvailableSessions(2L,
                LocalDate.of(2023, 11, 30))
                .forEach(System.out::println);
    }
}
