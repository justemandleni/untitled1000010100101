package com.epi.use.solution;
import java.util.Scanner;

public class User extends Player {
	public User(){
		super();
	}

	private boolean isValidDiscard(String discard[], int aceIndex) {
		if (discard.length == 1 && discard[0].equals(""))
			return true;
		for (int i = 0; i < discard.length; i++) {
			try {
				Integer.parseInt(discard[i]);
			} catch (NumberFormatException nfe) {
				System.out.println("ERROR: " + discard[i] + " is not a number.");
				return false;
			}

			if (Integer.parseInt(discard[i]) < 1 || Integer.parseInt(discard[i]) > 5) {
				System.out.println(discard[i] + " is not a valid position.");
				return false;
			}
		}

		if (discard.length == 4) {
			if (aceIndex == -1) {
				System.out.println("You can only discard at most three cards.");
				return false;
			}
			for (int i = 0; i < discard.length; i++) {
				if (Integer.parseInt(discard[i]) - 1 == aceIndex) {
					System.out.println("You must keep your ace if you want to discard four cards.");
					return false;
				}
			}
		}

		if (discard.length > 4) {
			System.out.println("You can't discard that many cards.");
			return false;
		}

		return true;
	}

    public void makeMove(Deck deck) {
		Scanner input = new Scanner(System.in);
		System.out.println("It is your turn to discard and draw new cards, if you wish.\n");

		String[] discard;
		int aceIndex;
		do {
			hand.displayHand();
			aceIndex = hand.hasAce();
			if (aceIndex != -1)
				System.out.println("Since you have an Ace you can keep the Ace and discard the other four cards.");
			System.out.println("List the card numbers you would like to discard separated by spaces: ");
			String discardString = input.nextLine();
			discard = discardString.split(" ");
		} while (!isValidDiscard(discard, aceIndex));


		for (int i = 0; i < discard.length; i++) {
			if (!discard[i].equals(""))
				discardAndReplaceCard(Integer.parseInt(discard[i]) - 1, deck.dealCard());
		}

		input.close();

	}
    
}
