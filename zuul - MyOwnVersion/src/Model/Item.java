package Model;
import java.util.Objects;

/**
 * @author Martin Koldste
 *
 */
public class Item {
	private double weight; // in kg.
	private String description;
	private String name; // One word name, otherwise it can't get picked up using a text command.
	private boolean canBePickedUp;

	/**
	 * 
	 * @param weight in kg.s
	 * @param description
	 * @param name has to be a one word name, otherwise it can't get picked up using a text command.
	 * @param canBePickedUp Can this item get picked up.
	 */
	public Item(double weight, String description, String name, boolean canBePickedUp) {
		this.weight = weight;
		this.description = description;
		this.name = name;
		this.canBePickedUp = canBePickedUp;
	}

	public double getWeight() {
		return weight;
	}

	public String getDescription() {
		return description;
	}
	
	public boolean getCanBePickedUp() {
		return this.canBePickedUp;
	}
	
	public String getName() {
		return name;
	}

	public String getDescriptionAndAttributes() {
		String weightString = weight < 1 ? "" + weight * 100 : "" + weight;
		return "\t" + name + "\n\t\tDescription: " + description + "\n" + "\t\t" + "Weight: " + weightString + getUnitString(weight) + "\n";
	}
	
	public static String getUnitString(double weightInKg) {
		return weightInKg >= 1 ? " kg." : " g.";
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Item)) {
			return false;
		}
		Item other = (Item) obj;
		return Objects.equals(name, other.name);
	}

	
	
}
