package org.palladiosimulator.trust.enforcer.decision.controller.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.trust.enforcer.decision.data.PrivacyLevel;
import org.palladiosimulator.trust.enforcer.decision.validation.DesignTimeDecisionMaker;
import org.palladiosimulator.trust.enforcer.decision.validation.DesignTimeDecisionMakerImpl;

public class TestRequestSolving {
	static DesignTimeDecisionMaker decision;
	static Path privacyConfig;
	static Path mappingConfig;
	static Path roleConfig;

	@BeforeAll
	static void init() {
		privacyConfig = Paths.get("test.csv");
		mappingConfig = Paths.get("mapping.csv");
		roleConfig = Paths.get("role.csv");
		try (BufferedWriter writerPrivacy = Files.newBufferedWriter(privacyConfig, Charset.forName("UTF-8"))) {

			writerPrivacy.write("foreman;read(*);machine;sensitive\n");
			writerPrivacy.write("worker;read(*);machine;highly_sensitive\n");
			writerPrivacy.write("foreman;read(*);worker;highly_sensitive\n");
			writerPrivacy.close();
			BufferedWriter writerConfig = Files.newBufferedWriter(mappingConfig, Charset.forName("UTF-8"));
			writerConfig.write("A-foreman;foreman\n");
			writerConfig.write("A-worker-001;worker\n");
			writerConfig.write("A-worker-002;worker\n");
			writerConfig.write("factory;factory\n");
			writerConfig.write("dispenser;dispenser\n");
			writerConfig.write("machine-A;machine\n");
			writerConfig.close();
			BufferedWriter writerRoles = Files.newBufferedWriter(roleConfig, Charset.forName("UTF-8"));
			writerRoles.write("foreman;highly_sensitive\n");
			writerRoles.write("worker;public\n");
			writerRoles.close();
			decision = new DesignTimeDecisionMakerImpl<>(PrivacyLevel.class, privacyConfig.getFileName().toString(),mappingConfig.getFileName().toString(),roleConfig.getFileName().toString());
		} catch (IOException e) {
			e.printStackTrace();
			fail("IO-Error");
		}
	}
	@Test
	void testHigherPrivacyLevel() {
		assertTrue(decision.checkRequest("A-foreman","read","machine-A"));
	}
	@Test
	void testLowerPrivacyLevel() {
		assertFalse(decision.checkRequest("A-worker-001","read","machine-A"));
	}
	@Test
	void testSamePrivacyLevel() {
		assertTrue(decision.checkRequest("A-foreman","read","A-worker-001"));
	}
	@AfterAll
	static void clean() {
		try {
			Files.delete(privacyConfig);
			Files.delete(mappingConfig);
			Files.delete(roleConfig);
		} catch (IOException e) {
			e.printStackTrace();
			fail("IO-Error: can't delete privacy File");
		}
	}
}
