package ch.zli.m223.ksh20.user.init;

import ch.zli.m223.ksh20.user.model.Reservation;
import ch.zli.m223.ksh20.user.model.User;
import ch.zli.m223.ksh20.user.model.enums.Duration;
import ch.zli.m223.ksh20.user.model.enums.Role;
import ch.zli.m223.ksh20.user.repository.ReservationRepository;
import ch.zli.m223.ksh20.user.repository.UserRepository;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ServerInit implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private ReservationRepository reservationRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.insertUser(new User("Max", "SI-UU", "max@siu.ch", "asd34454"));
        userRepository.insertUser(new User("OmegaMax", "tax", "dajfha@gmail.com", "dhjfaadf"));
        User u = new User("admin", "admin", "admin", "admin");
        u.setRole(Role.ADMIN);
        userRepository.insertUser(u);
        Reservation r = new Reservation(LocalDate.now(), Duration.HALF_DAY);
        r.setUser(u);
        reservationRepository.insertReservation(r);
    }
}
