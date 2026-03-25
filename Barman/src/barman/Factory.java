package barman;

import barman.Types.*;

/**
 * The Factory class is where tasks, methods and action are actually created.
 * Uses a configuration driven approach with simple switch logic.
 */
public class Factory {

        public static Task createTask(String taskName, String[] params) {
                DomainHelper helper = DomainHelper.getHelper();

                switch (taskName) {
                        case "AchieveContainsShakerIngredient":
                                return new AchieveContainsShakerIngredient(
                                                helper.getItem(params[0], Shaker.class),
                                                helper.getItem(params[1], Ingredient.class));

                        case "AchieveCleanShaker":
                                return new AchieveCleanShaker(helper.getItem(params[0], Shaker.class));

                        case "AchieveHandEmpty":
                                return new AchieveHandEmpty(helper.getItem(params[0], Hand.class));

                        case "AchieveContainsShotIngredient":
                                return new AchieveContainsShotIngredient(
                                                helper.getItem(params[0], Shot.class),
                                                helper.getItem(params[1], Ingredient.class));

                        case "AchieveContainsShakerCocktail":
                                return new AchieveContainsShakerCocktail(
                                                helper.getItem(params[0], Shaker.class),
                                                helper.getItem(params[1], Cocktail.class));

                        case "DoPourShakerToShot":
                                return new DoPourShakerToShot(
                                                helper.getItem(params[0], Shaker.class),
                                                helper.getItem(params[1], Shot.class),
                                                helper.getItem(params[2], Cocktail.class));

                        case "AchieveOnTable":
                                return new AchieveOnTable(helper.getItem(params[0], Container.class));

                        case "AchieveHolding":
                                return new AchieveHolding(
                                                helper.getItem(params[0], Hand.class),
                                                helper.getItem(params[1], Container.class));

                        case "AchieveCleanShot":
                                return new AchieveCleanShot(helper.getItem(params[0], Shot.class));

                        case "AchieveContainsShotCocktail":
                                return new AchieveContainsShotCocktail(
                                                helper.getItem(params[0], Shot.class),
                                                helper.getItem(params[1], Cocktail.class));

                        default:
                                System.err.println("Unknown task: " + taskName);
                                return null;
                }
        }
}
