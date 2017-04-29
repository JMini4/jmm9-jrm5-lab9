/*
Josie Maynard and Julia Mini
Jon's lab
4/27/17

Thought Questions:

1. In cases where the maximum service time is short, there is not much of a difference in the time it takes to process all of the customers between the two queue strategies. Whether many customers showed up at once or many customers showed up over a longer period of time, the total time was approximately the same. However when the maximum service time grew, it became clear that the single line strategy was faster at processing all of the customers than the multiple line strategy. For example, when the parameters were (numCustomers=60, numServicePoints=3, maxEventStart=6, minServiceTime= 5, maxServiceTime=20), which indicated many customers arriving in a short period of time with a moderate maximum service time, it took the multiple line strategy 279 steps, and the single line strategy 271. Thus the single line strategy was faster, but not by much. However, when we used the same parameters, but set maxServiceTime to 30, it took the multiple line strategy 400 time steps, and the single line strategy 379. Furthermore, when we increased maxServiceTime to 50, it took the multiple line strategy 667 time steps and single line strategy 583 time steps. Therefore, the greater the average service time, the bigger difference there is between the time it takes to process all of the customers for the two line strategies, and overall the single line simulation takes less than or equal to the amount of time taken by the multiple line simulation.

2. For every scenario we tried, we found that there was an equal average wait time for customers for the two different line techniques. For example, when the parameters were (numCustomers=60, numServicePoints=3, maxEventStart=6, minServiceTime= 5, maxServiceTime=10), which represented a lot of customers coming in during a short period of time with a relatively short service time, the average wait time for both the single line simulation and multiple line simulation was 76 (and 73 for both with a different seed). When we changed minService time to 15 and maxServiceTime to 25, indicating a moderate service time, the average wait time for both simulations was 189 (and 190 for both with a different seed). Lastly, when we raised minServiceTime to 40 and maxServiceTime to 50, indicating a long service time, the average wait time for both simulations was 427 (and 428 for both with a different seed). We also tried the simulation out with fewer customers and more service points, but no matter what we did, the average wait times for both simulations were the same.  

3. The way it is now, we represent the service points using a vector of queues. Therefore we can only access the customer who is currently being served at the teller (the customer who is at the head of the queue for that particular service point). Since changing lines would mean we would have to access customers at the back and in the middle of the line for each service point, we could no longer use a queue to represent this line. Instead, we could represent the service points using a vector of ordered vectors. Since vectors allow for random access within the data structure, this would allow us to remove customers from the back of the line if that customer would decide to switch lines. 

4. For the multiple line simulation, if lines were dedicated to serving customers of varying lengths of service times, it would improve the average wait time for a customer because customers with long service times would no longer hold up customers with short service times. This would give customers with short service times much shorter wait times. This would bring the average wait time down. For the single line simulation, if tellers could only serve customers with their designated wait times, this would not increase the efficiency. The way it is now, customers with short service times do not get stuck behind customers with long service times because they can go to the next available teller instead of committing to one when they arrive (this was why the single line simulation was faster than the multiple line simulation when the maximum service time was large). 

Program Description: an abstract class which contains a general business simulation which contains the properties that the single and multiple line simulations have in common. Contains a queue of customers with random arrival and service times and manages how each customer is processed once they have arrived at the service point. It is able to increment the time step and can check when the simulation is finished.

 */

import java.util.Vector;
import java.util.Random;
import structure5.*;

public abstract class BusinessSimulation {

    // sequence of customers, prioritized by randomly-generated event time
    protected static PriorityQueue<Customer> eventQueue;
    
    // series of service points where customers queue and are served 
    protected Vector<Queue<Customer>> servicePoints;
    
    // current time step in the simulation 
    protected int time = 0;

    // seed for Random() so that simulation is repeatable 
    protected int seed;

    protected int numCustomers;
    
    // combined wait time for every customer (for testing average wait)
    protected int totalWaitTime = 0; 
    
    // Used to bound the range of service times that Customers require
    protected static final int MIN_SERVICE_TIME = 5; 
    protected static final int MAX_SERVICE_TIME = 20; 

    // pre: numCustomers, numServicePoints, maxEventStart, and seed are all positive
    // post: creates a business simulation by taking the number of customers, number of service points, the latest time a customer
    // may arrive, and the seed.
    public BusinessSimulation(int numCustomers, int numServicePoints,
			      int maxEventStart, int seed) {
	assert(numCustomers >= 0 && numServicePoints > 0 && maxEventStart >= 0 && seed > 0):("All parameters must be positive");
	
	this.numCustomers = numCustomers;
	this.seed = seed;

	generateCustomerSequence(numCustomers, maxEventStart, seed);

	// creates a vector of queues for every teller
	servicePoints = new Vector<Queue<Customer> >(numServicePoints);
	for(int i = 0; i < numServicePoints; i++){
	    servicePoints.add(new QueueVector<Customer>());
	}
    }
    
    // pre: numCustomers, maxEventStart, and seed are all positive 
    // post: creates the eventQueue of customers with randomly generated arrival and service times
    // orders the queue by arrival time
    public static PriorityQueue<Customer> generateCustomerSequence(int numCustomers,
								   int maxEventStart,
								   int seed) {
	assert(numCustomers >= 0 && maxEventStart >= 0 && seed > 0):("All parameters must be positive");

	eventQueue = new PriorityVector<Customer>();
	
	Random arrivalTimeGenerator = new Random(seed);
	Random serviceTimeGenerator = new Random(seed);

	// assigns the arrival time and service time to a customer and adds them to the queue
	for(int i = 0; i < numCustomers; i++){
	    eventQueue.add(new Customer(arrivalTimeGenerator.nextInt(maxEventStart + 1),
					(serviceTimeGenerator.nextInt(MAX_SERVICE_TIME - MIN_SERVICE_TIME + 1) + MIN_SERVICE_TIME)));
	}
	return eventQueue;
    }

    // pre: timeSteps is positive
    // post: the simulation has advanced time steps @timeSteps and returns true if simulation is over
    public boolean step(int timeSteps){
	assert(timeSteps > 0):("Time steps must be positive");

	for(int i = 0; i < timeSteps; i++){
	    return this.step();
	}
	return this.isFinished();
    }
    
    
    // post: increments time steps and returns true if simulation is over
    public boolean step(){
	time = time + 1;
	return this.isFinished();
    }
    
    
    // post: returns a string representation of the simulation
    public String toString() {
	String str = "Time: " + time + "\n";
	str = str + "Event Queue: ";
	if (eventQueue != null) {
	    str = str + eventQueue.toString();
	}
	str = str + "\n";
	
	if (servicePoints != null)  {
	    for (Queue<Customer> sp : servicePoints) {
		str = str + "Service Point: " + sp.toString() + "\n";
	    }
	}
	return str;
    }
    
    // post: returns true if all of the servers are done processing customers
    public boolean isFinished(){
	for(int i = 0; i < servicePoints.size(); i++){
	    if(!servicePoints.get(i).isEmpty()){ // at least one server is still busy
		return false;
	    }
	}
	return eventQueue.isEmpty();
    }
    
    // post: abstract method that moves a customer to the appropriate server
    abstract void goToTeller();

    // post: decrements the service time of the customer at the teller
    // when service time is zero the server is done processing the customer and removes them from the server line
    public void manageTeller(){
	for(int i = 0; i < servicePoints.size(); i++){
	    Customer currentCustomer = servicePoints.get(i).get();
	                                                          
	    if(currentCustomer != null){ 
		if(currentCustomer.getServiceTime()== 0){ // they are done, leave server

		    //used for average wait time thought question
		    currentCustomer.setEndTime(time); 
		    totalWaitTime = totalWaitTime + currentCustomer.getWaitTime(); 

		    servicePoints.get(i).remove();
		    
		} else { // they are not done yet
		    currentCustomer.decrementServiceTime(); 
		}
	    }
	}
    }
    
    // post: returns the average wait time for the simulation (used for thought questions)
    public int averageWaitTime(){
	return totalWaitTime / numCustomers;
    }
}

