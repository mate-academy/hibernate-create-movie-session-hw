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
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie terminator = new Movie("Terminator");
        terminator.setDescription("An action film about terminator.");
        Movie fightClub = new Movie("Fight Club");
        fightClub.setDescription("An action film about fight club");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.add(terminator);
        movieService.add(fightClub);
        System.out.println("### Get movie by id: " + fastAndFurious.getId());
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("### Get movie by id: " + terminator.getId());
        System.out.println(movieService.get(terminator.getId()));
        System.out.println("### Get movie by id: " + fightClub.getId());
        System.out.println(movieService.get(fightClub.getId()));
        System.out.println("### Get all movies: ");
        movieService.getAll().forEach(System.out::println);

        CinemaHall smallCinemaHall = new CinemaHall();
        smallCinemaHall.setCapacity(20);
        smallCinemaHall.setDescription("Small hall with comfortable seats");
        CinemaHall mediumCinemaHall = new CinemaHall();
        mediumCinemaHall.setCapacity(40);
        mediumCinemaHall.setDescription("Medium hall with normal seats and luxury backseats");
        CinemaHall largeCinemaHall = new CinemaHall();
        largeCinemaHall.setCapacity(60);
        largeCinemaHall.setDescription("Large hall with normal seats");
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(smallCinemaHall);
        cinemaHallService.add(mediumCinemaHall);
        cinemaHallService.add(largeCinemaHall);
        System.out.println("### Get cinema hall by id: " + smallCinemaHall.getId());
        System.out.println(cinemaHallService.get(smallCinemaHall.getId()));
        System.out.println("### Get cinema hall by id: " + mediumCinemaHall.getId());
        System.out.println(cinemaHallService.get(mediumCinemaHall.getId()));
        System.out.println("### Get cinema hall by id: " + largeCinemaHall.getId());
        System.out.println(cinemaHallService.get(largeCinemaHall.getId()));
        System.out.println("### Get all cinema halls: ");
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(terminator);
        firstMovieSession.setCinemaHall(mediumCinemaHall);
        firstMovieSession.setShowTime(LocalDateTime.now().minusHours(10));
        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(terminator);
        secondMovieSession.setCinemaHall(smallCinemaHall);
        secondMovieSession.setShowTime(LocalDateTime.now().minusHours(10));
        MovieSession thirdMovieSession = new MovieSession();
        thirdMovieSession.setMovie(fastAndFurious);
        thirdMovieSession.setCinemaHall(smallCinemaHall);
        thirdMovieSession.setShowTime(LocalDateTime.now().minusHours(5));
        MovieSession fourthMovieSession = new MovieSession();
        fourthMovieSession.setMovie(fastAndFurious);
        fourthMovieSession.setCinemaHall(smallCinemaHall);
        fourthMovieSession.setShowTime(LocalDateTime.now().plusDays(2));
        MovieSession fifthMovieSession = new MovieSession();
        fifthMovieSession.setMovie(fightClub);
        fifthMovieSession.setCinemaHall(largeCinemaHall);
        fifthMovieSession.setShowTime(LocalDateTime.now().minusHours(4));
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(firstMovieSession);
        movieSessionService.add(secondMovieSession);
        movieSessionService.add(thirdMovieSession);
        movieSessionService.add(fourthMovieSession);
        movieSessionService.add(fifthMovieSession);
        System.out.println("### Get movie session by id: " + firstMovieSession.getId());
        System.out.println(movieSessionService.get(firstMovieSession.getId()));
        System.out.println("### Get movie session by id: " + secondMovieSession.getId());
        System.out.println(movieSessionService.get(secondMovieSession.getId()));
        System.out.println("### Get movie session by id: " + thirdMovieSession.getId());
        System.out.println(movieSessionService.get(thirdMovieSession.getId()));
        System.out.println("### Get movie session by id: " + fourthMovieSession.getId());
        System.out.println(movieSessionService.get(fourthMovieSession.getId()));
        System.out.println("### Get movie session by id: " + fifthMovieSession.getId());
        System.out.println(movieSessionService.get(fifthMovieSession.getId()));
        System.out.println("### Get available movie session for movie : " + terminator.getTitle());
        movieSessionService.findAvailableSessions(terminator.getId(),
                LocalDate.now()).forEach(System.out::println);
    }
}
