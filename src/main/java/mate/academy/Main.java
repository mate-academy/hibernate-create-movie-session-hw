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

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        movieService.get(fastAndFurious.getId());
        System.out.println();
        Movie theShawshankRedemption = new Movie("The Shawshank Redemption");
        theShawshankRedemption.setDescription("It is an adaptation of the Stephen King "
                + "novella Rita Hayworth and Shawshank Redemption.");
        movieService.add(theShawshankRedemption);
        movieService.get(theShawshankRedemption.getId());
        System.out.println();
        Movie theRevenant = new Movie("The Revenant");
        theRevenant.setDescription("A frontiersman on a fur trading expedition in the 1820s "
                + "fights for survival after being mauled by a bear "
                + "and left for dead by members of his own hunting team.");
        movieService.add(theRevenant);
        movieService.get(theRevenant.getId());
        System.out.println();
        movieService.getAll().forEach(System.out::println);
        System.out.println();
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall cinemaHallA = new CinemaHall(275, "IMAX cinema hall A");
        cinemaHallService.add(cinemaHallA);
        CinemaHall cinemaHallB = new CinemaHall(300, "3D cinema hall B");
        cinemaHallService.add(cinemaHallB);
        CinemaHall cinemaHallC = new CinemaHall(350, "Regular cinema hall C");
        cinemaHallService.add(cinemaHallC);
        cinemaHallService.getAll().forEach(System.out::println);
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        LocalDateTime theRevenantSessionTime = LocalDateTime.now().plusHours(20);
        MovieSession movieSessionTheRevenant
                = new MovieSession(theRevenant, cinemaHallA, theRevenantSessionTime);
        movieSessionService.add(movieSessionTheRevenant);
        LocalDateTime theRevenantSessionTime2 = LocalDateTime.now().plusHours(4);
        MovieSession movieSessionTheRevenant2
                = new MovieSession(theRevenant, cinemaHallB, theRevenantSessionTime2);
        movieSessionService.add(movieSessionTheRevenant2);
        LocalDateTime fastAndFuriousSessionTime = LocalDateTime.now().plusHours(3);
        MovieSession movieSessionFastAndFurious
                = new MovieSession(fastAndFurious, cinemaHallB, fastAndFuriousSessionTime);
        movieSessionService.add(movieSessionFastAndFurious);
        LocalDateTime fastAndFuriousSessionTime2 = LocalDateTime.now().plusHours(15);
        MovieSession movieSessionFastAndFurious2
                = new MovieSession(fastAndFurious, cinemaHallB, fastAndFuriousSessionTime2);
        movieSessionService.add(movieSessionFastAndFurious2);
        LocalDateTime fastAndFuriousSessionTime3 = LocalDateTime.now().plusDays(2);
        MovieSession movieSessionFastAndFurious3
                = new MovieSession(fastAndFurious, cinemaHallA, fastAndFuriousSessionTime3);
        movieSessionService.add(movieSessionFastAndFurious3);
        LocalDateTime theShawshankRedemptionSessionTime = LocalDateTime.now().plusDays(2);
        MovieSession movieSessionTheShawshankRedemption = new MovieSession();
        movieSessionTheShawshankRedemption.setMovie(theShawshankRedemption);
        movieSessionTheShawshankRedemption.setCinemaHall(cinemaHallC);
        movieSessionTheShawshankRedemption.setShowTime(theShawshankRedemptionSessionTime);
        movieSessionService.add(movieSessionTheShawshankRedemption);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now().plusDays(1)).forEach(System.out::println);
        System.out.println();
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now().plusDays(2)).forEach(System.out::println);
        System.out.println();
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()).forEach(System.out::println);
        System.out.println();
        movieSessionService.findAvailableSessions(theRevenant.getId(),
                LocalDate.now()).forEach(System.out::println);
        System.out.println();
        movieSessionService.findAvailableSessions(theRevenant.getId(),
                LocalDate.now().plusDays(1)).forEach(System.out::println);
        System.out.println();
        movieSessionService.findAvailableSessions(theShawshankRedemption.getId(),
                LocalDate.now().plusDays(2)).forEach(System.out::println);
        System.out.println();
        movieSessionService.getAll().forEach(System.out::println);
    }
}
