package TUI;
import Model.Player;

public class UnknownCommand extends Command {

	public UnknownCommand(CommandWord firstWord, String secondWord) {
		super(firstWord, secondWord);
	}

	@Override
	public boolean execute(Player player) {
		System.out.println("I don't know what you mean...");
		return false;
	}
}
