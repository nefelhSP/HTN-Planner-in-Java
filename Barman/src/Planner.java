package barman;
import java.util.*;
import barman.ProblemParser.*;
import barman.WorldState.*;
import barman.Types.*;

//Προφανώς, δεν αποτελεί λύση/κομμάτι λύσης, είναι απλά μία αρχή που είχα στο νου μου
/* Αυτή η κλάση θα είναι υπεύθυνη για τη λειτουργία του προγράμματος. Στην ουσία θα περιέχει τα στοιχεία
του domain καθώς επίσης και τη λογική της λύσης, εντός της θα υπάρξει το δέντρο, η κεντρική ιδέα της λύσης*/


public class Planner {

    private List<Method> allMethods;
    private List<Action> allActions;
    private Map<String, Anything> objectsMap;
    public List<Task> planFinder;

    public Planner(List<Method> methods, List<Action> actions, Map<String, Anything> objectsMap){
        this.allActions = actions;
        this.allMethods = methods;
        this.objectsMap = objectsMap;
    }

    public List<Action> planFinder(WorldState currentState, List<Task> tasksToDo){
        return solve(currentState, tasksToDo);
    }

    private List<Action> solve(WorldState currentState, List<Task> currentTasks) {

        // 1. Base Case: If no tasks remain, we found a plan!
        if (currentTasks.isEmpty()) {
            return new ArrayList<>();
        }

        // 2. Separate the Head (current task) from the Tail (remaining tasks)
        Task task = currentTasks.get(0);
        List<Task> remainingTasks = new ArrayList<>(currentTasks.subList(1, currentTasks.size()));

        // We use an empty map because your tasks use real names ("shot1"), not variables ("?x")
        Map<String, String> emptyConnections = Collections.emptyMap();
        Preconditions checker = new Preconditions(this.objectsMap);

        // 3. Case A: The Task is a Primitive Action
        if (task instanceof Action) {
            Action action = (Action) task;

            // Check if Action is applicable
            if (checker.checkPreconditions(action, currentState, emptyConnections)) {

                // Create a copy of the state (to allow backtracking)
                WorldState nextState = currentState.copyState();

                // Apply Effects to the new state
                Effects applier = new Effects(action.getEffects(), this.objectsMap);
                applier.applyEffects(nextState, action.getEffects(), emptyConnections);

                // Recurse deeper with the new state
                List<Action> result = solve(nextState, remainingTasks);

                // If a solution was found down the tree, append this action and return
                if (result != null) {
                    List<Action> fullPlan = new ArrayList<>();
                    fullPlan.add(action);
                    fullPlan.addAll(result);
                    return fullPlan;
                }
            }
        }

        // 4. Case B: The Task is a Compound Task (needs decomposition)
        else {
            // Try every method available for this task
            for (Method method : task.getMethods()) {

                // Check if Method is applicable
                if (checker.checkPreconditions(method, currentState, emptyConnections)) {

                    // Decompose: Create a new list = [Subtasks of Method] + [Remaining Tasks]
                    List<Task> newTaskList = new ArrayList<>(method.getSubtasks());
                    newTaskList.addAll(remainingTasks);

                    // Recurse with the SAME state, but broken-down tasks
                    List<Action> result = solve(currentState, newTaskList);

                    // If a solution was found, bubble it up
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        // 5. Backtracking: If no Action or Method worked, return null (Failure)
        return null;
    }
}
