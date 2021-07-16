package com.epi.use.solution;

public class Card {
    public enum _Suit { C, D, H, S }
    public enum _Rank {
        Two("2"), Three("3"), Four("4"), Five("5"),
        Six("6"), Seven("7"), Eight("8"), Nine("9"),
        Ten("T"), Jack("J"), Queen("Q"), King("K"), Ace("A");

        private String rank;

        _Rank(String rank){
            this.rank = rank;
        }

        String getSymbol(){
            return this.rank;
        }
    }

    private final _Suit suit;
    private final _Rank rank;

    public Card(_Rank rank, _Suit suit){
    	this.rank = rank;
    	this.suit = suit;
    }

    public _Rank getRank(){
        return rank;
    }

    public _Suit getSuit(){
        return suit;
    }

    public String toString(){
        return rank.getSymbol() + suit;
    }
}
