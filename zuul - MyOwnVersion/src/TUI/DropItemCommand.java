package TUI;
import Model.Player;

public class DropItemCommand extends Command {

	public DropItemCommand(CommandWord firstWord, String secondWord) {
		super(firstWord, secondWord);
	}

	/**
	 * Drops an item with the name given in the second word of the command, if the
	 * command "drop" has been entered and if the player is carrying an item with
	 * that name. Otherwise the player is informed about not being able to remove
	 * that item.
	 * 
	 * @param command should contain the command "drop" and the name of the item to
	 *                drop.
	 */
	@Override
	public boolean execute(Player player) {
		if (player.dropItem(getSecondWord())) {
			System.out.println(getSecondWord() + " has been dropped.");
		}


		System.out.println("The item \"" + getSecondWord() + "\" could not be removed.");
		System.out.println(player.getCarriedItemsWithDescriptionAndAttributes());

		return false;
	}
}
