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
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Movie starWars = new Movie("Star Wars",
                "Sci-fi movie about Jedi");
        Movie harryPotter = new Movie("Harry Potter",
                "Fantasy movie about magic");

        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);
        movieService.add(harryPotter);
        movieService.add(starWars); //final to avoid checkstyle error

        CinemaHall redCinemaHall = new CinemaHall(50, "Red cinema hall");
        CinemaHall blueCinemaHall = new CinemaHall(100, "Blue cinema hall");

        CinemaHallService cinemaHallService = (CinemaHallService) INJECTOR
                .getInstance(CinemaHallService.class);
        cinemaHallService.save(blueCinemaHall);
        cinemaHallService.save(redCinemaHall);

        MovieSession starWarsRedSession101010 = new MovieSession(starWars,
                redCinemaHall, LocalDateTime.parse("2024-10-10T10:00:00"));
        MovieSession starWarsBlueSession101011 = new MovieSession(starWars,
                blueCinemaHall, LocalDateTime.parse("2024-10-10T11:00:00"));
        MovieSession starWarsBlueSession101111 = new MovieSession(starWars,
                blueCinemaHall, LocalDateTime.parse("2024-10-11T11:00:00"));
        MovieSession harryPotterRedSession101011 = new MovieSession(harryPotter,
                redCinemaHall, LocalDateTime.parse("2024-10-10T11:00:00"));

        MovieSessionService movieSessionService =
                (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);

        movieSessionService.save(starWarsRedSession101010);
        movieSessionService.save(starWarsBlueSession101011);
        movieSessionService.save(starWarsBlueSession101111);
        movieSessionService.save(harryPotterRedSession101011);

        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(starWars.getId(), LocalDate.parse("2024-10-10"));
        System.out.println(availableSessions);
    }
}
