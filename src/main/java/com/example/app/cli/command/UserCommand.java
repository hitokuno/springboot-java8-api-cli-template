package com.example.app.cli.command;

import com.example.app.common.domain.User;
import com.example.app.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCommand {

    private final UserService userService;

    public void execute(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: user <id>");
            System.exit(1);
        }
        String id = args[0];
        User user = userService.findById(id);
        if (user == null) {
            System.out.println("User not found: " + id);
        } else {
            System.out.println("User: " + user);
        }
    }
}
