package com.scaler.splitwise.splitwise;

import com.scaler.splitwise.splitwise.commands.CommandRegistry;
import com.scaler.splitwise.splitwise.commands.RegisterUserCommand;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class SplitwiseApplication implements CommandLineRunner {
	private Scanner sc;
	private CommandRegistry commandRegistry;

	public SplitwiseApplication(CommandRegistry commandRegistry) {
		this.sc = new Scanner(System.in);
		this.commandRegistry = commandRegistry;
	}

	public static void main(String[] args) {
		SpringApplication.run(SplitwiseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		while (true){
			System.out.println("Tell me what do you want to do?");
			String input = sc.nextLine();
			commandRegistry.execute(input);
		}


	}
}
