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
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fast1 = new Movie("The Fast and the Furious");
        fast1.setDescription("An action film about street racing, heists, and spies.");

        Movie fast2 = new Movie("2 Fast 2 Furious");
        fast2.setDescription("Brian O'Conner teams up with Roman Pearce "
                + "to take down a Miami drug lord.");

        Movie fast3 = new Movie("The Fast and the Furious: Tokyo Drift");
        fast3.setDescription("An American teenager gets sent to Tokyo and discovers the world of "
                + "drift racing.");

        Movie fast4 = new Movie("Fast & Furious");
        fast4.setDescription("Dom and Brian reunite in Los Angeles to infiltrate a heroin cartel.");

        Movie fast5 = new Movie("Fast Five");
        fast5.setDescription("The crew plans a massive 100 million "
                + "dollar heist in Rio de Janeiro.");

        Movie fast6 = new Movie("Fast & Furious 6");
        fast6.setDescription("Dom's team is granted amnesty in exchange for helping take down a "
                + "mercenary crew.");

        Movie fast7 = new Movie("Furious 7");
        fast7.setDescription("A rogue assassin seeks vengeance against the crew for crippling his "
                + "brother.");

        Movie fast8 = new Movie("The Fate of the Furious");
        fast8.setDescription("A mysterious woman seduces Dom into the world of terrorism, forcing "
                + "the crew to stop him.");

        Movie fast9 = new Movie("F9: The Fast Saga");
        fast9.setDescription("Dom and his crew must stop a world-shattering plot led by Dom's "
                + "estranged brother.");

        Movie fast10 = new Movie("Fast X");
        fast10.setDescription("The crew faces the vengeful son of drug lord Hernan Reyes, "
                + "who targets Dom's family.");

        Movie fast11 = new Movie("Fast X: Part 2");
        fast11.setDescription("The final chapter where Dom and his family make their "
                + "last stand to protect their legacy.");

        List<Movie> fastAndFuriousSeries =
                List.of(fast1, fast2, fast3, fast4, fast5,
                        fast6, fast7, fast8, fast9, fast10, fast11);

        System.out.println("\n--- Movie Service ADD ---");
        for (Movie movie : fastAndFuriousSeries) {
            Movie savedMovie = movieService.add(movie);
            System.out.println("Pomyślnie dodano film: "
                    + savedMovie.getTitle() + " z ID: "
                    + savedMovie.getId());
        }

        System.out.println("\n--- Movie Service GET ---");
        for (Movie movie : fastAndFuriousSeries) {
            Movie retrievedMovie = movieService.get(movie.getId());
            System.out.println("Pobrano z bazy: " + retrievedMovie.getTitle());
        }

        System.out.println("\n--- Movie Service GET ALL ---");
        List<Movie> allMoviesFromDb = movieService.getAll();
        System.out.println("Liczba filmów w bazie danych: " + allMoviesFromDb.size());

        for (Movie dbMovie : allMoviesFromDb) {
            System.out.println(" - [" + dbMovie.getId() + "] " + dbMovie.getTitle());
        }

        CinemaHall imax = new CinemaHall();
        imax.setCapacity(300);
        imax.setDescription("IMAX - Huge screen with laser projection and 12-channel sound");

        CinemaHall vip = new CinemaHall();
        vip.setCapacity(30);
        vip.setDescription("VIP Premium - Leather reclining chairs and private bar service");

        CinemaHall standardHall = new CinemaHall();
        standardHall.setCapacity(150);
        standardHall.setDescription("Standard Hall 1 - Comfortable seats with 4K projection");

        List<CinemaHall> cinemaHalls = List.of(imax, vip, standardHall);

        System.out.println("\n--- Cinema Hall ADD ---");
        for (CinemaHall hall : cinemaHalls) {
            CinemaHall savedHall = cinemaHallService.add(hall);
            System.out.println("Dodano salę: " + savedHall.getDescription()
                    + " (Miejsca: " + savedHall.getCapacity() + ") z ID: " + savedHall.getId());
        }

        System.out.println("\n--- Cinema Hall GET ---");
        for (CinemaHall hall : cinemaHalls) {
            CinemaHall retrievedHall = cinemaHallService.get(hall.getId());
            System.out.println("Pobrano z bazy salę o ID ["
                    + retrievedHall.getId() + "]: " + retrievedHall.getDescription());
        }

        System.out.println("\n--- Cinema Hall GET ALL ---");
        List<CinemaHall> allHallsFromDb = cinemaHallService.getAll();

        System.out.println("Łączna liczba sal w bazie danych: " + allHallsFromDb.size());
        for (CinemaHall dbHall : allHallsFromDb) {
            System.out.println(" - Sala ID [" + dbHall.getId() + "]: "
                    + dbHall.getDescription() + " | Pojemność: " + dbHall.getCapacity());
        }

        MovieSession session1 = new MovieSession();
        session1.setMovie(fast1);
        session1.setCinemaHall(imax);
        session1.setShowTime(LocalDateTime.of(2026, 6, 12, 18, 0));

        MovieSession session2 = new MovieSession();
        session2.setMovie(fast1);
        session2.setCinemaHall(vip);
        session2.setShowTime(LocalDateTime.of(2026, 6, 12, 21, 30));

        MovieSession session3 = new MovieSession();
        session3.setMovie(fast1);
        session3.setCinemaHall(standardHall);
        session3.setShowTime(LocalDateTime.of(2026, 6, 13, 15, 0));

        MovieSession session4 = new MovieSession();
        session4.setMovie(fast2);
        session4.setCinemaHall(imax);
        session4.setShowTime(LocalDateTime.of(2026, 6, 12, 12, 0));

        System.out.println("\n--- TEST MOVIE SESSION: ADD ---");
        movieSessionService.add(session1);
        movieSessionService.add(session2);
        movieSessionService.add(session3);
        movieSessionService.add(session4);
        System.out.println("Zapisano 4 seanse kinowe.");

        System.out.println("\n--- TEST MOVIE SESSION: GET ---");
        MovieSession retrievedSession = movieSessionService.get(session1.getId());
        System.out.println("Pobrano seans ID [" + retrievedSession.getId() + "]: "
                + retrievedSession.getMovie().getTitle() + " w "
                + retrievedSession.getCinemaHall().getDescription());

        System.out.println("\n--- TEST MOVIE SESSION: FIND AVAILABLE SESSIONS ---");

        LocalDate targetDate = LocalDate.of(2026, 6, 12);
        Long targetMovieId = fast1.getId();

        System.out.println("Szukamy seansów dla filmu: '"
                + fast1.getTitle() + "' na dzień: " + targetDate);

        List<MovieSession> availableSessions =
                movieSessionService.findAvailableSessions(targetMovieId, targetDate);

        System.out.println("Znaleziono seansów: " + availableSessions.size());
        for (MovieSession session : availableSessions) {
            System.out.println(" -> Start: " + session.getShowTime().toLocalTime()
                    + " | Sala: " + session.getCinemaHall().getDescription());
        }

    }
}
