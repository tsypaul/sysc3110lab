package poker;
import java.util.*;

import poker.Card.Rank;
import poker.Card.Suit;

/**
 * A poker hand is a list of cards, which can be of some "kind" (pair, straight, etc.)
 * 
 */
public class Hand implements Comparable<Hand> {

    public enum Kind {HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, 
        FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH}

    private final List<Card> cards;

    /**
     * Create a hand from a string containing all cards (e,g, "5C TD AH QS 2D")
     */
    public Hand(String c) {
    	cards = new ArrayList();
        String[] cardArray = c.split(" ");
        for(String s : cardArray) {
        	Card card = new Card(s);
        	cards.add(card);
        }
        Collections.sort(cards, (Card c1, Card c2) -> c1.getRank().compareTo(c2.getRank()));
    }
    
    /**
     * @returns true if the hand has n cards of the same rank
	 * e.g., "TD TC TH 7C 7D" returns True for n=2 and n=3, and False for n=1 and n=4
     */
    protected boolean hasNKind(int n) {
    	Rank rank = cards.get(0).getRank();
    	int count = 0;
    	for(int i = 0; i < cards.size(); i++) {
    		Rank r = cards.get(i).getRank();
    		if(rank == r) {
    			count++;
    		}else {
    			rank = r;
    		}
    		if(count == n) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
	 * Optional: you may skip this one. If so, just make it return False
     * @returns true if the hand has two pairs
     */
    public boolean isTwoPair() {
    	return false;
    }   
    
    /**
     * @returns true if the hand is a straight 
     */
    public boolean isStraight() {
    	int n = 0;
    	for(Card c : cards) {
    		n++;
    		if(!cards.contains(getNextRank(c)) && n > 5) {
    			return false;
    		}
    	}
        return true;
    }
    
    public Rank getNextRank(Card c) {
    	int nextIndex = c.getRank().ordinal() + 1;
    	Rank[] nextCard = Card.Rank.values();
    	nextIndex %= nextCard.length;
    	return nextCard[nextIndex];
    }
    
    /**
     * @returns true if the hand is a flush
     */
    public boolean isFlush() {
        Suit s = cards.get(0).getSuit();
        for(Card c : cards) {
        	if(!c.getSuit().equals(s)) {
        		return false;
        	}
        }
        return true;
    }
    
    @Override
    public int compareTo(Hand h) {
        return this.kind().compareTo(h.kind());
    }
    
    /**
	 * This method is already implemented and could be useful! 
     * @returns the "kind" of the hand: flush, full house, etc.
     */
    public Kind kind() {
        if (isStraight() && isFlush()) return Kind.STRAIGHT_FLUSH;
        else if (hasNKind(4)) return Kind.FOUR_OF_A_KIND; 
        else if (hasNKind(3) && hasNKind(2)) return Kind.FULL_HOUSE;
        else if (isFlush()) return Kind.FLUSH;
        else if (isStraight()) return Kind.STRAIGHT;
        else if (hasNKind(3)) return Kind.THREE_OF_A_KIND;
        else if (isTwoPair()) return Kind.TWO_PAIR;
        else if (hasNKind(2)) return Kind.PAIR; 
        else return Kind.HIGH_CARD;
    }
    
    public static void main(String[] args) {
    	Hand h1 = new Hand("2S 2D 2H 4D 4C");
    	Hand h = new Hand("2D 3D 5D 4D 6D");
    	System.out.println(h1.hasNKind(2));
    	System.out.println(h1.hasNKind(3));
    	System.out.println(h.isStraight());
    	System.out.println(h.isFlush());

    }
}
