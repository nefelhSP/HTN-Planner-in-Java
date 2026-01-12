package barman;

import barman.Types.*;
import java.util.*;
import java.io.File;

/* Reads the PDDL problem file and:
1) Identifies the objects of the problem from the :objects field and creates them in Java
2) Reads the :init field and updates the WorldState class for the initial state */

public class ProblemParser {

    // Declarations here so they are visible in the methods
    private WorldState initialState;
    private List<String> goalTasks;
    public Map<String, Anything> objectsMap;

    public ProblemParser(String filepath) {

        // Initializations
        this.initialState = new WorldState();
        this.objectsMap = new HashMap<>();
        this.goalTasks = new ArrayList<>();
        String currentSection = "none";
        boolean HTN = false;

        File myProblemFile = new File(filepath);

        // Reading the file
        try (Scanner myReader = new Scanner(myProblemFile)) {
            // Checking the sections inside the while loop
            while (myReader.hasNextLine()) {

                String line = myReader.nextLine();
                line = line.trim();
                // System.out.println(line); Debug print

                // Identifying section based on the start of the line
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

                // Organizing in sections using helper methods for each category
                switch (currentSection) {
                    case "objects":
                        ObjectLine(line);
                        break;
                    case "htn":
                        if (line.startsWith(":ordered-subtasks")) {
                            HTN = true;
                        } else if (HTN == true && line.startsWith("(") && !line.startsWith("(and")) {
                            String taskLine = line.replaceAll("[()]", "").trim();
                            if (!taskLine.isEmpty()) {
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

    // Getters for accessing the results
    public WorldState getInitialState() {

        return this.initialState;
    }

    public List<String> getGoalTasks() {

        return this.goalTasks;
    }

    public Map<String, Anything> getObjects() {

        return this.objectsMap;
    }

    // Method for parsing lines from the objects field
    public void ObjectLine(String line) {
        // I don't process the line if it starts with the section declaration
        if (line.startsWith("(:objects")) {
            return;
        }
        // Split the line into two parts based on the dash
        String[] parts = line.split("\\s+-\\s+");
        if (parts.length < 2) {
            return;
        } // If I can't split the line into two parts, something went wrong
          // The first part contains the objects, which I split (if there are multiple)
          // based on the space
        String[] objectNames = parts[0].split("\\s+");
        // The second part is the type, I remove the parentheses
        String type = parts[1].trim();
        /*
         * I have a loop passing the names, for each name I check the type and create an
         * object and
         * then I insert it into the Map, the key is the name and the value is the type
         */
        for (String name : objectNames) {
            if (name.isEmpty())
                continue;

            switch (type) {
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

    // Method for parsing lines from the init field
    public void InitLine(String line) throws Exception {
        // Remove spaces from the start and end of the line
        line = line.trim();
        // I don't process the line if it starts with the section declaration or
        // parentheses
        if (line.startsWith("(:init")) {
            return;
        }
        // Remove parentheses, which are the first and last character of the line, to
        // proceed to the array division
        String content = line.substring(1, line.length() - 1);
        // I declare an Array of Strings and keep the parts that result from the
        // division
        String[] parts = content.split("\\s+"); // it splits based on the spaces in the order
        // I recognize the first word, since it is the predicate
        String predicate = parts[0];
        // Based on the predicate I add the object to the appropriate structure
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
            case "contains":
                Container c = getContainer(parts[1]);
                Beverage b = getBeverage(parts[2]);
                initialState.contains.computeIfAbsent(c, k -> new HashSet<>()).add(b);
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

    // Methods that ensure the type's correctness
    /*
     * All have the same structure
     * 1)I get the object from the map
     * 2)I check if the object exists
     * 3)I check the type of the object
     * 4)If it is correct, I return it
     * 5)If not, I throw an error
     */

    private Container getContainer(String name) {
        Anything obj = objectsMap.get(name);
        if (obj instanceof Container) {
            return (Container) obj;
        }
        throw new IllegalArgumentException("Parsing Error");
    }

    private Hand getHand(String name) {
        Anything obj = objectsMap.get(name);
        if (obj instanceof Hand) {
            return (Hand) obj;
        }
        throw new IllegalArgumentException("Parsing Error");
    }

    public Level getLevel(String name) throws Exception {
        Anything obj = objectsMap.get(name);
        if (obj instanceof Level) {
            return (Level) obj;
        }
        throw new IllegalArgumentException("Parsing Error");
    }

    private Shaker getShaker(String name) {
        Anything obj = objectsMap.get(name);
        if (obj instanceof Shaker) {
            return (Shaker) obj;
        }
        throw new IllegalArgumentException("Parsing Error");
    }

    private Cocktail getCocktail(String name) {
        Anything obj = objectsMap.get(name);
        if (obj instanceof Cocktail) {
            return (Cocktail) obj;
        }
        throw new IllegalArgumentException("Parsing Error");
    }

    private Ingredient getIngredient(String name) {
        Anything obj = objectsMap.get(name);
        if (obj instanceof Ingredient) {
            return (Ingredient) obj;
        }
        throw new IllegalArgumentException("Parsing Error");
    }

    private Dispenser getDispenser(String name) {
        Anything obj = objectsMap.get(name);
        if (obj instanceof Dispenser) {
            return (Dispenser) obj;
        }
        throw new IllegalArgumentException("Parsing Error");
    }

    private Beverage getBeverage(String name) {
        Anything obj = objectsMap.get(name);
        if (obj instanceof Beverage) {
            return (Beverage) obj;
        }
        throw new IllegalArgumentException("Parsing Error");
    }
}
