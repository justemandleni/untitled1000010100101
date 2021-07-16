package com.epi.use.solution;

public class HandValue {
	private int matchCount1Rank, matchCount2Rank, straightMax, value;
	public Card[] hand;

	public HandValue(int rank1, int rank2, int str8Max, int handValue, Card[] hand) {
		matchCount1Rank = rank1;
		matchCount2Rank = rank2;
		straightMax = str8Max;
		value = handValue;
		this.hand = hand;
	}

	public int getHandValue() {
		return value;
	}

	public int getMatchRank(int i) {
		if (i == 1) return matchCount1Rank;
		if (i == 2) return matchCount2Rank;
		else return -1;
	}

	public static int compareHands(HandValue[] hands, int numHands) {
		int max = 0;
		boolean tie = false;
		int tiedHands[] = new int[numHands];
		int numTies = 0;
		int winner = 0;

		//first we walk through the hand values and find the biggest one 
		//noting if a tie occurs. 
		for (int i = 0; i < numHands; i++) tiedHands[i] = 0;
		for (int i = 0; i < numHands; i++) {
			if (hands[i].value == max) {
				tie = true;
				tiedHands[numTies++] = i;
			} else if (hands[i].value > max) {
				tie = false;
				numTies = 0;
				tiedHands[numTies++] = i;
				max = hands[i].value;
				winner = i;
			}
		}

		//there are no ties, so whoever had maxvalue won 
		if (!tie) {
			return winner;
		}

		//figure out what they're tied for 
		int handvalue = hands[tiedHands[0]].value;
		switch (handvalue) {
			//if tied with a high card or a flush, we simply compare the top ranks
			//for all the hands and return the best one.
			case 0:
			case 5:
				max = -1;
				tie = false;

				for (int j = 0; j < 5; j++) { //we know there will only be up to 5 possible ties.
					int numTies2 = 1;
					int arr[] = new int[5];
					for (int i = 0; i < numTies; i++) {
						if ((hands[tiedHands[i]].hand[j].getRank().ordinal() == max)) {
							tie = true;
							arr[numTies2] = tiedHands[i];
							numTies2++;
						} else if (hands[tiedHands[i]].hand[j].getRank().ordinal() > max) {
							max = hands[tiedHands[i]].hand[j].getRank().ordinal();
							winner = tiedHands[i];
							tie = false;
							numTies2 = 1;
							arr[0] = tiedHands[i];

						}
					}
					if (!tie) break;
					else if (j == 4) {
						return -1;
					} else {
						tie = false;
						numTies = numTies2;
						tiedHands = arr;
					}
				}
				return winner;

			//if tied with a one pair, then we compare matchCount1Ranks for the tied hands
			case 1:
				max = -1;
				for (int i = 0; i < numTies; i++)
					if (hands[tiedHands[i]].matchCount1Rank == 1) {
						max = 14;
						winner = tiedHands[i];
					} else if (hands[tiedHands[i]].matchCount1Rank > max) {
						winner = tiedHands[i];
						max = hands[tiedHands[i]].matchCount1Rank;
					}
				return winner;

			//if tied with a two-pair, we compare both match1 and match2 for the tied hands
			case 2:
				max = -1;
				for (int i = 0; i < numTies; i++) {
					if (hands[tiedHands[i]].matchCount1Rank == 1) {
						winner = tiedHands[i];
						max = 14;
					} else if (hands[tiedHands[i]].matchCount1Rank > max) {
						winner = tiedHands[i];
						max = hands[tiedHands[i]].matchCount1Rank;
					}

					if (hands[tiedHands[i]].matchCount2Rank == 1) {
						winner = tiedHands[i];
						max = 14;
					} else if (hands[tiedHands[i]].matchCount2Rank > max) {
						winner = tiedHands[i];
						max = hands[tiedHands[i]].matchCount2Rank;
					}
				}
				return winner;

			//if tied with a 3,4 of a kind compare the matchCount1Ranks
			case 7:
			case 3:
				max = -1;
				for (int i = 0; i < numTies; i++)
					if (hands[tiedHands[i]].matchCount1Rank == 1) {
						winner = tiedHands[i];
						max = 14;
					} else if (hands[tiedHands[i]].matchCount1Rank > max) {
						winner = tiedHands[i];
						max = hands[tiedHands[i]].matchCount1Rank;
					}
				return winner;

			//for full house we compare only matchCount2 (because that has the 3staright),
			case 6:
				max = -1;
				for (int i = 0; i < numTies; i++)
					if (hands[tiedHands[i]].matchCount2Rank == 1) {
						winner = tiedHands[i];
						max = 14;
					} else if (hands[tiedHands[i]].matchCount2Rank > max) {
						winner = tiedHands[i];
						max = hands[tiedHands[i]].matchCount2Rank;
					}
				return winner;

			//if tied with a straight or a straight flush, compare the straightMax
			case 8:
			case 4:
				max = -1;
				tie = false;
				for (int i = 0; i < numTies; i++)
					//the straight max is already set up to account for Ace being a high or a low card
					if (hands[tiedHands[i]].straightMax == max) tie = true;
					else if (hands[tiedHands[i]].straightMax > max) {
						winner = tiedHands[i];
						max = hands[tiedHands[i]].straightMax;
					} else if (hands[tiedHands[i]].straightMax == 1) {
						winner = tiedHands[i];
						max = 14;
					}
				if (!tie) return winner;
				else return -1;

				//should never ever get here
			default:
				System.out.println("?!?!?!?!?!");
				return winner;
		}
	}
}