package barman;
import java.util.List;

/* Αυτή η abstract κλάση αποτυπώνει τα actions του domain. Ένα action μπορεί να εκτελεστεί αν ισχύουν όλα τα
 preconditions και έχει προκαθορισμένα effects που αλλάζουν το currentState με την εκτέλεση του */

public abstract class Action extends Task{

    //private final για να είναι αμετάβλητα έχοντας επίσης πλήρη ενθυλάκωση
    private final List<String> preconditions;
    private final List<String> effects;

    public Action(String name, List<String> parameters, List<String> preconditions, List<String> effects) {
       //Κλήση parent constructor και αρχικοποίηση πεδίων
        super(name, parameters);
        this.preconditions = preconditions;
        this.effects = effects;
    }

    //Μέθοδοι πρόσβασης - Getters
    public String getName(){

        return name;
    }

    public List<String> getParameters(){

        return parameters;
    }

    public List<String> getPreconditions() {
        return preconditions;
    }

    public List<String> getEffects() {
        return effects;
    }
}

