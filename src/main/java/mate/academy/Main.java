package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
        MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie sherlock = new Movie("Sherlock");
        sherlock.setDescription("movie about sherlock holmes and doctor watson ");
        Movie shawshankRedemption = new Movie("The Shawshank Redemption");
        shawshankRedemption.setDescription("Two imprisoned men bond over a number of years,"
                + " finding solace and eventual redemption through acts of common decency");
        Movie darkKnight = new Movie("The Dark Knight");
        darkKnight.setDescription("Batman must accept one of the greatest psychological "
                + "and physical tests of his ability to fight injustice.");
        Movie lordOfRings = new Movie("The Lord of the Rings");
        lordOfRings.setDescription("A meek Hobbit from the Shire "
                + "and eight companions set out on a journey to destroy the powerful One Ring "
                + "and save Middle-earth from the Dark Lord Sauron.");

        CinemaHall cinemaHall1 = new CinemaHall();
        cinemaHall1.setCapacity(150);
        cinemaHall1.setDescription("Modern cinema hall with state-of-the-art technology,"
                + " providing an immersive viewing experience for up to 150 moviegoers.");
        CinemaHall cinemaHall2 = new CinemaHall();
        cinemaHall2.setCapacity(100);
        cinemaHall2.setDescription("Cozy cinema hall with plush seating,"
                + " perfect for intimate screenings and gatherings of up to 100 guests.");
        CinemaHall cinemaHall3 = new CinemaHall();
        cinemaHall3.setCapacity(200);
        cinemaHall3.setDescription("Spacious cinema hall designed for 200 guests, "
                + "featuring advanced audiovisual technology "
                + "for an unforgettable cinematic experience.");

        MovieSession batmanSession = new MovieSession();
        batmanSession.setCinemaHall(cinemaHall1);
        batmanSession.setMovie(darkKnight);
        batmanSession.setShowTime(LocalDateTime.of(2024, 11, 19, 16, 0));
        MovieSession sherlockSession = new MovieSession();
        sherlockSession.setCinemaHall(cinemaHall2);
        sherlockSession.setMovie(sherlock);
        sherlockSession.setShowTime(LocalDateTime.of(2024, 3, 15, 13,0));
        MovieSession lordSession = new MovieSession();
        lordSession.setCinemaHall(cinemaHall3);
        lordSession.setMovie(lordOfRings);
        lordSession.setShowTime(LocalDateTime.of(2024, 3, 18, 17,30));

        List<Movie> movies = List.of(
                fastAndFurious, sherlock, shawshankRedemption, darkKnight, lordOfRings);
        List<CinemaHall> cinemaHalls = List.of(cinemaHall1, cinemaHall2, cinemaHall3);
        List<MovieSession> movieSessions = List.of(batmanSession, sherlockSession, lordSession);

        for (Movie movie : movies) {
            movieService.add(movie);
        }
        for (CinemaHall cinemaHall : cinemaHalls) {
            cinemaHallService.add(cinemaHall);
        }
        for (MovieSession movieSession : movieSessions) {
            movieSessionService.add(movieSession);
        }

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        System.out.println("end Of Movies");

        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("end Cinema Halls");

        System.out.println(movieSessionService
                .findAvailableSessions(4L, LocalDate.of(2024, 11, 19)));
    }
}
