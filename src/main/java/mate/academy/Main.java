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
    private static final Injector injector
            = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);

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
        java.setDescription("The film about programmists");
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
        CinemaHall cinemaHall1 = new CinemaHall();
        cinemaHall1.setCapacity(10);
        cinemaHall1.setDescription("CinemaHall number 1");
        cinemaHallService.add(cinemaHall1);

        CinemaHall cinemaHall2 = new CinemaHall();
        cinemaHall2.setCapacity(100);
        cinemaHall2.setDescription("CinemaHall number 2");
        cinemaHallService.add(cinemaHall2);

        CinemaHall cinemaHall3 = new CinemaHall();
        cinemaHall3.setCapacity(200);
        cinemaHall3.setDescription("CinemaHall number 3");
        cinemaHallService.add(cinemaHall3);

        System.out.println(cinemaHallService.get(cinemaHall1.getId()));
        System.out.println("------------------");
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(terminator);
        movieSession1.setCinemaHall(cinemaHall1);
        movieSession1.setShowTime(
                LocalDateTime.of(2021,01,01,8,01));

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(terminator2);
        movieSession2.setCinemaHall(cinemaHall2);
        movieSession2.setShowTime(
                LocalDateTime.of(2021,01,01,10,01));

        MovieSession movieSession3 = new MovieSession();
        movieSession3.setMovie(mummy);
        movieSession3.setCinemaHall(cinemaHall2);
        movieSession3.setShowTime(
                LocalDateTime.of(2021,01,01,11,01));

        MovieSession movieSession4 = new MovieSession();
        movieSession4.setMovie(fastAndFurious);
        movieSession4.setCinemaHall(cinemaHall3);
        movieSession4.setShowTime(
                LocalDateTime.of(2021,01,01,20,01));

        MovieSession movieSession5 = new MovieSession();
        movieSession5.setMovie(terminator);
        movieSession5.setCinemaHall(cinemaHall3);
        movieSession5.setShowTime(
                LocalDateTime.of(2021,01,01,21,01));

        MovieSession movieSession6 = new MovieSession();
        movieSession6.setMovie(java);
        movieSession6.setCinemaHall(cinemaHall3);
        movieSession6.setShowTime(
                LocalDateTime.of(2021,01,01,22,01));

        MovieSession movieSession7 = new MovieSession();
        movieSession7.setMovie(java);
        movieSession7.setCinemaHall(cinemaHall1);
        movieSession7.setShowTime(
                LocalDateTime.of(2021,01,02,9,01));

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        movieSessionService.add(movieSession3);
        movieSessionService.add(movieSession4);
        movieSessionService.add(movieSession5);
        movieSessionService.add(movieSession6);
        movieSessionService.add(movieSession7);

        System.out.println(movieSessionService.get(movieSession3.getId()));
        System.out.println("--------------");
        movieSessionService.findAvailableSessions(terminator2.getId(), LocalDate.of(2021,01,01))
                .forEach(System.out::println);
    }
}
