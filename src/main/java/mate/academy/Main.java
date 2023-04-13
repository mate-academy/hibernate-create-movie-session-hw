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
        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie johnWick = new Movie("John Wick");
        johnWick.setDescription("An action film about one killer");
        movieService.add(johnWick);

        Movie interstellar = new Movie("Interstellar");
        interstellar.setDescription("An epic science fiction"
                + " film about Earth’s last chance to find a habitable planet");
        movieService.add(interstellar);

        Movie martian = new Movie("The Martian");
        martian.setDescription("An science fiction about an astronaut which stuck on mars");
        movieService.add(martian);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall imax = new CinemaHall();
        imax.setDescription("IMAX cinema hall");
        imax.setCapacity(100);
        cinemaHallService.add(imax);

        CinemaHall deluxe = new CinemaHall();
        deluxe.setDescription("Deluxe cinema hall");
        deluxe.setCapacity(40);
        cinemaHallService.add(deluxe);
        System.out.println(cinemaHallService.get(deluxe.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstPremiere = new MovieSession();
        firstPremiere.setMovie(fastAndFurious);
        firstPremiere.setCinemaHall(imax);
        firstPremiere.setShowTime(LocalDateTime.now().plusHours(3));
        movieSessionService.add(firstPremiere);

        MovieSession secondPremiere = new MovieSession();
        secondPremiere.setMovie(johnWick);
        secondPremiere.setCinemaHall(imax);
        secondPremiere.setShowTime(LocalDateTime.now().plusHours(3));
        movieSessionService.add(secondPremiere);

        MovieSession thirdPremiere = new MovieSession();
        thirdPremiere.setMovie(interstellar);
        thirdPremiere.setCinemaHall(deluxe);
        thirdPremiere.setShowTime(LocalDateTime.now().plusHours(1));
        movieSessionService.add(thirdPremiere);

        MovieSession fourthPremiere = new MovieSession();
        fourthPremiere.setMovie(martian);
        fourthPremiere.setCinemaHall(deluxe);
        fourthPremiere.setShowTime(LocalDateTime.now().plusHours(1));
        movieSessionService.add(fourthPremiere);

        System.out.println(movieSessionService.get(firstPremiere.getId()));
        System.out.println(movieSessionService
                .findAvailableSessions(martian.getId(), LocalDate.now()));
    }
}
