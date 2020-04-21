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
        File contract = getContractFile();
        System.out.println("Contract file path: " + contract.getAbsolutePath());

        System.setProperty("path", contract.getAbsolutePath());
        System.setProperty("host", "localhost");
        System.setProperty("port", "8080");

        context = SpringApplication.run(Application.class);
    }

    private static File getContractFile() {
        if(inGithubCI()) {
            String workspace = System.getenv("GITHUB_WORKSPACE");
            return new File(workspace + "/contracts/example/petstore/1.contract");
        } else {
            return new File(System.getProperty("user.home") + "/.qontract/repos/petstore/repo/examples/petstore/1.contract");
        }
    }

    private static boolean inGithubCI() {
        return System.getenv("CI") == "true";
    }

    @AfterAll
    public static void tearDown() {
        context.stop();
    }
}
