package by.avsak.test;

public enum CombinationType {
    StraightFlush(9),
    FourOfKind(8),
    FullHouse(7),
    Flush(6), Straight(5),
    ThreeOfKind(4),
    TwoPairs(3),
    Pair(2),
    HighCard(1);

    private int power;

    CombinationType(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }
}
