package TUI;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * This class is part of the "World of Zuul" application. "World of Zuul" is a
 * very simple, text based adventure game.
 * 
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and tries
 * to interpret the line as a two word command. It returns the command as an
 * object of class Command.
 *
 * The parser has a set of known command words. It checks user input against the
 * known commands, and if the input is not one of the known commands, it returns
 * a command object that is marked as an unknown command.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Parser {
	private CommandWords commands; // holds all valid command words
	private Scanner reader; // source of command input
	private static List<String> scriptList;

	/**
	 * Create a parser to read from the terminal window.
	 */
	public Parser() {
		commands = new CommandWords();
		reader = new Scanner(System.in);
		Parser.scriptList = new ArrayList<>();
	}

	/**
	 * @return The next command from the user.
	 * @throws InvalidCommandWordException
	 */
	public Command getCommand() {
		String inputLine; // will hold the full input line
		String word1 = null;
		String word2 = null;

		System.out.print("> "); // print prompt

		inputLine = reader.nextLine();

		Parser.scriptList.add(inputLine);

		inputLine = inputLine.toLowerCase().trim().strip();

		// Find up to two words on the line.
		@SuppressWarnings("resource")
		Scanner tokenizer = new Scanner(inputLine);
		if (tokenizer.hasNext()) {
			word1 = tokenizer.next(); // get first word
			if (tokenizer.hasNext()) {
				word2 = tokenizer.next(); // get second word
				// note: we just ignore the rest of the input line.
			}
		}
		return commands.getCommand(word1, word2);
	}

	public void createScriptFile(int timesPlayed, String startTimestamp) {
		String folder = "files/Scripts/";
		String filename = folder	+ "game" + timesPlayed + "_" + startTimestamp + " - "
							+ LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "_"
							+ LocalTime.now().format(DateTimeFormatter.ofPattern("Hm")) + ".txt";
		try (FileWriter writer = new FileWriter(filename)) {
			for (String command : scriptList) {
				writer.write(command);
				writer.write('\n');
			}
		} catch (IOException e) {
			System.err.println("There was a problem writing a script to " + filename + " or creating the file.");
		}
	}

}
