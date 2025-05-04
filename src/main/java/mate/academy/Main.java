package mate.academy;

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
    private static final LocalDateTime THIRD_SEPTEMBER_MORNING_DATE = LocalDateTime.of(
            2023, Month.SEPTEMBER, 3,
            10, 0, 0);
    private static final LocalDateTime FOURTH_SEPTEMBER_NIGHT_DATE = LocalDateTime.of(
            2023, Month.SEPTEMBER, 4,
            23, 59, 59);
    private static final LocalDateTime THIRD_SEPTEMBER_EVENING_DATE = LocalDateTime.of(
            2023, Month.SEPTEMBER, 3,
            18, 0, 0);

    public static void main(String[] args) {
        final Injector injector = Injector.getInstance("mate.academy");

        //Movie service actions
        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie mib = new Movie("Men in black");
        mib.setDescription("A police officer joins a secret organization "
                + "that polices and monitors extraterrestrial interactions on Earth");
        movieService.add(mib);
        System.out.println(movieService.get(mib.getId()));
        movieService.getAll().forEach(System.out::println);

        //CinemaHallService actions
        final CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);

        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(100);
        redHall.setDescription("Red cinema hall with comfortable seats");
        cinemaHallService.add(redHall);
        System.out.println(cinemaHallService.get(redHall.getId()));

        CinemaHall blueHall = new CinemaHall();
        blueHall.setCapacity(200);
        blueHall.setDescription(
                "Blue cinema hall with extra large screen and powerful audio system");
        cinemaHallService.add(blueHall);
        System.out.println(cinemaHallService.get(blueHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        //MovieSessionService Action
        final MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        MovieSession thirdSeptemberMorning = new MovieSession();
        thirdSeptemberMorning.setMovie(fastAndFurious);
        thirdSeptemberMorning.setCinemaHall(redHall);
        thirdSeptemberMorning.setShowTime(THIRD_SEPTEMBER_MORNING_DATE);
        movieSessionService.add(thirdSeptemberMorning);
        System.out.println(movieSessionService.get(thirdSeptemberMorning.getId()));

        MovieSession thirdSeptemberEvening = new MovieSession();
        thirdSeptemberEvening.setMovie(mib);
        thirdSeptemberEvening.setCinemaHall(blueHall);
        thirdSeptemberEvening.setShowTime(THIRD_SEPTEMBER_EVENING_DATE);
        movieSessionService.add(thirdSeptemberEvening);
        System.out.println(movieSessionService.get(thirdSeptemberEvening.getId()));

        MovieSession fourthSeptemberNight = new MovieSession();
        fourthSeptemberNight.setMovie(mib);
        fourthSeptemberNight.setCinemaHall(redHall);
        fourthSeptemberNight.setShowTime(FOURTH_SEPTEMBER_NIGHT_DATE);
        movieSessionService.add(fourthSeptemberNight);
        System.out.println(movieSessionService.get(fourthSeptemberNight.getId()));
        movieSessionService
                .findAvailableSessions(mib.getId(), THIRD_SEPTEMBER_EVENING_DATE.toLocalDate())
                .forEach(System.out::println);

    }
}
