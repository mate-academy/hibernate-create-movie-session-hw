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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(harryPotter);
        final Movie starWarsDB = movieService.add(starWars); //final to avoid checkstyle error

        CinemaHall redCinemaHall = CinemaHall.builder()
                .capacity(50)
                .description("Red cinema hall")
                .build();
        CinemaHall blueCinemaHall = CinemaHall.builder()
                .capacity(100)
                .description("Blue cinema hall")
                .build();
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.save(blueCinemaHall);
        cinemaHallService.save(redCinemaHall);

        MovieSession starWarsRedSession101010 = MovieSession.builder()
                .cinemaHall(redCinemaHall)
                .movie(starWars)
                .showTime(LocalDateTime.parse("2024-10-10T10:00:00"))
                .build();
        MovieSession starWarsBlueSession101011 = MovieSession.builder()
                .cinemaHall(blueCinemaHall)
                .movie(starWars)
                .showTime(LocalDateTime.parse("2024-10-10T11:00:00"))
                .build();
        MovieSession starWarsBlueSession101111 = MovieSession.builder()
                .cinemaHall(blueCinemaHall)
                .movie(starWars)
                .showTime(LocalDateTime.parse("2024-10-11T11:00:00"))
                .build();
        MovieSession harryPotterRedSession101011 = MovieSession.builder()
                .cinemaHall(redCinemaHall)
                .movie(harryPotter)
                .showTime(LocalDateTime.parse("2024-10-10T11:00:00"))
                .build();

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        movieSessionService.save(starWarsRedSession101010);
        movieSessionService.save(starWarsBlueSession101011);
        movieSessionService.save(starWarsBlueSession101111);
        movieSessionService.save(harryPotterRedSession101011);

        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(starWarsDB.getId(), LocalDate.parse("2024-10-10"));
        System.out.println(availableSessions);
    }
}
