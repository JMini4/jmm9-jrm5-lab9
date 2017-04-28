import structure5.*;

public class SingleLineSimulation extends BusinessSimulation {

    public SingleLineSimulation(int numCustomers, int numServicePoints,
				  int maxEventStart, int seed) {
	super(numCustomers, numServicePoints, maxEventStart, seed);
    }
    
    public void goToTeller(){

	Customer nextCustomer = eventQueue.getFirst();
	int i;
	
	while(nextCustomer != null && nextCustomer.getArrivalTime() <= time){
	    for(i = 0; i < servicePoints.size(); i++){
		if(servicePoints.get(i).isEmpty()){
		    eventQueue.remove();
		    servicePoints.get(i).add(nextCustomer);
		    break;
		}
	    }
	    if(i == servicePoints.size()){
		return;
	    } else{
		nextCustomer = eventQueue.getFirst();	
	    }
	}
    }
    
}
