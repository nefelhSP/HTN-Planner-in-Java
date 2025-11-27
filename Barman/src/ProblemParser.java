package barman;
import barman.Types.*;
import barman.WorldState.*;
import java.io.File;
import java.util.*;

/* Διαβάζει το αρχείο προβλήματος pddl και:
1)Εντοπίζει τα αντικέιμενα του προβλήματος από το πεδίο :objects και τα δημιουργεί σε Java
2) Διαβάζει το πεδίο :init και ενημερώνει την κλάση WorldState για την αρχική κατάσταση */

public class ProblemParser {

    //Δήλωση εδώ για να είναι ορατές και στις μεθόδους
    private WorldState initialState;
    private List<String> goalTasks;
    public Map<String, Anything> objectsMap;

    public ProblemParser(String filepath){

        //Αρχικοποιήσεις
        this.initialState = new WorldState();
        this.objectsMap = new HashMap<>();
        this.goalTasks = new ArrayList<>();
        String currentSection = "none";
        boolean HTN = false;

        File myProblemFile = new File(filepath);

        //Ανάγνωση αρχείου
        try (Scanner myReader = new Scanner(myProblemFile)) {
            //Εντός της while ελέγχω τα sections και κατανέμω τις γραμμές βάσει αυτών
            while (myReader.hasNextLine()) {

                String line = myReader.nextLine();
                line = line.trim();
                //System.out.println(line); Για λόγους ελέγχου

                //Ανίχνευση section με βάση την αρχή της γραμμής
                if (line.startsWith("(:objects")) {
                    currentSection = "objects";
                    HTN = false;
                } else if (line.startsWith("(:htn")) {
                    currentSection = "htn";
                } else if (line.startsWith("(:init")) {
                    currentSection = "init";
                    HTN = false;
                } else if (line.equals(")")) {
                    currentSection = "none";
                    HTN = false;
                    continue;
                }

                //Οργάνωση σε section με χρήση βοηθητικών μεθόδων για κάθε κατηγορία
                switch(currentSection){
                    case "objects":
                        ObjectLine(line);
                        break;
                    case "htn":
                        if (line.startsWith(":ordered-subtasks")) {
                            HTN = true;
                        }else if (HTN == true && line.startsWith("(") && !line.startsWith("(and")){
                            String taskLine = line.replaceAll("[()]","").trim();
                            if (!taskLine.isEmpty()){
                                this.goalTasks.add(taskLine);
                            }
                        }
                        break;
                    case "init":
                        InitLine(line);
                        break;
                }

            }
        } catch (Exception e) {
            System.out.println("File couldn't be parsed");
            e.printStackTrace();
        }
    }

    //Getters για να έχω πρόσβαση στα αποτελέσματα
    public WorldState getInitialState(){

        return this.initialState;
    }

    public List<String> getGoalTasks(){

        return this.goalTasks;
    }

    public Map<String, Anything> getObjects(){

        return this.objectsMap;
    }



    //Μέθοδος επεξεργασίας γραμμών από το πεδίο objects
    public void ObjectLine(String line){
        //Δεν επεξεργάζομαι τη γραμμή που αρχίζει με τη δήλωση του section
        if (line.startsWith("(:objects")){ return;}
        //Χωρίζω τη γραμμή σε δύο μέρη με βάση την πάυλα -
        String[] parts = line.split("\\s+-\\s+");
        if (parts.length < 2){ return;} //Αν δεν καταφέρω να χωρίσω στα δύο τη γραμμή, κάτι πήγε λάθος
        //Το πρώτο μέλος περιέχει τα object, τα οποία ξαναχωρίζω (αν είναι πολλά) με βάση το κενό
        String[] objectNames = parts[0].split("\\s+");
        //Το δεύτερο μέλος είναι ο τύπος, αφαιρώ τις παρενθέσεις
        String type = parts[1].trim();
        /*Έχω loop περνώντας τα ονόματα, σε κάθε όνομα ελέγχω τον τύπο και δημιουργώ ένα object και
            έπειτα το εισάγω στο Map, το key είναι το name και το value είναι το type */
        for (String name : objectNames){
            if (name.isEmpty()) continue;

            switch(type){
                case "hand":
                    Hand hand = new Hand(name);
                    objectsMap.put(name, hand);
                    break;
                case "shaker":
                    Shaker shaker = new Shaker(name);
                    objectsMap.put(name, shaker);
                    break;
                case "shot":
                    Shot shot = new Shot(name);
                    objectsMap.put(name, shot);
                    break;
                case "ingredient":
                    Ingredient ingredient = new Ingredient(name);
                    objectsMap.put(name, ingredient);
                    break;
                case "cocktail":
                    Cocktail cocktail = new Cocktail(name);
                    objectsMap.put(name, cocktail);
                    break;
                case "dispenser":
                    Dispenser dispenser = new Dispenser(name, null);
                    objectsMap.put(name, dispenser);
                    break;
                case "level":
                    Level level = new Level(name);
                    objectsMap.put(name, level);
                    break;
            }
        }
    }

    //Μέθοδος επεξεργασίας γραμμών από το πεδίο init
    public void InitLine(String line) throws Exception {
        //Καθαρίζω από κενά στην αρχή και στο τέλος τηε γραμμής
        line = line.trim();
        //Δεν επεξεργάζομαι τη γραμμή που αρχίζει με τη δήλωση του section ή παρενθέσεις
        if (line.startsWith("(:init")) {return;}
        /* Αφαιρώ τις παρενθέσεις, οι οποίες αποτελούν τον πρώτο και τελευταίο χαρακτήρα
        της γραμμής, για να προχωρήσω στην κατανομή σε Array */
        String content = line.substring(1, line.length()-1);
        //Δηλώνω ένα Array of Strings και κρατάω τα κομμάτια που προκύπτουν από το σπάσιμο
        //Το \\s+ σημαίνει ότι χωρίζει με βάσει τα κενά διαστήματα στη σειρά
        String[] parts = content.split("\\s+");
        //Αναγνωρίζω την πρώτη λέξη, αφού είναι το predicate
        String predicate = parts[0];
        //Με βάση το predicate προσθέτω στην κατάλληλη δομή το object μου
        switch (predicate) {
            case "ontable":
                initialState.onTable.add(getContainer(parts[1]));
                break;
            case "clean":
                initialState.clean.add(getContainer(parts[1]));
                break;
            case "empty":
                initialState.empty.add(getContainer(parts[1]));
                break;
            case "handEmpty":
                initialState.handEmpty.add(getHand(parts[1]));
                break;
            case "shakerLevel":
                initialState.shakerLevel.put(getShaker(parts[1]), getLevel(parts[2]));
                break;
            case "dispenses":
                initialState.dispenses.put(getDispenser(parts[1]), getIngredient(parts[2]));
                break;
            case "next":
                initialState.next.put(getLevel(parts[1]), getLevel(parts[2]));
                break;
            case "shakerEmptyLevel":
                initialState.shakerEmptyLevel.put(getShaker(parts[1]), getLevel(parts[2]));
                break;
            case "cocktailPart1":
                initialState.cocktailPart1.put(getCocktail(parts[1]), getIngredient(parts[2]));
                break;
            case "cocktailPart2":
                initialState.cocktailPart2.put(getCocktail(parts[1]), getIngredient(parts[2]));
                break;
        }
    }

    //Μέθοδοι που εξασφαλίζουν την ορθότητα των τύπων
    /*Ακολουθούν όλες την ίδια δομή
    1)Παίρνω το object από το map
    2)Ελέγχω αν υπάρχει το object
    3)Ελέγχω τον τύπο του object
    4)Αν είναι σωστό, το επιστρέφω
    5)Αν όχι, εμφανίζω error */

    private Container getContainer(String name){
        Anything obj = objectsMap.get(name);
        if (obj == null){
            throw new IllegalArgumentException("Parsing Error");
        }
        if (obj instanceof Container) {
            return (Container) obj;
        }
        throw new IllegalArgumentException("Parsing Error");
    }

    private Hand getHand(String name) {
        Anything obj = objectsMap.get(name);
        if (obj == null){
            throw new IllegalArgumentException("Parsing Error");
        }
        if (obj instanceof Hand){
            return (Hand) obj;
        }
        throw new IllegalArgumentException("Parsing Error");
    }

    public Level getLevel(String name) throws Exception{
        Anything obj = objectsMap.get(name);
        if (obj == null){
            throw new IllegalArgumentException("Parsing Error");
        }
        if (obj instanceof Level){
            return (Level) obj;}
        throw new IllegalArgumentException("Parsing Error");
    }

    private Shaker getShaker(String name) {
        Anything obj = objectsMap.get(name);
        if (obj == null){
            throw new IllegalArgumentException("Parsing Error");
        }
        if (obj instanceof Shaker){
            return (Shaker) obj;
        }
        throw new IllegalArgumentException("Parsing Error");
    }

    private Cocktail getCocktail(String name) {
        Anything obj = objectsMap.get(name);
        if (obj == null){
            throw new IllegalArgumentException("Parsing Error");
        }
        if (obj instanceof Cocktail){
            return (Cocktail) obj;
        }
        throw new IllegalArgumentException("Parsing Error");
    }

    private Ingredient getIngredient(String name) {
        Anything obj = objectsMap.get(name);
        if (obj == null){
            throw new IllegalArgumentException("Parsing Error");
        }
        if (obj instanceof Ingredient){
            return (Ingredient) obj;
        }
        throw new IllegalArgumentException("Parsing Error");
    }

    private Dispenser getDispenser(String name) {
        Anything obj = objectsMap.get(name);
        if (obj == null) {
        throw new IllegalArgumentException("Parsing Error");}
        if (obj instanceof Dispenser){
            return (Dispenser) obj;}
        throw new IllegalArgumentException("Parsing Error");
    }

    public void printGoal(){
        System.out.println("          Task/Tasks to decompose: ");
        for (String task : this.goalTasks) {
            System.out.println("  - " + task);
        }
    }
}
