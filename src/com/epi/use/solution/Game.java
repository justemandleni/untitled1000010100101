package com.epi.use.solution;

import java.util.Scanner;
public class Game {
	public static void main(String args[]) {
		int nComputers = 1;
		Scanner input = new Scanner(System.in);

		Deck deck = new Deck();

		User user = new User();
		Opponent[] computer = new Opponent[nComputers];
		for (int i = 0; i < nComputers; i++) {
			computer[i] = new Opponent();
		}


		System.out.println("\nShuffling...");
		System.out.println("Shuffling...");
		System.out.println("Shuffling...");
		System.out.println("Shuffling...\n");
		deck.shuffle();

		System.out.println("Cards are now being dealt to " + nComputers + " computer opponent(s) and 1 human player.\n");
		for (int i = 0; i < 5; i++) {
			user.insertCard(deck.dealCard());
			for (int j = 0; j < nComputers; j++) {
				computer[j].insertCard(deck.dealCard());
			}
		}

		HandValue results[] = new HandValue[nComputers + 1];

		user.makeMove(deck);
		for (int i = 0; i < nComputers; i++) {
			int numDiscards = computer[i].makeMove(deck);
			System.out.println("Computer player " + (i + 1) + " discarded " + numDiscards + " cards.");
		}
		System.out.println("\nYour hand:");
		user.displayHand();
		results[0] = user.evaluateHand();
		for (int i = 0; i < nComputers; i++) {
			System.out.println("Computer player #" + (i + 1) + "'s hand:");
			computer[i].displayHand();
			results[i + 1] = computer[i].evaluateHand();
		}

		int winner = HandValue.compareHands(results, nComputers + 1);
		if (winner < 0)
			System.out.println("There was an unresolved tie");
		else if (winner == 0)
			System.out.println("You Win!");
		else
			System.out.println("Computer #" + winner + " Wins!");

		System.out.println();
		input.close();
	}
}
	