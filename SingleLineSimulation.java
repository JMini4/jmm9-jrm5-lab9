/*
Names: Josie Maynard and Julia Mini
Lab Sections: Jon's Lab
Lab 9 - Business Simulation

 */


import structure5.*;

// class that simulates the goToTeller method for a single line simulation, like a bank     
public class SingleLineSimulation extends BusinessSimulation {

    public SingleLineSimulation(int numCustomers, int numServicePoints,
				  int maxEventStart, int seed) {
	super(numCustomers, numServicePoints, maxEventStart, seed);
    }
    
    public void goToTeller(){

	Customer nextCustomer = eventQueue.getFirst();
	int i;
	
	while(nextCustomer != null && nextCustomer.getArrivalTime() <= time){ //there is a customer and their current time is at or after their arrival time
	    for(i = 0; i < servicePoints.size(); i++){ //assert that size is either 0 or 1
		if(servicePoints.get(i).isEmpty()){ // the teller is free
		    eventQueue.remove();
		    servicePoints.get(i).add(nextCustomer); // go to first available teller
		    break;
		}
	    }
	    if(i == servicePoints.size()){ // all tellers are busy
		return;
	    } else{
		nextCustomer = eventQueue.getFirst();	
	    }
	}
    }
    
}
