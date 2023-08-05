package TUI;
import Model.Player;

public class ItemsCommand extends Command{

	public ItemsCommand(CommandWord firstWord, String secondWord) {
		super(firstWord, secondWord);
	}

	@Override
	public boolean execute(Player player) {
		System.out.print(player.getCarriedItemsWithDescriptionAndAttributes());
		return false;
	}
	
}
