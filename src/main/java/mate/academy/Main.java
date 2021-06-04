package mate.academy;

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
        MovieService movieServiceOne = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFuriousNine = new Movie("Fast and Furious 9");
        fastAndFuriousNine.setDescription("An action film about street racing, heists, and spies.");
        movieServiceOne.add(fastAndFuriousNine);

        MovieService movieServiceTwo = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFuriousEight = new Movie("Fast and Furious 8");
        fastAndFuriousEight.setDescription("An action film about street racing, "
                + "heists, and spies.");
        movieServiceTwo.add(fastAndFuriousEight);

        CinemaHallService cinemaHallServiceOne =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHallOne = new CinemaHall(100);
        cinemaHallOne.setDescription("VIP zone 1");
        cinemaHallServiceOne.add(cinemaHallOne);

        CinemaHallService cinemaHallServiceTwo =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHallTwo = new CinemaHall(150);
        cinemaHallTwo.setDescription("VIP zone 2");
        cinemaHallServiceTwo.add(cinemaHallTwo);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession movieSessionOne = new MovieSession(fastAndFuriousNine, cinemaHallOne);
        LocalDateTime localDateTime = LocalDateTime.now();
        movieSessionOne.setShowTime(localDateTime);
        movieSessionService.add(movieSessionOne);
        MovieSession movieSessionTwo = new MovieSession(fastAndFuriousEight, cinemaHallTwo);
        movieSessionTwo.setShowTime(localDateTime);
        movieSessionService.add(movieSessionTwo);

        System.out.println(movieSessionService.get(movieSessionOne.getId()));

        movieSessionService.findAvailableSessions(fastAndFuriousEight.getId(),
                localDateTime.toLocalDate()).forEach(System.out::println);

    }
}
