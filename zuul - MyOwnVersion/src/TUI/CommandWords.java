package TUI;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is part of the "World of Zuul" application. "World of Zuul" is a
 * very simple, text based adventure game.
 * 
 * This class holds an enumeration of all command words known to the game. It is
 * used to recognise commands as they are typed in.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class CommandWords {
	// A mapping between a command word string and the CommandWord class associated
	// with it.
	private Map<String, Class<? extends Command>> validCommands = new HashMap<>();

	/**
	 * Constructor - initialise the command words.
	 */
	public CommandWords() {
		validCommands.put(CommandWord.GO.toString(), GoToRoomCommand.class);
		validCommands.put(CommandWord.HELP.toString(), HelpCommand.class);
		validCommands.put(CommandWord.DROP.toString(), DropItemCommand.class);
		validCommands.put(CommandWord.EAT.toString(), EatCommand.class);
		validCommands.put(CommandWord.ITEMS.toString(), ItemsCommand.class);
		validCommands.put(CommandWord.LOOK.toString(), LookCommand.class);
		validCommands.put(CommandWord.QUIT.toString(), QuitCommand.class);
		validCommands.put(CommandWord.TAKE.toString(), TakeCommand.class);
		validCommands.put(CommandWord.UNKNOWN.toString(), UnknownCommand.class);
	}

	/**
	 * Check whether a given String is a valid command word.
	 * 
	 * @return true if a given string is a valid command, false if it isn't.
	 */
	private boolean isCommand(String aString) {
		if(CommandWord.valueOf(aString.toUpperCase()).equals(CommandWord.UNKNOWN)) {
			return false;
		}
		return validCommands.containsKey(aString);
	}

	/**
	 * Find the CommandWord associated with a command word.
	 * 
	 * @param commandWord The word to look up.
	 * @return The CommandWord corresponding to commandWord, or UNKNOWN if it is not
	 *         a valid command word.
	 */
	private CommandWord getCommandWord(String commandWord) {
		if (isCommand(commandWord)) {
			return CommandWord.valueOf(commandWord.toUpperCase());
		} else {
			return CommandWord.UNKNOWN;
		}
	}

	public Command getCommand(String commandWord, String secondWord) {
		if (!isCommand(commandWord)) {
			return new UnknownCommand(getCommandWord(commandWord), secondWord);
		}

		try {
			Class<? extends Command> commandSubClass = validCommands.get(commandWord);
			// Get the constructor for the command subclass that takes two String
			// parameters.
			Constructor<? extends Command> constructor = commandSubClass.getConstructor(CommandWord.class, String.class);

			// instantiate Command object using the correct subclass of Command.
			return constructor.newInstance(getCommandWord(commandWord), secondWord);
		} catch (Exception e) {
			throw new CommandInstantiationException("Error instantiating Command object using the command word: " + commandWord, e);
		}
	}
}
