/*                                                                                                                                                                      
Josie Maynard and Julia Mini                                                                                                                    Jon's lab                                                                                                                                       4/27/17   

Program Description: Creates a customer object that has a random arrival time and service time. Customers are ordered based on their arrival time. The times associated with each customer, like arrival time and wait time can be accessed.
*/

import structure5.*;

public class Customer implements Comparable<Customer> {

    private int arrivalTime; 

    private int serviceTime;

    //time when customer is done being served
    private int endTime = 0;

    //to remember the service time the customer started with before decrementation
    // used for thought question
    private int originalServiceTime;
    
    // pre: eventTime and serviceTime are greater than or equal to 0    
    // post: creates a customer that arrives at eventTime and takes serviceTime for teller to process 
    public Customer(int eventTime, int serviceTime) {
	assert(eventTime >= 0 && serviceTime >= 0):("Times must be greater or equal to zero");
	
	arrivalTime = eventTime;
	this.serviceTime = serviceTime;
	originalServiceTime = serviceTime;
    }

    // post: compares customers by arrival times
    public int compareTo(Customer other) {
	return arrivalTime - other.arrivalTime;
    }

    // post: creates a string representation of the customer
    public String toString() {
	return "*(" + arrivalTime + ", " + serviceTime + ")";
	
    }

    // post: returns the arrival time
    public int getArrivalTime(){
	return arrivalTime;
    }

    //post: returns the service time left at that time step
    public int getServiceTime(){
	return serviceTime;
    }

    // post: reduces the service time when a time step is complete
    public void decrementServiceTime(){
	serviceTime = serviceTime - 1;
    }

    // post: records when the customer is done being served
    // used for thought questions
    public void setEndTime(int time){
	endTime = time;
    }

    // post: returns the wait time for a customer
    // used for thought questions
    public int getWaitTime(){
	return endTime - originalServiceTime - arrivalTime;
    }
}
