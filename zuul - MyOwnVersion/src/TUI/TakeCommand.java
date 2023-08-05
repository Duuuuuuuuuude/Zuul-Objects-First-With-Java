package TUI;

import Model.CannotPickUpItemException;
import Model.ExceedsMaximumCarryWeightException;
import Model.ItemNotFoundException;
import Model.Player;

public class TakeCommand extends Command {

	public TakeCommand(CommandWord firstWord, String secondWord) {
		super(firstWord, secondWord);
	}

	/**
	 * To be called if the user enters the "take" command followed by a name of an
	 * item. Player is informed if the item could not be found in the current room,
	 * if the item cannot be picked up or if the player cannot carry anymore.
	 * 
	 * @param command containing the commandword "take" and the name of the item to
	 *                pick up.
	 */
	@Override
	public boolean execute(Player player) {
		String itemName = getSecondWord();

		try {
			if (!hasSecondWord()) {
				System.out.println("\nPickup what?");
				return false;
			}

			if (player.isCarryingItemByName(itemName)) {
				System.out.println("You are already carrying that item.");
				return false;
			}
			
			player.pickupItem(itemName);
			System.out.println("You just picked up \"" + itemName + "\"");
			return false;

		} catch (ItemNotFoundException infe) {
			System.out.println("Could not find \"" + itemName + "\" in this room.");
		} catch (CannotPickUpItemException cpie) {
			System.out.println(itemName + "\" cannot be picked up.");

		} catch (ExceedsMaximumCarryWeightException emcwe) {
			System.out.println("You cannot carry any more.");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
