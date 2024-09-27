package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie batman = new Movie("Batman");
        batman.setDescription("I'm BATMAN!!");
        Movie interception = new Movie("Interception");
        interception.setDescription("Professional thief who steals information");

        LocalDateTime nowDate = LocalDateTime.now();
        // set sessions
        CinemaHall hallA = new CinemaHall("HallA", 100);
        MovieSession sessionTodayMorningBatman = new MovieSession();
        sessionTodayMorningBatman.setMovie(batman);
        sessionTodayMorningBatman.setCinemaHall(hallA);
        sessionTodayMorningBatman.setShowTime(nowDate);

        MovieSession sessionTodayAfternoonBatman = new MovieSession();
        sessionTodayAfternoonBatman.setMovie(batman);
        sessionTodayAfternoonBatman.setCinemaHall(hallA);
        sessionTodayAfternoonBatman.setShowTime(nowDate.plusHours(8));

        CinemaHall hallB = new CinemaHall("HallB", 80);
        MovieSession sessionTodayInterception = new MovieSession();
        sessionTodayInterception.setMovie(interception);
        sessionTodayInterception.setCinemaHall(hallB);
        sessionTodayInterception.setShowTime(nowDate.plusHours(1));

        LocalDateTime tomorrowDate = LocalDateTime.now().plusDays(1);
        MovieSession sessionTomorrowBatman = new MovieSession();
        sessionTomorrowBatman.setMovie(batman);
        sessionTomorrowBatman.setCinemaHall(hallB);
        sessionTomorrowBatman.setShowTime(tomorrowDate);

        LocalDateTime nextWeekDate = LocalDateTime.now().plusWeeks(1);
        MovieSession sessionNextWeekBatman = new MovieSession();
        sessionNextWeekBatman.setMovie(batman);
        sessionNextWeekBatman.setCinemaHall(hallB);
        sessionNextWeekBatman.setShowTime(nextWeekDate);

        // add movies/halls/sessions
        movieService.add(fastAndFurious);
        movieService.add(batman);
        movieService.add(interception);

        cinemaHallService.add(hallA);
        cinemaHallService.add(hallB);

        movieSessionService.add(sessionTodayMorningBatman);
        movieSessionService.add(sessionTodayAfternoonBatman);
        movieSessionService.add(sessionTodayInterception);
        movieSessionService.add(sessionTomorrowBatman);
        movieSessionService.add(sessionNextWeekBatman);

        // show all them
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(batman.getId()));
        System.out.println(movieService.get(interception.getId()));

        System.out.println(cinemaHallService.get(hallA.getId()));
        System.out.println(cinemaHallService.get(hallB.getId()));

        System.out.println(sessionTodayMorningBatman.getId());
        System.out.println(sessionTodayInterception.getId());
        System.out.println(sessionTomorrowBatman.getId());
        System.out.println(sessionNextWeekBatman.getId());

        movieService.getAll().forEach(System.out::println);
        cinemaHallService.getAll().forEach(System.out::println);
        // find today's batman movies sessions
        List<MovieSession> availableSessions
                = movieSessionService.findAvailableSessions(batman.getId(), LocalDate.now());

        System.out.println("\n");
        for (MovieSession session : availableSessions) {
            System.out.println(session);
        }
    }
}
