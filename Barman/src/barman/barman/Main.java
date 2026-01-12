package barman;

import java.util.*;
import barman.Types.*;

/**
 * The Main class is the entry point of the program and initializes all
 * components.
 */
class Main {
    public static void main(String[] args) {

        String filepath = ("problemFiles/pfile20.hddl");
        long startTime = System.currentTimeMillis();

        ProblemParser myProblem = new ProblemParser(filepath);

        WorldState currentState = myProblem.getInitialState();

        System.out.println("============================================================");
        System.out.println("                      INITIAL STATE");
        System.out.println("============================================================");
        currentState.printInitState();

        // Retrieve data
        List<String> goalTaskStrings = myProblem.getGoalTasks();
        Map<String, Anything> objectsMap = myProblem.getObjects();
        DomainHelper domainHelper = new DomainHelper(objectsMap, currentState);

        // Parse all goal tasks
        List<Task> allGoalTasks = new ArrayList<>();

        System.out.println("\n============================================================");
        System.out.println("                   TASKS TO DECOMPOSE");
        System.out.println("============================================================");

        for (String goal : goalTaskStrings) {
            String[] parts = goal.split("\\s+");
            if (parts.length == 0)
                continue;

            String taskName = parts[0];
            String[] parameters = Arrays.copyOfRange(parts, 1, parts.length);
            Task task = Factory.createTask(taskName, parameters);

            if (task != null) {
                allGoalTasks.add(task);
                System.out.println("  " + allGoalTasks.size() + ". " + task.getName() + " " + task.getParameters());
            }
        }

        // Initialize components
        Planner planner = new Planner(objectsMap);
        Validate validator = new Validate(objectsMap, false);
        Cleanup cleanup = new Cleanup(objectsMap);

        // Track results for the final print
        List<Action> fullPlan = new ArrayList<>();
        List<String> fullAgenda = new ArrayList<>();
        Set<Shot> goalShots = new HashSet<>();
        boolean allSuccess = true;

        // Planning loop
        for (int i = 0; i < allGoalTasks.size(); i++) {
            Task goalTask = allGoalTasks.get(i);

            System.out.println("\n============================================================");
            System.out.println("  TASK " + (i + 1) + ": " + goalTask.getName() + " " + goalTask.getParameters());
            System.out.println("============================================================");

            // Find plan
            List<Task> singleTaskList = new ArrayList<>();
            singleTaskList.add(goalTask);
            Planner.Result result = planner.planFinder(currentState, singleTaskList);

            if (result == null) {
                System.out.println("\n  Failure, No plan found!");
                allSuccess = false;
                break;
            }

            // Print the full agenda
            /*
             * System.out.println("\n  --- Agenda ---");
             * for (String item : result.agenda) {
             * System.out.println("    " + item);
             * }
             * Too much output for the terminal to handle
             */

            // Print the plan
            System.out.println("\n  --- Plan (" + result.plan.size() + " actions) ---");
            for (Action action : result.plan) {
                System.out.println("    (" + action.getName() + " " + String.join(" ", action.getParameters()) + ")");
            }

            // Validate the plan and show results
            System.out.println("\n  --- Validation ---");
            Validate.ValidationResult validation = validator.validate(currentState, result.plan);
            Validate.printValidationResult(validation);

            if (!validation.isValid) {
                System.out.println("\n  Failure, Validation failed!");
                allSuccess = false;
                break;
            }

            // Store results
            fullAgenda.add("--- Task " + (i + 1) + ": " + goalTask.getName() + " ---");
            fullAgenda.addAll(result.agenda);
            fullPlan.addAll(result.plan);

            // Update state
            currentState = validation.finalState.copyState();

            // Track goal shots
            for (Map.Entry<Container, Set<Beverage>> entry : currentState.contains.entrySet()) {
                if (!entry.getValue().isEmpty() && entry.getKey() instanceof Shot) {
                    goalShots.add((Shot) entry.getKey());
                }
            }

            // Run the cleanup, except if it is the last task
            if (i < allGoalTasks.size() - 1) {
                List<Action> cleanupActions = cleanup.generateCleanupPlan(currentState, goalShots);

                if (!cleanupActions.isEmpty()) {
                    // Validate the cleanup
                    Validate.ValidationResult cleanupValidation = validator.validate(currentState, cleanupActions);

                    if (cleanupValidation.isValid) {
                        fullPlan.addAll(cleanupActions);
                        fullAgenda.add("--- CLEANUP PHASE ---");
                        for (Action a : cleanupActions) {
                            fullAgenda.add("(" + a.getName() + " " + String.join(" ", a.getParameters()) + ")");
                        }
                        currentState = cleanupValidation.finalState.copyState();

                        System.out.println("\n  --- Cleanup Phase (" + cleanupActions.size() + " actions) ---");
                        for (Action action : cleanupActions) {
                            System.out.println(
                                    "    (" + action.getName() + " " + String.join(" ", action.getParameters()) + ")");
                        }
                    } else {
                        System.out.println("\n  [WARNING] Cleanup failed at step " + cleanupValidation.failureStep);
                    }
                }
            }
        }

        long endTime = System.currentTimeMillis();
        double totalTimeSeconds = (endTime - startTime) / 1000.0;

        // Final results
        System.out.println("\n\n============================================================");
        System.out.println("                  FINAL RESULTS");
        System.out.println("============================================================");

        if (allSuccess) {
            // Full Agenda
            /*
             * System.out.println("\n--- FULL AGENDA ---");
             * for (String item : fullAgenda) {
             * System.out.println(item);
             * }
             * Huge output that can't fit to the terminal
             */

            // Full Plan
            System.out.println("\n--- FULL PLAN (" + fullPlan.size() + " actions) ---");
            for (int i = 0; i < fullPlan.size(); i++) {
                Action action = fullPlan.get(i);
                System.out.println(
                        (i + 1) + ". (" + action.getName() + " " + String.join(" ", action.getParameters()) + ")");
            }

            System.out.println("\nSuccess! All " + allGoalTasks.size() + " tasks completed!");
        } else {
            System.out.println("\nFailure! Planning incomplete.");
            System.out.println("Partial plan has " + fullPlan.size() + " actions.");
        }

        System.out.println("Total Execution Time: " + totalTimeSeconds + "sec");

    }
}