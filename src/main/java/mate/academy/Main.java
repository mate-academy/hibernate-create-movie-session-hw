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

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie imArobot = new Movie("I am a robot");
        imArobot.setDescription("Action,fantastic");
        movieService.add(imArobot);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall bigHall = new CinemaHall();
        bigHall.setCapacity(300);
        bigHall.setDescription("big hall cinema");
        cinemaHallService.add(bigHall);
        CinemaHall smallHall = new CinemaHall();
        smallHall.setCapacity(50);
        smallHall.setDescription("small hall cinema");
        cinemaHallService.add(smallHall);

        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        MovieSession sessionTomorrow = new MovieSession();
        sessionTomorrow.setMovie(fastAndFurious);
        sessionTomorrow.setCinemaHall(bigHall);
        LocalDateTime timeTomorrow = LocalDateTime.now().plusDays(1L);
        sessionTomorrow.setShowTime(timeTomorrow);
        movieSessionService.add(sessionTomorrow);

        MovieSession sessionToDay = new MovieSession();
        sessionToDay.setMovie(imArobot);
        sessionToDay.setCinemaHall(smallHall);
        LocalDateTime timeNow = LocalDateTime.now();
        sessionToDay.setShowTime(timeNow);
        movieSessionService.add(sessionToDay);

        LocalDate dateToDate = LocalDate.from(timeNow);
        List<MovieSession> sessionsNow = movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), dateToDate);
        System.out.println(sessionsNow);
        LocalDate dateTomorrow = LocalDate.from(timeTomorrow);
        List<MovieSession> sessionsTomorrow = movieSessionService
                .findAvailableSessions(imArobot.getId(), dateTomorrow);
        System.out.println(sessionsTomorrow);
    }
}
