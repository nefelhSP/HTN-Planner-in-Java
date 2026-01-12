package barman;

import barman.Types.*;

/**
 * The Factory class is a factory that creates tasks and methods.
 */
public class Factory {

    /**
     * Creates a task based on the given task name and parameters.
     * 
     * @param taskName The name of the task to create.
     * @param params   The parameters for the task.
     * @return The created task.
     */
    public static Task createTask(String taskName, String[] params) {
        DomainHelper helper = DomainHelper.getHelper();

        switch (taskName) {
            case "AchieveContainsShakerIngredient":
                if (params.length < 2)
                    throw new IllegalArgumentException("Missing arguments for " + taskName);
                Shaker shaker1 = helper.getShaker(params[0]);
                Ingredient ingredient1 = helper.getIngredient(params[1]);
                return new AchieveContainsShakerIngredient(shaker1, ingredient1);

            case "AchieveCleanShaker":
                if (params.length < 1)
                    throw new IllegalArgumentException("Missing arguments for " + taskName);
                Shaker shaker2 = helper.getShaker(params[0]);
                return new AchieveCleanShaker(shaker2);

            case "AchieveHandEmpty":
                if (params.length < 1)
                    throw new IllegalArgumentException("Missing arguments for " + taskName);
                Hand hand1 = helper.getHand(params[0]);
                return new AchieveHandEmpty(hand1);

            case "AchieveContainsShotIngredient":
                if (params.length < 2)
                    throw new IllegalArgumentException("Missing arguments for " + taskName);
                Shot shot1 = helper.getShot(params[0]);
                Ingredient ingredient2 = helper.getIngredient(params[1]);
                return new AchieveContainsShotIngredient(shot1, ingredient2, null);

            case "AchieveContainsShakerCocktail":
                if (params.length < 2)
                    throw new IllegalArgumentException("Missing arguments for " + taskName);
                Shaker shaker3 = helper.getShaker(params[0]);
                Cocktail cocktail1 = helper.getCocktail(params[1]);
                return new AchieveContainsShakerCocktail(shaker3, cocktail1);

            case "DoPourShakerToShot":
                if (params.length < 3)
                    throw new IllegalArgumentException("Missing arguments for " + taskName);
                Shaker shaker4 = helper.getShaker(params[0]);
                Shot shot2 = helper.getShot(params[1]);
                Cocktail cocktail2 = helper.getCocktail(params[2]);
                return new DoPourShakerToShot(shaker4, shot2, cocktail2);

            case "AchieveOnTable":
                if (params.length < 1)
                    throw new IllegalArgumentException("Missing arguments for " + taskName);
                Container container1 = helper.getContainer(params[0]);
                return new AchieveOnTable(container1);

            case "AchieveHolding":
                if (params.length < 2)
                    throw new IllegalArgumentException("Missing arguments for " + taskName);
                Hand hand2 = helper.getHand(params[0]);
                Container container2 = helper.getContainer(params[1]);
                return new AchieveHolding(hand2, container2);

            case "AchieveCleanShot":
                if (params.length < 1)
                    throw new IllegalArgumentException("Missing arguments for " + taskName);
                Shot shot3 = helper.getShot(params[0]);
                return new AchieveCleanShot(shot3);

            case "AchieveContainsShotCocktail":
                if (params.length < 2)
                    throw new IllegalArgumentException("Missing arguments for " + taskName);
                Shot shot4 = helper.getShot(params[0]);
                Cocktail cocktail3 = helper.getCocktail(params[1]);
                return new AchieveContainsShotCocktail(shot4, cocktail3);

            default:
                System.err.println("Unknown Task: " + taskName);
                return null;
        }
    }

    /**
     * Creates a method based on the given method name and parameters.
     * 
     * @param methodName The name of the method to create.
     * @param params     The parameters for the method.
     * @return The created method.
     */
    public static Method createMethod(String methodName, String[] params) {
        DomainHelper helper = DomainHelper.getHelper();

        // All method creations include 'null' at the end because the Factory creates
        // methods without a specific parent Task context.
        switch (methodName) {

            case "MakeAndPourCocktail":
                if (params.length < 4)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Shot shot_mapc = helper.getShot(params[0]);
                Cocktail cocktail_mapc = helper.getCocktail(params[1]);
                Shaker shaker_mapc = helper.getShaker(params[2]);
                Hand hand_mapc = helper.getHand(params[3]);
                return new MakeAndPourCocktail(shot_mapc, cocktail_mapc, shaker_mapc, hand_mapc, null);

            case "MakeAndPourCocktailNull":
                if (params.length < 2)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Shot shot_mapcN = helper.getShot(params[0]);
                Cocktail cocktail_mapcN = helper.getCocktail(params[1]);
                return new MakeAndPourCocktailNull(shot_mapcN, cocktail_mapcN, null);

            case "MakeCocktail":
                if (params.length < 6)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Shaker shaker_mc = helper.getShaker(params[0]);
                Cocktail cocktail_mc = helper.getCocktail(params[1]);
                Ingredient ingredient2_mc = helper.getIngredient(params[2]);
                Hand hand1_mc = helper.getHand(params[3]);
                Hand hand2_mc = helper.getHand(params[4]);
                Ingredient ingredient1_mc = helper.getIngredient(params[5]);
                return new MakeCocktail(shaker_mc, cocktail_mc, ingredient2_mc, hand1_mc, hand2_mc, ingredient1_mc,
                        null);

            case "MakeCocktailNull":
                if (params.length < 2)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Shaker shaker_mcN = helper.getShaker(params[0]);
                Cocktail cocktail_mcN = helper.getCocktail(params[1]);
                return new MakeCocktailNull(shaker_mcN, cocktail_mcN, null);

            case "AddIngredientToEmptyShaker":
                if (params.length < 6)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Shaker shaker_aies = helper.getShaker(params[0]);
                Ingredient ingredient_aies = helper.getIngredient(params[1]);
                Level level1_aies = helper.getLevel(params[2]);
                Level level2_aies = helper.getLevel(params[3]);
                Shot shot_aies = helper.getShot(params[4]);
                Hand hand_aies = helper.getHand(params[5]);
                return new AddIngredientToEmptyShaker(shaker_aies, ingredient_aies, level1_aies, level2_aies, shot_aies,
                        hand_aies, null, null);

            case "AddIngredientToUsedShaker":
                if (params.length < 6)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Shaker shaker_aius = helper.getShaker(params[0]);
                Ingredient ingredient_aius = helper.getIngredient(params[1]);
                Level level1_aius = helper.getLevel(params[2]);
                Level level2_aius = helper.getLevel(params[3]);
                Shot shot_aius = helper.getShot(params[4]);
                Hand hand_aius = helper.getHand(params[5]);
                return new AddIngredientToUsedShaker(shaker_aius, ingredient_aius, level1_aius, level2_aius, shot_aius,
                        hand_aius, null, null);

            case "AddIngredientToShakerNull":
                if (params.length < 2)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Shaker shaker_aisN = helper.getShaker(params[0]);
                Ingredient ingredient_aisN = helper.getIngredient(params[1]);
                return new AddIngredientToShakerNull(shaker_aisN, ingredient_aisN, null);

            case "AddIngredientToShot":
                if (params.length < 5)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Shot shot_ais = helper.getShot(params[0]);
                Ingredient ingredient_ais = helper.getIngredient(params[1]);
                Dispenser dispenser_ais = helper.getDispenser(params[2]);
                Hand hand1_ais = helper.getHand(params[3]);
                Hand hand2_ais = helper.getHand(params[4]);
                return new AddIngredientToShot(shot_ais, ingredient_ais, dispenser_ais, hand1_ais, hand2_ais, null);

            case "AddIngredientToShotNull":
                if (params.length < 2)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Shot shot_aishN = helper.getShot(params[0]);
                Ingredient ingredient_aishN = helper.getIngredient(params[1]);
                return new AddIngredientToShotNull(shot_aishN, ingredient_aishN, null);

            case "CleanFullShot":
                if (params.length < 4)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Shot shot_cfs = helper.getShot(params[0]);
                Hand hand1_cfs = helper.getHand(params[1]);
                Beverage beverage_cfs = helper.getBeverage(params[2]);
                Hand hand2_cfs = helper.getHand(params[3]);
                return new CleanFullShot(shot_cfs, hand1_cfs, beverage_cfs, hand2_cfs, null);

            case "CleanEmptyShot":
                if (params.length < 4)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Shot shot_ces = helper.getShot(params[0]);
                Hand hand1_ces = helper.getHand(params[1]);
                Beverage beverage_ces = helper.getBeverage(params[2]);
                Hand hand2_ces = helper.getHand(params[3]);
                return new CleanEmptyShot(shot_ces, hand1_ces, beverage_ces, hand2_ces, null);

            case "CleanShotNull":
                if (params.length < 1)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Shot shot_csN = helper.getShot(params[0]);
                return new CleanShotNull(shot_csN, null);

            case "CleanEmptyShaker":
                if (params.length < 3)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Shaker shaker_cesh = helper.getShaker(params[0]);
                Hand hand1_cesh = helper.getHand(params[1]);
                Hand hand2_cesh = helper.getHand(params[2]);
                return new CleanEmptyShaker(shaker_cesh, hand1_cesh, hand2_cesh, null);

            case "CleanFullShaker":
                if (params.length < 6)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Shaker shaker_cfsh = helper.getShaker(params[0]);
                Level level1_cfsh = helper.getLevel(params[1]);
                Cocktail cocktail_cfsh = helper.getCocktail(params[2]);
                Hand hand1_cfsh = helper.getHand(params[3]);
                Hand hand2_cfsh = helper.getHand(params[4]);
                Level level2_cfsh = helper.getLevel(params[5]);
                return new CleanFullShaker(shaker_cfsh, level1_cfsh, cocktail_cfsh, hand1_cfsh, hand2_cfsh, level2_cfsh,
                        null);

            case "CleanShakerNull":
                if (params.length < 1)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Shaker shaker_cshN = helper.getShaker(params[0]);
                return new CleanShakerNull(shaker_cshN, null);

            case "PickUp":
                if (params.length < 2)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Hand hand_pu = helper.getHand(params[0]);
                Container container_pu = helper.getContainer(params[1]);
                return new PickUp(hand_pu, container_pu, null);

            case "HoldingNull":
                if (params.length < 2)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Hand hand_hN = helper.getHand(params[0]);
                Container container_hN = helper.getContainer(params[1]);
                return new HoldingNull(hand_hN, container_hN, null);

            case "EmptyHand":
                if (params.length < 2)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Hand hand_eh = helper.getHand(params[0]);
                Container container_eh = helper.getContainer(params[1]);
                return new EmptyHand(hand_eh, container_eh, null);

            case "HandEmptyNull":
                if (params.length < 1)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Hand hand_heN = helper.getHand(params[0]);
                return new HandEmptyNull(hand_heN, null);

            case "PutDown":
                if (params.length < 2)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Container container_pd = helper.getContainer(params[0]);
                Hand hand_pd = helper.getHand(params[1]);
                return new PutDown(container_pd, hand_pd, null);

            case "OnTableNull":
                if (params.length < 1)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Container container_otN = helper.getContainer(params[0]);
                return new OnTableNull(container_otN, null);

            case "pour_shaker_to_shot_action":
                if (params.length < 6)
                    throw new IllegalArgumentException("Missing arguments for " + methodName);
                Shaker shaker_pssa = helper.getShaker(params[0]);
                Shot shot_pssa = helper.getShot(params[1]);
                Cocktail cocktail_pssa = helper.getCocktail(params[2]);
                Level level2_pssa = helper.getLevel(params[3]);
                Hand hand2_pssa = helper.getHand(params[4]);
                Level level1_pssa = helper.getLevel(params[5]);
                return new pour_shaker_to_shot_action(shaker_pssa, shot_pssa, cocktail_pssa, level2_pssa, hand2_pssa,
                        level1_pssa, null);

            default:
                System.err.println("Unknown Method: " + methodName);
                return null;
        }
    }

    /**
     * Creates an action with the given action name and parameters.
     * 
     * @param actionName The name of the action to create
     * @param params     The parameters for the action
     * @return The created action
     */
    public static Action createAction(String actionName, String[] params) {

        DomainHelper helper = DomainHelper.getHelper();

        switch (actionName) {
            case "clean-shaker":
                if (params.length < 3)
                    throw new IllegalArgumentException("Missing arguments for " + actionName);
                Shaker shaker_csh = helper.getShaker(params[0]);
                Hand hand1_csh = helper.getHand(params[1]);
                Hand hand2_csh = helper.getHand(params[2]);
                return new clean_shaker(shaker_csh, hand1_csh, hand2_csh);

            case "clean-shot":
                if (params.length < 4)
                    throw new IllegalArgumentException("Missing arguments for " + actionName);
                Shot shot_cs = helper.getShot(params[0]);
                Beverage beverage_cs = helper.getBeverage(params[1]);
                Hand hand1_cs = helper.getHand(params[2]);
                Hand hand2_cs = helper.getHand(params[3]);
                return new clean_shot(shot_cs, beverage_cs, hand1_cs, hand2_cs);

            case "drop":
                if (params.length < 2)
                    throw new IllegalArgumentException("Missing arguments for " + actionName);
                Hand hand_d = helper.getHand(params[0]);
                Container container_d = helper.getContainer(params[1]);
                return new drop(hand_d, container_d);

            case "empty-shaker":
                if (params.length < 5)
                    throw new IllegalArgumentException("Missing arguments for " + actionName);
                Hand hand_esh = helper.getHand(params[0]);
                Shaker shaker_esh = helper.getShaker(params[1]);
                Cocktail cocktail_esh = helper.getCocktail(params[2]);
                Level level1_esh = helper.getLevel(params[3]);
                Level level2_esh = helper.getLevel(params[4]);
                return new empty_shaker(hand_esh, shaker_esh, cocktail_esh, level1_esh, level2_esh);

            case "empty-shot":
                if (params.length < 3)
                    throw new IllegalArgumentException("Missing arguments for " + actionName);
                Hand hand_est = helper.getHand(params[0]);
                Shot shot_est = helper.getShot(params[1]);
                Beverage beverage_est = helper.getBeverage(params[2]);
                return new empty_shot(hand_est, shot_est, beverage_est);

            case "fill-shot":
                if (params.length < 5)
                    throw new IllegalArgumentException("Missing arguments for " + actionName);
                Shot shot_fs = helper.getShot(params[0]);
                Ingredient ingredient_fs = helper.getIngredient(params[1]);
                Hand hand1_fs = helper.getHand(params[2]);
                Hand hand2_fs = helper.getHand(params[3]);
                Dispenser dispenser_fs = helper.getDispenser(params[4]);
                return new fill_shot(shot_fs, ingredient_fs, hand1_fs, hand2_fs, dispenser_fs);

            case "grasp":
                if (params.length < 2)
                    throw new IllegalArgumentException("Missing arguments for " + actionName);
                Hand hand_g = helper.getHand(params[0]);
                Container container_g = helper.getContainer(params[1]);
                return new grasp(hand_g, container_g);

            case "pour-shaker-to-shot":
                if (params.length < 6)
                    throw new IllegalArgumentException("Missing arguments for " + actionName);
                Cocktail cocktail_psts = helper.getCocktail(params[0]);
                Shot shot_psts = helper.getShot(params[1]);
                Hand hand_psts = helper.getHand(params[2]);
                Shaker shaker_psts = helper.getShaker(params[3]);
                Level level1_psts = helper.getLevel(params[4]);
                Level level2_psts = helper.getLevel(params[5]);
                return new pour_shaker_to_shot(cocktail_psts, shot_psts, hand_psts, shaker_psts, level1_psts,
                        level2_psts);

            case "pour-shot-to-clean-shaker":
                if (params.length < 6)
                    throw new IllegalArgumentException("Missing arguments for " + actionName);
                Shot shot_pstcs = helper.getShot(params[0]);
                Ingredient ingredient_pstcs = helper.getIngredient(params[1]);
                Shaker shaker_pstcs = helper.getShaker(params[2]);
                Hand hand_pstcs = helper.getHand(params[3]);
                Level level1_pstcs = helper.getLevel(params[4]);
                Level level2_pstcs = helper.getLevel(params[5]);
                return new pour_shot_to_clean_shaker(shot_pstcs, ingredient_pstcs, shaker_pstcs, hand_pstcs,
                        level1_pstcs, level2_pstcs);

            case "pour-shot-to-used-shaker":
                if (params.length < 6)
                    throw new IllegalArgumentException("Missing arguments for " + actionName);
                Shot shot_pstus = helper.getShot(params[0]);
                Ingredient ingredient_pstus = helper.getIngredient(params[1]);
                Shaker shaker_pstus = helper.getShaker(params[2]);
                Hand hand_pstus = helper.getHand(params[3]);
                Level level1_pstus = helper.getLevel(params[4]);
                Level level2_pstus = helper.getLevel(params[5]);
                return new pour_shot_to_used_shaker(shot_pstus, ingredient_pstus, shaker_pstus, hand_pstus,
                        level1_pstus, level2_pstus);

            case "shake":
                if (params.length < 6)
                    throw new IllegalArgumentException("Missing arguments for " + actionName);
                Cocktail cocktail_sh = helper.getCocktail(params[0]);
                Ingredient ingredient1_sh = helper.getIngredient(params[1]);
                Ingredient ingredient2_sh = helper.getIngredient(params[2]);
                Shaker shaker_sh = helper.getShaker(params[3]);
                Hand hand1_sh = helper.getHand(params[4]);
                Hand hand2_sh = helper.getHand(params[5]);
                return new shake(cocktail_sh, ingredient1_sh, ingredient2_sh, shaker_sh, hand1_sh, hand2_sh);

            default:
                System.err.println("Unknown Action: " + actionName);
                return null;
        }
    }
}