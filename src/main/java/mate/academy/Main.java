package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        Movie snatch = new Movie("Snatch");
        snatch.setDescription("An action film about world of english crimes");
        movieService.add(snatch);
        System.out.println(movieService.get(snatch.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHall yellowCinemaHall = new CinemaHall();
        yellowCinemaHall.setDescription("Yellow");
        yellowCinemaHall.setCapacity(1000);
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(yellowCinemaHall);
        System.out.println(cinemaHallService.get(yellowCinemaHall.getId()));
        CinemaHall blueCinemaHall = new CinemaHall();
        blueCinemaHall.setDescription("Blue");
        blueCinemaHall.setCapacity(2000);
        cinemaHallService.add(blueCinemaHall);
        System.out.println(cinemaHallService.get(blueCinemaHall.getId()));
        System.out.println(cinemaHallService.getAll());
        LocalDateTime showTime = LocalDate.now().atStartOfDay();
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(genMovieSession(fastAndFurious, yellowCinemaHall,
                showTime.plusHours(9)));
        movieSessionService.add(genMovieSession(fastAndFurious, blueCinemaHall,
                showTime.plusHours(10)));
        movieSessionService.add(genMovieSession(fastAndFurious, yellowCinemaHall,
                showTime.plusHours(12)));
        movieSessionService.add(genMovieSession(fastAndFurious, blueCinemaHall,
                showTime.plusHours(13)));
        movieSessionService.add(genMovieSession(snatch, yellowCinemaHall,
                showTime.plusHours(15)));
        movieSessionService.add(genMovieSession(snatch, blueCinemaHall,
                showTime.plusHours(16)));
        movieSessionService.add(genMovieSession(snatch, yellowCinemaHall,
                showTime.plusHours(18)));
        movieSessionService.add(genMovieSession(snatch, blueCinemaHall,
                showTime.plusHours(19)));
        showTime = showTime.toLocalDate().atStartOfDay().plusDays(1);
        movieSessionService.add(genMovieSession(snatch, yellowCinemaHall,
                showTime.plusHours(9)));
        movieSessionService.add(genMovieSession(snatch, blueCinemaHall,
                showTime.plusHours(10)));
        movieSessionService.add(genMovieSession(snatch, yellowCinemaHall,
                showTime.plusHours(12)));
        movieSessionService.add(genMovieSession(snatch, blueCinemaHall,
                showTime.plusHours(13)));
        movieSessionService.add(genMovieSession(fastAndFurious, yellowCinemaHall,
                showTime.plusHours(15)));
        movieSessionService.add(genMovieSession(fastAndFurious, blueCinemaHall,
                showTime.plusHours(16)));
        movieSessionService.add(genMovieSession(fastAndFurious, yellowCinemaHall,
                showTime.plusHours(18)));
        movieSessionService.add(genMovieSession(fastAndFurious, blueCinemaHall,
                showTime.plusHours(19)));
        System.out.println(("*********SCHEDULE*********"));
        System.out.println("*********" + showTime.format(DateTimeFormatter.ofPattern("MM/dd/YYYY"))
                + "*********");
        movieSessionService.findAvailableSessions(snatch.getId(),
                showTime.toLocalDate()).forEach(System.out::println);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                showTime.toLocalDate()).forEach(System.out::println);
        showTime = showTime.minusDays(1);
        System.out.println(("*********SCHEDULE*********"));
        System.out.println("*********" + showTime.format(DateTimeFormatter.ofPattern("MM/dd/YYYY"))
                + "*********");
        movieSessionService.findAvailableSessions(snatch.getId(),
                showTime.toLocalDate()).forEach(System.out::println);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                showTime.toLocalDate()).forEach(System.out::println);
    }

    private static MovieSession genMovieSession(Movie movie, CinemaHall hall,
                                                LocalDateTime showTime) {
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movie);
        movieSession.setCinemaHall(hall);
        movieSession.setShowTime(showTime);
        return movieSession;
    }
}
