package com.petstore.test;

import com.petstore.demo.Application;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import run.qontract.test.QontractJUnitSupport;

import java.io.File;

public class PetStoreContractTest extends QontractJUnitSupport {
    private static ConfigurableApplicationContext context;

    @BeforeAll
    public static void setUp() {
        File contract = new File("contract/service.contract");
        System.setProperty("path", contract.getAbsolutePath());
        System.setProperty("host", "localhost");
        System.setProperty("port", "8080");

        context = SpringApplication.run(Application.class);
    }

    @AfterAll
    public static void tearDown() {
        context.stop();
    }
}
