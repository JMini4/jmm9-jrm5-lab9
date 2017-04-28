/*
Names: Josie Maynard and Julia Mini
Lab Sections: Jon's Lab
Lab 9 - Business Simulation

Program Description: class that simulates how customers wait for an available teller in a single line simulation, like a bank
 */

import structure5.*;

public class SingleLineSimulation extends BusinessSimulation {

    // post: constructs the single line business simulation 
    public SingleLineSimulation(int numCustomers, int numServicePoints,
				  int maxEventStart, int seed) {
	super(numCustomers, numServicePoints, maxEventStart, seed);
    }

    // post: places the customer at a service point when they arrive if one is empty
    // there will always be 0 or 1 customers at each service point
    public void goToTeller(){

	Customer nextCustomer = eventQueue.getFirst();

	//index of the current service point
	int i;

	//while there is a customer in the eventQueue and the time step is at or past the customer arrival
	while(nextCustomer != null && nextCustomer.getArrivalTime() <= time){ 
	    for(i = 0; i < servicePoints.size(); i++){
		//go to first available teller
		if(servicePoints.get(i).isEmpty()){
		    eventQueue.remove();
		    servicePoints.get(i).add(nextCustomer);
		    break;
		}
	    }
	    if(i == servicePoints.size()){ // all tellers are busy
		return;
	    } else{
		//check for the next customer
		nextCustomer = eventQueue.getFirst();	
	    }
	}
    }
}
