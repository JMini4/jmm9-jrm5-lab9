import structure5.*;

public class Customer implements Comparable<Customer> {

	// TODO: fill this class in, adding member variables and
	// methods as needed

    public int arrivalTime; //public or private?
    private int serviceTime;
    private int toTellerTime;
    
	/**
	 * Creates a Customer that arrives at time step @eventTime and
	 * requires @serviceTime time steps to be satisfied after
	 * arriving at a service point.
	 */
    public Customer(int eventTime, int serviceTime) {
	arrivalTime = eventTime;
	this.serviceTime = serviceTime;
	toTellerTime = 0;
    }

	/**
	 * Compares Customers by arrival time
	 */
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

    public int setTellerTime(int time){
	toTellerTime = time;
    }
    public int getWaitTime(){
	return toTellerTime - arrivalTime;
    }
}
