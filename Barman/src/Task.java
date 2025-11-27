package barman;
import java.util.ArrayList;
import java.util.List;

/* Αυτή η κλάση αποτυπώνει τα tasks του domain. Ένα task δεν μπορεί να εκτελεστεί
απευθείας, χρειάζεται να αποσυντεθεί σε subtasks - methods/actions */

public abstract class Task{

    //Protected fields για να έχουν πρόσβαση οι υποκλάσεις (Action) και final για να είναι αμετάβλητα
    protected final String name;
    protected final List<String> parameters;
    //Private field για τη λίστα μεθόδων που μπορούν να αποσυνθέσουν αυτό το task
    private final List<Method> methods;

    public Task(String name, List<String> parameters) {
        //Αρχικοποίηση βασικών πεδίων
        this.name = name;
        this.parameters = parameters;
        this.methods = new ArrayList<>();
    }

    //Μέθοδοι πρόσβασης - Getters
    public String getName(){
        return name;
    }

    public List<String> getParameters(){
        return parameters;
    }

    public List<Method> getMethods(){
        return methods;
    }

    //Προσθήκη method για την αποσύνθεση του task
    public void addMethod(Method method){
        this.methods.add(method);
    }
}
