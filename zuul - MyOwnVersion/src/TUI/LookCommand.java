package TUI;
import Model.Player;

public class LookCommand extends Command {

	public LookCommand(CommandWord firstWord, String secondWord) {
		super(firstWord, secondWord);
	}

	/**
	 * If "look" has been entered, this method calls the
	 * <code>player.getCurrentRoom().getLongDescription</code> method, and prints
	 * the <code>string</code> containing a long description of the
	 * <code>currentRoom</code>.
	 */
	@Override
	public boolean execute(Player player) {
		System.out.println(player.getCurrentRoom().getLongDescription());
		return false;
	}

}
