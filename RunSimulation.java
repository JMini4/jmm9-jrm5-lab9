import structure5.*;
import java.util.Scanner;

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
	
	if(args[0].equals("single")){
	    simulator = new SingleLineSimulation(numCustomers, numServicePoints, maxStart, seed);
	} else if (args[0].equals("multiple")){
	    simulator = new MultipleLineSimulation(numCustomers, numServicePoints, maxStart, seed);
	} else {
	    System.out.println("First select single or multiple line simulation");
	    return;
	}
     
	while(!simulator.isFinished()){
	    simulator.goToTeller(simulator.getFirstCustomer());
	    simulator.manageTeller(simulator.getFirstCustomer());
	    simulator.step();
	    
	}
    }
}
