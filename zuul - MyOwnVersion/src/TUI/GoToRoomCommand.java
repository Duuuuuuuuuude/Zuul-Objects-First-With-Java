package TUI;
import Model.Player;
import Model.Room;

public class GoToRoomCommand extends Command {

	public GoToRoomCommand(CommandWord firstWord, String secondWord) {
		super(firstWord, secondWord);
	}

	/**
	 * Try to go in one direction. If there is an exit, enter the new room,
	 * otherwise print an error message.
	 * @return A long description of the current room, including all the
	 * 		   information about exists, what it contains and so on.
	 */
	@Override
	public boolean execute(Player player) {
		if (getSecondWord().toLowerCase().equals("back")) {
			goBack(player);
		} else {
			goToRoom(player);
		}
		return false;
	}
	
	/**
	 * Goes back to the previous room, if the previous room is not where the player
	 * started, otherwise a message is printed, about not being able to go back.
	 */
	private void goBack(Player player) {
		Room prevRoom = player.pollPrevRoom();
		if (prevRoom == null) {
			System.out.println("You can't go further back. This is where you started.");
		}
		player.setCurrentRoom(prevRoom);

		System.out.println(player.getCurrentRoom().getLocationlnfo());
	}

	private void goToRoom(Player player) {
		if (!hasSecondWord()) {
			// if there is no second word, we don't know where to go...
			System.out.println("Go where?");
		}
		String direction = getSecondWord();

		// Try to leave current room.
		Room nextRoom = player.getCurrentRoom().getExit(direction);

		if (nextRoom == null) {
			System.out.println("There is no door!");
		} else {
			player.pushPrevRoom(nextRoom);
			player.setCurrentRoom(nextRoom);
			
			System.out.println(player.getCurrentRoom().getLocationlnfo());
		}
	}
}
