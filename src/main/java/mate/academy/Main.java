package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        initDb();
        List<Movie> movies = movieService.getAll();
        System.out.println("All movies:");
        movies.forEach(System.out::println);
        System.out.println("All halls:");
        cinemaHallService.getAll().forEach(System.out::println);

        String format = "Movie with id = %s, date = %s";
        Random random = new Random();
        int randomMovieIndex = random.nextInt(1, movies.size());
        LocalDate date = LocalDate.now();

        System.out.println(String.format(format, randomMovieIndex, date));
        movieSessionService.findAvailableSessions(movies.get(randomMovieIndex).getId(), date)
                .forEach(System.out::println);

        date = LocalDate.of(2023, 10, 06);
        randomMovieIndex = random.nextInt(1, movies.size());
        System.out.println(String.format(format, randomMovieIndex, date));
        movieSessionService.findAvailableSessions(movies.get(randomMovieIndex).getId(), date)
                .forEach(System.out::println);
    }

    private static void initDb() {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie homeAlong = new Movie("Home Along");
        homeAlong.setDescription("Family comedy");
        movieService.add(homeAlong);

        Movie exorcist = new Movie("The Exorcist: Believer");
        exorcist.setDescription("Horror movie");
        movieService.add(exorcist);

        Movie creator = new Movie("The Creator");
        creator.setDescription("Action, Adventure, Drama");
        movieService.add(creator);

        Movie barbie = new Movie("Barbie");
        barbie.setDescription("Adventure, Comedy, Fantasy");
        movieService.add(barbie);

        CinemaHall hallA = new CinemaHall();
        hallA.setCapacity(200);
        hallA.setDescription("cozy hall for families");
        cinemaHallService.add(hallA);

        CinemaHall hallB = new CinemaHall();
        hallB.setCapacity(100);
        hallB.setDescription("great hall for horror movies");
        cinemaHallService.add(hallB);

        CinemaHall hallC = new CinemaHall();
        hallC.setCapacity(124);
        hallC.setDescription("great hall for drama movies");
        cinemaHallService.add(hallC);

        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setCinemaHall(hallA);
        LocalDate date = LocalDate.of(2023, 10, 3);
        LocalTime time = LocalTime.of(10, 20);
        fastAndFuriousSession.setShowTime(LocalDateTime.of(date, time));
        movieSessionService.add(fastAndFuriousSession);

        MovieSession fastAndFuriousSession1 = new MovieSession();
        fastAndFuriousSession1.setMovie(fastAndFurious);
        fastAndFuriousSession1.setCinemaHall(hallA);
        date = LocalDate.of(2023, 10, 3);
        time = LocalTime.of(12, 5);
        fastAndFuriousSession1.setShowTime(LocalDateTime.of(date, time));
        movieSessionService.add(fastAndFuriousSession1);

        MovieSession fastAndFuriousSession2 = new MovieSession();
        fastAndFuriousSession2.setMovie(fastAndFurious);
        fastAndFuriousSession2.setCinemaHall(hallC);
        date = LocalDate.of(2023, 10, 3);
        time = LocalTime.of(17, 30);
        fastAndFuriousSession2.setShowTime(LocalDateTime.of(date, time));
        movieSessionService.add(fastAndFuriousSession2);

        MovieSession fastAndFuriousSession3 = new MovieSession();
        fastAndFuriousSession3.setMovie(fastAndFurious);
        fastAndFuriousSession3.setCinemaHall(hallB);
        date = LocalDate.of(2023, 10, 4);
        time = LocalTime.of(17, 50);
        fastAndFuriousSession3.setShowTime(LocalDateTime.of(date, time));
        movieSessionService.add(fastAndFuriousSession3);

        MovieSession homeAlongSession = new MovieSession();
        homeAlongSession.setMovie(homeAlong);
        homeAlongSession.setCinemaHall(hallB);
        date = LocalDate.of(2023, 10, 3);
        time = LocalTime.of(13, 15);
        homeAlongSession.setShowTime(LocalDateTime.of(date, time));
        movieSessionService.add(homeAlongSession);

        MovieSession exorcistSession = new MovieSession();
        exorcistSession.setMovie(exorcist);
        exorcistSession.setCinemaHall(hallB);
        date = LocalDate.of(2023, 10, 3);
        time = LocalTime.of(22, 15);
        exorcistSession.setShowTime(LocalDateTime.of(date, time));
        movieSessionService.add(exorcistSession);

        MovieSession exorcistSession1 = new MovieSession();
        exorcistSession1.setMovie(exorcist);
        exorcistSession1.setCinemaHall(hallB);
        date = LocalDate.of(2023, 10, 10);
        time = LocalTime.of(22, 15);
        exorcistSession1.setShowTime(LocalDateTime.of(date, time));
        movieSessionService.add(exorcistSession1);

        MovieSession creatorSession = new MovieSession();
        creatorSession.setMovie(creator);
        creatorSession.setCinemaHall(hallC);
        date = LocalDate.of(2023, 10, 5);
        time = LocalTime.of(18, 45);
        creatorSession.setShowTime(LocalDateTime.of(date, time));
        movieSessionService.add(creatorSession);

        MovieSession creatorSession1 = new MovieSession();
        creatorSession1.setMovie(creator);
        creatorSession1.setCinemaHall(hallC);
        date = LocalDate.of(2023, 10, 6);
        time = LocalTime.of(18, 45);
        creatorSession1.setShowTime(LocalDateTime.of(date, time));
        movieSessionService.add(creatorSession1);

        MovieSession barbieSession = new MovieSession();
        barbieSession.setMovie(barbie);
        barbieSession.setCinemaHall(hallA);
        date = LocalDate.of(2023, 10, 6);
        time = LocalTime.of(10, 10);
        barbieSession.setShowTime(LocalDateTime.of(date, time));
        movieSessionService.add(barbieSession);

        MovieSession barbieSession1 = new MovieSession();
        barbieSession1.setMovie(barbie);
        barbieSession1.setCinemaHall(hallA);
        date = LocalDate.of(2023, 10, 6);
        time = LocalTime.of(18, 55);
        barbieSession1.setShowTime(LocalDateTime.of(date, time));
        movieSessionService.add(barbieSession1);
    }
}
