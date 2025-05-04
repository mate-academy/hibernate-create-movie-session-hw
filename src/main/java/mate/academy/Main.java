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
    private static final MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fightClub = new Movie("Fight Club");
        fightClub.setDescription("Office worker and a soap maker form a fight club.");
        movieService.add(fightClub);

        Movie prestige = new Movie("The Prestige");
        prestige.setDescription("The story of the confrontation between two magicians.");
        movieService.add(prestige);

        Movie interstellar = new Movie("Interstellar");
        interstellar.setDescription("A team of explorers travel through a wormhole in space.");
        movieService.add(interstellar);

        System.out.println("All Movies");
        movieService.getAll().forEach(x -> System.out.println(x));

        CinemaHall firstHall = new CinemaHall();
        firstHall.setDescription("IMAX");
        firstHall.setCapacity(150);
        cinemaHallService.add(firstHall);

        CinemaHall secondHall = new CinemaHall();
        secondHall.setDescription("IMAX 3D");
        secondHall.setCapacity(200);
        cinemaHallService.add(secondHall);

        CinemaHall thirdHall = new CinemaHall();
        thirdHall.setDescription("4DX");
        thirdHall.setCapacity(100);
        cinemaHallService.add(thirdHall);

        System.out.println("All Cinema Halls");
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession morning = new MovieSession();
        morning.setMovie(prestige);
        morning.setCinemaHall(firstHall);
        morning.setShowTime(LocalDateTime
                .of(2022, 10, 1, 10, 0));
        movieSessionService.add(morning);

        MovieSession afternoon = new MovieSession();
        afternoon.setMovie(fightClub);
        afternoon.setCinemaHall(secondHall);
        afternoon.setShowTime(LocalDateTime
                .of(2022, 10, 1, 16, 30));
        movieSessionService.add(afternoon);

        MovieSession evening = new MovieSession();
        evening.setMovie(interstellar);
        evening.setCinemaHall(thirdHall);
        evening.setShowTime(LocalDateTime
                .of(2022, 10, 1, 20, 15));
        movieSessionService.add(evening);

        System.out.println(movieSessionService.findAvailableSessions(
                prestige.getId(), LocalDate.of(2022, 10, 1)));
    }
}
