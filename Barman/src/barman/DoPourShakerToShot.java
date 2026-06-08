package barman;

import java.util.*;
import barman.Types.*;

/**
 * The DoPourShakerToShot class is a task that pours the contents of a shaker
 * into a shot glass.
 */
public class DoPourShakerToShot extends Task {

    private final Shaker shaker;
    private final Shot shot;
    private final Cocktail cocktail;

    /**
     * Initializes the DoPourShakerToShot task with the given shaker, shot, and cocktail.
     * @param shaker   The shaker to pour from
     * @param shot     The shot glass to pour into
     * @param cocktail The cocktail to pour
     */
    public DoPourShakerToShot(Shaker shaker, Shot shot, Cocktail cocktail) {
        super("DoPourShakerToShot", List.of(shaker.getName(), shot.getName(), cocktail.getName()));
        this.shaker = shaker;
        this.shot = shot;
        this.cocktail = cocktail;
    }

    // Generates all possible methods
    @Override
    protected void generateMethods() {
        DomainHelper helper = DomainHelper.getHelper();
        List<Hand> allHands = helper.getAllHands();
        List<Level> allLevels = helper.getAllItems(Level.class);

        // The method pour_shaker_to_shot_action requires a shaker, a shot, a cocktail, two levels and a hand
        for (Level level1 : allLevels) {
            Level level2 = null;
            if (level1.getName().equals("level1")) {
                for (Level level : allLevels) {
                    if (level.getName().equals("level2")) {
                        level2 = level;
                        break;
                    }
                }
            } else if (level1.getName().equals("level2")) {
                for (Level level : allLevels) {
                    if (level.getName().equals("level3")) {
                        level2 = level;
                        break;
                    }
                }
            }

            if (level2 != null) {
                for (Hand hand : allHands) {
                    this.addMethod(new pour_shaker_to_shot_action(shaker, shot, cocktail, level2, hand, level1, this));
                }
            }
        }
    }
}
