package mate.academy;

import java.time.LocalDateTime;
import java.time.Month;
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
        //* testing movie services
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie theDarkestMinds = new Movie("The Darkest Minds");
        theDarkestMinds.setDescription("When teens mysteriously develop powerful new abilities, "
                                       + "they are declared a threat by the government and "
                                       + "detained.");
        movieService.add(theDarkestMinds);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        //* testing cinemaHall services
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall cinemagic = new CinemaHall(100, "Cinemagic 3D");
        cinemaHallService.add(cinemagic);

        CinemaHall starView = new CinemaHall(300, "StarView 3D");
        cinemaHallService.add(starView);

        System.out.println(cinemaHallService.get(starView.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        //* testing movie session services
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        MovieSession sessionCinemagicMon1500 = new MovieSession(fastAndFurious, cinemagic);
        LocalDateTime scheduledMon1500 = LocalDateTime
                .of(2024, Month.SEPTEMBER, 3, 15, 0, 0);
        sessionCinemagicMon1500.setShowTime(scheduledMon1500);
        movieSessionService.add(sessionCinemagicMon1500);

        MovieSession sessionCinemagicMon1200 = new MovieSession(fastAndFurious, cinemagic);
        LocalDateTime scheduledMon1200 = LocalDateTime
                .of(2024, Month.SEPTEMBER, 3, 12, 0, 0);
        sessionCinemagicMon1200.setShowTime(scheduledMon1200);
        movieSessionService.add(sessionCinemagicMon1200);

        MovieSession sessionStarViewMon1000 = new MovieSession(theDarkestMinds, starView);
        LocalDateTime scheduledMon1000 = LocalDateTime
                .of(2024, Month.SEPTEMBER, 3, 10, 0, 0);
        sessionStarViewMon1000.setShowTime(scheduledMon1000);
        movieSessionService.add(sessionStarViewMon1000);

        MovieSession sessionCinemagicTue1500 = new MovieSession(fastAndFurious, cinemagic);
        LocalDateTime scheduledTue1500 = LocalDateTime
                .of(2024, Month.SEPTEMBER, 4, 15, 0, 0);
        sessionCinemagicTue1500.setShowTime(scheduledTue1500);
        movieSessionService.add(sessionCinemagicTue1500);

        MovieSession sessionCinemagicTue1200 = new MovieSession(fastAndFurious, cinemagic);
        LocalDateTime scheduledTue1200 = LocalDateTime
                .of(2024, Month.SEPTEMBER, 4, 12, 0, 0);
        sessionCinemagicTue1200.setShowTime(scheduledTue1200);
        movieSessionService.add(sessionCinemagicTue1200);

        MovieSession sessionCinemagicTue1000 = new MovieSession(theDarkestMinds, starView);
        LocalDateTime scheduledTue1000 = LocalDateTime
                .of(2024, Month.SEPTEMBER, 4, 10, 0, 0);
        sessionCinemagicTue1000.setShowTime(scheduledTue1000);
        movieSessionService.add(sessionCinemagicTue1000);

        System.out.println(movieSessionService.get(sessionCinemagicMon1500.getId()));
        List<MovieSession> availableSessions = movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), scheduledMon1500.toLocalDate());
        availableSessions.forEach(System.out::println);
    }
}
