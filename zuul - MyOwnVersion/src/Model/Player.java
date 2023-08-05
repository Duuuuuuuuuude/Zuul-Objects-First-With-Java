package Model;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class Player {
	private Set<Item> carriedItems;
	private double maximumCarryWeight;
	private Room currentRoom;
	private Deque<Room> prevRooms;
	private String name;
	
	public Player(String name, double maximumCarryWeight) {
		this.carriedItems = new HashSet<>();
		this.maximumCarryWeight = maximumCarryWeight;
		this.prevRooms = new ArrayDeque<>();
		this.name = name;
	}
	
	public boolean isCarryingItemByName(String itemName) {
		return carriedItems.stream().anyMatch(item -> item.getName().toLowerCase().equals(itemName));
	}
	
	public double getMaximumCarryWeight() {
		return maximumCarryWeight;
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}
	
	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}
	
	public Room pollPrevRoom() {
		return prevRooms.pollFirst();
	}
	
	public void pushPrevRoom(Room prevRoom) {
		prevRooms.push(prevRoom);
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Drops an item that the players is carrying.
	 * 
	 * @param itemName The name of the item to drop.
	 * @return 
	 * @return true if the item was removed.
	 */
	public boolean dropItem(String itemName) {
		for (Item item : carriedItems) {
	        if (item.getName().toLowerCase().equals(itemName.toLowerCase())) {
	            currentRoom.addItem(item);
	            return carriedItems.remove(item);
	        }
	    }
	    return false;
	}
	
	/**
	 * Adds an item to the player, unless it can't get picked up or the maximum carry weight would get exceeded.
	 * 
	 * @param itemName The name of the new item to carry.
	 * @throws ItemNotFoundException if the item isn't found in the current room.
	 * @throws CannotPickUpItemException if the item found cannot be picked up.
	 * @throws ExceedsMaximumCarryWeightException if carrying the item would exceed the maximum allowed weight.
	 */
	public void pickupItem(String itemName) throws Exception {
		Item item = currentRoom.getItemByName(itemName.toLowerCase());
		
		if(item == null) {
			throw new ItemNotFoundException("Can't find item: " + itemName + " in the current room: " + currentRoom.getShortDescription() + ".");
		}
		
		if(!item.getCanBePickedUp()) {
			throw new CannotPickUpItemException("The item: " + item.getName() + " cannot be picked up.");
		}
		
		if((getCurrentCarryWeight() + item.getWeight()) > maximumCarryWeight) {
			throw new ExceedsMaximumCarryWeightException ("The item: " + item.getName() + " cannot be carried because it exceeds the maximum carry weight.");
		} 
		
		carriedItems.add(item);
		currentRoom.removeItem(item);
	}
	
	private double getCurrentCarryWeight() {
		double res = 0;
		for (Item item : carriedItems) {
			res += item.getWeight();
		}
		return res;
	}

	/**
	 * The player eats the first cookie found in the players possession, and the maximum carry weight increases.
	 */
	public boolean eatMagicCookie() {
		boolean cookieFound = false;
		
		for(Item item : carriedItems) {
            if(item instanceof magicCookieFoodItem) {
                magicCookieFoodItem magicCookie = (magicCookieFoodItem) item;
                maximumCarryWeight += magicCookie.getCarryWeightIncreaseInKg();
                cookieFound = carriedItems.remove(magicCookie);
                break;
            }
        }
		return cookieFound;
	}
	
	public String getCarriedItemsWithDescriptionAndAttributes() {
		String res = "";
		
		if (!carriedItems.isEmpty()) {
			res += "\nThese are the items you are currently carrying:";
			res += getCarriedItemsWithDescriptionAndAttributes();
		} else {
			res += "\nYou are not carrying anything.";
		}
		
		for (Item currItem : carriedItems) {
			res += "\n" + currItem.getDescriptionAndAttributes();
		}
		return res;
	}
}
