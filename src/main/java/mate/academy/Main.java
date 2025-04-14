package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        injector.getInstance(MovieService.class);
        injector.getInstance(CinemaHallService.class);
        injector.getInstance(MovieSessionService.class);
    }
}
