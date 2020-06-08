package org.palladiosimulator.trust.enforcer.decision.validation;

import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.palladiosimulator.trust.enforcer.decision.data.CharacteristicTable;
import org.palladiosimulator.trust.enforcer.decision.data.DataObject;
import org.palladiosimulator.trust.enforcer.decision.data.Operation;
import org.palladiosimulator.trust.enforcer.decision.data.rules.AllowRule;
import org.palladiosimulator.trust.enforcer.decision.io.CharacteristicLoader;
import org.palladiosimulator.trust.enforcer.decision.io.CharacteristicMapping;
import org.palladiosimulator.trust.enforcer.decision.io.RoleMapping;

public class DesignTimeDecisionMakerImpl<CT extends Enum<CT>> implements DesignTimeDecisionMaker {
    private final Class<CT> enumClass;
	private CharacteristicTable<CT> characteristicTable;
	private Map<String, CT> allowedCharacteristics;
	private Map<String, String> datatypeMapping;

	public DesignTimeDecisionMakerImpl(Class<CT> enumClass, String resultMappingPath, String typeMappingPath, String roleMappingPath)
			throws IOException {
	    this.enumClass = enumClass;
		CharacteristicLoader<CT> characteristicLoader = new CharacteristicLoader<>(enumClass, resultMappingPath);
		characteristicTable = characteristicLoader.getCharacteristicTable();
		datatypeMapping = new RoleMapping(typeMappingPath).getTypeMapping();
		allowedCharacteristics = new CharacteristicMapping<>(enumClass, roleMappingPath).getTypeMapping();
	}

	//TODO ExecptionHandling: Exception should produce useful messages 
	@Override
	public boolean checkRequest(String subject, String operation, String object) throws NullPointerException {
		var requestRule = new AllowRule(DataObject.mapObject(subject, datatypeMapping),
				Operation.parseOperation(operation), DataObject.mapObject(object, datatypeMapping));
		Optional<CT> characteristic = characteristicTable.getCharacteritic(requestRule.getSubject(),
				requestRule.getOperation(), requestRule.getObject());
		if (!characteristic.isPresent())
			return false;
		var allowedCharacteristic = allowedCharacteristics.get(requestRule.getSubject().getType());
		var set = EnumSet.range(characteristic.get(), getHighestValue());
		return set.contains(allowedCharacteristic);
	}
	
	private CT getHighestValue() {
	    List<CT> enumLiterals = Arrays.asList(enumClass.getEnumConstants()).stream().filter(enumClass::isInstance).map(enumClass::cast).collect(Collectors.toList());
	    return enumLiterals.get(enumLiterals.size() - 1);
	}

}
