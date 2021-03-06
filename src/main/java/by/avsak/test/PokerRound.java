package by.avsak.test;

import java.util.List;

public class PokerRound {
    private Board board;
    private List<Hand> hands;
    private List<Hand> sortedHands;
    private boolean isValid = true;
    private String errorMessage;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Hand> getHands() {
        return hands;
    }

    public void setHands(List<Hand> hands) {
        this.hands = hands;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Hand> getSortedHands() {
        return sortedHands;
    }

    public void setSortedHands(List<Hand> sortedHands) {
        this.sortedHands = sortedHands;
    }
}
