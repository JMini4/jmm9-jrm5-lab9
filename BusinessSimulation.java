/*
Josie Maynard and Julia Mini
Jon's lab
4/27/17

Thought Questions:

1. In cases where the maximum service time is short, there is not much of a difference in the time it takes to process all of the customers between the two queue strategies. Whether many customers showed up at once or many customers showed up over a longer period of time, the total time was approximately the same. However when the maximum service time grew, it became clear that the single line strategy was faster at processing all of the customers than the multiple line strategy. For example, when the parameters were (numCustomers=60, numServicePoints=3, maxEventStart=6, minServiceTime= 5, maxServiceTime=20), which indicated many customers arriving in a short period of time with a moderate maximum service time, it took the multiple line strategy 279 steps, and the single line strategy 271. Thus the single line strategy was faster, but not by much. However, when we used the same parameters, but set maxServiceTime to 30, it took the multiple line strategy 400 time steps, and the single line strategy 379. Furthermore, when we increased maxServiceTime to 50, it took the multiple line strategy 667 time steps and single line strategy 583 time steps. Therefore, the greater the average service time, the bigger difference there is between the time it takes to process all of the customers for the two line strategies, and overall the single line simulation takes less than or equal to the amount of time taken by the multiple line simulation.

2. For every scenario we tried, we found that there was an equal average wait time for customers for the two different line techniques. 
FINISH AND WRITE DOWN PARAMETERS

3.

4.

Program Description:

 */

import java.util.Vector;
import java.util.Random;
import structure5.*;

public abstract class BusinessSimulation {

	/* sequence of customers, prioritized by randomly-generated event time */
	protected static PriorityQueue<Customer> eventQueue;

	/* series of service points where customers queue and are served */
	protected Vector<Queue<Customer>> servicePoints;

	/* current time step in the simulation */
	public int time;

	/* seed for Random() so that simulation is repeatable */
	protected int seed;

    protected int numCustomers;
    
    public int totalWaitTime;
    
	/* Used to bound the range of service times that Customers require */
	protected static final int MIN_SERVICE_TIME = 40; //TODO: set appropraitely
	protected static final int MAX_SERVICE_TIME = 50; //TODO: set appropriately

	/**
	 * Creates a BusinessSimulation.
	 * @post the step() function may be called.
	 *
	 * @numCustomers number of random customers that appear in the simulation
	 * @numSerivicePoints number of tellers in this simulation
	 * @maxEventStart latest timeStep that a Customer may appear in the simulation
	 * @seed used to seed a Random() so that simulation is repeatable.
	 */
	public BusinessSimulation(int numCustomers, int numServicePoints,
				  int maxEventStart, int seed) {
	    this.numCustomers = numCustomers;
	    this.seed = seed;
	    totalWaitTime = 0;
	    time = 0;
	    generateCustomerSequence(numCustomers, maxEventStart, seed);
	    servicePoints = new Vector<Queue<Customer> >(numServicePoints);
	    for(int i = 0; i < numServicePoints; i++){
		servicePoints.add(new QueueVector<Customer>());
	    }
	}

	/**
	 * Generates a sequence of Customer objects, stored in a PriorityQueue.
	 * Customer priority is determined by arrival time
	 * @arg numCustomers number of customers to simulate
	 * @arg maxEventStart maximum timeStep that a customer arrives
	 *      in @eventQueue
	 * @arg seed use Random(seed) to make customer sequence repeatable
	 * @return A PriorityQueue that represents Customer arrivals,
	 *         ordered by Customer arrival time
	 */
	public static PriorityQueue<Customer> generateCustomerSequence(int numCustomers,
								       int maxEventStart,
								       int seed) {
	    eventQueue = new PriorityVector<Customer>();
	    Random arrivalTimeGenerator = new Random(seed);
	    Random serviceTimeGenerator = new Random(seed);

	    for(int i = 0; i < numCustomers; i++){
		eventQueue.add(new Customer(arrivalTimeGenerator.nextInt(maxEventStart), (serviceTimeGenerator.nextInt(MAX_SERVICE_TIME-MIN_SERVICE_TIME) + MIN_SERVICE_TIME)));
	    }
	    return eventQueue;
	}

	/**
	 * Advances @timeSteps time steps through the simulation.
	 *
	 * @post the simulation has advanced @timeSteps time steps
	 * @return true if the simulation is over, false otherwise
	 */

    public boolean step(int timeSteps){
	for(int i = 0; i < timeSteps; i++){
	    return this.step();
	}
	return this.isFinished();
    }
    
	/**
	 * Advances 1 time step through the simulation.
	 *
	 * @post the simulation has advanced 1 time step
	 * @return true if the simulation is over, false otherwise
	 */
    public boolean step(){
	time = time + 1;
	return this.isFinished();
    }
    
	/**
	 * @return a string representation of the simulation
	 */
	public String toString() {
		// TODO: modify if needed.
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

    public boolean isFinished(){
	for(int i = 0; i < servicePoints.size(); i++){
	    if(!servicePoints.get(i).isEmpty()){
		return false;
	    }
	}
	return eventQueue.isEmpty();
    }

    public Customer getFirstCustomer(){
	return eventQueue.getFirst();
    }
    
    abstract void goToTeller();

    public void manageTeller(){
	for(int i = 0; i < servicePoints.size(); i++){
	    Customer currentCustomer = servicePoints.get(i).get();

	    if(currentCustomer != null){
		if(currentCustomer.getServiceTime()== 0){
		    currentCustomer.setEndTime(time);
		    totalWaitTime = totalWaitTime + currentCustomer.getWaitTime();
		    servicePoints.get(i).remove();
		} else {
		    currentCustomer.decrementServiceTime();
		}
	    }
	}
    }

    public int averageWaitTime(){
	return totalWaitTime/numCustomers;
    }
    
}

