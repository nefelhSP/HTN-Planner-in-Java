package barman;

import java.util.*;
import barman.Types.*;

/**
 * The Main class is the entry point of the program and initializes all
 * components.
 */
class Main {
    public static void main(String[] args) {

        // Creating scanner object to get user input
        Scanner scanner = new Scanner(System.in);
        int number = 0;
        String filepath = "";
        boolean validInput = false; // Flag to control the while loop

        while (!validInput) {
            System.out.println("Enter the number of the problem file you want to solve (1-20): ");
            // Checking if the input is an integer
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();

                // Checking if the input is within the range of 1-20
                if (number >= 1 && number <= 20) {
                    if ((number >= 1 && number <= 9)) {
                        filepath = "problemFiles/pfile0" + number + ".hddl";
                    } else {
                        filepath = "problemFiles/pfile" + number + ".hddl";
                    }
                    validInput = true;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 20.");
                }
            } else {
                // Handle non-numeric input
                System.out.println("That's not a number. Please enter a digit.");
                scanner.next();
            }
        }

        // Starting the timer
        long startTime = System.currentTimeMillis();

        // Parsing the problem through ProblemParser and getting the proper information
        ProblemParser myProblem = new ProblemParser(filepath);

        // Assigning the currentState based on the information I got through
        // ProblemParser
        WorldState currentState = myProblem.getInitialState();
        // Printing the initial stat (mostly for debugging reasons)
        currentState.printInitState();

        // Retrieving data
        List<String> goalTaskStrings = myProblem.getGoalTasks();
        Map<String, Anything> objectsMap = myProblem.getObjects();
        DomainHelper helper = new DomainHelper(objectsMap, currentState);

        // Parsing all the tasks to be decomposed
        List<Task> goalTasks = new ArrayList<>();

        System.out.println("\n============================================================");
        System.out.println("                   TASKS TO DECOMPOSE");
        System.out.println("============================================================");

        for (String goal : goalTaskStrings) {
            String[] parts = goal.split("\\s+");
            if (parts.length == 0)
                continue;

            /*
             * Assigning the taskName and the parameters based on the following formatting:
             * | AchieveContainsShotCocktail | shotX | cocktailX |
             * parts[0] parts[1] parts[2]
             */
            String taskName = parts[0];
            String[] parameters = Arrays.copyOfRange(parts, 1, parts.length);
            Task task = Factory.createTask(taskName, parameters);

            // Adding the Task, to be decomposed, to the goalTasks list and printing it
            if (task != null) {
                goalTasks.add(task);
                System.out.println("  " + goalTasks.size() + ". " + task.getName() + " " + task.getParameters());
            }
        }

        // Initialize components
        Planner planner = new Planner();
        Validate validator = new Validate(false);
        Cleanup cleanup = new Cleanup();

        // Track results for the final print
        List<Action> fullPlan = new ArrayList<>(); // Only contains actions
        List<String> fullAgenda = new ArrayList<>(); // Contains everystep
        Set<Shot> goalShots = new HashSet<>();
        boolean allSuccess = true;

        // Planning loop
        for (int i = 0; i < goalTasks.size(); i++) {
            Task goalTask = goalTasks.get(i);

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

            System.out.println("\n           Plan (Decomposed into " + result.plan.size() + " actions)\n");
            for (Action action : result.plan) {
                System.out.println("    (" + action.getName() + " " + String.join(" ", action.getParameters()) + ")");
            }

            // Validate the plan and show results
            System.out.println("\n\n============================================================");
            System.out.println("                      VALIDATION PHASE");
            System.out.println("============================================================");
            Validate.ValidationResult validation = validator.validate(currentState, result.plan);
            Validate.printValidationResult(validation);

            if (!validation.isValid) {
                System.out.println("\n XXX  FAILURE! Validation failed!  XXX");
                allSuccess = false;
                break;
            }

            // Store results
            fullAgenda.add("\n  Task " + (i + 1) + ": " + goalTask.getName());
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

            // Running the cleanup phase, except if the current task is the last to be decomposed
            if (i < goalTasks.size() - 1) {
                List<Action> cleanupActions = cleanup.generateCleanupPlan(currentState, goalShots);

                if (!cleanupActions.isEmpty()) {

                    // Validate the cleanup
                    Validate.ValidationResult cleanupValidation = validator.validate(currentState, cleanupActions);

                    if (cleanupValidation.isValid) {
                        fullPlan.addAll(cleanupActions);
                        fullAgenda.add("\n--- CLEANUP PHASE ---");
                        for (Action a : cleanupActions) {
                            fullAgenda.add("(" + a.getName() + " " + String.join(" ", a.getParameters()) + ")");
                        }
                        currentState = cleanupValidation.finalState.copyState();

                        System.out.println("\n============================================================");
                        System.out.println("                 CLEANUP PHASE (" + cleanupActions.size() + " actions) ");
                        System.out.println("============================================================");
                        for (Action action : cleanupActions) {
                            System.out.println(
                                    "    (" + action.getName() + " " + String.join(" ", action.getParameters()) + ")");
                        }
                    } else {
                        System.out.println("\nCleanup failed at step " + cleanupValidation.failureStep);
                    }
                }
            }
        }

        // Printing the Final Results
        System.out.println("\n\n============================================================");
        System.out.println("                      FINAL RESULTS");
        System.out.println("============================================================");

        if (allSuccess) {

            // Printing the entire Agenda
            // Huge output that can't fit to the terminal for harder problems
            System.out.println("------------------------------------------------------------");
            System.out.println("                      FULL AGENDA");
            System.out.println("------------------------------------------------------------");
            for (String item : fullAgenda) {
                System.out.println(item);
            }

            // Printing the entire Plan
            System.out.println("\n------------------------------------------------------------");
            System.out.println("              FULL PLAN (Consisting of " + fullPlan.size() + " Actions)");
            System.out.println("------------------------------------------------------------");
            for (int i = 0; i < fullPlan.size(); i++) {
                Action action = fullPlan.get(i);
                System.out.println(
                        (i + 1) + ". (" + action.getName() + " " + String.join(" ", action.getParameters()) + ")");
            }

            // Printing the messages for the appropriate situation
            System.out.println("\n------------------------------------------------------------");
            System.out.println("              ALL " + goalTasks.size() + " TASKS COMPLETED SUCCESSFULLY!");
            System.out.println("------------------------------------------------------------");
        } else {
            System.out.println("\n------------------------------------------------------------");
            System.out.println("\n            FAILURE! PLANNING INCOMPLETE.");
            System.out.println("           PARTIAL PLAN CONSIST OF " + fullPlan.size() + " ACTIONS.");
            System.out.println("------------------------------------------------------------");
        }

        // Timing
        long endTime = System.currentTimeMillis();
        double totalTimeSeconds = (endTime - startTime) / 1000.0;
        System.out.println("------------------------------------------------------------");
        System.out.println("               TOTAL EXECUTION TIME: " + totalTimeSeconds + "sec");
        System.out.println("------------------------------------------------------------");

    }
}