package by.avsak.test;

public enum CardRank {
    Ace(  14, "A"),
    King( 13, "K"),
    Queen(12, "Q"),
    Jack( 11, "J"),
    C10(  10, "T"),
    C9(   9,  "9"),
    C8(   8,  "8"),
    C7(   7,  "7"),
    C6(   6,  "6"),
    C5(   5,  "5"),
    C4(   4,  "4"),
    C3(   3,  "3"),
    C2(   2,  "2"),
    UNKNOWN(0,"0");

    private int power;
    private String symbol;

    CardRank(int power, String symbol) {
      this.power = power;
      this.symbol = symbol;
    }

    public int getPower() {
        return power;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
