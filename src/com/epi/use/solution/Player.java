package com.epi.use.solution;

public class Player {
	protected Hand hand;

	public Player(){
		hand = new Hand();
	}

	public void insertCard(Card card){
		hand.insertCard(card);
	}

	public void displayHand(){
		hand.displayHand();
	}

	public void discardAndReplaceCard(int i, Card card){
		hand.discardAndReplaceCard(i, card);
	}

	public HandValue evaluateHand(){
		return hand.evaluateHand(true);
	}
}