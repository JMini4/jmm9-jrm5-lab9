import structure5.*;

public class SingleLineSimulation extends BusinessSimulation {

    public SingleLineSimulation(int numCustomers, int numServicePoints,
				  int maxEventStart, int seed) {
	super(numCustomers, numServicePoints, maxEventStart, seed);
    }

    public void goToTeller(Customer nextCustomer){
	if(nextCustomer.getArrivalTime() >= time){
	    for(int i = 0; i < servicePoints.size(); i++){
		if(servicePoints.get(i).isEmpty()){
		    eventQueue.remove();
		    servicePoints.get(i).add(nextCustomer);
		    nextCustomer.setTellerTime(time);

		    if(eventQueue.getFirst().getArrivalTime() >= time){
			goToTeller(eventQueue.getFirst());
		    }
		    
		    break;
		}
	    }
	}
    }
    

}
