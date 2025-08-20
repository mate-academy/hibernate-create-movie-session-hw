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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie movie = new Movie("Inception");
        movie.setDescription("Mind-bending sci-fi thriller");
        movie = movieService.add(movie);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall hall = new CinemaHall();
        hall.setCapacity(120);
        hall.setDescription("IMAX Hall");
        hall = cinemaHallService.add(hall);

        LocalDateTime showTime = LocalDate
                .now()
                .plusDays(1)
                .atTime(19, 0);
        MovieSession session = new MovieSession();
        session.setMovie(movie);
        session.setCinemaHall(hall);
        session.setShowTime(showTime);
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        session = movieSessionService.add(session);

        System.out.println("== Created ==");
        System.out.println("Movie: " + movie.getId()
                + " - " + movie.getTitle());
        System.out.println("Hall:  " + hall.getId()
                + " - " + hall.getDescription());
        System.out.println("Sess:  " + session.getId()
                + " - " + session.getShowTime());

        System.out.println("\n== All Movies ==");
        movieService.getAll().forEach(System.out::println);

        System.out.println("\n== All Halls ==");
        cinemaHallService.getAll()
                .forEach(h -> System.out.println(h.getId()
                        + " "
                        + h.getDescription() + " cap="
                        + h.getCapacity()));

        var dateToSearch = showTime.toLocalDate();
        System.out.println("\n== Sessions for movie '"
                + movie.getTitle() + "' on "
                + dateToSearch + " ==");
        movieSessionService.findAvailableSessions(movie.getId(), dateToSearch)
                .forEach(ms -> System.out.println(
                        "SessionId=" + ms.getId()
                                + " movie=" + ms.getMovie().getTitle()
                                + " hall=" + ms.getCinemaHall().getDescription()
                                + " time=" + ms.getShowTime()
                ));
    }
}
