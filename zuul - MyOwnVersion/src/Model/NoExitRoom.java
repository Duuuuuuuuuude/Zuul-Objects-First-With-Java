package Model;
/* *//**
		 * In case a players ends up in <code>noExitRoom</code>, they should not get the
		 * usual exit message, but instead a call for <code>noExitPrints()</code>, which
		 * needs a secret password.
		 * 
		 * @return "Exits: " + a list of exists, unless it's the noExitRoom, in which
		 *         case, the <code>noExitPrints()</code> method is called.
		 */
/*
 * public class NoExitRoom { // For the noExitRoom. private Room
 * FutureExitForTheNoExitRoom; private boolean secretPassWordHasBeenEntered;
 * private boolean isFirstTimeInRoom; private int noExitCount; // Counts how
 * many times the player tries to leave the noExitRoom, different //TODO En ide
 * kunne være at have flere instance variabler der kan sættes udefra når rummet
 * bliver oprettet, //ligesom man kan med f.eks. isNoExitRoom, sådan at man kan
 * vælge f.eks. password eller den tekst der skal printes når man træder ind i
 * rummet.
 * 
 * public NoExitRoom() { this.secretPassWordHasBeenEntered = false;
 * this.noExitCount = 0; this.isFirstTimeInRoom = true; }
 * 
 * 
 * public void setFutureExitRoom(Room futureExitForTheNoExitRoom) {
 * this.FutureExitForTheNoExitRoom = futureExitForTheNoExitRoom; }
 * 
 * public Room getFutureExitForTheNoExitRoom() { return
 * FutureExitForTheNoExitRoom; }
 * 
 * public boolean getSecretPassWordHasBeenEntered() { return
 * secretPassWordHasBeenEntered; }
 * 
 * public void setSecretPassWordHasBeenEntered(boolean
 * HasSecretPassWordBeenEntered) { secretPassWordHasBeenEntered =
 * HasSecretPassWordBeenEntered;
 * 
 * }
 * 
 *//**
	 * Increments the <code>noExitCount</code> By One.
	 * 
	 */
/*
 * public void incrementNoExitCount() { this.noExitCount++; }
 * 
 * public int getNoExitCount() { return noExitCount; }
 * 
 * public boolean getIsFirstTimeInRoom() { return isFirstTimeInRoom; }
 * 
 * public void setIsFirstTimeInRoom(boolean firstTime) { this.isFirstTimeInRoom
 * = firstTime; }
 * 
 * boolean hasSecretExitBeenCreated() { return
 * exits.containsKey("the secret password"); }
 * 
 *//**
	 * Prints out messages until noExitCount reaches 7, and at that point, a new
	 * exit opens up, that only requires a secret password to go through.
	 * 
	 * @throws InterruptedException
	 *//*
		 * private void noExitPrints() { // noExitRoom, // med Room som superclass, der
		 * er selvfølgelig også // andre ting skal skal flyttes, // også selvom denne
		 * method ikke ender med at blive // flyttet alligevel. if
		 * (currentRoom.getNoExitCount() == 0 || currentRoom.getNoExitCount() <= 4) {
		 * currentRoom.incrementNoExitCount(); System.out.println("There is no door!");
		 * 
		 * } else if (currentRoom.getNoExitCount() == 5) {
		 * currentRoom.incrementNoExitCount();
		 * 
		 * System.out.println("You are " + currentRoom.getDescription() + "\n");
		 * System.out.print("Exits:"); // Thread.sleep(250); System.out.print("."); //
		 * Thread.sleep(1000); System.out.print("."); // Thread.sleep(2000);
		 * System.out.print(".");
		 * 
		 * // Thread.sleep(1500); System.out.print(" null\n\n"); // Thread.sleep(500);
		 * System.out.println("Yep, you're fucked\n");
		 * 
		 * } else if (currentRoom.getNoExitCount() == 6) {
		 * currentRoom.incrementNoExitCount(); System.out.print(
		 * "Exits: I'm just gonna say it like it is, it looks like you're pretty much just fucked!! THERE ARE NO EXISTS!!!! U+1F631\\n\""
		 * ); System.out.println();
		 * 
		 * } else if (currentRoom.getNoExitCount() >= 7 && currentRoom.getNoExitCount()
		 * <= 12) { currentRoom.incrementNoExitCount();
		 * System.out.println("Yep, still fucked\n");
		 * 
		 * } else if (currentRoom.getNoExitCount() >= 13) { if
		 * (currentRoom.getNoExitCount() == 13) { currentRoom.incrementNoExitCount();
		 * System.out.println("Ok, fine. I'll let you out\n"); // Thread.sleep(5000);
		 * System.out.println("If you say the secret password (insert trollface here)\n"
		 * ); currentRoom.addEntryToExits("the secret password",
		 * currentRoom.getFutureExitForTheNoExitRoom()); // Sets the secret exit, a
		 * player can't get out before this happens. } else {
		 * System.out.println("Just write the secret password\n"); } }
		 * 
		 * Scanner reader = new Scanner(System.in); Room noExitRoom = currentRoom;
		 * 
		 * while (!noExitRoom.getSecretPassWordHasBeenEntered()) { // Runs as long as
		 * the secret password hasn't yet. String inputLine =
		 * reader.nextLine().toLowerCase().trim().strip(); if
		 * (!(inputLine.equals("the secret password"))) { // If the incorrect secret
		 * password or a command has // been entered. Command command =
		 * this.parser.getCommandFromInputLine(inputLine); String commandWord =
		 * command.getCommandWord();
		 * 
		 * if (commandWord != null) { //TODO Virker ikke som det skal if
		 * (commandWord.equals("quit")) { if(!processCommand(command)) { continue; }
		 * gameEnded = true; return; }else { processCommand(command); } } else { if
		 * (inputLine.equals("what is the secret password") ||
		 * inputLine.equals("what is the secret password?")) {
		 * System.out.println("It's a secret, dumbass!!!!"); } else {
		 * System.out.println("Wrong password\n"); } }
		 * 
		 * } else { noExitRoom.setSecretPassWordHasBeenEntered(true); // If the correct
		 * secret password has been // entered. if (noExitRoom.getIsFirstTimeInRoom()) {
		 * System.out.println("Congratulation, you figured it out!!!!!\n"); } else {
		 * System.out.println("You have remembered the password correctly, good job.\n"
		 * ); } Command command = new Command("go", "the secret password"); // opens up
		 * the secret Exit. goRoom(command); // Goes through the exit. }
		 * 
		 * noExitRoom.setIsFirstTimeInRoom(false); // Different text is shown if the
		 * password hass been entered, // depending on if it's the first time visiting
		 * the room or not. noExitRoom.setSecretPassWordHasBeenEntered(false); } }
		 * 
		 * if (currentRoom.getIsNoExitRoom() &&
		 * !currentRoom.getSecretPassWordHasBeenEntered()) { noExitPrints(); return; //
		 * Needs to be there, unless your want problem with the //
		 * <code>noExitPrints()</code> method going into endless loop after the secret
		 * // password has been entered. } room noExitRoom; noExitRoom = new
		 * Room("in an unkown room, all by your self. Naked... for some reason");
		 * 
		 * noExitRoom.setToNoExitRoom(); noExitRoom.setFutureExitRoom(auditorium); }
		 */