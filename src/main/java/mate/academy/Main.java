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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        Movie starWars = Movie.builder()
                .title("Star Wars")
                .description("Sci-fi movie about Jedi")
                .build();
        Movie harryPotter = Movie.builder()
                .title("Harry Potter")
                .description("Fantasy movie about magic")
                .build();

        CinemaHall redCinemaHall = CinemaHall.builder()
                .capacity(50)
                .description("Red cinema hall")
                .build();
        CinemaHall blueCinemaHall = CinemaHall.builder()
                .capacity(100)
                .description("Blue cinema hall")
                .build();

        MovieSession starWarsRedSession10_10_10 = MovieSession.builder()
                .cinemaHall(redCinemaHall)
                .movie(starWars)
                .showTime(LocalDateTime.parse("2024-10-10T10:00:00"))
                .build();
        MovieSession starWarsBlueSession10_10_11 = MovieSession.builder()
                .cinemaHall(blueCinemaHall)
                .movie(starWars)
                .showTime(LocalDateTime.parse("2024-10-10T11:00:00"))
                .build();
        MovieSession starWarsBlueSession10_11_11 = MovieSession.builder()
                .cinemaHall(blueCinemaHall)
                .movie(starWars)
                .showTime(LocalDateTime.parse("2024-10-11T11:00:00"))
                .build();
        MovieSession harryPotterRedSession10_10_11 = MovieSession.builder()
                .cinemaHall(redCinemaHall)
                .movie(harryPotter)
                .showTime(LocalDateTime.parse("2024-10-10T11:00:00"))
                .build();

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);

        Movie starWarsDB = movieService.add(starWars);
        Movie harryPotterDB = movieService.add(harryPotter);

        CinemaHall blueCinemaHallDB = cinemaHallService.save(blueCinemaHall);
        CinemaHall redCinemaHallDB = cinemaHallService.save(redCinemaHall);

        movieSessionService.save(starWarsRedSession10_10_10);
        movieSessionService.save(starWarsBlueSession10_10_11);
        movieSessionService.save(starWarsBlueSession10_11_11);
        movieSessionService.save(harryPotterRedSession10_10_11);

        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(starWarsDB.getId(), LocalDate.parse("2024-10-10"));
        System.out.println(availableSessions);
    }
}
