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
    static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        Movie starWarsIV = new Movie("Star Wars. Episode IV: A New Hope");
        starWarsIV.setDescription("American epic space opera film written and "
                + "directed by George Lucas");
        movieService.add(starWarsIV);
        movieService.getAll().forEach(System.out::println);

        CinemaHall greenHall = new CinemaHall();
        greenHall.setDescription("Ordinary hall");
        greenHall.setCapacity(200);
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(greenHall);
        CinemaHall goldenHall = new CinemaHall();
        goldenHall.setDescription("lux hall");
        goldenHall.setCapacity(10);
        cinemaHallService.add(goldenHall);
        System.out.println(cinemaHallService.get(goldenHall.getId()));
        System.out.println(cinemaHallService.getAll());

        MovieSession fastAndFuriousMorning = new MovieSession();
        fastAndFuriousMorning.setMovie(fastAndFurious);
        fastAndFuriousMorning.setCinemaHall(greenHall);
        fastAndFuriousMorning.setShowTime(LocalDateTime.of(2022, 06, 01, 11, 00));
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousMorning);
        MovieSession fastAndFuriousDay = new MovieSession();
        fastAndFuriousDay.setMovie(fastAndFurious);
        fastAndFuriousDay.setCinemaHall(greenHall);
        fastAndFuriousDay.setShowTime(LocalDateTime.of(2022, 06, 01, 15, 00));
        movieSessionService.add(fastAndFuriousDay);
        MovieSession fastAndFuriousEvening = new MovieSession();
        fastAndFuriousEvening.setMovie(fastAndFurious);
        fastAndFuriousEvening.setCinemaHall(goldenHall);
        fastAndFuriousEvening.setShowTime(LocalDateTime.of(
                2022, 06, 01, 19, 45));
        movieSessionService.add(fastAndFuriousEvening);
        MovieSession starWarsNightSession = new MovieSession();
        starWarsNightSession.setMovie(starWarsIV);
        starWarsNightSession.setCinemaHall(goldenHall);
        starWarsNightSession.setShowTime(LocalDateTime.of(2022, 06, 01, 23, 00));
        movieSessionService.add(starWarsNightSession);
        System.out.println(movieSessionService.get(starWarsIV.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, 06,01));
    }
}
