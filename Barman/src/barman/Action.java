package barman;

import java.util.List;

/* This abstract class represents the actions of the domain. An action can be executed if all 
preconditions are true and has predefined effects that change the currentState upon execution */

public abstract class Action extends Task {

    // private final variables to make them immutable and also have full
    // encapsulation
    private final List<List<String>> preconditions;
    private final List<String> effects;

    public Action(String name, List<String> parameters, List<List<String>> preconditions, List<String> effects) {
        // Call parent constructor and initialize fields
        super(name, parameters);
        this.preconditions = preconditions;
        this.effects = effects;
    }

    /**
     * Returns the name of the action.
     * 
     * @return The name of the action
     */
    public String getName() {

        return name;
    }

    /**
     * Returns the parameters of the action.
     * 
     * @return The parameters of the action
     */
    public List<String> getParameters() {

        return parameters;
    }

    /**
     * Returns the preconditions of the action.
     * 
     * @return The preconditions of the action
     */
    public List<List<String>> getPreconditions() {
        return preconditions;
    }

    /**
     * Returns the effects of the action.
     * 
     * @return The effects of the action
     */
    public List<String> getEffects() {
        return effects;
    }
}
