package org.palladiosimulator.trust.enforcer.decision.controller.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.trust.enforcer.decision.data.DataObject;
import org.palladiosimulator.trust.enforcer.decision.data.Operation;
import org.palladiosimulator.trust.enforcer.decision.data.PrivacyLevel;
import org.palladiosimulator.trust.enforcer.decision.data.rules.AllowRule;
import org.palladiosimulator.trust.enforcer.decision.data.rules.DenyRule;

class TestRule {
	@Nested
	@DisplayName("Tests Different Equality")
	class testEqualsRule {
		AllowRule allowRule;
		DenyRule denyRule;

		@BeforeEach
		void initial() {
			allowRule = new AllowRule(new DataObject("test123"), new Operation("test234"), new DataObject("test345"));
			denyRule = new DenyRule(new DataObject("test123"), new Operation("test234"),new DataObject("test345"), PrivacyLevel.HIGHLY_SENSITIVE);
		}

		@Test
		void testEqualsWithDenyAndAllowRule() {
			assertFalse(allowRule.equals(denyRule));
		}

		@Test
		void testEqualsAllowRule() {
			assertTrue(allowRule
					.equals(new AllowRule(allowRule.getSubject(), allowRule.getOperation(), allowRule.getObject())));
		}

		@Test
		void testEqualsDenyRule() {
			assertTrue(denyRule.equals(new DenyRule(denyRule.getSubject(), denyRule.getOperation(), denyRule.getObject(),
					denyRule.getPrivacyLevel())));
		}

		@Test
		void testSameRule() {
			assertTrue(allowRule.equalRule(denyRule));
		}
	}
}
