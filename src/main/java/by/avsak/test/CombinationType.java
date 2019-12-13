package by.avsak.test;

public enum CombinationType {
    StraightFlush(9, "Straight flush"),
    FourOfKind(   8, "Four of a kind"),
    FullHouse(    7, "Full house"),
    Flush(        6, "Flush"),
    Straight(     5, "Straight"),
    ThreeOfKind(  4, "Three of a kind"),
    TwoPairs(     3, "Two pairs"),
    Pair(         2, "Pair"),
    HighCard(     1, "High card"),
    UNKNOWN(      0, "Unknown");

    private int power;
    private String name;

    CombinationType(int power, String name) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
