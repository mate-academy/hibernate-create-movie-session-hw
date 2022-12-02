package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate TOMORROW = LocalDate.now().plusDays(1L);
    private static final LocalTime MORNING_TIME = LocalTime.parse("10:30");
    private static final LocalTime EVENING_TIME = LocalTime.parse("20:00");
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie gentlemen = new Movie();
        gentlemen.setTitle("The Gentlemen");
        gentlemen.setDescription("An American comedy");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.add(gentlemen);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall250 = new CinemaHall(250, "2D");
        CinemaHall cinemaHallVip = new CinemaHall(50, "3D, VIP");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall250);
        cinemaHallService.add(cinemaHallVip);
        System.out.println(cinemaHallService.get(cinemaHallVip.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSessionMorning = new MovieSession();
        movieSessionMorning.setMovie(fastAndFurious);
        movieSessionMorning.setCinemaHall(cinemaHall250);
        movieSessionMorning.setShowTime(LocalDateTime.of(TODAY, MORNING_TIME));
        MovieSession movieSessionTodayEvening = new MovieSession();
        movieSessionTodayEvening.setMovie(gentlemen);
        movieSessionTodayEvening.setCinemaHall(cinemaHall250);
        movieSessionTodayEvening.setShowTime(LocalDateTime.of(TOMORROW, MORNING_TIME));
        MovieSession movieSessionTomorrowEvening = new MovieSession();
        movieSessionTomorrowEvening.setMovie(gentlemen);
        movieSessionTomorrowEvening.setCinemaHall(cinemaHallVip);
        movieSessionTomorrowEvening.setShowTime(LocalDateTime.of(TOMORROW, EVENING_TIME));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSessionMorning);
        movieSessionService.add(movieSessionTodayEvening);
        movieSessionService.add(movieSessionTomorrowEvening);
        System.out.println(movieSessionService.get(movieSessionMorning.getId()));
        movieSessionService.findAvailableSessions(gentlemen.getId(), TOMORROW)
                .forEach(System.out::println);
    }
}
