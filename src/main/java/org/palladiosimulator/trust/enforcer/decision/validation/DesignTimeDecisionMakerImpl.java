package org.palladiosimulator.trust.enforcer.decision.validation;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.palladiosimulator.trust.enforcer.decision.data.DataObject;
import org.palladiosimulator.trust.enforcer.decision.data.Operation;
import org.palladiosimulator.trust.enforcer.decision.data.PrivacyLevel;
import org.palladiosimulator.trust.enforcer.decision.data.PrivacyTable;
import org.palladiosimulator.trust.enforcer.decision.data.rules.AllowRule;
import org.palladiosimulator.trust.enforcer.decision.io.PrivacyLevelMapping;
import org.palladiosimulator.trust.enforcer.decision.io.PrivacyLoader;
import org.palladiosimulator.trust.enforcer.decision.io.StringMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class DesignTimeDecisionMakerImpl implements DesignTimeDecisionMaker {
	private PrivacyTable privacyTable;
	private Map<String, PrivacyLevel> allowedPrivacyLevel;
	private Map<String, String> datatypeMapping;

	public DesignTimeDecisionMakerImpl(String privacyLevelPath, String typeMappingPath, String roleMappingPath)
			throws IOException {
		PrivacyLoader privacyLoader = new PrivacyLoader(privacyLevelPath);
		privacyTable = privacyLoader.getPrivacyTable();
		datatypeMapping = new StringMapping(typeMappingPath).getTypeMapping();
		allowedPrivacyLevel = new PrivacyLevelMapping(roleMappingPath).getTypeMapping();
	}

	//TODO ExecptionHandling: Exception should produce useful messages 
	@Override
	public boolean checkRequest(String subject, String operation, String object) throws NullPointerException {
		var requestRule = new AllowRule(DataObject.mapObject(subject, datatypeMapping),
				Operation.parseOperation(operation), DataObject.mapObject(object, datatypeMapping));
		Optional<PrivacyLevel> privacyLevel = privacyTable.getPrivacyLevel(requestRule.getSubject(),
				requestRule.getOperation(), requestRule.getObject());
		if (!privacyLevel.isPresent())
			return false;
		var allowedLevel = allowedPrivacyLevel.get(requestRule.getSubject().getType());
		var set = EnumSet.range(privacyLevel.get(),PrivacyLevel.HIGHLY_SENSITIVE);
		return set.contains(allowedLevel);
	}

}
