package com.epi.use.solution;

public class Deck {
	private Card[] deck;
	private int cardsUsed;

	public Deck() {
		deck = new Card[52];
		int cardCount = 0;
		for (Card._Suit suit : Card._Suit.values()) {
			for (Card._Rank rank : Card._Rank.values()) {
				deck[cardCount] = new Card(rank, suit);
				cardCount++;
			}
		}
		cardsUsed = 0;
	}

	public void shuffle() {
		for (int i = 0; i < deck.length; i++) {
			int random = (int) (Math.random() * deck.length);
			Card temp = deck[i];
			deck[i] = deck[random];
			deck[random] = temp;
		}
		cardsUsed = 0;
	}

	public Card dealCard() {
		if (cardsUsed == deck.length)
			throw new IllegalArgumentException("Deck is out of cards!\n");
		cardsUsed++;
		return deck[cardsUsed - 1];
	}
}
