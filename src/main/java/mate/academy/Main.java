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
        Movie terminator = new Movie("Terminator");
        terminator.setDescription("The film about future");
        movieService.add(terminator);
        Movie terminator2 = new Movie("Terminator - 2");
        terminator2.setDescription("The film about future - SecondPart");
        movieService.add(terminator2);
        Movie mummy = new Movie("Mummy");
        mummy.setDescription("The film about Egypt");
        movieService.add(mummy);
        Movie java = new Movie("JAVA");
        java.setDescription("The film about programming");
        movieService.add(java);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(terminator.getId()));
        System.out.println(movieService.get(terminator2.getId()));
        System.out.println(movieService.get(mummy.getId()));
        System.out.println(movieService.get(java.getId()));
        System.out.println("--------------------");
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(10);
        firstCinemaHall.setDescription("CinemaHall number 1");
        cinemaHallService.add(firstCinemaHall);

        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(100);
        secondCinemaHall.setDescription("CinemaHall number 2");
        cinemaHallService.add(secondCinemaHall);

        CinemaHall thirdCinemaHall = new CinemaHall();
        thirdCinemaHall.setCapacity(200);
        thirdCinemaHall.setDescription("CinemaHall number 3");
        cinemaHallService.add(thirdCinemaHall);

        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));
        System.out.println("------------------");
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(terminator);
        firstMovieSession.setCinemaHall(firstCinemaHall);
        firstMovieSession.setShowTime(
                LocalDateTime.of(2021,01,01,8,01));

        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(terminator2);
        secondMovieSession.setCinemaHall(secondCinemaHall);
        secondMovieSession.setShowTime(
                LocalDateTime.of(2021,01,01,10,01));

        MovieSession thirdMovieSession = new MovieSession();
        thirdMovieSession.setMovie(mummy);
        thirdMovieSession.setCinemaHall(secondCinemaHall);
        thirdMovieSession.setShowTime(
                LocalDateTime.of(2021,01,01,11,01));

        MovieSession fourthMovieSession = new MovieSession();
        fourthMovieSession.setMovie(fastAndFurious);
        fourthMovieSession.setCinemaHall(thirdCinemaHall);
        fourthMovieSession.setShowTime(
                LocalDateTime.of(2021,01,01,20,01));

        MovieSession fifthMovieSession = new MovieSession();
        fifthMovieSession.setMovie(terminator);
        fifthMovieSession.setCinemaHall(thirdCinemaHall);
        fifthMovieSession.setShowTime(
                LocalDateTime.of(2021,01,01,21,01));

        MovieSession sixthMovieSession = new MovieSession();
        sixthMovieSession.setMovie(java);
        sixthMovieSession.setCinemaHall(thirdCinemaHall);
        sixthMovieSession.setShowTime(
                LocalDateTime.of(2021,01,01,22,01));

        MovieSession seventhMovieSession = new MovieSession();
        seventhMovieSession.setMovie(java);
        seventhMovieSession.setCinemaHall(firstCinemaHall);
        seventhMovieSession.setShowTime(
                LocalDateTime.of(2021,01,02,9,01));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(firstMovieSession);
        movieSessionService.add(secondMovieSession);
        movieSessionService.add(thirdMovieSession);
        movieSessionService.add(fourthMovieSession);
        movieSessionService.add(fifthMovieSession);
        movieSessionService.add(sixthMovieSession);
        movieSessionService.add(seventhMovieSession);

        System.out.println(movieSessionService.get(thirdMovieSession.getId()));
        System.out.println("--------------");
        movieSessionService.findAvailableSessions(terminator2.getId(), LocalDate.of(2021,01,01))
                .forEach(System.out::println);
    }
}
