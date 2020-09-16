import java.util.Scanner;

public class Main
{
	public static void main(String[] args) {
	    
		Scanner reader = new Scanner(System.in);
		
		System.out.println("Enter the Mass of the water in grams.");
		double Mass = reader.nextDouble();
		System.out.println("Enter the initial temperature, in Celsius.");
		double initialtemp = reader.nextDouble();
		if(initialtemp < -273.14)
		    initialtemp = -273.14;
		System.out.println("Enter the final temperature, in Celsius.");
		double finaltemp = reader.nextDouble();
		if(finaltemp < -273.14)
		    finaltemp = -273.14;
		    
		String initialPhase = "liquid";
		if(initialtemp < 0)
		    initialPhase = "solid";
		if(initialtemp > 100)
		    initialPhase = "vapor";
		
		String finalPhase = "liquid";
		if(finaltemp < 0)
		    finalPhase = "solid";
		if(finaltemp > 100)
		    finalPhase = "vapor";
		    
		System.out.println("Mass: + " + Mass + "g");
		System.out.println("Initial temperature: " + initialtemp + "C " + initialPhase);
		System.out.println("Final temperature: " + finaltemp + "C " + finalPhase + "\n");
		
		boolean endothermic = false;
		if(finaltemp > initialtemp)
		    endothermic = true;
		
		double heatEnergy = 0;
		//solid
		if(initialPhase.equals("solid")) {
		    heatEnergy += tempChangeSolid(Mass, initialtemp, finaltemp, finalPhase, endothermic);
		    
		    if(!finalPhase.equals("solid")) {
		        heatEnergy += fusion(Mass, endothermic);
		        heatEnergy += tempChangeLiquid(Mass, 0, finaltemp, finalPhase, endothermic);
		    }
		    
		    if(finalPhase.equals("vapor")) {
		        heatEnergy += fusion(Mass, endothermic);
		        heatEnergy += tempChangeVapor(Mass, 100, finaltemp, finalPhase, endothermic);
		    }
		    
		}
		//liquid
			if(initialPhase.equals("liquid")) {
			    heatEnergy += tempChangeLiquid(Mass, initialtemp, finaltemp, finalPhase, endothermic);
			    
			     if(finalPhase.equals("liquid")) {
			         heatEnergy += fusion(Mass, endothermic);
			         heatEnergy += tempChangeSolid(Mass, 0, finaltemp, finalPhase, endothermic);
			     }
			     if(finalPhase.equals("vapor")) {
		        heatEnergy += fusion(Mass, endothermic);
		        heatEnergy += tempChangeVapor(Mass, 100, finaltemp, finalPhase, endothermic);
			    }
			}
			//vapor
			if(initialPhase.equals("vapor")) {
		    heatEnergy += tempChangeVapor(Mass, initialtemp, finaltemp, finalPhase, endothermic);
		    
		    if(!finalPhase.equals("vapor")) {
		        heatEnergy += vaporization(Mass, endothermic);
		        heatEnergy += tempChangeLiquid(Mass, 100, finaltemp, finalPhase, endothermic);
		    }
		    
		    if(finalPhase.equals("solid")) {
			         heatEnergy += fusion(Mass, endothermic);
			         heatEnergy += tempChangeSolid(Mass, 0, finaltemp, finalPhase, endothermic);
		    }
			}
		
		System.out.println("Total Heat Energy: " + heatEnergy + "KJ");
		
	}
	
	public static double tempChangeSolid(double Mass, double startTemp, double endTemp, String endPhase, boolean endothermic) {
	   if(!endPhase.equals("solid"))
	        endTemp = 0;
	   double energyChange = round(Mass*0.002108*(endTemp - startTemp));
	   if(endothermic)
	        System.out.println("Healing (solid): " + energyChange + "kJ");
	   else
	        System.out.println("Cooling (solid): " + energyChange + "kJ");
	   return energyChange;
	}  
	
	public static double tempChangeLiquid(double Mass, double startTemp, double endTemp, String endPhase, boolean endothermic) {
	   if(endPhase.equals("solid"))
	        endTemp = 0;
	   if(endPhase.equals("vapor"))
	        endTemp = 100;
	   double energyChange = round(Mass*0.004184*(endTemp - startTemp));
	   if(endothermic)
	        System.out.println("Heating (liquid): " + energyChange + "kJ");
	   else
	        System.out.println("Cooling (liquid): " + energyChange + "kJ");
	   return energyChange;
	}  
	
	public static double tempChangeVapor(double Mass, double startTemp, double endTemp, String endPhase, boolean endothermic) {
	   if(!endPhase.equals("vapor"))
	        endTemp = 100;
	   double energyChange = round(Mass*0.001996*(endTemp - startTemp));
	   if(endothermic)
	        System.out.println("Heating (vapor): " + energyChange + "kJ");
	   else
	        System.out.println("Cooling (vapor): " + energyChange + "kJ");
	   return energyChange;
	}  
	
	public static double fusion(double Mass, boolean endothermic) {
	    double energyChange;
	    if(endothermic) {
	        energyChange = round(Mass*0.333);
	        System.out.println("Melting: " + energyChange + "kJ");
	    }
	    else{
	        energyChange = round(-0.333*Mass);
	        System.out.println("Freezing: " + energyChange + "kJ");
	    }
	    return energyChange;
	    
	
	}
	public static double vaporization(double Mass, boolean endothermic) {
	    double energyChange;
	    if(endothermic) {
	        energyChange = round(Mass*2.257);
	        System.out.println("Evaporation: " + energyChange + "kJ");
	    }
	    else{
	        energyChange = round(-2.257*Mass);
	        System.out.println("Condensation: " + energyChange + "kJ");
	    }
	    return energyChange;
	}
	
	public static double round(double x) {
	   x *= 1000;
	   if(x > 0)
	        return (int)(x + 0.5)/1000.0;
	   else
	        return (int)(x - 0.5)/1000.0;
	}
	
}
