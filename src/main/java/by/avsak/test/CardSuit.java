package by.avsak.test;

public enum CardSuit {
    Clubs("c"), Diamonds("d"), Hearts("h"), Spades("s"), UNKNOWN("0");

    private String symbol;

    CardSuit(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
