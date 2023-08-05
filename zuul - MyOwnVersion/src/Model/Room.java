package Model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. "World of Zuul" is a
 * very simple, text based adventure game.
 *
 * A "Room" represents one location in the scenery of the game. It is connected
 * to other rooms via exits. The exits are labelled north, east, south, west.
 * For each direction, the room stores a reference to the neighboring room, or
 * null if there is no exit in that direction.
 * 
 * @author Michael KÃ¶lling and David J. Barnes
 * @version 2016.02.29
 */
public class Room {
	private String description;
	private HashMap<Direction, Room> exits;
	private Set<Item> items;

	/**
	 * Create a room described "description". Initially, it has no exits.
	 * "description" is something like "a kitchen" or "an open court yard".
	 * 
	 * @param description The room's description.
	 */
	public Room(String description) {
		this.description = description;
		this.exits = new HashMap<>();
		this.items = new HashSet<>();

	}

	/**
	 * Define the exits of this room. Every direction either leads to another room
	 * or is null (no exit there).
	 * 
	 * @param direction The direction of the exit.
	 * @param neighbor  The room to which the exit leads.
	 */
	public void setExits(Direction direction, Room neighbor) {
		exits.put(direction, neighbor);
	}

	/**
	 * @return The short description of the room (the one that was defined in the
	 *         constructor).
	 */
	public String getShortDescription() {
		return description;
	}

	/**
	 * return a long description of this room, of the form: You are in the kitchen.
	 * Exits: north west
	 * 
	 * @return "You are " + description + ".\n" + getExitString();
	 */
	public String getLongDescription() {
		return "You are " + description + ".\n\n" + getExitString();
	}

	/**
	 * Return a string describing the room's exits, for example "Exits: north west".
	 * 
	 * @return Details of the room's exits.
	 */
	private String getExitString() // TODO Gør sådan at alle exists ikke er all capital.
	{
		String returnString = "Exits:\n      ";
		Set<Direction> keys = exits.keySet();
		for (Direction exit : keys) {
			returnString += " " + exit;
		}
		return returnString;
	}

	/**
	 * Return the room that is reached if we go from this room in direction
	 * "direction". If there is no room in that direction, return null.
	 * 
	 * @param direction The exit's direction.
	 * @return The room in the given direction.
	 */

	public Room getExit(String direction) {
		if (!isExit(direction)) {
			return null;
		} else {
			return exits.get(Direction.valueOf(direction.toUpperCase()));
		}
	}

	private boolean isExit(String directionString) {
		boolean isDirectionStringUnknown = Direction.UNKNOWN.toString().equals(directionString.toString());// Is direction string equal

		boolean isDirection = false;
		for (Direction direction : Direction.values()) {
			if (direction.toString().equals(directionString.toLowerCase())) {
				isDirection = true;
				break;
			}
		}

		return !isDirectionStringUnknown && isDirection;
	}

	/**
	 * @param ltem
	 * @param string
	 */
	public void addItem(Item item) {
		this.items.add(item);
	}

	public boolean removeItem(Item item) {
		return items.remove(item);
	}

	public Set<Item> getItems() {
		return new HashSet<Item>(items);
	}

	/**
	 * @param Name of the item.
	 * @return found item or null if not found.
	 */
	public Item getItemByName(String itemName) {
		for (Item item : items) {
			if (item.getName().equalsIgnoreCase(itemName)) {
				return item;
			}
		}

		return null;
	}

	/**
	 * @return A long description of the current room, including all the information
	 *         about exists and what it contains.
	 */
	public String getLocationlnfo() {
		String res = getLongDescription();

		res += "\n" + "Items:";
		for (Item currItem : getItems()) {
			res += "\n" + currItem.getDescriptionAndAttributes();
		}

		return res + "\n";
	}
}