package mate.academy;

import java.time.LocalDateTime;
import java.time.Month;
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
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie terminatorII = new Movie("Terminator II");
        terminatorII.setDescription("The Terminator is a 1984 American science "
                + "fiction action film directed by James Cameron");
        movieService.add(terminatorII);

        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);

        CinemaHall greenCinemaHall = new CinemaHall("Green hall for 49 seats, "
                + "which give you an unforgettable journey into the 3D world of cinema");
        greenCinemaHall.setCapacity(225);
        cinemaHallService.add(greenCinemaHall);
        System.out.println(cinemaHallService.get(greenCinemaHall.getId()));

        CinemaHall redCinemaHall = new CinemaHall("64 channels and special geometry"
                + " of the hall guarantee the accuracy of sound "
                + "positioning and the effect of presence");
        redCinemaHall.setCapacity(285);
        cinemaHallService.add(redCinemaHall);

        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);

        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        Month month = now.getMonth();
        int dayOfMonth = now.getDayOfMonth();

        int[] sessionTime = {10,16,20};
        LocalDateTime[] localDateTimes = new LocalDateTime[5];
        for (int i = 0; i < sessionTime.length; i++) {
            localDateTimes[i + 1] = LocalDateTime.of(year,month,dayOfMonth,sessionTime[i],0,0);
        }

        MovieSession firstMovieSessionGreen = new MovieSession(
                movieService.get(1L),cinemaHallService.get(1L),localDateTimes[1]);
        MovieSession secondMovieSessionGreen = new MovieSession(
                movieService.get(2L),cinemaHallService.get(1L),localDateTimes[2]);
        MovieSession thirdMovieSessionGreen = new MovieSession(
                movieService.get(1L),cinemaHallService.get(1L),localDateTimes[3]);
        MovieSession firstMovieSessionRed = new MovieSession(
                movieService.get(2L),cinemaHallService.get(2L),localDateTimes[1]);
        MovieSession secondMovieSessionRed = new MovieSession(
                movieService.get(1L),cinemaHallService.get(2L),localDateTimes[2]);
        MovieSession thirdMovieSessionRed = new MovieSession(
                movieService.get(2L),cinemaHallService.get(2L),localDateTimes[3]);

        movieSessionService.add(firstMovieSessionGreen);
        movieSessionService.add(firstMovieSessionRed);
        movieSessionService.add(secondMovieSessionGreen);
        movieSessionService.add(secondMovieSessionRed);
        movieSessionService.add(thirdMovieSessionGreen);
        movieSessionService.add(thirdMovieSessionRed);
        System.out.println(movieSessionService.get(secondMovieSessionRed.getId()));

        System.out.println(movieSessionService
                .findAvailableSessions(1L, localDateTimes[2].toLocalDate()));

        MovieSession testingMovieSessionRed = new MovieSession(
                movieService.get(2L), cinemaHallService.get(1L),
                LocalDateTime.of(2022,Month.JANUARY,22,11,0));
        movieSessionService.add(testingMovieSessionRed);

        testingMovieSessionRed.setShowTime(LocalDateTime.of(2022,Month.JANUARY,22,11,0));
        localDateTimes[2] = LocalDateTime.of(2022,Month.JANUARY,22,11,0);
        System.out.println(
                movieSessionService.findAvailableSessions(2L, localDateTimes[2].toLocalDate()));
    }
}
