package org.palladiosimulator.trust.enforcer.decision.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.palladiosimulator.trust.enforcer.decision.io.CharacteristicLoader;

class TestPrivacyLoader {
	static Path t;

	@BeforeAll
	static void init() {
		t = Paths.get("test.csv");
		try (BufferedWriter writer = Files.newBufferedWriter(t, Charset.forName("UTF-8"))) {
			writer.write("foreman;read(*);worker;public\n");
			writer.write("foreman;read(*);machine;sensitive\n");
            writer.write("worker;read(*);machine;highly_sensitive\n");
		} catch (IOException e) {
			e.printStackTrace();
			fail("IO-Error");
		}
	}
	@Test
	void testPrivacyTableCreation() {
		try {
            assertEquals(TestHelper.getPrivacyTable(),new CharacteristicLoader<>(PrivacyLevel.class, t.toString()).getCharacteristicTable(),"Tests Privacy Table");
		} catch (IOException e) {
			e.printStackTrace();
			fail("IO-Error");
		}

	}
	@AfterAll
	static void clean() {
		try {
			Files.delete(t);
		} catch (IOException e) {
			e.printStackTrace();
			fail("IO-Error: can't delete privacy File");
		}
	}
}
