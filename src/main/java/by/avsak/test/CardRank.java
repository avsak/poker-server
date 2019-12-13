package by.avsak.test;

public enum CardRank {
    Ace(14),
    King(13),
    Queen(12),
    Jack(11),
    C10(10),
    C9(9),
    C8(8),
    C7(7),
    C6(6),
    C5(5),
    C4(4),
    C3(3),
    C2(2),
    UNKNOWN(0);

    private int power;

    CardRank(int power) {
      this.power = power;
    }

    public int getPower() {
        return power;
    }
}
