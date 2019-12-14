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
    HighCard(     1, "High card");

    private int power;
    private String name;

    CombinationType(int power, String name) {
        this.power = power;
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    @Override
    public String toString() {
        return name;
    }
}
