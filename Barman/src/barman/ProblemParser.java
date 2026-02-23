package barman;

import barman.Types.*;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * Parses PDDL problem files to initialize the world state and objects.
 * First, it reads the PDDL file and then has three distinct methods:
 * 1. parseObjectLine: Parses the objects section to create Java objects.
 * 2. parseHTNLine: Parses the htn section for task definitions.
 * 3. parseInitLine: Parses the init section to set up the initial world state.
 */
public class ProblemParser {

    private final WorldState initialState;
    private final List<String> goalTasks;
    public final Map<String, Anything> objectsMap;

    /**
     * Constructs the parser and processes the specified file.
     * @param filepath Path to the PDDL problem file.
     */
    public ProblemParser(String filepath) {
        this.initialState = new WorldState();
        this.objectsMap = new HashMap<>();
        this.goalTasks = new ArrayList<>();

        try {
            parseFile(filepath);
        } catch (Exception e) {
            System.err.println("Error reading problem file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public WorldState getInitialState() {
        return initialState;
    }

    public List<String> getGoalTasks() {
        return goalTasks;
    }

    public Map<String, Anything> getObjects() {
        return objectsMap;
    }

    /**
     * Reads and parses the PDDL file line by line.
     */
    private void parseFile(String filepath) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filepath));
        String currentSection = "none";
        boolean isHTN = false;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith(";")) {
                continue;
            }

            // Determine the current section
            if (line.startsWith("(:objects")) {
                currentSection = "objects";
                isHTN = false;
                continue;
            } else if (line.startsWith("(:htn")) {
                currentSection = "htn";
                isHTN = true; // Use boolean flag for complicated multiline logic
                continue;
            } else if (line.startsWith("(:init")) {
                currentSection = "init";
                isHTN = false;
                continue;
            } else if (line.equals(")")) {
                currentSection = "none";
                isHTN = false;
                continue;
            }

            // Process lines based on the active section
            switch (currentSection) {
                case "objects":
                    parseObjectLine(line);
                    break;
                case "htn":
                    parseHTNLine(line);
                    break;
                case "init":
                    parseInitLine(line);
                    break;
            }
        }
    }

    /**
     * Parses a line from the (:objects) section.
     * Its format is name1 name2 - type
     */
    private void parseObjectLine(String line) {
        if (!line.contains("-")) {
            return;
        }

        String[] parts = line.split("\\s+-\\s+");
        //Error message if I have less than two sections after splitting the line based on the - separation
        if (parts.length < 2) {
            System.err.println("Invalid object definition: " + line);
            return;
        }

        // Splitting the first section to find distinct objects
        String[] names = parts[0].trim().split("\\s+");

        String type = parts[1].trim();

        // Creating objects based on the previous section split
        for (String name : names) {
            if (name.isEmpty()){
                continue;
            }
            createObject(name, type);
        }
    }

    /**
     * Creates an object based on its type and adds it to the map
     */
    private void createObject(String name, String type) {
        switch (type) {
            case "hand":
                objectsMap.put(name, new Hand(name));
                break;
            case "shaker":
                objectsMap.put(name, new Shaker(name));
                break;
            case "shot":
                objectsMap.put(name, new Shot(name));
                break;
            case "ingredient":
                objectsMap.put(name, new Ingredient(name));
                break;
            case "cocktail":
                objectsMap.put(name, new Cocktail(name));
                break;
            case "dispenser":
                objectsMap.put(name, new Dispenser(name, null));
                break;
            case "level":
                objectsMap.put(name, new Level(name));
                break;
            default:
                System.err.println("Unknown type: " + type + " for object " + name);
        }
    }

    /**
     * Parses a line from the (:htn) section.
     */
    private void parseHTNLine(String line) {
        if (line.startsWith(":parameters") || line.startsWith(":ordered-subtasks") || line.equals("(:htn")) {
            return;
        }

        // Extract the tasks to be decomposed
        if (line.startsWith("(") && !line.contains("(and")) {
            String taskDefinition = line.replaceAll("[()]", "").trim();
            if (!taskDefinition.isEmpty()) {
                goalTasks.add(taskDefinition);
            }
        }
    }

    /**
     * Parses a line from the (:init) section.
     * Its format is (predicate param1 param2)
     */
    private void parseInitLine(String line) {
        if (line.startsWith("(:init") || line.equals(")")) {
            return;
        }

        // Remove the first and last character (parenthesis) to have clean line
        String content = line.trim();
        if (content.startsWith("(") && content.endsWith(")")) {
            content = content.substring(1, content.length() - 1);
        }

        //Splitting into sections based on " ".
        String[] parts = content.split("\\s+");
        //If I do not have distinct sections, then return
        if (parts.length == 0)
            return;

        // Assign the first part to the predicate
        String predicate = parts[0];

        //Adds the objects to the correct predicate set/map to form the initial state
        try {
            switch (predicate) {
                case "ontable":
                    initialState.onTable.add(getObject(parts[1], Container.class));
                    break;
                case "clean":
                    initialState.clean.add(getObject(parts[1], Container.class));
                    break;
                case "empty":
                    initialState.empty.add(getObject(parts[1], Container.class));
                    break;
                case "handEmpty":
                    initialState.handEmpty.add(getObject(parts[1], Hand.class));
                    break;
                case "shakerLevel":
                    initialState.shakerLevel.put(getObject(parts[1], Shaker.class), getObject(parts[2], Level.class));
                    break;
                case "dispenses":
                    initialState.dispenses.put(getObject(parts[1], Dispenser.class), getObject(parts[2], Ingredient.class));
                    break;
                case "next":
                    initialState.next.put(getObject(parts[1], Level.class), getObject(parts[2], Level.class));
                    break;
                case "shakerEmptyLevel":
                    initialState.shakerEmptyLevel.put(getObject(parts[1], Shaker.class), getObject(parts[2], Level.class));
                    break;
                case "cocktailPart1":
                    initialState.cocktailPart1.put(getObject(parts[1], Cocktail.class), getObject(parts[2], Ingredient.class));
                    break;
                case "cocktailPart2":
                    initialState.cocktailPart2.put(getObject(parts[1], Cocktail.class), getObject(parts[2], Ingredient.class));
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error processing init line: " + line + " -> " + e.getMessage());
        }
    }

    /**
     * Local helper that retrieves a typed object from the map
     */
    private <Object extends Anything> Object getObject(String name, Class<Object> type) {
        Anything obj = objectsMap.get(name);
        if (obj == null) {
            throw new IllegalArgumentException("Object not found: " + name);
        }
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException("Type mismatch for " + name);
        }
        return type.cast(obj);
    }
}
