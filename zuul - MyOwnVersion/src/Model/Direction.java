package Model;
/**
 * Representations for all the valid directions for the game,
 * along with a string in a particular language.
 * 
 * @author Martin Koldste
 */
public enum Direction {
	// A value for each direction along with its
	// corresponding user interface string.
	NORTH("north"), SOUTH("south"), EAST("east"), WEST("west"), RIGHT("right"), LEFT("left"), DOWN("down"), UP("up"), UNKNOWN("unknown");

	private String directionString;

	/**
	 * Initialize with the corresponding direction string.
	 * @param directionString The direction string.
	 */
	Direction(String directionString) {
		this.directionString = directionString;
	}
	
	/**
	 * @return The direction as a string.
	 */
	public String toString() {
		return directionString;
	}
}
