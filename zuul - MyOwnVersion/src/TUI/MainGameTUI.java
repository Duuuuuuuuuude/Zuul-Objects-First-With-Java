package TUI;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Set;

import Model.Direction;
import Model.Item;
import Model.Player;
import Model.Room;
import Model.magicCookieFoodItem;

/**
 * This class is the main class of the "World of Zuul" application. "World of
 * Zuul" is a very simple, text based adventure game. Users can walk around some
 * scenery. That's all. It should really be extended to make it more
 * interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * method.
 * 
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 * 
 * @author Michael KÃ¶lling and David J. Barnes
 * @version 2016.02.29
 */
// TODO Serialization.
// TODO Tre-lags arkitektur.

// TODO Fra side 292 i bogen.
// TODO Exercise 8.41: 
// 			Add some form of time limit to your game.If a certain task
// 			is not completed in a specific time, the player loses. A time limit can easily 
// 			be implemented by counting the number of moves or the number of entered
// 			commands. You do not need to use real time.
// TODO Exercise 8.42:
// 			Implement a trapdoor somewhere (or some other form of door
// 			that you can only cross one way).
// TODO Exercise 8.43:
// 			Add a beamer to the game. A beamer is a device that can
//			be charged and fired. When you charge the beamer, it memorizes the current
// 			room. When you fire the beamer, it transports you immediately back to the 
// 			room it was charged in. The beamer could either be standart equipment or an item that the player can find.Of course, you need commands to charge and fire
// 			the beamer.
// TODO Exercise 8.44:
// 			Add locked doors to your game. The player needs to find (or 
// 			otherwise obtain) a key to open a door.
// TODO Exercise 8.45:
// 			Add a transporter room. Whenever the player enters this room,
// 			he/she is randomly transported into one of other rooms. Note: Coming up
// 			with a good design for this is not trivial. It might be interesting to discuss
// 			design alternatives for this with other students. (We disucss design alternatives
// 			for this task at the end of Chapter 11. The adventuous our advanced reader may
//			want to skip ahead and have a look).
// TODO Comments

public class MainGameTUI {

	private Parser parser;
	private final String StartTimestamp = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "_"
											+ LocalTime.now().format(DateTimeFormatter.ofPattern("Hm"));
	private Player player;

	public static void main(String[] args) {
		MainGameTUI game = new MainGameTUI();
		game.play();
	}

	/**
	 * Create the game and initialise its internal map and an ArrayDeque to contain
	 * previous rooms as a stack.
	 */
	public MainGameTUI() {
		this.player = new Player("Karen", 0.50); // TODO: Ask for players name.
		createRooms();
		parser = new Parser();
		increaseTimesPlayedByOne();
	}

	/**
	 * @Author Martin Koldste
	 */
	private void increaseTimesPlayedByOne() { // TODO: Times player for each player.
		int prevTimesPlayedInt = getTimesPlayed();
		int newTimesPlayedInt = ++prevTimesPlayedInt;
		String newTimesPlayedString = "" + newTimesPlayedInt;

		byte[] bytes = newTimesPlayedString.getBytes(StandardCharsets.UTF_8);
		String newTimesPlayedUTF8EncodingString = new String(bytes, StandardCharsets.UTF_8);
//		assert newTimesPlayedString.equals(newTimesPlayedUTF8EncodingString)
//				: "newTimesPlayedString does not equal newTimesPlayedUTF8EncodingString in " + this.getClass()
//						+ ".increaseTimesPlayedByOne()";

		File txtFile = new File("files/TimesPlayed.txt");
		try (FileWriter writer = new FileWriter(txtFile)) {
			writer.write(newTimesPlayedUTF8EncodingString);
		} catch (IOException ioe) {
			System.err.println("There was a problem writing timesPlayed to files/TimesPlayed.txt");
			ioe.printStackTrace();
		}

	}

	/**
	 * @return number of times this game has been played;
	 * @throws FileNotFoundException
	 */
	private int getTimesPlayed() {
		File file = new File("files/TimesPlayed.txt");
		int res = 0;
		try (Scanner scanner = new Scanner(file)) {
			res = scanner.nextInt();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block. Måske en filewriter ligesom i Parser der
			// opretter en .txt fil der indeholder 0?
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Create all the rooms and link their exits together.
	 */
	private void createRooms() {
		Room outsideMainEntrance, insideMainEntrance, reception, hallwayZero, office, outsideLibraryBookshop, libraryBookShop,
				storageSpaceConferenceRoom, employeeKitchen, conferenceRoom, hallwayOne, teachersRoom, outsideTeachersRoom, auditorium, classRoomOne,
				hallwayTwo, classRoomTwo, outsideClassRoomTwo, outsideCommonArea, commonArea, classRoomFour, hallwayThree, classRoomThree,
				outsideClassRoomThree, outsideCanteen, canteenAndFridayBar, hallwayFour, storageSpaceWorkshop, canteenKitchen, techLab, workshop,
				outsideCanteenKitchen, outsideTechLab, outsideWorkshop, outsideSouthWestCorner, outsideSouthEastCorner, outsideNorthEastCorner,
				outsideNorthWestCorner, hallwayFive, noExitRoom, cellar, officeWestWall;

		// create the rooms
		reception = new Room("behind the reception desk");
		office = new Room("in the administration office");
		libraryBookShop = new Room("in the library/bookshop");
		storageSpaceConferenceRoom = new Room("in the storage space of the conference room");
		employeeKitchen = new Room("in the employees kitchen");
		conferenceRoom = new Room("in the conference room");
		teachersRoom = new Room("in the teachers' room");
		auditorium = new Room("in the auditorium");
		commonArea = new Room("in the common area");
		canteenAndFridayBar = new Room("in the cantee/friday bar");
		storageSpaceWorkshop = new Room("in the storage space of the workshop");
		canteenKitchen = new Room("in the canteen kitchen");
		techLab = new Room("in the techLab");
		workshop = new Room("in the workshop");
		insideMainEntrance = new Room("in front of the reception desk");
		cellar = new Room("in the cellar, welcome to the Fight Club");

		officeWestWall = new Room("facing the east wall inside the office");

		// TODO NoExitRoom er ikke gjort færdigt endnu.
		// noExitRoom = new Room("in an unkown room, all by your self. Naked... for some
		// reason");

		outsideMainEntrance = new Room("outside the main entrance of UCN");
		outsideLibraryBookshop = new Room("outside at the entrance to the library/bookshop (west)");
		outsideTeachersRoom = new Room("outside at the entrance to the teachers' room (east)");
		outsideClassRoomTwo = new Room("outside at the entrance to class room 2 (east)");
		outsideCommonArea = new Room("outside at the entrance to the common area (west)");
		outsideClassRoomThree = new Room("outside at the entrance to class room 3 (east)");
		outsideCanteen = new Room("outside at the entrance to the canteen/friday bar (west)");
		outsideCanteenKitchen = new Room("outside at the entrance to the canteen kitchen (north)");
		outsideTechLab = new Room("outside at the entrance to the techLab (north)");
		outsideWorkshop = new Room("outside at the entrance to the workshop (north)");

		outsideSouthWestCorner = new Room("outside at the southwest corner");
		outsideSouthEastCorner = new Room("outside at the southeast corner");
		outsideNorthEastCorner = new Room("outside at the northeast corner");
		outsideNorthWestCorner = new Room("outside at the northwest corner");

		classRoomOne = new Room("in class room 1 - Datamatiker class 1");
		classRoomTwo = new Room("in class room 2 - Datamatiker class 2");
		classRoomThree = new Room("in class room 3 - Computer science class 2");
		classRoomFour = new Room("in class room 4 - Computer science class 1");

		hallwayZero = new Room("hallway left of the main entrance");
		hallwayOne = new Room("in the first part of the hallway");
		hallwayTwo = new Room("in the second part of the hallway");
		hallwayThree = new Room("in the third part of the hallway");
		hallwayFour = new Room("in the fourth part of the hallway");
		hallwayFive = new Room("in the fifth part of the hallway");

		// initialise room exits
		outsideMainEntrance.setExits(Direction.NORTH, insideMainEntrance);
		outsideMainEntrance.setExits(Direction.EAST, outsideSouthEastCorner);
		outsideMainEntrance.setExits(Direction.WEST, outsideSouthWestCorner);

		insideMainEntrance.setExits(Direction.NORTH, hallwayOne);
		insideMainEntrance.setExits(Direction.EAST, reception);
		insideMainEntrance.setExits(Direction.SOUTH, outsideMainEntrance);
		insideMainEntrance.setExits(Direction.WEST, hallwayZero);

		hallwayZero.setExits(Direction.EAST, insideMainEntrance);
		hallwayZero.setExits(Direction.WEST, libraryBookShop);

		reception.setExits(Direction.EAST, office);
		reception.setExits(Direction.WEST, insideMainEntrance);

		office.setExits(Direction.WEST, officeWestWall);
		officeWestWall.setExits(Direction.RIGHT, employeeKitchen);
		officeWestWall.setExits(Direction.LEFT, reception);

		outsideLibraryBookshop.setExits(Direction.NORTH, outsideCommonArea);
		outsideLibraryBookshop.setExits(Direction.EAST, libraryBookShop);
		outsideLibraryBookshop.setExits(Direction.SOUTH, outsideSouthWestCorner);

		libraryBookShop.setExits(Direction.EAST, hallwayZero);
		libraryBookShop.setExits(Direction.WEST, outsideLibraryBookshop);

		storageSpaceConferenceRoom.setExits(Direction.NORTH, conferenceRoom);

		employeeKitchen.setExits(Direction.NORTH, teachersRoom);
		employeeKitchen.setExits(Direction.EAST, office);

		conferenceRoom.setExits(Direction.EAST, hallwayOne);
		conferenceRoom.setExits(Direction.SOUTH, storageSpaceConferenceRoom);

		hallwayOne.setExits(Direction.NORTH, hallwayTwo);
		hallwayOne.setExits(Direction.EAST, teachersRoom);
		hallwayOne.setExits(Direction.SOUTH, insideMainEntrance);
		hallwayOne.setExits(Direction.WEST, conferenceRoom);

		teachersRoom.setExits(Direction.EAST, outsideTeachersRoom);
		teachersRoom.setExits(Direction.SOUTH, employeeKitchen);
		teachersRoom.setExits(Direction.WEST, hallwayOne);
		teachersRoom.setExits(Direction.DOWN, cellar);

		outsideTeachersRoom.setExits(Direction.NORTH, outsideClassRoomTwo);
		outsideTeachersRoom.setExits(Direction.SOUTH, outsideSouthWestCorner);
		outsideTeachersRoom.setExits(Direction.WEST, teachersRoom);

		auditorium.setExits(Direction.NORTH, commonArea);
		// auditorium.setExits("south", noExitRoom);

		classRoomOne.setExits(Direction.EAST, hallwayTwo);

		hallwayTwo.setExits(Direction.NORTH, hallwayThree);
		hallwayTwo.setExits(Direction.EAST, classRoomTwo);
		hallwayTwo.setExits(Direction.SOUTH, hallwayOne);
		hallwayTwo.setExits(Direction.WEST, classRoomTwo);

		classRoomTwo.setExits(Direction.EAST, outsideClassRoomTwo);
		classRoomTwo.setExits(Direction.WEST, hallwayTwo);

		outsideClassRoomTwo.setExits(Direction.NORTH, outsideClassRoomThree);
		outsideClassRoomTwo.setExits(Direction.SOUTH, outsideTeachersRoom);
		outsideClassRoomTwo.setExits(Direction.WEST, classRoomTwo);

		outsideCommonArea.setExits(Direction.NORTH, outsideCanteen);
		outsideCommonArea.setExits(Direction.EAST, commonArea);
		outsideCommonArea.setExits(Direction.SOUTH, outsideLibraryBookshop);

		commonArea.setExits(Direction.NORTH, canteenAndFridayBar);
		commonArea.setExits(Direction.EAST, hallwayThree);
		commonArea.setExits(Direction.SOUTH, auditorium);
		commonArea.setExits(Direction.WEST, outsideCommonArea);

		classRoomFour.setExits(Direction.EAST, hallwayFour);

		hallwayThree.setExits(Direction.NORTH, hallwayFour);
		hallwayThree.setExits(Direction.SOUTH, hallwayTwo);
		hallwayThree.setExits(Direction.WEST, commonArea);

		classRoomThree.setExits(Direction.EAST, outsideClassRoomThree);
		classRoomThree.setExits(Direction.WEST, hallwayFour);

		outsideClassRoomThree.setExits(Direction.NORTH, outsideNorthEastCorner);
		outsideClassRoomThree.setExits(Direction.SOUTH, outsideClassRoomTwo);
		outsideClassRoomThree.setExits(Direction.WEST, classRoomThree);

		outsideCanteen.setExits(Direction.EAST, outsideTechLab);
		outsideCanteen.setExits(Direction.SOUTH, canteenKitchen);
		outsideCanteen.setExits(Direction.WEST, outsideNorthWestCorner);

		canteenAndFridayBar.setExits(Direction.NORTH, canteenKitchen);
		canteenAndFridayBar.setExits(Direction.SOUTH, commonArea);
		canteenAndFridayBar.setExits(Direction.WEST, outsideCanteen);

		hallwayFour.setExits(Direction.NORTH, hallwayFive);
		hallwayFour.setExits(Direction.EAST, classRoomThree);
		hallwayFour.setExits(Direction.SOUTH, hallwayThree);
		hallwayFour.setExits(Direction.WEST, classRoomFour);

		storageSpaceWorkshop.setExits(Direction.NORTH, workshop);

		canteenKitchen.setExits(Direction.NORTH, outsideCanteenKitchen);
		canteenKitchen.setExits(Direction.SOUTH, canteenAndFridayBar);

		techLab.setExits(Direction.NORTH, outsideTechLab);
		techLab.setExits(Direction.EAST, hallwayFive);

		workshop.setExits(Direction.NORTH, outsideWorkshop);
		workshop.setExits(Direction.SOUTH, storageSpaceWorkshop);
		workshop.setExits(Direction.WEST, hallwayFive);

		outsideCanteenKitchen.setExits(Direction.EAST, outsideTechLab);
		outsideCanteenKitchen.setExits(Direction.SOUTH, canteenKitchen);
		outsideCanteenKitchen.setExits(Direction.WEST, outsideNorthWestCorner);

		outsideTechLab.setExits(Direction.EAST, outsideWorkshop);
		outsideTechLab.setExits(Direction.SOUTH, techLab);
		outsideTechLab.setExits(Direction.WEST, outsideCanteenKitchen);

		outsideWorkshop.setExits(Direction.EAST, outsideNorthEastCorner);
		outsideWorkshop.setExits(Direction.SOUTH, workshop);
		outsideWorkshop.setExits(Direction.WEST, outsideTechLab);

		outsideSouthWestCorner.setExits(Direction.NORTH, outsideLibraryBookshop);
		outsideSouthWestCorner.setExits(Direction.EAST, outsideMainEntrance);

		outsideSouthEastCorner.setExits(Direction.NORTH, outsideTeachersRoom);
		outsideSouthEastCorner.setExits(Direction.WEST, outsideMainEntrance);

		outsideNorthEastCorner.setExits(Direction.SOUTH, outsideClassRoomThree);
		outsideNorthEastCorner.setExits(Direction.WEST, outsideWorkshop);

		outsideNorthWestCorner.setExits(Direction.EAST, outsideCanteenKitchen);
		outsideNorthWestCorner.setExits(Direction.SOUTH, outsideCanteen);

		hallwayFive.setExits(Direction.EAST, workshop);
		hallwayFive.setExits(Direction.SOUTH, hallwayFour);
		hallwayFive.setExits(Direction.WEST, techLab);

		cellar.setExits(Direction.UP, teachersRoom);

		// create items. weights are in kilo. No item must ever have the same name as
		// another item.
		Item istvansWhiteBoardEraser = new Item(0.2, "István's whiteboard eraser, that he can never find.", "István's_Eraser", true);
		Item bluePen = new Item(0.05, "A blue pen.", "Blue_pen", true);
		Item heavyItem = new Item(1000, "A heavy item.", "Anvil", false);
		Item usedSock = new Item(0.40, "A used socked", "Used_sock", true);

		Item magicCookie = new magicCookieFoodItem(0.15, "This magic cookie increases the weight that you can carry.", "Magic_Cookie", true, 1);

		// Add items to each room.
		employeeKitchen.addItem(istvansWhiteBoardEraser);
		employeeKitchen.addItem(bluePen);
		employeeKitchen.addItem(heavyItem);
		employeeKitchen.addItem(usedSock);
		employeeKitchen.addItem(magicCookie);

		player.setCurrentRoom(outsideMainEntrance); // start game outside main entrance.
	}

	/**
	 * Main play routine. Loops until end of play.
	 * 
	 */
	public void play() {

		printWelcome();
		// Enter the main command loop. Here we repeatedly read commands and
		// execute them until the game is over.

		boolean finished = false;
		while (!finished) {
			Command command = parser.getCommand();
			
			finished = command.execute(player);
		}
		parser.createScriptFile(getTimesPlayed(), StartTimestamp);

		System.out.println("Thank you for playing. Good bye.");
	}

	/**
	 * Print out the opening message for the player.
	 */
	private void printWelcome() {
		System.out.println();
		System.out.println("Welcome to the World of Zuul!");
		System.out.println("World of Zuul is a new, incredibly boring adventure game.");
		System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
		System.out.println();

		System.out.print(player.getCurrentRoom().getLocationlnfo());
	}

//	/**
//	 * Given a command, process (that is: execute) the command.
//	 * 
//	 * @param command The command to be processed.
//	 * @return true If the command ends the game, false otherwise.
//	 */
//	private boolean processCommand(Command command) {
//		boolean wantToQuit = false;
//		CommandWord commandWord = command.getCommandWord();
//
//		switch (commandWord) {
////			case UNKNOWN:
////				System.out.println("I don't know what you mean...");
////				return false;
////				
////			case HELP:
////				printHelp();
////				break;
////				
////			case GO:
////				if (command.getSecondWord().toLowerCase().equals("back")) {
////					goBack();
////				} else {
////					goRoom(command);
////				}
////				break;
////
////			case QUIT:
////				wantToQuit = quit(command);
////				break;
////				
////			case LOOK:
////				look();
////				break;
////				
////			case EAT:
////				eat(command);
////				break;
////				
////			case TAKE:
////				pickupItem(command);
////				break;
////				
////			case DROP:
////				dropItem(command);
////				break;
////				
////			case ITEMS:
////				printPlayersCarriedItemsWithDescriptionAndAttributes();
////				break;
////		}
////		return wantToQuit;
//	}

	// implementations of user commands:

	/**
	 * Print out some help information. Here we print some stupid, cryptic message
	 * and a list of the command words.
	 */
//	private void printHelp() {
//		System.out.println("You are lost. You are alone. You wander around at UCN.");
//		System.out.println();
//		System.out.println("Your command words are:");
//		System.out.println(parser.showCommands());
//	}

//	/**
//	 * "Quit" was entered. Check the rest of the command to see whether we really
//	 * quit the game.
//	 * 
//	 * @return true, if this command quits the game, false otherwise.
//	 */
//	private boolean quit(Command command) {
//		if (command.hasSecondWord()) {
//			System.out.println("Quit what?");
//			return false;
//		} else {
//			return true; // signal that we want to quit
//		}
//	}

//	/**
//	 * If "look" has been entered, this method calls the
//	 * <code>player.getCurrentRoom().getLongDescription</code> method, and prints the <code>string</code>
//	 * containing a long description of the <code>currentRoom</code>.
//	 */
//	private void look() {
//		System.out.println(player.getCurrentRoom().getLongDescription());
//	}

	/**
	 * Drops an item with the name given in the second word of the command, if the
	 * command "drop" has been entered and if the player is carrying an item with
	 * that name. Otherwise the player is informed about not being able to remove
	 * that item.
	 * 
	 * @param command should contain the command "drop" and the name of the item to
	 *                drop.
	 */
//	private void dropItem(Command command) {
//		if (player.dropItem(command.getSecondWord())) {
//			System.out.println(command.getSecondWord() + " has been dropped.");
//
//		} else {
//			System.out.println("The item \"" + command.getSecondWord() + "\" could not be removed.\n");
//			if (!player.getCarriedItems().isEmpty()) {
//				printPlayersCarriedItemsWithDescriptionAndAttributes();
//			} else {
//				System.out.println("You are not carrying anything.");
//			}
//		}
//	}

	/**
	 * To be called if the user enters the "take" command followed by a name of an
	 * item. Player is informed if the item could not be found in the current room,
	 * if the item cannot be picked up or if the player cannot carry anymore.
	 * 
	 * @param command containing the commandword "take" and the name of the item to
	 *                pick up.
	 */
//	private void pickupItem(Command command) {
//		if (!command.hasSecondWord()) {
//			System.out.println("Pickup what?");
//			return;
//		}
//
//		String itemName = command.getSecondWord();
//
//		try {
//			player.pickupItem(itemName);
//			System.out.println("You just picked up \"" + itemName + "\"");
//
//		} catch (ItemNotFoundException infe) {
//			if (player.isCarryingItemByName(itemName)) {
//				System.out.println("You are already carrying that item.");
//			} else {
//				System.out.println("Could not find \"" + itemName + "\" in this room.");
//			}
//
//		} catch (CannotPickUpItemException cpie) {
//			System.out.println("\"" + itemName + "\" cannot be picked up.");
//
//		} catch (ExceedsMaximumCarryWeightException emcwe) {
//			System.out.println("You cannot carry any more.");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//
//		}
//	}

//	private void printPlayersCarriedItemsWithDescriptionAndAttributes() {
//		Set<Item> carriedItems = player.getCarriedItems();
//
//		if (carriedItems.isEmpty()) {
//			System.out.println("You are not carrying anything.");
//			return;
//		}
//
//		System.out.println("These are the items you are currently carrying:");
//		for (Item currItem : carriedItems) {
//			System.out.println(currItem.getDescriptionAndAttributes());
//		}
//	}

//	private void eat(Command command) {
//		if (command.hasSecondWord() && command.getSecondWord().equals("cookie")) {
//			double carryWeightBefore = player.getMaximumCarryWeight();
//
//			if (player.eatMagicCookie()) {
//				double carryWeightAfter = player.getMaximumCarryWeight();
//				double carryWeightIncreasedBy = (carryWeightAfter - carryWeightBefore);
//
//				System.out.println("The magic cookie increased your maximum carry weight by "	+ carryWeightIncreasedBy
//									+ Item.getUnitString(carryWeightIncreasedBy) + ", and is now " + carryWeightAfter
//									+ Item.getUnitString(carryWeightAfter));
//			} else {
//				System.out.println("You are not carrying a magic cookie.");
//			}
//		} else {
//			System.out.println("You have eaten now, and you are not hungry anymore.");
//		}
//	}
}
