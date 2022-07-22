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
    private static MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie spiderman = new Movie("Spider-Man");
        fastAndFurious.setDescription("Filmabout spiderman");
        movieService.add(spiderman);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
    
        CinemaHall cinemaHall2D = new CinemaHall();
        cinemaHall2D.setCapacity(20);
        cinemaHall2D.setDescription("CinemaHall2D description");
        cinemaHallService.add(cinemaHall2D);
        CinemaHall cinemaHall3D = new CinemaHall();
        cinemaHall3D.setCapacity(30);
        cinemaHall3D.setDescription("CinemaHall3D description");
        cinemaHallService.add(cinemaHall3D);
        System.out.println(cinemaHallService.get(cinemaHall2D.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
    
        MovieSession fastAndFuriousMS = new MovieSession();
        fastAndFuriousMS.setMovie(fastAndFurious);
        fastAndFuriousMS.setCinemaHall(cinemaHall2D);
        fastAndFuriousMS.setShowTime(LocalDateTime.now());
        
        MovieSession spidermanMS = new MovieSession();
        spidermanMS.setMovie(spiderman);
        spidermanMS.setCinemaHall(cinemaHall3D);
        spidermanMS.setShowTime(LocalDateTime.now().minusDays(1L));
        movieSessionService.add(fastAndFuriousMS);
        movieSessionService.add(spidermanMS);
        
        movieSessionService.findAvailableSession(spiderman.getId(),
                        LocalDate.from(LocalDateTime.now().minusDays(1L)))
                .forEach(System.out::println);
    }
}
