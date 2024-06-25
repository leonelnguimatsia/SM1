package de.thk.sm;

public class Toolbox {

     /**
     * Main Methode
     * je nach dem erstem Argument werden die weiteren Argumente verarbeitet
     *
     * @param args Liste von Strings, die aus einem Befehl und ggf einem oder mehreren Argumenten besteht
     */
 	public static void main(String[] args) {
        if ((args.length < 2)) {
            System.out.println("Usage: java Toolbox <command> <command-specific arguments>");
        }
        else if (args[0].equals("length")) {
            System.out.println(args[1].length());
        }
        else if (args[0].equals("reverse")) {
            for(int i=args[1].length()-1; i >= 0; i--){
                System.out.print(args[1].charAt(i));
            }
            System.out.println("");
        }
        
    }
}
