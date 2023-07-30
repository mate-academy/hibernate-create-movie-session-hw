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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie passengers = new Movie("Passengers");
        passengers.setDescription("Jennifer Lawrence and Chris Pratt are two passengers "
                + "onboard a spaceship transporting them to a new life on another planet.");
        movieService.add(passengers);
        System.out.println(movieService.get(passengers.getId()));

        Movie murderMystery = new Movie("Murder Mystery 2");
        murderMystery.setDescription("Four years after solving their first murder mystery, Nick "
                + "and Audrey Spitz are now full-time detectives struggling to get their private "
                + "eye agency off the ground");
        movieService.add(murderMystery);
        System.out.println(movieService.get(murderMystery.getId()));

        Movie kandahar = new Movie("Kandahar");
        kandahar.setDescription("CIA operative Tom Harris failed his mission and his identity"
                + " was exposed. Now the agent has to escape from Afghanistan.");
        movieService.add(kandahar);
        System.out.println(movieService.get(kandahar.getId()));

        movieService.getAll().forEach(System.out::println);

        CinemaHall greenHall = new CinemaHall();
        greenHall.setCapacity(46);
        greenHall.setDescription("Green Hall");
        cinemaHallService.add(greenHall);
        System.out.println(cinemaHallService.get(greenHall.getId()));

        CinemaHall blueHall = new CinemaHall();
        blueHall.setCapacity(80);
        blueHall.setDescription("Blue Hall");
        cinemaHallService.add(blueHall);
        System.out.println(cinemaHallService.get(blueHall.getId()));

        System.out.println(cinemaHallService.getAll());

        MovieSession passengersDaySession = new MovieSession();
        passengersDaySession.setMovie(passengers);
        passengersDaySession.setCinemaHall(greenHall);
        passengersDaySession.setShowTime(LocalDateTime
                .of(2023, Month.AUGUST, 02, 16, 50, 00));
        movieSessionService.add(passengersDaySession);
        System.out.println(movieSessionService.get(passengersDaySession.getId()));

        MovieSession passengersEveningSession = new MovieSession();
        passengersEveningSession.setMovie(passengers);
        passengersEveningSession.setCinemaHall(blueHall);
        passengersEveningSession.setShowTime(LocalDateTime
                .of(2023, Month.AUGUST, 02, 20, 00, 00));
        movieSessionService.add(passengersEveningSession);
        System.out.println(movieSessionService.get(passengersEveningSession.getId()));

        MovieSession murderMysteryDaySession = new MovieSession();
        murderMysteryDaySession.setMovie(murderMystery);
        murderMysteryDaySession.setCinemaHall(blueHall);
        murderMysteryDaySession.setShowTime(LocalDateTime
                .of(2023, Month.AUGUST, 02, 13, 20, 00));
        movieSessionService.add(murderMysteryDaySession);
        System.out.println(movieSessionService.get(murderMysteryDaySession.getId()));

        MovieSession murderMysteryEveningSession = new MovieSession();
        murderMysteryEveningSession.setMovie(murderMystery);
        murderMysteryEveningSession.setCinemaHall(blueHall);
        murderMysteryEveningSession.setShowTime(LocalDateTime
                .of(2023, Month.AUGUST, 03, 19, 45, 00));
        movieSessionService.add(murderMysteryEveningSession);
        System.out.println(movieSessionService.get(murderMysteryEveningSession.getId()));

        MovieSession kandaharEveningSession = new MovieSession();
        kandaharEveningSession.setMovie(kandahar);
        kandaharEveningSession.setCinemaHall(greenHall);
        kandaharEveningSession.setShowTime(LocalDateTime
                .of(2023, Month.AUGUST, 03, 18, 30, 00));
        movieSessionService.add(kandaharEveningSession);
        System.out.println(movieSessionService.get(kandaharEveningSession.getId()));

        MovieSession kandaharNightSession = new MovieSession();
        kandaharNightSession.setMovie(kandahar);
        kandaharNightSession.setCinemaHall(greenHall);
        kandaharNightSession.setShowTime(LocalDateTime
                .of(2023, Month.AUGUST, 04, 22, 00, 00));
        movieSessionService.add(kandaharNightSession);
        System.out.println(movieSessionService.get(kandaharNightSession.getId()));

        System.out.println(movieSessionService.findAvailableSessions(passengers.getId(),
                LocalDate.of(2023, Month.AUGUST, 02)));

        System.out.println(movieSessionService.findAvailableSessions(murderMystery.getId(),
                LocalDate.of(2023, Month.AUGUST, 03)));

        System.out.println(movieSessionService.findAvailableSessions(kandahar.getId(),
                LocalDate.of(2023, Month.AUGUST, 04)));
    }
}
