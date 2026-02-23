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

        public static Method createMethod(String methodName, String[] params) {
                DomainHelper helper = DomainHelper.getHelper();

                switch (methodName) {
                        case "MakeAndPourCocktail":
                                return new MakeAndPourCocktail(
                                        helper.getItem(params[0], Shot.class),
                                        helper.getItem(params[1], Cocktail.class),
                                        helper.getItem(params[2], Shaker.class),
                                        helper.getItem(params[3], Hand.class), null);

                        case "MakeAndPourCocktailNull":
                                return new MakeAndPourCocktailNull(
                                        helper.getItem(params[0], Shot.class),
                                        helper.getItem(params[1], Cocktail.class), null);

                        case "MakeCocktail":
                                return new MakeCocktail(
                                        helper.getItem(params[0], Shaker.class),
                                        helper.getItem(params[1], Cocktail.class),
                                        helper.getItem(params[2], Ingredient.class),
                                        helper.getItem(params[3], Hand.class),
                                        helper.getItem(params[4], Hand.class),
                                        helper.getItem(params[5], Ingredient.class), null);

                        case "MakeCocktailNull":
                                return new MakeCocktailNull(
                                        helper.getItem(params[0], Shaker.class),
                                        helper.getItem(params[1], Cocktail.class), null);

                        case "AddIngredientToEmptyShaker":
                                return new AddIngredientToEmptyShaker(
                                        helper.getItem(params[0], Shaker.class),
                                        helper.getItem(params[1], Ingredient.class),
                                        helper.getItem(params[2], Level.class),
                                        helper.getItem(params[3], Level.class),
                                        helper.getItem(params[4], Shot.class),
                                        helper.getItem(params[5], Hand.class), null);

                        case "AddIngredientToUsedShaker":
                                return new AddIngredientToUsedShaker(
                                        helper.getItem(params[0], Shaker.class),
                                        helper.getItem(params[1], Ingredient.class),
                                        helper.getItem(params[2], Level.class),
                                        helper.getItem(params[3], Level.class),
                                        helper.getItem(params[4], Shot.class),
                                        helper.getItem(params[5], Hand.class), null);

                        case "AddIngredientToShakerNull":
                                return new AddIngredientToShakerNull(
                                        helper.getItem(params[0], Shaker.class),
                                        helper.getItem(params[1], Ingredient.class), null);

                        case "AddIngredientToShot":
                                return new AddIngredientToShot(
                                        helper.getItem(params[0], Shot.class),
                                        helper.getItem(params[1], Ingredient.class),
                                        helper.getItem(params[2], Dispenser.class),
                                        helper.getItem(params[3], Hand.class),
                                        helper.getItem(params[4], Hand.class), null);

                        case "AddIngredientToShotNull":
                                return new AddIngredientToShotNull(
                                        helper.getItem(params[0], Shot.class),
                                        helper.getItem(params[1], Ingredient.class), null);

                        case "CleanFullShot":
                                return new CleanFullShot(
                                        helper.getItem(params[0], Shot.class),
                                        helper.getItem(params[1], Hand.class),
                                        helper.getItem(params[2], Beverage.class),
                                        helper.getItem(params[3], Hand.class), null);

                        case "CleanEmptyShot":
                                return new CleanEmptyShot(
                                        helper.getItem(params[0], Shot.class),
                                        helper.getItem(params[1], Hand.class),
                                        helper.getItem(params[2], Beverage.class),
                                        helper.getItem(params[3], Hand.class), null);

                        case "CleanShotNull":
                                return new CleanShotNull(helper.getItem(params[0], Shot.class), null);

                        case "CleanEmptyShaker":
                                return new CleanEmptyShaker(
                                        helper.getItem(params[0], Shaker.class),
                                        helper.getItem(params[1], Hand.class),
                                        helper.getItem(params[2], Hand.class), null);

                        case "CleanFullShaker":
                                return new CleanFullShaker(
                                        helper.getItem(params[0], Shaker.class),
                                        helper.getItem(params[1], Level.class),
                                        helper.getItem(params[2], Cocktail.class),
                                        helper.getItem(params[3], Hand.class),
                                        helper.getItem(params[4], Hand.class),
                                        helper.getItem(params[5], Level.class), null);

                        case "CleanShakerNull":
                                return new CleanShakerNull(helper.getItem(params[0], Shaker.class), null);

                        case "PickUp":
                                return new PickUp(
                                        helper.getItem(params[0], Hand.class),
                                        helper.getItem(params[1], Container.class), null);

                        case "HoldingNull":
                                return new HoldingNull(
                                        helper.getItem(params[0], Hand.class),
                                        helper.getItem(params[1], Container.class), null);

                        case "EmptyHand":
                                return new EmptyHand(
                                        helper.getItem(params[0], Hand.class),
                                        helper.getItem(params[1], Container.class), null);

                        case "HandEmptyNull":
                                return new HandEmptyNull(helper.getItem(params[0], Hand.class), null);

                        case "PutDown":
                                return new PutDown(
                                        helper.getItem(params[0], Container.class),
                                        helper.getItem(params[1], Hand.class), null);

                        case "OnTableNull":
                                return new OnTableNull(helper.getItem(params[0], Container.class), null);

                        case "pour_shaker_to_shot_action":
                                return new pour_shaker_to_shot_action(
                                        helper.getItem(params[0], Shaker.class),
                                        helper.getItem(params[1], Shot.class),
                                        helper.getItem(params[2], Cocktail.class),
                                        helper.getItem(params[3], Level.class),
                                        helper.getItem(params[4], Hand.class),
                                        helper.getItem(params[5], Level.class), null);

                        default:
                                System.err.println("Unknown method: " + methodName);
                                return null;
                }
        }

        public static Action createAction(String actionName, String[] params) {
                DomainHelper helper = DomainHelper.getHelper();

                switch (actionName) {
                        case "clean-shaker":
                                return new clean_shaker(
                                        helper.getItem(params[0], Shaker.class),
                                        helper.getItem(params[1], Hand.class),
                                        helper.getItem(params[2], Hand.class));

                        case "clean-shot":
                                return new clean_shot(
                                        helper.getItem(params[0], Shot.class),
                                        helper.getItem(params[1], Beverage.class),
                                        helper.getItem(params[2], Hand.class),
                                        helper.getItem(params[3], Hand.class));

                        case "drop":
                                return new drop(
                                        helper.getItem(params[0], Hand.class),
                                        helper.getItem(params[1], Container.class));

                        case "empty-shaker":
                                return new empty_shaker(
                                        helper.getItem(params[0], Hand.class),
                                        helper.getItem(params[1], Shaker.class),
                                        helper.getItem(params[2], Cocktail.class),
                                        helper.getItem(params[3], Level.class),
                                        helper.getItem(params[4], Level.class));

                        case "empty-shot":
                                return new empty_shot(
                                        helper.getItem(params[0], Hand.class),
                                        helper.getItem(params[1], Shot.class),
                                        helper.getItem(params[2], Beverage.class));

                        case "fill-shot":
                                return new fill_shot(
                                        helper.getItem(params[0], Shot.class),
                                        helper.getItem(params[1], Ingredient.class),
                                        helper.getItem(params[2], Hand.class),
                                        helper.getItem(params[3], Hand.class),
                                        helper.getItem(params[4], Dispenser.class));

                        case "grasp":
                                return new grasp(
                                        helper.getItem(params[0], Hand.class),
                                        helper.getItem(params[1], Container.class));

                        case "pour-shaker-to-shot":
                                return new pour_shaker_to_shot(
                                        helper.getItem(params[0], Cocktail.class),
                                        helper.getItem(params[1], Shot.class),
                                        helper.getItem(params[2], Hand.class),
                                        helper.getItem(params[3], Shaker.class),
                                        helper.getItem(params[4], Level.class),
                                        helper.getItem(params[5], Level.class));

                        case "pour-shot-to-clean-shaker":
                                return new pour_shot_to_clean_shaker(
                                        helper.getItem(params[0], Shot.class),
                                        helper.getItem(params[1], Ingredient.class),
                                        helper.getItem(params[2], Shaker.class),
                                        helper.getItem(params[3], Hand.class),
                                        helper.getItem(params[4], Level.class),
                                        helper.getItem(params[5], Level.class));

                        case "pour-shot-to-used-shaker":
                                return new pour_shot_to_used_shaker(
                                        helper.getItem(params[0], Shot.class),
                                        helper.getItem(params[1], Ingredient.class),
                                        helper.getItem(params[2], Shaker.class),
                                        helper.getItem(params[3], Hand.class),
                                        helper.getItem(params[4], Level.class),
                                        helper.getItem(params[5], Level.class));

                        case "shake":
                                return new shake(
                                        helper.getItem(params[0], Cocktail.class),
                                        helper.getItem(params[1], Ingredient.class),
                                        helper.getItem(params[2], Ingredient.class),
                                        helper.getItem(params[3], Shaker.class),
                                        helper.getItem(params[4], Hand.class),
                                        helper.getItem(params[5], Hand.class));

                        default:
                                System.err.println("Unknown action: " + actionName);
                                return null;
                }
        }
}