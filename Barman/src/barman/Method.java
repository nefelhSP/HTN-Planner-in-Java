package barman;
import java.util.List;
/**
 * The Method class is an abstract class that represents a method in the domain.
 * A method can be executed if all its preconditions are met. It has a Task that it "fulfills" with its execution.
 * It decomposes a task to subtasks, that may be tasks or actions.
 */
public abstract class Method implements hasPreconditions {

    /**
     * Initializes the Method with the given name, parameters, preconditions, subtasks, and task to decompose.
     * @param name            The name of the method
     * @param parameters      The parameters of the method
     * @param preconditions   The preconditions of the method
     * @param subtasks        The subtasks of the method
     * @param taskToDecompose The task to decompose
     */
    private final String name;
    private final List<String> parameters;
    private final List<List<String>> preconditions;
    private final List<Task> subtasks;
    private final Task taskToDecompose;

    public Method(String name, List<String> parameters, List<List<String>> preconditions, List<Task> subtasks,
            Task taskToDecompose) {

        // Initialize fields with constructor parameters
        this.name = name;
        this.parameters = parameters;
        this.preconditions = preconditions;
        this.subtasks = subtasks;
        this.taskToDecompose = taskToDecompose;
    }

    /**
     * Name Getter
     * @return The name of the method
     */
    public String getName() {
        return name;
    }

    /**
     * Parameter Getter
     * @return The parameters of the method
     */
    public List<String> getParameters() {
        return parameters;
    }

    /**
     * Precondition Getter
     * @return The preconditions of the method
     */
    public List<List<String>> getPreconditions() {
        return preconditions;
    }

    /**
     * Subtask Getter
     * @return The subtasks of the method
     */
    public List<Task> getSubtasks() {
        return subtasks;
    }

    /**
     * Task Getter
     * @return The task to decompose
     */
    public Task getTaskToDecompose() {
        return taskToDecompose;
    }

}
