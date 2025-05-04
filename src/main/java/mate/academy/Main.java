package mate.academy;

import java.time.LocalDate;
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
        //-------------------MovieService test-------------------
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious",
                "An action film about street racing, heists, and spies");
        Movie theShawshankRedemption = new Movie("The Shawshank Redemption",
                "The Shawshank Redemption is an uplifting, deeply satisfying prison "
                + "drama with sensitive direction and fine performances");

        movieService.add(fastAndFurious);
        movieService.add(theShawshankRedemption);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        //-------------------CinemaHallService test-------------------
        CinemaHallService cinemaHallService
                = (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);
        CinemaHall busanCinemaCentre = new CinemaHall(4000, "Busan Cinema Centre");
        CinemaHall solCinema = new CinemaHall(8, "Sol Cinema");

        cinemaHallService.add(busanCinemaCentre);
        cinemaHallService.add(solCinema);
        System.out.println(cinemaHallService.get(busanCinemaCentre.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        //-------------------MovieSessionService test-------------------
        MovieSessionService movieSessionService
                = (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);
        MovieSession session1 = new MovieSession(
                theShawshankRedemption, busanCinemaCentre, "2023-09-18T00:00:00.00000");
        MovieSession session2 = new MovieSession(
                theShawshankRedemption, solCinema, "2023-09-19T00:00:00.00000");
        MovieSession session3 = new MovieSession(
                fastAndFurious, busanCinemaCentre, "2023-09-18T00:00:00.00000");
        MovieSession session4 = new MovieSession(
                fastAndFurious, solCinema, "2023-09-19T00:00:00.00000");

        movieSessionService.add(session1);
        movieSessionService.add(session2);
        movieSessionService.add(session3);
        movieSessionService.add(session4);
        System.out.println(movieSessionService.get(session1.getId()));
        System.out.println(movieSessionService.findAvailableSessions(theShawshankRedemption.getId(),
                LocalDate.parse("2023-09-19")));
    }
}
