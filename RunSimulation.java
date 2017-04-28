/*
Names: Josie Maynard and Julia Mini
Lab Section: Jon's Lab
Lab 9 - Business Simulation
 */


import structure5.*;
import java.util.Scanner;

// Class that runs the given simulation from a user input by asking for the numCustomers, numServicePoints, maxStart,  and seed
public class RunSimulation {
    public static void main(String args[]){
	int simulationType;
	BusinessSimulation simulator;

	Scanner in = new Scanner(System.in);
	System.out.println("Enter number of customers, number of service points, maximum event start, and seed number");

	int numCustomers = in.nextInt();
	int numServicePoints = in.nextInt();
	int maxStart = in.nextInt();
	int seed = in.nextInt();
	
	if(args[0].equals("single")){ // run Single Line Simulation
	    simulator = new SingleLineSimulation(numCustomers, numServicePoints, maxStart, seed);
	} else if (args[0].equals("multiple")){ // run Multiple Line Simulation
	    simulator = new MultipleLineSimulation(numCustomers, numServicePoints, maxStart, seed);
	} else { // tried to enter something other than single or multiple
	    System.out.println("First select single or multiple line simulation");
	    return;
	}
     
	while(!simulator.isFinished()){
	    simulator.goToTeller();
	    simulator.manageTeller();
	    //System.out.println(simulator.toString());
	    simulator.step();
	    
	}
	System.out.println(simulator.averageWaitTime());
	System.out.println(simulator.totalWaitTime);
    }
}
