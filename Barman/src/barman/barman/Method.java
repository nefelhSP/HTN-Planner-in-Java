package barman;

import java.util.List;

/**
 * The Method class is an abstract class that represents a method in the domain.
 * A method can be executed if all its preconditions are met. It has a Task that
 * it "fulfills" with its execution.
 */
public abstract class Method {

    /**
     * Initializes the Method with the given name, parameters, preconditions,
     * subtasks, and task to decompose.
     * 
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
     * Returns the name of the method.
     * 
     * @return The name of the method
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the parameters of the method.
     * 
     * @return The parameters of the method
     */
    public List<String> getParameters() {
        return parameters;
    }

    /**
     * Returns the preconditions of the method.
     * 
     * @return The preconditions of the method
     */
    public List<List<String>> getPreconditions() {
        return preconditions;
    }

    /**
     * Returns the subtasks of the method.
     * 
     * @return The subtasks of the method
     */
    public List<Task> getSubtasks() {
        return subtasks;
    }

    /**
     * Returns the task to decompose.
     * 
     * @return The task to decompose
     */
    public Task getTaskToDecompose() {
        return taskToDecompose;
    }

}
