/*
Names: Josie Maynard and Julia Mini
Lab: Jon's Lab
Lab 9 - Business Simulation

 */

import structure5.*;

// class that simulates the goToTeller method for a multiple line simulation, like a grocery store
public class MultipleLineSimulation extends BusinessSimulation {

    public MultipleLineSimulation(int numCustomers, int numServicePoints,
				   int maxEventStart, int seed) {
	super(numCustomers, numServicePoints, maxEventStart, seed);	
    }

    
    
    public void goToTeller(){
	Customer nextCustomer = eventQueue.getFirst();
	
	while(nextCustomer != null && nextCustomer.getArrivalTime() <= time){ //there is a customer in the eventQueue and the time step is at or past the customer arrival
	    int shortestLineIndex = 0;                                             

	    for(int i = 1; i < servicePoints.size(); i++){
		if(servicePoints.get(i).size() < servicePoints.get(shortestLineIndex).size()){ // the next line is shorter than the previously shortest line
		    shortestLineIndex = i; //update
		}
	    }
	    eventQueue.remove(); 
	    servicePoints.get(shortestLineIndex).add(nextCustomer); // customer goes to the shortst line
	    
	    nextCustomer = eventQueue.getFirst(); //update first customer in the eventQueue
	}
    }
    
}
