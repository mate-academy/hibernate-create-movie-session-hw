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
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, spies.");
        Movie dovbush = new Movie("DOVBUSH");
        dovbush.setDescription("Ukrainian historical drama");
        movieService.add(fastAndFurious);
        movieService.add(dovbush);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(dovbush.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall hallOne = new CinemaHall("Hall #1");
        hallOne.setCapacity(60);
        CinemaHall hallTwo = new CinemaHall("Hall #2");
        hallTwo.setCapacity(80);
        CinemaHall hallThree = new CinemaHall("Hall #3");
        hallThree.setCapacity(80);
        CinemaHall hallFour = new CinemaHall("Hall #4");
        hallFour.setCapacity(100);
        CinemaHall hallFive = new CinemaHall("Hall #5");
        hallFive.setCapacity(120);
        CinemaHall imax = new CinemaHall("IMAX");
        imax.setCapacity(150);
        CinemaHallService cinemaHallService = (CinemaHallService) INJECTOR.getInstance(
                CinemaHallService.class);
        cinemaHallService.add(hallOne);
        cinemaHallService.add(hallTwo);
        cinemaHallService.add(hallThree);
        cinemaHallService.add(hallFour);
        cinemaHallService.add(hallFive);
        cinemaHallService.add(imax);
        System.out.println(cinemaHallService.get(hallOne.getId()));
        System.out.println(cinemaHallService.get(imax.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession sessionFastAndFuriousOne = new MovieSession(fastAndFurious, hallOne);
        sessionFastAndFuriousOne.setShowTime(LocalDateTime.parse("2023-09-12T18:00:00.000"));
        MovieSession sessionFastAndFuriousTwo = new MovieSession(fastAndFurious, hallTwo);
        sessionFastAndFuriousTwo.setShowTime(LocalDateTime.parse("2023-09-12T19:00:00.000"));
        MovieSession sessionFastAndFuriousThree = new MovieSession(fastAndFurious, hallThree);
        sessionFastAndFuriousThree.setShowTime(LocalDateTime.parse("2023-09-13T20:00:00.000"));
        MovieSession sessionDovbushOne = new MovieSession(dovbush, imax);
        sessionDovbushOne.setShowTime(LocalDateTime.parse("2023-09-15T19:30:00.000"));
        MovieSession sessionDovbushTwo = new MovieSession(dovbush, hallFour);
        sessionDovbushTwo.setShowTime(LocalDateTime.parse("2023-09-15T21:00:00.000"));
        MovieSession sessionDovbushThree = new MovieSession(dovbush, hallFive);
        sessionDovbushThree.setShowTime(LocalDateTime.parse("2023-09-18T19:30:00.000"));
        MovieSessionService movieSessionService = (MovieSessionService) INJECTOR.getInstance(
                MovieSessionService.class);
        movieSessionService.add(sessionFastAndFuriousOne);
        movieSessionService.add(sessionFastAndFuriousTwo);
        movieSessionService.add(sessionFastAndFuriousThree);
        movieSessionService.add(sessionDovbushOne);
        movieSessionService.add(sessionDovbushTwo);
        movieSessionService.add(sessionDovbushThree);
        System.out.println(movieSessionService.get(sessionFastAndFuriousOne.getId()));
        System.out.println(movieSessionService.get(sessionFastAndFuriousThree.getId()));
        System.out.println(movieSessionService.get(sessionDovbushOne.getId()));
        System.out.println(movieSessionService.get(sessionDovbushThree.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                        LocalDate.parse("2023-09-12")).forEach(System.out::println);
        movieSessionService.findAvailableSessions(dovbush.getId(), LocalDate.parse("2023-09-15"))
                .forEach(System.out::println);
    }
}
