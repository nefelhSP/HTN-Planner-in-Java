package barman;

import barman.Types.*;
import java.util.*;
import static java.util.Collections.*;

/**
 * The Validate class is a class that validates the plan.
 * For each action of the plan the preconditions are checked based on the
 * current state.
 * If the preconditions are not satisfied the plan is invalid.
 * If the preconditions are satisfied the effects are applied to the current
 * state.
 * Includes a toggle switch between detailed debigging output and minimal output
 */
public class Validate {

    private final Map<String, Anything> objectsMap;
    private final Preconditions preconditionChecker;
    private final boolean detailedOutput;

    public Validate(Map<String, Anything> objectsMap, boolean detailedOutput) {
        this.objectsMap = objectsMap;
        this.preconditionChecker = new Preconditions(objectsMap);
        this.detailedOutput = detailedOutput;
    }

    public static class ValidationResult {
        public final boolean isValid;
        public final List<String> log;
        public final String failureReason;
        public final int failureStep;
        public final WorldState finalState;

        /**
         * Constructor for ValidationResult
         * 
         * @param isValid       True if the plan is valid, false otherwise
         * @param log           List of strings containing the log of the validation
         * @param failureReason String containing the reason for the failure
         * @param failureStep   Integer containing the step where the failure occurred
         * @param finalState    WorldState containing the final state of the world
         */
        public ValidationResult(boolean isValid, List<String> log, String failureReason, int failureStep,
                WorldState finalState) {
            this.isValid = isValid;
            this.log = log;
            this.failureReason = failureReason;
            this.failureStep = failureStep;
            this.finalState = finalState;
        }
    }

    /**
     * Validates a plan by executing each action sequentially.
     * Checks the preconditions before each action and applies the effects after.
     *
     * @param initialState The initial state of the world
     * @param plan         The list of actions to validate
     * @return ValidationResult with the results of the validation
     */
    public ValidationResult validate(WorldState initialState, List<Action> plan) {
        List<String> log = new ArrayList<>();
        WorldState currentState = initialState.copyState();
        Map<String, String> connections = emptyMap();

        if (detailedOutput) {
            log.add("=== Starting Plan Validation ===");
            log.add("Total actions in plan: " + plan.size());
            log.add("");
        }

        for (int i = 0; i < plan.size(); i++) {
            Action action = plan.get(i);
            String actionStr = "(" + action.getName() + " " + String.join(" ", action.getParameters()) + ")";

            if (detailedOutput) {
                log.add("Step " + (i + 1) + ": " + actionStr);
            }

            // Checks preconditions
            if (!preconditionChecker.checkPreconditions(action, currentState, connections)) {
                String reason = "Preconditions failed for action: " + actionStr;
                log.add("FAILED - " + reason);

                if (this.detailedOutput) {
                    log.add("");
                    log.add("Validation FAILED at step " + (i + 1));
                }

                return new ValidationResult(false, log, reason, i + 1, currentState);
            }

            if (detailedOutput) {
                log.add("   Preconditions satisfied");
            }

            // Applies effects
            Effects effectsApplier = new Effects(action.getEffects(), objectsMap);
            effectsApplier.applyEffects(currentState, action.getEffects(), connections);
            if (detailedOutput) {
                log.add("   Effects applied");
                log.add("");
            }
        }

        if (detailedOutput) {
            log.add("Validation SUCCESSFUL");
            log.add("All " + plan.size() + " actions executed successfully.");
        }

        return new ValidationResult(true, log, null, -1, currentState);
    }

    // Prints the result of the validation
    public static void printValidationResult(ValidationResult result) {

        for (String line : result.log) {
            System.out.println(line);
        }

        if (!result.isValid) {
            System.out.println("\n Plan is INVALID!");
            System.out.println("Failure at step: " + result.failureStep);
        } else {
            System.out.println("\n Plan is VALID!");
        }
    }
}
