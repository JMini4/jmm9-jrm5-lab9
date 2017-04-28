/*
Names: Josie Maynard and Julia Mini
Lab Section: Jon's Lab
Lab 9 - Business Simulation

Program Description: Runs the given simulation from a user input at the command line (either multiple or single line simulation) by asking for the number of customers, number of service points, maxmimum arrival time, and seed to remember the customers.      

 */

import structure5.*;
import java.util.Scanner;

public class RunSimulation {
    
    public static void main(String args[]){
	
	BusinessSimulation simulator;

	Scanner in = new Scanner(System.in);
        
	if (args.length == 0 || (!args[0].equals("single") && !args[0].equals("multiple"))){ //user did not input valid simulation type
	    System.out.println("First select single or multiple line simulation");
	    return;
	    
	} else {
	    //ask for integer parameters
	    System.out.println("Enter positive integers for number of customers, number of service points, maximum event start, and seed number");

	    int numCustomers = in.nextInt();
	    int numServicePoints = in.nextInt();
	    int maxStart = in.nextInt();
	    int seed = in.nextInt();

	    if(args[0].equals("single")){ // run Single Line Simulation
		simulator = new SingleLineSimulation(numCustomers, numServicePoints, maxStart, seed);

	    } else { // run Multiple Line Simulation
		simulator = new MultipleLineSimulation(numCustomers, numServicePoints, maxStart, seed);
	    }
	}

	// simulates the business until all customers are done being served
	while(!simulator.isFinished()){
	    System.out.println(simulator.toString());
	    simulator.goToTeller();
	    simulator.manageTeller();
	    simulator.step();
	}
    }
}
