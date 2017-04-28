

import structure5.*;

public class Customer implements Comparable<Customer> {

    public int arrivalTime; //public or private?
    private int serviceTime;
    private int endTime;
    private int originalServiceTime;
    
	/**
	 * Creates a Customer that arrives at time step @eventTime and
	 * requires @serviceTime time steps to be satisfied after
	 * arriving at a service point.
	 */
    // post: creates a customer that arrives at eventTime and takes serviceTime for teller to process 
    public Customer(int eventTime, int serviceTime) {
	arrivalTime = eventTime;
	this.serviceTime = serviceTime;
	originalServiceTime = serviceTime;
	endTime = 0;
    }

	/**
	 * Compares Customers by arrival time
	 */
    // post: compares customers by arrival times
    public int compareTo(Customer other) {
	return arrivalTime - other.arrivalTime;
    }
    
    public String toString() {
	return "*(" + arrivalTime + ", " + serviceTime + ")";
	
    }
    
    public int getArrivalTime(){
	return arrivalTime;
    }

    public int getServiceTime(){
	return serviceTime;
    }

    public int getOriginalServiceTime(){
	return originalServiceTime; //before customer reaches teller, before decremented
    }

    public void decrementServiceTime(){
	serviceTime = serviceTime - 1;
    }
    public void setEndTime(int time){
	endTime = time;
    }

    public int getEndTime(){
	return endTime;
    }
    public int getWaitTime(){
	return endTime - originalServiceTime - arrivalTime;
    }
}
