package org.palladiosimulator.trust.enforcer.decision.controller;

import org.palladiosimulator.trust.enforcer.decision.data.AggregationLevel;
import org.palladiosimulator.trust.enforcer.decision.validation.DesignTimeDecisionMaker;
import org.palladiosimulator.trust.enforcer.decision.validation.DesignTimeDecisionMakerImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan({ "org.palladiosimulator.trust.enforcer.decision.validation" })
@RestController
public class DecisionPointController implements ApplicationRunner {
	private static DesignTimeDecisionMaker decisionMaker;

	public static void main(String[] args) {
		SpringApplication.run(DecisionPointController.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (!args.containsOption("path.typemapping"))
			throw new IllegalArgumentException("Missing path for typemapping");
		if (!args.containsOption("path.resultmapping"))
			throw new IllegalArgumentException("Missing path for results");
		if (!args.containsOption("path.rolemapping"))
			throw new IllegalArgumentException("Missing path for roles");
		var pathResultMapping = args.getOptionValues("path.resultmapping").get(0);
		var pathTypeMapping = args.getOptionValues("path.typemapping").get(0);
		var pathRoleMapping = args.getOptionValues("path.rolemapping").get(0);
		decisionMaker = new DesignTimeDecisionMakerImpl<>(AggregationLevel.class, pathResultMapping, pathTypeMapping, pathRoleMapping);
	}

	@RequestMapping(value = "/service/validate/{subjectId}/{verb}/{objectID}")
	public AccessResponse query(@PathVariable String subjectId, @PathVariable String verb,
			@PathVariable String objectID) {
		var result = decisionMaker.checkRequest(subjectId, verb, objectID);
		var respond = new AccessResponse(result);
		return respond;
	}

}
