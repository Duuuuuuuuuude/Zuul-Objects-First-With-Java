package TUI;
import Model.Player;

public class QuitCommand extends Command {

	public QuitCommand(CommandWord firstWord, String secondWord) {
		super(firstWord, secondWord);
	}

	/**
	 * "Quit" was entered. Check the rest of the command to see whether we really
	 * quit the game.
	 * 
	 * @return true, if this command quits the game, false otherwise.
	 */
	@Override
	public boolean execute(Player player) {
		if (hasSecondWord()) {
			System.out.println("\nQuit what?");
			return false;
		} else {
			return true;
		}
	}
}
