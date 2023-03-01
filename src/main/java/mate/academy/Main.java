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
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall bigCinemaHall = new CinemaHall();
        bigCinemaHall.setCapacity(200);
        bigCinemaHall.setDescription("Biggest cinema hall");
        cinemaHallService.add(bigCinemaHall);
        System.out.println(cinemaHallService.get(bigCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        Movie fightClub = new Movie("Fight club");
        fightClub.setDescription("The first rule of Fight Club is don't talk about Fight Club");
        movieSession.setMovie(fightClub);
        movieSession.setCinemaHall(bigCinemaHall);
        LocalDateTime showTime = LocalDateTime.parse("1999-11-11T10:15");
        movieSession.setShowTime(showTime);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.get(movieSession.getId());
        movieSessionService.findAvailableSessions(9L, LocalDate.parse("1999-11-11"));
    }
}
