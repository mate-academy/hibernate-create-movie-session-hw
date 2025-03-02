package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        Random random = new Random();

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService cinemaHallService = (CinemaHallService) injector.getInstance(
                CinemaHallService.class);
        MovieSessionService movieSessionService = (MovieSessionService) injector.getInstance(
                MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious 1");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie fastAndFurious2 = new Movie("Fast and Furious 2");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie fastAndFurious3 = new Movie("Fast and Furious 3");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie fastAndFurious4 = new Movie("Fast and Furious 4");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie fastAndFuriousTD = new Movie("Fast and Furious: Tokyo Drift");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        movieService.add(fastAndFurious2);
        movieService.add(fastAndFurious3);
        movieService.add(fastAndFurious4);
        movieService.add(fastAndFuriousTD);

        CinemaHall cinemaHall = new CinemaHall(10, "Cinema in LA");
        CinemaHall cinemaHall2 = new CinemaHall(10, "Cinema in London");
        CinemaHall cinemaHall3 = new CinemaHall(10, "Cinema in Tokyo");
        CinemaHall cinemaHall4 = new CinemaHall(10, "Cinema in Kiev");
        CinemaHall cinemaHall5 = new CinemaHall(10, "Cinema in Warsaw");
        cinemaHallService.add(cinemaHall);
        cinemaHallService.add(cinemaHall2);
        cinemaHallService.add(cinemaHall3);
        cinemaHallService.add(cinemaHall4);
        cinemaHallService.add(cinemaHall5);
        CinemaHall[] cinemaHalls = {cinemaHall, cinemaHall2, cinemaHall3, cinemaHall4, cinemaHall5};
        Movie[] movies = {fastAndFurious, fastAndFurious2, fastAndFurious3, fastAndFurious4,
                fastAndFuriousTD};
        LocalDateTime today = LocalDateTime.now();
        for (int i = 0; i < 10; i++) {
            today = today.minusDays(i);
            for (int j = 0; j < 5; j++) {
                today = today.minusHours(j);
                for (Movie movie : movies) {
                    movieSessionService.add(new MovieSession(movie, cinemaHalls[random.nextInt(cinemaHalls.length)], today));
                }
            }
        }

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        System.out.println(cinemaHallService.getAll());
        List<MovieSession> movieSessions = movieSessionService.findAllAvailableSessions(fastAndFurious3.getId(), LocalDate.now());
        System.out.println(movieSessions.size());
        System.out.println(movieSessions);
    }
}
