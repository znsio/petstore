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
import static org.codehaus.plexus.archiver.tar.TarLongFileMode.fail;
import static run.qontract.core.ContractUtilities.getLatestCompatibleContractFileName;
import static run.qontract.core.versioning.ContractIdentifierKt.contractNameToRelativePath;

public class PetStoreContractTest extends QontractJUnitSupport {
    private static ConfigurableApplicationContext context;

    @BeforeAll
    public static void setUp() {
        String contractPath = getContractPath("examples.petstore", 1);
        System.out.println("Contract file path: " + contractPath);
        System.setProperty("path", contractPath);

        System.setProperty("host", "localhost");
        System.setProperty("port", "8080");

        context = SpringApplication.run(Application.class);
    }

    private static String getContractPath(String name, Integer version) {
        if(inGithubCI()) {
            String workspace = System.getenv("GITHUB_WORKSPACE");
            String contractPath = workspace + File.separator + "contracts" + File.separator + contractNameToRelativePath(name);
            return getLatestCompatibleContractFileName(new File(contractPath).getAbsolutePath(), version);
        } else {
            Path path = Paths.get(System.getProperty("user.home"), "contracts", "petstore-contracts", contractNameToRelativePath(name)).toAbsolutePath();
            String contractPath = getLatestCompatibleContractFileName(new File(path.toString()).getAbsolutePath(), version);
            if(contractPath == null)
                fail("Contract must exist at path USER_HOME/contracts/petstore-contracts. Checkout https://github.com/qontract/petstore-contracts into USER_HOME/contracts.");

            return contractPath;
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
