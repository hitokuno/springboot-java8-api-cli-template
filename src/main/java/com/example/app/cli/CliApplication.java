package com.example.app.cli;

import com.example.app.cli.command.UserCommand;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * CLI entrypoint. Usage:
 *   java -cp app.jar com.example.app.cli.CliApplication <command> [args...]
 *
 * Example:
 *   java -cp app.jar com.example.app.cli.CliApplication user A001
 */
@SpringBootApplication(scanBasePackages = "com.example.app")
public class CliApplication {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: CliApplication <command> [args...]");
            System.err.println("Commands: user <id>");
            System.exit(1);
        }

        SpringApplication app = new SpringApplication(CliApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.setBannerMode(Banner.Mode.OFF);

        try (ConfigurableApplicationContext ctx = app.run(args)) {
            String command = args[0];
            String[] commandArgs = java.util.Arrays.copyOfRange(args, 1, args.length);

            switch (command) {
                case "user":
                    ctx.getBean(UserCommand.class).execute(commandArgs);
                    break;
                default:
                    System.err.println("Unknown command: " + command);
                    System.exit(1);
            }
        }
    }
}
