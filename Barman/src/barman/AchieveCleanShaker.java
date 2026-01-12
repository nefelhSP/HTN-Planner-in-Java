package barman;

import barman.Types.*;
import java.util.List;

/**
 * AchieveCleanShaker class that implements the Task interface.
 * It represents the task: AchieveCleanShaker
 */
public class AchieveCleanShaker extends Task {

    private final Shaker shaker;

    /**
     * Constructor for AchieveCleanShaker
     * 
     * @param shaker The shaker to be cleaned
     */
    public AchieveCleanShaker(Shaker shaker) {
        super("AchieveCleanShaker", List.of(shaker.getName()));
        this.shaker = shaker;
    }

    // Generates the methods for the task
    @Override
    protected void generateMethods() {
        DomainHelper helper = DomainHelper.getHelper();

        // Get all candidate objects
        List<Hand> allHands = helper.getAllHands();
        List<Level> allLevels = helper.getAllLevels();
        List<Cocktail> allCocktails = helper.getAllCocktails();

        // The method CleanShakerNull requires a shaker
        this.addMethod(new CleanShakerNull(shaker, this));

        // The method CleanEmptyShaker requires two hands and a shaker
        for (Hand hand1 : allHands) {
            for (Hand hand2 : allHands) {
                if (hand1.equals(hand2))
                    continue;
                this.addMethod(new CleanEmptyShaker(shaker, hand1, hand2, this));
            }
        }

        // The method CleanFullShaker requires two hands, two levels, a shaker and a
        // cocktail
        for (Hand hand1 : allHands) {
            for (Hand hand2 : allHands) {
                if (hand1.equals(hand2))
                    continue;

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
                        for (Cocktail cocktail : allCocktails) {
                            this.addMethod(new CleanFullShaker(shaker, level1, cocktail, hand1, hand2, level2, this));
                        }
                    }
                }
            }
        }
    }
}
