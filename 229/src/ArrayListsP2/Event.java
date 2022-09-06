package ArrayListsP2;

/**
 * An example of how you might structure a generic response to some action the
 * user takes. The Java library is full of similar "Event" objects that serve
 * much more complex purposes! The cool thing about this kind of framework is
 * how flexible it is. You can add any properties you want to your event without
 * messing with existing implementations!
 * 
 * @author Doctor Fish
 *
 */
public class Event {
	public int value;

	/**
	 * The sub-type of event is determined by the OpCode
	 * 
	 * @author Doctor Fish
	 *
	 */
	public enum OpCode {
		Insert, Remove, Search, Sort
	};

	/**
	 * OpCode may be passed in the Event, or set elsewhere! Up to you!
	 */
	public OpCode opcode;
	public Object origin;

	public Event(Object origin) {
		this.origin = origin;
	}

}
