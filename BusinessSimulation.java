import java.util.Vector;
import java.util.Random;
import structure5.*;

public abstract class BusinessSimulation {

	/* sequence of customers, prioritized by randomly-generated event time */
	PriorityQueue<Customer> eventQueue;

	/* series of service points where customers queue and are served */
	Vector<Queue<Customer>> servicePoints;

	/* current time step in the simulation */
	int time;

	/* seed for Random() so that simulation is repeatable */
	int seed;

	/* Used to bound the range of service times that Customers require */
	static final int MIN_SERVICE_TIME = 5; //TODO: set appropraitely
	static final int MAX_SERVICE_TIME = 20; //TODO: set appropriately

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

	    //when a teller is free, remove the head of the queue as long as their arrival time is <= current time
	    
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
	    eventQueue = new PriorityVector();
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
    abstract public boolean step(int timeSteps){
	time = time + timeSteps;
	return eventQueue.isEmpty(); //add that all the customers are being served
	
    };

	/**
	 * Advances 1 time step through the simulation.
	 *
	 * @post the simulation has advanced 1 time step
	 * @return true if the simulation is over, false otherwise
	 */
    abstract public boolean step(){
	time++;
	return eventQueue.isEmpty(); //add that all the customers are being served  
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
}
