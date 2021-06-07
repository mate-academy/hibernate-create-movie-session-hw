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
    private static final MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);
    
    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        
        Movie matrix = new Movie("Matrix");
        matrix.setDescription(
                "The Matrix is a computer-generated dream world designed to keep these humans "
                        + "under control.");
        movieService.add(matrix);
        System.out.println(movieService.get(matrix.getId()));
        movieService.getAll().forEach(System.out::println);
        
        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(100);
        redHall.setDescription("RED hall");
        cinemaHallService.add(redHall);
        System.out.println(cinemaHallService.get(redHall.getId()));
        
        CinemaHall blackHall = new CinemaHall();
        blackHall.setCapacity(150);
        blackHall.setDescription("BLACK hall");
        cinemaHallService.add(blackHall);
        System.out.println(cinemaHallService.get(blackHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        
        MovieSession fastAndFuriousRedHall = new MovieSession();
        fastAndFuriousRedHall.setCinemaHall(redHall);
        fastAndFuriousRedHall.setMovie(fastAndFurious);
        fastAndFuriousRedHall.setShowTime(LocalDateTime.now());
        movieSessionService.add(fastAndFuriousRedHall);
        
        MovieSession matrixBlackHall = new MovieSession();
        matrixBlackHall.setMovie(matrix);
        matrixBlackHall.setCinemaHall(blackHall);
        matrixBlackHall.setShowTime(LocalDateTime.now().plusDays(3L));
        movieSessionService.add(matrixBlackHall);
        movieSessionService
                .findAvailableSessions(matrix.getId(), LocalDate.now().plusDays(3L))
                .forEach(System.out::println);
        
    }
}
