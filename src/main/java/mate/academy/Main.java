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
        fastAndFurious.setDescription("An action film about street racing,"
                + " heists, and spies.");
        movieService.add(fastAndFurious);
        Movie fightClub = new Movie("Fight Club");
        fightClub.setDescription("Unhappy with his capitalistic lifestyle"
                + ", a white-collared insomniac "
                + "forms an underground fight club with Tyler"
                + ", a careless soap salesman. "
                + "Soon, their venture spirals down into something sinister.");
        movieService.add(fightClub);
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHallGreen = new CinemaHall(250, "Green Hall");
        cinemaHallService.add(cinemaHallGreen);
        MovieSession movieSession1 = new MovieSession(
                fastAndFurious, cinemaHallGreen, LocalDateTime.of(2022, 03, 26, 17, 30));
        MovieSession movieSession2 = new MovieSession(
                fightClub, cinemaHallGreen, LocalDateTime.of(2022, 03, 26, 20, 0));
        MovieSession movieSession3 = new MovieSession(
                fightClub, cinemaHallGreen, LocalDateTime.of(2022, 03, 26, 22, 30));
        MovieSession movieSession4 = new MovieSession(
                fastAndFurious, cinemaHallGreen, LocalDateTime.of(2022, 03, 27, 17, 30));
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        movieSessionService.add(movieSession3);
        movieSessionService.add(movieSession4);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        System.out.println("Fight Club: 26.03.2022");
        movieSessionService.findAvailableSessions(
                fightClub.getId(), LocalDate.of(2022, 03, 26)).forEach(System.out::println);
        System.out.println("Fast and Furious: 26.03.2022");
        movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.of(2022, 03, 26)).forEach(System.out::println);
    }
}
