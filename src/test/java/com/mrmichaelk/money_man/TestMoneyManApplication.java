package com.mrmichaelk.money_man;

import org.springframework.boot.SpringApplication;

public class TestMoneyManApplication {

	public static void main(String[] args) {
		SpringApplication.from(MoneyManApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
