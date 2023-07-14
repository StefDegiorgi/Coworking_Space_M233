package ch.zli.m223.ksh20.user.controller.rest;

import ch.zli.m223.ksh20.user.controller.rest.dto.UserDto;
import ch.zli.m223.ksh20.user.model.User;
import ch.zli.m223.ksh20.user.model.enums.Role;
import ch.zli.m223.ksh20.user.security.JwtResponse;
import ch.zli.m223.ksh20.user.security.JwtUtils;
import ch.zli.m223.ksh20.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    boolean firstUser = true;

    @PostMapping(path="/register")
    User register(@ModelAttribute UserDto u) {
        User user = new User(u.getFirstname(), u.getLastname(), u.getEmail(), u.getPassword());
        if (firstUser) {
            user.setRole(Role.ADMIN);
            firstUser = false;
        }
        return userService.addUser(user);
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@ModelAttribute UserDto u) {
        User user = userService.authorize(u.getEmail(), u.getPassword());
        String token = jwtUtils.generateJwtToken(user.getEmail(), user.getRole(), user.getId());

        JwtResponse jwtResponse = new JwtResponse(token);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Email", user.getEmail());
        headers.add("id", user.getId().toString());
        headers.add("role", user.getRole().toString());
        return ResponseEntity.ok().headers(headers).body(jwtResponse);
        //return ResponseEntity.ok(new JwtResponse(token));
    }

    @DeleteMapping("/{id}/delete")
    void deleteUser(
            @PathVariable Long id,
            @RequestHeader("Authorization") String header
    ) {
        String token = header.split(" ")[1].trim();

        userService.deleteUser(id, token);
    }

    @PutMapping("/{id}/update")
    User updateUser(
            @PathVariable Long id,
            @ModelAttribute UserDto userDto,
            @RequestHeader("Authorization") String header
    ) {
        String token = header.split(" ")[1].trim();

        return userService.updateUser(id, userDto, token);
    }

    @GetMapping("/list")
    List<UserDto> getUserList() {
        return userService.getUserList().stream().map(UserDto::new).collect(Collectors.toList());
    }
}