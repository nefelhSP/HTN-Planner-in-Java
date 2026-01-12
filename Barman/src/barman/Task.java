package barman;

import java.util.*;

/**
 * The Task class is an abstract class that represents a task.
 * It is used to represent a task that needs to be decomposed into subtasks
 * (methods & actions).
 */
public abstract class Task {

    // Protected fields to allow subclasses (Actions) to access them and final for
    // immutability
    protected final String name;
    protected final List<String> parameters;
    // Private field to store the list of methods that can compose this task
    private List<Method> methods;
    private boolean methodsGenerated = false;

    /**
     * Initializes the Task with the given name and parameters.
     * 
     * @param name       The name of the task
     * @param parameters The parameters of the task
     */
    public Task(String name, List<String> parameters) {
        this.name = name;
        this.parameters = parameters;
        this.methods = null;
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<String> getParameters() {
        return parameters;
    }

    // Lazy method generation, it only creates methods when needed
    public List<Method> getMethods() {
        if (!methodsGenerated) {
            methods = new ArrayList<>();
            generateMethods();
            methodsGenerated = true;
        }
        return methods;
    }

    // Subclasses override this to generate their methods
    protected void generateMethods() {
    }

    // Add method for task decomposition
    public void addMethod(Method method) {
        if (methods == null) {
            methods = new ArrayList<>();
        }
        this.methods.add(method);
    }
}
