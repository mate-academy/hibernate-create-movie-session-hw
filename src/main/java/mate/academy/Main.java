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
    private static CinemaHallService cinemaHallService;
    private static MovieService movieService;
    private static MovieSessionService movieSessionService;

    public static void main(String[] args) {
        cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        movieService = (MovieService) injector.getInstance(MovieService.class);
        movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        System.out.println("=====Checking methods from MovieService=====");
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        Movie gentlemenBroncos = new Movie("Gentlemen Broncos");
        gentlemenBroncos.setDescription("American comedy film written by Jared and Jerusha Hess");

        movieService.add(fastAndFurious);
        movieService.add(gentlemenBroncos);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println("=====Checking methods from CinemaHallService=====");
        CinemaHall cinemaPlanet = new CinemaHall();
        cinemaPlanet.setCapacity(194);
        cinemaPlanet.setDescription("hall equipped with 4DX format");

        CinemaHall kievCinema = new CinemaHall();
        kievCinema.setCapacity(120);
        kievCinema.setDescription("Cozy cinema in a sleeping area of Kharkiv");

        CinemaHall eightAndAHalf = new CinemaHall();
        eightAndAHalf.setCapacity(98);
        eightAndAHalf.setDescription("Located in downtown of Kharkiv");

        cinemaHallService.add(cinemaPlanet);
        cinemaHallService.add(kievCinema);
        cinemaHallService.add(eightAndAHalf);
        System.out.println(cinemaHallService.get(cinemaPlanet.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("=====Checking methods from MovieSessionService=====");
        final MovieSession fastPlanetSession = new MovieSession();
        fastPlanetSession.setMovie(fastAndFurious);
        fastPlanetSession.setCinemaHall(cinemaPlanet);
        fastPlanetSession.setShowTime(LocalDateTime
                .of(2022,7,29,17,40));

        final MovieSession fastKievSession = new MovieSession();
        fastKievSession.setMovie(fastAndFurious);
        fastKievSession.setCinemaHall(kievCinema);
        fastKievSession.setShowTime(LocalDateTime
                .of(2022,7,29,20,55));

        final MovieSession fastSessionInEightAndHalf = new MovieSession();
        fastSessionInEightAndHalf.setMovie(fastAndFurious);
        fastSessionInEightAndHalf.setCinemaHall(eightAndAHalf);
        fastSessionInEightAndHalf.setShowTime(LocalDateTime
                .of(2022,7,28,19,00));

        movieSessionService.add(fastPlanetSession);
        movieSessionService.add(fastKievSession);
        movieSessionService.add(fastSessionInEightAndHalf);
        System.out.println(movieSessionService.get(fastPlanetSession.getId()));
        movieSessionService.findAvailableSessions(fastPlanetSession.getMovie().getId(), LocalDate
                .of(2022,7,29)).forEach(System.out::println);
    }
}
