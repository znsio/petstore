package com.petstore.test;

import com.petstore.demo.Application;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import run.qontract.test.QontractJUnitSupport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.fail;
import static org.springframework.util.FileSystemUtils.deleteRecursively;
import static run.qontract.core.ContractUtilities.getContractFileName;
import static run.qontract.core.versioning.ContractIdentifierKt.contractNameToRelativePath;

public class PetStoreContractTest extends QontractJUnitSupport {
    private static ConfigurableApplicationContext context;

    @BeforeAll
    public static void setUp() {
        System.setProperty("manifestFile", "./manifest.json");

        String workingDirectory = "./target/qontract";
        File workingDirectoryFile = new java.io.File(workingDirectory);
        if(workingDirectoryFile.exists())
            deleteRecursively(workingDirectoryFile);

        System.setProperty("workingDirectory", workingDirectory);

        System.setProperty("host", "localhost");
        System.setProperty("port", "8080");

        context = SpringApplication.run(Application.class);
    }

    @AfterAll
    public static void tearDown() {
        context.stop();
    }
}
