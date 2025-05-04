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
    private static final Injector injector
            = Injector.getInstance("mate.academy");
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie forestGump = new Movie("Forest Gump");
        forestGump.setDescription("American comedy-drama film directed by "
                + " Robert Zemeckis and written by Eric Roth");
        Movie harryPotter = new Movie("Harry Potter");
        harryPotter.setDescription("Is a series of seven fantasy novels "
                + " written by British author J. K. Rowling");
        Movie freddyKrueger = new Movie("A Nightmare On Elm Street");
        freddyKrueger.setDescription("An American supernatural slasher-horror media "
                + " franchise consisting of nine films, a television series, novels, comic books, "
                + " and various other media.");
        Movie saw = new Movie("Saw");
        saw.setDescription("American psychological horror film directed by James Wan.");
        Movie shrek = new Movie("Shrek");
        shrek.setDescription("American computer-animated comedy film loosely based on "
                + " the 1990 picture book of the same name by William Steig");
        Movie bambi = new Movie("Bambi");
        bambi.setDescription("American animated drama film directed by David Hand");
        movieService.add(fastAndFurious);
        movieService.add(forestGump);
        movieService.add(harryPotter);
        movieService.add(freddyKrueger);
        movieService.add(saw);
        movieService.add(shrek);
        movieService.add(bambi);
        movieService.getAll().forEach(System.out::println);

        CinemaHall greenHall = new CinemaHall();
        greenHall.setCapacity(20);
        greenHall.setDescription("Imax");
        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(200);
        redHall.setDescription("3D hall");
        CinemaHall darkHall = new CinemaHall();
        darkHall.setCapacity(300);
        darkHall.setDescription("2D hall");
        CinemaHall blueHall = new CinemaHall();
        blueHall.setCapacity(500);
        blueHall.setDescription("VIP hall");
        cinemaHallService.add(greenHall);
        cinemaHallService.add(redHall);
        cinemaHallService.add(darkHall);
        cinemaHallService.add(blueHall);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession morningSession = new MovieSession();
        morningSession.setCinemaHall(darkHall);
        morningSession.setMovie(bambi);
        morningSession.setShowTime(LocalDateTime.of(2022, 9,
                28, 10, 00));
        MovieSession daySession = new MovieSession();
        daySession.setCinemaHall(redHall);
        daySession.setMovie(forestGump);
        daySession.setShowTime(LocalDateTime.of(2022, 9,
                28, 15, 00));
        MovieSession eveningSession = new MovieSession();
        eveningSession.setCinemaHall(greenHall);
        eveningSession.setMovie(harryPotter);
        eveningSession.setShowTime(LocalDateTime.of(2022, 9,
                28, 19, 00));
        MovieSession nightSession = new MovieSession();
        nightSession.setCinemaHall(blueHall);
        nightSession.setMovie(freddyKrueger);
        nightSession.setShowTime(LocalDateTime.of(2022, 9,
                28, 23, 00));
        movieSessionService.add(morningSession);
        movieSessionService.add(daySession);
        movieSessionService.add(eveningSession);
        movieSessionService.add(nightSession);

        System.out.println(movieSessionService.get(daySession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(freddyKrueger.getId(),
                LocalDate.of(2022, 9, 28)));
    }
}
