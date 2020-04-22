package com.petstore.test;

import com.petstore.demo.Application;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import run.qontract.test.QontractJUnitSupport;

import java.io.File;

import static run.qontract.core.versioning.RepoUtils.*;

public class PetStoreContractTest extends QontractJUnitSupport {
    private static ConfigurableApplicationContext context;

    @BeforeAll
    public static void setUp() {
        String contractPath = getContractPath();
        System.out.println("Contract file path: " + contractPath);
        System.setProperty("path", contractPath);

        System.setProperty("host", "localhost");
        System.setProperty("port", "8080");

        context = SpringApplication.run(Application.class);
    }

    private static String getContractPath() {
        if(inGithubCI()) {
            String workspace = System.getenv("GITHUB_WORKSPACE");
            return new File(workspace + "/contracts/examples/petstore/1.contract").getAbsolutePath();
        } else {
            return new File(getContractFilePath("examples.petstore", 1)).getAbsolutePath();
        }
    }

    private static boolean inGithubCI() {
        return "true".equals(System.getenv("CI"));
    }

    @AfterAll
    public static void tearDown() {
        context.stop();
    }
}
