package TUI;
import Model.Item;
import Model.Player;

public class EatCommand extends Command {

	public EatCommand(CommandWord firstWord, String secondWord) {
		super(firstWord, secondWord);
	}

	@Override
	public boolean execute(Player player) {
		if (hasSecondWord() && getSecondWord().equals("cookie")) {
			double carryWeightBefore = player.getMaximumCarryWeight();

			if (player.eatMagicCookie()) {
				double carryWeightAfter = player.getMaximumCarryWeight();
				double carryWeightIncreasedBy = (carryWeightAfter - carryWeightBefore);

				System.out.println("The magic cookie increased your maximum carry weight by " + carryWeightIncreasedBy
						+ Item.getUnitString(carryWeightIncreasedBy) + ", and is now " + carryWeightAfter + Item.getUnitString(carryWeightAfter));
			} else {
				System.out.println("You are not carrying a magic cookie.");
			}
		} else {
			System.out.println("You have eaten now, and you are not hungry anymore.");
		}
		return false;
	}
}
