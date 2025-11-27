package barman;
import java.util.List;

/* Αυτή η κλάση αποτυπώνει τις methods του domain. Μια method μπορεί να εκτελεστεί αν ισχύουν όλα τα
 preconditions της. Έχει κάποιο Task το οποίο "εκπληρώνει" με την εκτέλεση της */

public abstract class Method {

    //private final για να είναι αμετάβλητα έχοντας επίσης πλήρη ενθυλάκωση
    private final String name;
    private final List<String> parameters;
    private final List<String> preconditions;
    private final List<Task> subtasks;
    private final Task taskToDecompose;

    public Method(String name, List<String> parameters, List<String> preconditions, List<Task> subtasks, Task taskToDecompose){

        //Αρχικοποίηση πεδίων με τις παραμέτρους του constructor
        this.name = name;
        this.parameters = parameters;
        this.preconditions = preconditions;
        this.subtasks = subtasks;
        this.taskToDecompose = taskToDecompose;
    }

    //Μέθοδοι πρόσβασης - Getters
    public String getName(){
        return name;
    }

    public List<String> getParameters(){
        return parameters;
    }

    public List<String> getPreconditions(){
        return preconditions;
    }

    public List<Task> getSubtasks(){
        return subtasks;
    }

    public Task getTaskToDecompose(){
        return taskToDecompose;
    }

}
