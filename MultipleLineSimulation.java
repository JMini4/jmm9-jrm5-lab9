import structure5.*;


public class MultipleLineSimulation extends BusinessSimulation {

    public MultipleLineSimulation(int numCustomers, int numServicePoints,
				   int maxEventStart, int seed) {
	super(numCustomers, numServicePoints, maxEventStart, seed);	
    }

    public void goToTeller(Customer nextCustomer){

	if(nextCustomer.getArrivalTime() >= time){
	    int shortestLineIndex = 0; 
	
	    for(int i = 1; i < servicePoints.size(); i++){
		if(servicePoints.get(i).size() < servicePoints.get(shortestLineIndex).size()){
		    shortestLineIndex = i;
		}
	    }
	    eventQueue.remove();
	    servicePoints.get(shortestLineIndex).add(nextCustomer);
	    nextCustomer.setTellerTime(time);

	    if(eventQueue.getFirst().getArrivalTime() >= time){
		goToTeller(eventQueue.getFirst());
	    }
	}
    }    

}
