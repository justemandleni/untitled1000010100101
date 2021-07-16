package com.epi.use.solution;

public class Hand {
	private Card[] hand;
	private int numCards;

	public Hand() {
		hand = new Card[5];
		numCards = 0;
	}

	public void insertCard(Card card) {
		hand[numCards] = card;
		numCards++;
	}

	public void discardAndReplaceCard(int i, Card card) {
		hand[i] = card;
	}

	private void sortHand() {
		for (int i = 0; i < 5; i++) {
			for (int j = i; j > 0; j--) {
				if (hand[j - 1].getRank().ordinal() < hand[j].getRank().ordinal()) {
					Card temp = hand[j];
					hand[j] = hand[j - 1];
					hand[j - 1] = temp;
				}
			}
		}
	}

	public int hasAce() {
		for (int i = 0; i < 5; i++) {
			if (hand[i].getRank().getSymbol() == "A")
				return i;
		}
		return -1;
	}

	private void sortHandByMatches() {
		int matchCount[] = new int[5];
		for (int i = 0; i < 5; i++) matchCount[i] = 0;
		sortHand();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++)
				if (hand[i].getRank().ordinal() == hand[j].getRank().ordinal()) {
					matchCount[i]++;
				}
		}

		for (int i = 0; i < 5; i++) {
			for (int j = i; j > 0; j--) {
				if (matchCount[j - 1] < matchCount[j]) {
					int tempCount = matchCount[j];
					matchCount[j] = matchCount[j - 1];
					matchCount[j - 1] = tempCount;

					Card temp = hand[j];
					hand[j] = hand[j - 1];
					hand[j - 1] = temp;
				}
			}
		}
	}

	public void displayHand() {
		sortHandByMatches();
		for (int i = 0; i < 5; i++)
			System.out.print(i + 1 + ". " + hand[i] + "\t");
		System.out.println();
	}

	private String getRank(int rank) {
		switch (rank) {
			case 2:
				return "Two";
			case 3:
				return "Three";
			case 4:
				return "Four";
			case 5:
				return "Five";
			case 6:
				return "Six";
			case 7:
				return "Seven";
			case 8:
				return "Eight";
			case 9:
				return "Nine";
			case 10:
				return "Ten";
			case 11:
				return "Jack";
			case 12:
				return "Queen";
			case 13:
				return "King";
			case 14:
				return "Ace";
			default:
				return null;
		}
	}

	public HandValue evaluateHand(boolean print) {
		sortHand();
		sortHandByMatches();
		int ranks[] = new int[15];
		for (int i = 0; i < 15; i++) {
			ranks[i] = 0;
		}
		for (int i = 0; i < 5; i++)
			ranks[hand[i].getRank().ordinal() + 2]++;

		int matchCount1 = 1;
		int matchCount2 = 1;
		int matchCount1Rank = 0;
		int matchCount2Rank = 0;
		for (int i = 2; i < 15; i++) {
			if (ranks[i] > matchCount1) {
				if (matchCount1 != 1) {
					matchCount2 = matchCount1;
					matchCount2Rank = matchCount1Rank;
				}
				matchCount1 = ranks[i];
				matchCount1Rank = i;
			} else if (ranks[i] > matchCount2) {
				matchCount2 = ranks[i];
				matchCount2Rank = i;
			}
		}

		/*
		 We know if there are one or two "matches" at this point in the code.
		 We also know whether we have a three-of-a-kind or a four-of-a-kind hand.
		 The first count match is matchCount1 (3 of a kind, 4 of a kind)
		 If we have a 2pair or a full house, matchCount2 has the second matchCount
		 Straights and flushes must now be determined.
		*/

		int straightValue = 0;
		boolean straight = false;

		if (hand[0].getRank().ordinal() == hand[1].getRank().ordinal() + 1 &&
				hand[1].getRank().ordinal() == hand[2].getRank().ordinal() + 1 &&
				hand[2].getRank().ordinal() == hand[3].getRank().ordinal() + 1 &&
				hand[3].getRank().ordinal() == hand[4].getRank().ordinal() + 1) {
			straight = true;
			if (hand[0].getRank().getSymbol() == "A")
				straightValue = 16;
			else
				straightValue = hand[0].getRank().ordinal() + 2;
		}

		else if (hand[0].getRank().getSymbol() == "A" &&
				hand[1].getRank().getSymbol() == "5" &&
				hand[2].getRank().getSymbol() == "4" &&
				hand[3].getRank().getSymbol() == "3" &&
				hand[4].getRank().getSymbol() == "2") {
			straight = true;
			straightValue = 1;
		}


		boolean flush = true;
		for (int i = 0; i < 4; i++)
			if (hand[i].getSuit() != hand[i + 1].getSuit())
				flush = false;

		int value;
		//straight flush
		if (straight && flush) {
			if (print) System.out.println("You have a straight flush to the " + hand[0].getRank() + ".");
			value = 8;
		}
		//four of kind
		else if (matchCount1 == 4) {
			if (print) System.out.println("You have a Four-Of-A-Kind(" + getRank(matchCount1Rank) + "'s).");
			value = 7;
		}

		else if (matchCount2 == 4) {
			if (print) System.out.println("You have a Four-Of-A-Kind(" + getRank(matchCount2Rank) + "'s).");
			value = 7;
		}
		//full house
		else if ((matchCount1 == 3 && matchCount2 == 2) || (matchCount2 == 3 && matchCount1 == 2)) {
			if (print)
				System.out.println("You have a full house(" + getRank(matchCount1Rank) + "'s and " + getRank(matchCount2Rank) + "'s).");
			value = 6;
		}
		//flush 
		else if (flush) {
			if (print) System.out.println("You have a flush(" + hand[0].getSuit() + ").");
			value = 5;
		}
		//straight
		else if (straight) {
			if (print) System.out.print("You have a straight to the " + hand[0].getRank() + ".\n");
			value = 4;
		}
		//three of a kind
		else if (matchCount1 == 3) {
			if (print) System.out.println("You have a Three-Of-A-Kind(" + getRank(matchCount1Rank) + "'s).");
			value = 3;
		}
		//two pair
		else if (matchCount1 == 2 && matchCount2 == 2) {
			if (print)
				System.out.println("You have Two-Pair(" + getRank(matchCount1Rank) + "'s and " + getRank(matchCount2Rank) + "'s).");
			value = 2;
		}
		//one pair 
		else if (matchCount1 == 2 && matchCount2 == 1) {
			if (print) System.out.println("You have a Pair(" + getRank(matchCount1Rank) + "'s).");
			value = 1;
		}
		//high card
		else {
			if (print) System.out.println("You just have a High Card(" + hand[0].getRank() + ").");
			value = 0;
		}

		System.out.println();

		sortHand();
		return new HandValue(matchCount1Rank, matchCount2Rank, straightValue, value, hand);
	}
}
