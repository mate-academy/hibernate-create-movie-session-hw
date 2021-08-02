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
        //--------Movies--------//
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie interstellar = new Movie("Interstellar");
        interstellar.setDescription("When Earth becomes uninhabitable in the future, "
                + "a farmer and ex-NASA pilot, Joseph Cooper, is tasked to pilot a spacecraft, "
                + "along with a team of researchers, to find a new planet for humans.");
        movieService.add(interstellar);

        Movie tronLegacy = new Movie("Tron: Legacy");
        tronLegacy.setDescription("Sam misses his father, a virtual world designer, "
                + "and enters a virtual space that has become much "
                + "more dangerous than his father intended. Now, both "
                + "father and son embark upon a life-and-death journey.");
        movieService.add(tronLegacy);

        Movie inception = new Movie("Inception");
        inception.setDescription("Cobb steals information from his targets by entering "
                + "their dreams. Saito offers to wipe clean Cobb's criminal history "
                + "as payment for performing an inception on his sick competitor's son.");
        movieService.add(inception);

        movieService.getAll().forEach(System.out::println);

        //--------Cinema halls--------//
        CinemaHall redCinemaHall = new CinemaHall(50);
        redCinemaHall.setDescription("Red cinema hall with comfy chairs");
        cinemaHallService.add(redCinemaHall);

        CinemaHall greenCinemaHall = new CinemaHall(60);
        greenCinemaHall.setDescription("Green cinema hall with big screen chairs");
        cinemaHallService.add(greenCinemaHall);

        CinemaHall blueCinemaHall = new CinemaHall(70);
        blueCinemaHall.setDescription("blue cinema hall with comfy chairs, big screen and IMAX");
        cinemaHallService.add(blueCinemaHall);
        cinemaHallService.get(blueCinemaHall.getId());
        cinemaHallService.getAll().forEach(System.out::println);

        //--------Movie sessions--------//
        MovieSession movieSession1 = new MovieSession(interstellar, blueCinemaHall,
                LocalDateTime.now().plusMinutes(5).plusHours(3).plusDays(5));
        movieSessionService.add(movieSession1);

        MovieSession movieSession2 = new MovieSession(interstellar, blueCinemaHall,
                LocalDateTime.now().plusMinutes(10).plusHours(6).plusDays(5));
        movieSessionService.add(movieSession2);

        MovieSession movieSession3 = new MovieSession(inception, greenCinemaHall,
                LocalDateTime.now().plusMinutes(30).plusHours(5).plusDays(2));
        movieSessionService.add(movieSession3);

        MovieSession movieSession4 = new MovieSession(tronLegacy, blueCinemaHall,
                LocalDateTime.now().plusMinutes(15).plusHours(5).plusDays(5));
        movieSessionService.add(movieSession4);

        MovieSession movieSession5 = new MovieSession(fastAndFurious, redCinemaHall,
                LocalDateTime.now().plusMinutes(5).plusHours(5).plusDays(5));
        movieSessionService.add(movieSession5);

        movieSessionService.get(movieSession3.getId());
        movieSessionService.findAvailableSessions(interstellar.getId(),
                LocalDate.now().plusDays(5)).forEach(System.out::println);
    }
}
