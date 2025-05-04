package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie harryPotter = new Movie("Harry Potter");
        harryPotter.setDescription("Adventure movie, where main hero is young wizard boy");
        Movie fightClub = new Movie("Fight club");
        fightClub.setDescription("An insomniac office worker and a "
                + "devil-may-care soap maker form an underground "
                + "fight club that evolves into much more.");
        MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.add(harryPotter);
        movieService.add(fightClub);
        System.out.println("Work with movie service");
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHall vipHall = new CinemaHall();
        vipHall.setCapacity(1);
        vipHall.setDescription("Not big hall with comfort  and functional sofas.");
        CinemaHall imaxHall = new CinemaHall();
        imaxHall.setCapacity(2);
        imaxHall.setDescription("Large hall with quality sound and imax screen.");
        CinemaHall hall3D = new CinemaHall();
        hall3D.setCapacity(3);
        hall3D.setDescription("Middle sized hall with 3D pictures");
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(vipHall);
        cinemaHallService.add(imaxHall);
        cinemaHallService.add(hall3D);
        System.out.println("Work with cinemaHall service");
        System.out.println(cinemaHallService.get(imaxHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        MovieSession morningSession = new MovieSession();
        morningSession.setCinemaHall(vipHall);
        morningSession.setMovie(harryPotter);
        morningSession.setShowTime(LocalDateTime.of(2022, Month.APRIL,19,10,00));
        MovieSession lunchSession = new MovieSession();
        lunchSession.setCinemaHall(imaxHall);
        lunchSession.setMovie(fastAndFurious);
        lunchSession.setShowTime(LocalDateTime.of(2022,Month.APRIL, 19, 2,20));
        MovieSession eveningSession = new MovieSession();
        eveningSession.setCinemaHall(hall3D);
        eveningSession.setMovie(fastAndFurious);
        eveningSession.setShowTime(LocalDateTime.of(2022,Month.APRIL,19,8,00));
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(morningSession);
        movieSessionService.add(lunchSession);
        movieSessionService.add(eveningSession);
        System.out.println("Work with movieSession service");
        System.out.println(movieSessionService.get(lunchSession.getId()));
        movieSessionService.findAvailableSessions(1L,LocalDate.of(2022,Month.APRIL,19));
    }
}
