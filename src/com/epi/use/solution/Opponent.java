package com.epi.use.solution;

public class Opponent extends Player {
	public Opponent() {
		super();
	}

	public int makeMove(Deck deck) {
		HandValue handValue = hand.evaluateHand(false);
		int value = handValue.getHandValue();

		int numDiscards = 0;

		//check if comp has one pair or better
		//if so, discard all other cards
		//one pair. or 3 of a kind, or 4 of a kind check matchCount1
		if (value == 1 || value == 3 || value == 7) {
			for (int i = 0; i < 5; i++)
				if (handValue.hand[i].getRank().ordinal() + 1 != handValue.getMatchRank(1)) {
					hand.discardAndReplaceCard(i, deck.dealCard());
					numDiscards++;
				}

			return numDiscards;
		}

		//two pair throw out the mismatching card.
		else if (value == 2) {
			for (int i = 0; i < 5; i++)
				if (handValue.hand[i].getRank().ordinal() + 1 != handValue.getMatchRank(1) &&
						handValue.hand[i].getRank().ordinal() + 1 != handValue.getMatchRank(2)) {
					hand.discardAndReplaceCard(i, deck.dealCard());
					numDiscards++;
				}

			return numDiscards;
		}
		//in a full house, straight, straight flush, or flush, keep everything
		else if (value == 6 || value == 8 || value == 5 || value == 4)
			return 0;


		//if they have a high card, figure out if they have 4 of same suit.
		//discard the different suit card
		if (value == 0) {
			int suitCount = 0;
			int suit = handValue.hand[0].getSuit().ordinal();
			for (int i = 0; i < 5; i++) {
				int matches = 0;
				for (int j = 0; j < 5; j++) {
					if (handValue.hand[i].getSuit().ordinal() == handValue.hand[j].getSuit().ordinal())
						matches++;
				}
				if (matches > suitCount) {
					suitCount = matches;
					suit = handValue.hand[i].getSuit().ordinal();
				}
			}

			//figure out which is different and discard it.
			if (suitCount == 4) {
				for (int i = 0; i < 5; i++)
					if (handValue.hand[i].getSuit().ordinal() != suit) {
						hand.discardAndReplaceCard(i, deck.dealCard());
						numDiscards++;
					}
				return numDiscards;
			}
		}


		//if they have 4 in sequence
		//discard the out of sequence one
		//since they are in order, we know the sequence is either going to be positions
		//0 1 2 3 or 1 2 3 4 or
		boolean sequence = true;
		int endcard = -1;
		for (int i = 0; i < 3; i++) {
			if (handValue.hand[i].getRank().ordinal() != handValue.hand[i].getRank().ordinal() + 1)
				sequence = false;
			endcard = 3;
		}
		if (!sequence) {
			sequence = true;
			for (int i = 1; i < 4; i++)
				if (handValue.hand[i].getRank().ordinal() != handValue.hand[i].getRank().ordinal() + 1)
					sequence = false;
			endcard = 4;
		}

		if (sequence) {
			hand.discardAndReplaceCard(endcard + 1, deck.dealCard());
			return 1;
		}

		//if they have an ace
		//discard the other 4 cards
		int AceIndex = hand.hasAce();
		if (AceIndex != -1) {
			for (int i = 0; i < 5; i++) {
				if (i != AceIndex) {
					hand.discardAndReplaceCard(i, deck.dealCard());
					numDiscards++;
				}
			}
			return numDiscards;
		}

		//else keep 2 highest
		for (int i = 2; i < 5; i++) {
			hand.discardAndReplaceCard(i, deck.dealCard());
			numDiscards++;
		}

		return numDiscards;
	}
}
