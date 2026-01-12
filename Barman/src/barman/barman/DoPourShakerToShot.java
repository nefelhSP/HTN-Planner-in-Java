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
     * Initializes the DoPourShakerToShot task with the given shaker, shot, and
     * cocktail.
     * 
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

    /**
     * Generates all possible methods for the DoPourShakerToShot task.
     * 
     * @throws Exception if an error occurs during method generation
     */
    @Override
    protected void generateMethods() {
        DomainHelper helper = DomainHelper.getHelper();

        List<Hand> allHands = helper.getAllHands();
        allHands.sort((hand1, hand2) -> hand1.getName().compareTo(hand2.getName())); // Sort ascending (left > right)
        List<Level> allLevels = helper.getAllLevels();

        // The method pour_shaker_to_shot_action requires a shaker, a shot, a cocktail,
        // two levels and a hand
        for (Hand hand : allHands) {
            for (Level level1 : allLevels) {
                for (Level level2 : allLevels) {
                    this.addMethod(new pour_shaker_to_shot_action(shaker, shot, cocktail, level2, hand, level1, this));
                }
            }
        }
    }
}
