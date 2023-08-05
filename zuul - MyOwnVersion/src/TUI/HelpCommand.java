package TUI;
import Model.Player;

public class HelpCommand extends Command {

	public HelpCommand(CommandWord firstWord, String secondWord) {
		super(firstWord, secondWord);
	}

	/**
	 * Print out some help information. Here we print some stupid, cryptic message
	 * and a list of the command words.
	 * //	 * @return A String of all the valid commands, in the format: command1 command2
	 * //	 * Print all valid commands to the System.out.
	 */
	@Override
	public boolean execute(Player player) {
		System.out.println("You are lost. You are alone. You wander around at UCN.\n");
		
		showCommands();
		
		return false;
	}

	private void showCommands() {
		System.out.println("Your command words are:");
		for (CommandWord command : CommandWord.values()) {
			if(command != CommandWord.UNKNOWN) {
				System.out.print(command.toString() + "  ");
			}
		}
	}
	
}
