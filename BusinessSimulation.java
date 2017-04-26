import java.util.Vector;
import java.util.Random;
import structure5.*;

public abstract class BusinessSimulation {

	/* sequence of customers, prioritized by randomly-generated event time */
	protected static PriorityQueue<Customer> eventQueue;

	/* series of service points where customers queue and are served */
	protected Vector<Queue<Customer>> servicePoints;

	/* current time step in the simulation */
	protected int time;

	/* seed for Random() so that simulation is repeatable */
	protected int seed;

	/* Used to bound the range of service times that Customers require */
	protected static final int MIN_SERVICE_TIME = 5; //TODO: set appropraitely
	protected static final int MAX_SERVICE_TIME = 20; //TODO: set appropriately

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
	    this.seed = seed;
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
	    Random arrivalTimeGenerator = new Random();
	    Random serviceTimeGenerator = new Random();

	    for(int i = 0; i < numCustomers; i++){
		eventQueue.add(new Customer(arrivalTimeGenerator.nextInt(maxEventStart), serviceTimeGenerator.nextInt(MAX_SERVICE_TIME-MIN_SERVICE_TIME) + MIN_SERVICE_TIME));
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
	time++;
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
}
