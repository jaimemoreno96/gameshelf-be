package com.jaimemoreno.gameshelf;

import org.springframework.boot.SpringApplication;

public class TestGameshelfApplication {

	public static void main(String[] args) {
		SpringApplication.from(GameshelfApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
