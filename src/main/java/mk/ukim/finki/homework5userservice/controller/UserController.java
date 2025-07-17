package mk.ukim.finki.homework5userservice.controller;

import mk.ukim.finki.homework5userservice.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final List<User> users = List.of(
            new User(1L, "Milan"),
            new User(2L, "Nikola"),
            new User(3L, "Filip")
    );

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }
}

