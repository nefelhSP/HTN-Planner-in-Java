package barman;
import java.util.*;
import barman.Types.*;

// Κλάση Main, αποτελεί σημείο εισόδου του προγράμματος και αρχικοποιεί όλα τα components

class Main {
    public static void main(String[] args)
    {
        List<Method> allMethods = new ArrayList<>(); //Προς το παρόν δεν κάνει κάτι, για μελλοντική χρήση
        List<Action> allActions = new ArrayList<>(); //Προς το παρόν δεν κάνει κάτι, για μελλοντική χρήση

        String filepath = ("problem files/pfile02.hddl");
        ProblemParser myProblem = new ProblemParser(filepath);

        //Δημιουργία και αρχικοποίηση του currentState
        WorldState currentState = myProblem.getInitialState();

        //Προσωρινές εκτυπώσεις για έλεγχο
        currentState.printState();
        System.out.println("");
        myProblem.printGoal();

        //Ανάκτηση των σημαντικών στοιχείων για μελλοντική χρήση από τον planner
        List<String> goalTaskStrings = myProblem.getGoalTasks(); //Tasks προς αποσύνθεση
        List<Task> tasksToDo = new ArrayList<>();
        Map<String, Anything> objectsMap = myProblem.getObjects(); //Αντικείμενα του προβλήματος

        //Δημιουργία DomainHelper για διαχείριση αντικειμένων
        DomainHelper domainHelper = new DomainHelper(objectsMap, currentState);

        for (String goalStr : goalStrings) {
            // Convert String -> Task Object
            Task t = TaskParser.parseTask(goalStr);

            if (t != null) {
                tasksToDo.add(t);
            }
        }

        //Δημιουργία του Planner, δεν έχει προγραμματιστεί ακόμα για να εκτελεί κάποια ενέργεια
        Planner planner = new Planner(allMethods, allActions, objectsMap);
        planner.planFinder(currentState, tasksToDo);
    }
}