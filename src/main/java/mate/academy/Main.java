package mate.academy;

import mate.academy.lib.Inject;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Injector injector
            = Injector.getInstance("mate.academy");
    private static final LocalDate FIRST_DATE = LocalDate.of(2023, 11, 10);
    private static final LocalDate SECOND_DATE = LocalDate.of(2020, 5, 8);
    private static final LocalDate THIRD_DATE = LocalDate.of(2019, 12, 9);
    private static final LocalDate FOURTH_DATE = LocalDate.of(2004, 3, 17);
    private static final LocalDateTime FIRST_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime SECOND_DATE_TIME
            = LocalDateTime.of(2004, 3, 17, 15, 30);
    private static final LocalDateTime THIRD_DATE_TIME
            = LocalDateTime.of(2020, 5, 8, 12, 0);
    private static final LocalDateTime FOURTH_DATE_TIME
            = LocalDateTime.of(2022, 10, 8, 10, 10);

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        //----------------------------------------------------
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(1L));

        Movie theRevenant = new Movie("The Revenant");
        theRevenant.setDescription("About a man who survived");
        movieService.add(theRevenant);
        System.out.println(movieService.get(2L));

        Movie attackOnTitan = new Movie("Attack On Titan");
        attackOnTitan.setDescription("An anime series with a manga of the same name");
        movieService.add(attackOnTitan);
        System.out.println(movieService.get(3L));

        Movie onePlusOne = new Movie("One plus One");
        onePlusOne.setDescription("The best film");
        movieService.add(onePlusOne);
        System.out.println(movieService.get(4L));

        System.out.println(movieService.getAll());
        //---------------------------------------------------
        CinemaHall firstCinemaHall = new CinemaHall(30, "Hall with 30 places");
        cinemaHallService.add(firstCinemaHall);
        System.out.println(cinemaHallService.get(1L));

        CinemaHall secondCinemaHall = new CinemaHall(20, "Hall with 20 places");
        cinemaHallService.add(secondCinemaHall);
        System.out.println(cinemaHallService.get(2L));

        CinemaHall thirdCinemaHall = new CinemaHall(15, "Hall with 15 places");
        cinemaHallService.add(thirdCinemaHall);
        System.out.println(cinemaHallService.get(3L));

        CinemaHall fourthCinemaHall = new CinemaHall(57, "Hall with 57 places");
        cinemaHallService.add(fourthCinemaHall);
        System.out.println(cinemaHallService.get(4L));

        System.out.println(cinemaHallService.getAll());
        //-----------------------------------------------------
        MovieSession firstSession
                = new MovieSession(fastAndFurious, firstCinemaHall, FIRST_DATE_TIME);
        movieSessionService.add(firstSession);
        System.out.println(movieSessionService.get(1L));

        MovieSession secondSession
                = new MovieSession(theRevenant, secondCinemaHall, SECOND_DATE_TIME);
        movieSessionService.add(secondSession);
        System.out.println(movieSessionService.get(2L));

        MovieSession thirdSession
                = new MovieSession(attackOnTitan, thirdCinemaHall, THIRD_DATE_TIME);
        movieSessionService.add(thirdSession);
        System.out.println(movieSessionService.get(3L));

        MovieSession fourthSession
                = new MovieSession(onePlusOne, fourthCinemaHall, FOURTH_DATE_TIME);
        movieSessionService.add(fourthSession);
        System.out.println(movieSessionService.get(4L));

        System.out.println(movieSessionService.findAvailableSessions(2L, FOURTH_DATE));
        System.out.println(movieSessionService.findAvailableSessions(3L, FIRST_DATE));
    }
}
