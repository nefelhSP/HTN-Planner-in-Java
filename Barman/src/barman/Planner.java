package barman;

import barman.Types.Anything;
import java.util.*;

/**
 * Planner class that implements the Depth-First Search (DFS) algorithm to find
 * a plan.
 */

public class Planner {

    // Setting safety limits based on my computer's capabilities
    private static final int MaxNodes = 10000;
    private static final int MaxDepth = 40;

    /**
     * @param objectsMap          A map of all objects in the world
     * @param preconditionChecker A preconditions checker object to verift if
     *                            actions can be done
     * @param nodeCount           The number of nodes visited
     */
    private final Map<String, Anything> objectsMap;
    private final Preconditions preconditionChecker;
    private int nodeCount;

    /**
     * Constructor Planner
     * 
     * @param objectsMap A map of all objects in the world
     */
    public Planner(Map<String, Anything> objectsMap) {
        this.objectsMap = objectsMap;
        this.preconditionChecker = new Preconditions(objectsMap);
    }

    /**
     * @param plan       The plan found, an ordered list that only contains
     *                   primitive actions
     * @param agenda     The agenda of the plan, an ordered list that contains
     *                   actions and methods
     * @param finalState The current state after the execution of the plan
     */
    public static class Result {
        public final List<Action> plan;
        public final List<String> agenda;
        public final WorldState finalState;

        public Result(List<Action> plan, List<String> agenda, WorldState finalState) {
            this.plan = plan;
            this.agenda = agenda;
            this.finalState = finalState;
        }
    }

    /**
     * Finds a plan using Depth-First Search (DFS)
     * 
     * @param initialState The initial state of the world
     * @param goalTasks    The list of tasks to be decomposed
     */
    public Result planFinder(WorldState initialState, List<Task> goalTasks) {
        nodeCount = 0;
        List<String> agenda = new ArrayList<>();
        List<Action> plan = new ArrayList<>();

        WorldState resultState = dfs(initialState, new ArrayList<>(goalTasks), plan, agenda, 0);

        if (resultState != null) {
            return new Result(plan, agenda, resultState);
        }

        System.err.println("No plan found after " + nodeCount + " nodes.");
        return null;
    }

    /**
     * @param state  The current state of the world
     * @param tasks  The list of tasks to be decomposed
     * @param plan   The plan found, an ordered list that only contains primitive
     *               actions
     * @param agenda The agenda of the plan, an ordered list that contains actions
     *               and methods
     * @param depth  The current depth of the search
     */
    private WorldState dfs(WorldState state, List<Task> tasks, List<Action> plan, List<String> agenda, int depth) {
        // Checking the Limits
        if (++nodeCount > MaxNodes) {
            // System.out.println("Max nodes limit reached");
            return null;
        }
        if (depth > MaxDepth) {
            // System.out.println("Max depth limit reached");
            return null;
        }

        // If i do not have any tasks left to decompose, then i have found a plan
        if (tasks.isEmpty()) {
            return state;
        }

        // First task
        Task task = tasks.get(0);
        List<Task> remainingTasks = new ArrayList<>(tasks.subList(1, tasks.size()));

        // If the task is an action (primitive)
        if (task instanceof Action) {
            Action action = (Action) task;
            Map<String, String> connections = new HashMap<>();

            if (preconditionChecker.checkPreconditions(action, state, connections)) {
                // Apply effects to a copy of the currentState
                WorldState nextState = state.copyState();
                Effects effectsApplier = new Effects(action.getEffects(), objectsMap);
                effectsApplier.applyEffects(nextState, action.getEffects(), connections);

                // Add action to the plan
                plan.add(action);
                agenda.add("(" + action.getName() + " " + String.join(" ", action.getParameters()) + ")");

                // Continue with remaining tasks
                WorldState result = dfs(nextState, remainingTasks, plan, agenda, depth + 1);

                if (result != null) {
                    return result; // Success
                }

                // Backtracking by undoing the changes
                plan.remove(plan.size() - 1);
                agenda.remove(agenda.size() - 1);
            }
            return null;
        }

        // If the task is a method (compound)
        List<Method> methods = task.getMethods();
        Map<String, String> connections = Collections.emptyMap();

        for (Method method : methods) {
            if (preconditionChecker.checkPreconditions(method, state, connections)) {
                // Add method to the agenda
                agenda.add("(" + method.getName() + " " + String.join(" ", method.getParameters()) + ")");

                // Decomposing by adding subtasks + remaining tasks
                List<Task> nextTasks = new ArrayList<>();
                nextTasks.addAll(method.getSubtasks());
                nextTasks.addAll(remainingTasks);

                // Recursively call dfs
                WorldState result = dfs(state.copyState(), nextTasks, plan, agenda, depth + 1);

                if (result != null) {
                    return result; // Success, found valid decomposition
                }

                // Backtracking by undoing the changes
                agenda.remove(agenda.size() - 1);
            }
        }

        return null; // Failure, no valid decomposition found
    }
}
