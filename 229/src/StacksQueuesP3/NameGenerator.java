package StacksQueuesP3;

public class NameGenerator {

	public static String[] names = { "Bob", "Susie", "Steve", "Joe", "Fred", "Ted", "Jed", "Jeb",
			"Meb", "Melvin", "Kevin", "Kelvin", "Xerxes", "Twerkxes", "Alpha", "Beta", "Delta",
			"Gamma", "Omicron", "Phi", "Epsilon", "Gary", "Larry", "Mary", "Barry" };

	/**
	 * Pulls a random name from the names list
	 * 
	 * @return
	 */
	public static String getRandomName() {
		return names[(int) (Math.random() * names.length)];
	}

}
