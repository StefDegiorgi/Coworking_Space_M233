package ch.zli.m223.ksh20.user.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import ch.zli.m223.ksh20.user.model.Reservation;
import ch.zli.m223.ksh20.user.model.User;
import ch.zli.m223.ksh20.user.repository.ReservationRepository;
import ch.zli.m223.ksh20.user.service.ReservationService;
import ch.zli.m223.ksh20.user.service.UserService;

@Controller
public class ReservationWebController {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired 
    private UserService userService;

    @GetMapping("/web/reservations")
    String getReservationList(Model model){
        //User user = userService.getUserById(id);

        //model.addAttribute("userId", id);
        //List<Reservation> resList = reservationService.getReservationList(null);
        return "reservations";
    }
    /* @GetMapping("/web/users")
    String getUserList(Model model) {
        List<User> users = userService.getUserList();

        //Add data to model
        model.addAttribute("users", users);

        //Pocess with 'userList' template
        return "userList";
    } */
}
