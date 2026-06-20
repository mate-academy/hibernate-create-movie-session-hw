package mate.academy;

import java.time.LocalDate;
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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        
        Movie theGodfather = new Movie("The Godfather");
        theGodfather.setDescription("Crime film directed by Francis Ford Coppola," 
                + " based on Mario Puzo's bestselling novel.");
        movieService.add(theGodfather);
        System.out.println(movieService.get(theGodfather.getId()));
        
        Movie starWarsEpisodeThree = new Movie("Star Wars: Episode III");
        starWarsEpisodeThree.setDescription("""
                Revenge of the Sith is the climactic conclusion
                to the prequel trilogy, chronicling Anakin Skywalker's
                tragic fall to the dark side and the collapse of the
                Jedi Order
                """);
        movieService.add(starWarsEpisodeThree);
        System.out.println(movieService.get(starWarsEpisodeThree.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHallFirst = new CinemaHall();
        cinemaHallFirst.setCapacity(32);
        cinemaHallFirst.setDescription("1-st cinema hall");
        cinemaHallService.add(cinemaHallFirst);
        System.out.println(cinemaHallService.get(cinemaHallFirst.getId()));

        CinemaHall cinemaHallSecond = new CinemaHall();
        cinemaHallSecond.setCapacity(70);
        cinemaHallSecond.setDescription("2-nd cinema hall");
        cinemaHallService.add(cinemaHallSecond);
        System.out.println(cinemaHallService.get(cinemaHallSecond.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSessionOne = new MovieSession();
        movieSessionOne.setMovie(fastAndFurious);
        movieSessionOne.setCinemaHall(cinemaHallFirst);
        movieSessionOne.setShowTime(LocalDate.now().atStartOfDay());
        
        MovieSession movieSessionTwo = new MovieSession();
        movieSessionTwo.setMovie(theGodfather);
        movieSessionTwo.setCinemaHall(cinemaHallFirst);
        movieSessionTwo.setShowTime(LocalDate.now().atStartOfDay().plusHours(23)
                .plusMinutes(59).plusSeconds(59));
        
        MovieSession movieSessionThree = new MovieSession();
        movieSessionThree.setMovie(starWarsEpisodeThree);
        movieSessionThree.setCinemaHall(cinemaHallSecond);
        movieSessionThree.setShowTime(LocalDate.now().plusDays(1).atStartOfDay().plusHours(11));
        
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSessionOne);
        movieSessionService.add(movieSessionTwo);
        movieSessionService.add(movieSessionThree);
        System.out.println(movieSessionService.get(movieSessionOne.getId()));
        System.out.println(movieSessionService.findAvailableSessions(movieSessionOne.getId(),
                LocalDate.now()));
    }
}
