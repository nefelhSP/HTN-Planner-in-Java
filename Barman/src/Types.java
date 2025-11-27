package barman;

/* Αυτή η κλάση ορίζει το μοντέλο των αντικειμένων του domain. Χρησιμοποιούνται
nested classes για την ομαδοποίηση τους με βάση το domain*/


public class Types {

    //Βασική κλάση για όλα τα αντικείμενα του domain
    public static abstract class Anything{
        protected  String name;

        public Anything (String name){
            this.name = name;
        }

        //Getters
        public String getName(){
            return name;}
        public String toString(){
            return name;}
    }

    //Βασική κλάση για όλα τα αντικείμενα τύπου Beverage - Ingredient, Cocktail
    public static abstract class Beverage extends Anything{
        public Beverage (String name){
            super(name);
        }
    }

    //Βασική κλάση για όλα τα αντικείμενα τύπου Container - Shot, Shaker
    public static abstract class Container extends Anything{
        public Container (String name){
            super(name);
        }
    }


    public static class Ingredient extends Beverage{
        public Ingredient (String name) {
            super(name);}
    }

    public static class Cocktail extends Beverage{
        public Cocktail (String name) {
            super(name);}
    }

    public static class Shaker extends Container{
        public Shaker (String name) {
            super(name);}
    }

    public static class Shot extends Container{
        public Shot (String name) {
            super(name);}
    }


    //Dispenser που έχει ένα συγκεκριμένο Ingredient
    public static class Dispenser extends Anything{
        private final Ingredient dispensedIngredient;

        public Dispenser(String name, Ingredient ingredient) {
            super(name);
            this.dispensedIngredient = ingredient;
        }
        public Ingredient getDispensedIngredient(){
            return dispensedIngredient;}
    }

    public static class Level extends Anything{
        public Level(String name) {
            super(name);
        }
    }

    public static class Hand extends Anything{
        public Hand (String side) {
            super(side);}
    }

}
