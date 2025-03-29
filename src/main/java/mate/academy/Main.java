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

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie leon = new Movie("Leon");
        leon.setDescription("Interesting story about the best cleaner in New York city.");
        movieService.add(leon);

        Movie cityOfHeroes = new Movie("City of Heroes");
        cityOfHeroes.setDescription("Awesome cartoon about the good and evil war.");
        movieService.add(cityOfHeroes);

        Movie theEdgeOfTomorrow = new Movie("The Edge of Tomorrow");
        theEdgeOfTomorrow.setDescription("Conceptive movie about time management of war.");
        movieService.add(theEdgeOfTomorrow);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall blackCinemaHall = new CinemaHall();
        blackCinemaHall.setCapacity(60);
        blackCinemaHall.setDescription("Black Cinema Hall");
        cinemaHallService.add(blackCinemaHall);

        CinemaHall blueCinemaHall = new CinemaHall();
        blueCinemaHall.setCapacity(80);
        blueCinemaHall.setDescription("Blue Cinema Hall");
        cinemaHallService.add(blueCinemaHall);

        CinemaHall whiteCinemaHall = new CinemaHall();
        whiteCinemaHall.setCapacity(100);
        whiteCinemaHall.setDescription("White Cinema Hall");
        cinemaHallService.add(whiteCinemaHall);

        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(blackCinemaHall);
        movieSession.setShowTime(LocalDateTime.parse("2025-03-16T12:00"));

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(leon);
        movieSession1.setCinemaHall(blueCinemaHall);
        movieSession1.setShowTime(LocalDateTime.parse("2025-03-16T15:00"));
        movieSessionService.add(movieSession1);

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(leon);
        movieSession2.setCinemaHall(blueCinemaHall);
        movieSession2.setShowTime(LocalDateTime.parse("2025-03-16T22:00"));
        movieSessionService.add(movieSession2);

        MovieSession movieSession3 = new MovieSession();
        movieSession3.setMovie(theEdgeOfTomorrow);
        movieSession3.setCinemaHall(blackCinemaHall);
        movieSession3.setShowTime(LocalDateTime.parse("2025-03-17T12:00"));
        movieSessionService.add(movieSession3);

        MovieSession movieSession4 = new MovieSession();
        movieSession4.setMovie(cityOfHeroes);
        movieSession4.setCinemaHall(blackCinemaHall);
        movieSession4.setShowTime(LocalDateTime.parse("2025-03-18T12:00"));
        movieSessionService.add(movieSession4);

        movieSessionService.findAvailableSessions(2L,
                LocalDate.parse("2025-03-16")).forEach(System.out::println);
    }
}
