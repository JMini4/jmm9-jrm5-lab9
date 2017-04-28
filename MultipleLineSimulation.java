/*
Names: Josie Maynard and Julia Mini
Lab: Jon's Lab
Lab 9 - Business Simulation

Program Description: class that simulates how customers wait for an available teller in a multiple line simulation, like a grocery store
 */

import structure5.*;

public class MultipleLineSimulation extends BusinessSimulation {

    // post: constructs the multiple line business simulation
    public MultipleLineSimulation(int numCustomers, int numServicePoints,
				   int maxEventStart, int seed) {
	super(numCustomers, numServicePoints, maxEventStart, seed);	
    }

    
    // post: places the customer in the shortest line when they arrive
    public void goToTeller(){
	
	Customer nextCustomer = eventQueue.getFirst();

	//while there is a customer in the eventQueue and the time step is at or past the customer arrival
	while(nextCustomer != null && nextCustomer.getArrivalTime() <= time){ 
	    int shortestLineIndex = 0;                                             

	    //find the shortest line
	    for(int i = 1; i < servicePoints.size(); i++){
		if(servicePoints.get(i).size() < servicePoints.get(shortestLineIndex).size()){
		    shortestLineIndex = i; 
		}
	    }

	    //move the customer into the shortest line and check for the next customer
	    eventQueue.remove(); 
	    servicePoints.get(shortestLineIndex).add(nextCustomer); 
	    nextCustomer = eventQueue.getFirst(); 
	}
    }
}
