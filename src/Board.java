import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Domino> dominos;

    public Board() {
        this.dominos = new ArrayList<>();
    }

    public void addDomino(Domino domino, boolean addToLeft) {
        if (dominos.isEmpty()) {
            dominos.add(domino);
        } else if (addToLeft) {
            if (domino.getRightValue() == dominos.get(0).getLeftValue()) {
                dominos.add(0, domino);
            } else {
                domino.rotate();
                dominos.add(0, domino);
            }
        } else {
            int lastIndex = dominos.size() - 1;
            if (domino.getLeftValue() == dominos.get(lastIndex).getRightValue()) {
                dominos.add(domino);
            } else {
                domino.rotate();
                dominos.add(domino);
            }
        }
    }

    public boolean isValidMove(Domino domino) {
        if (dominos.isEmpty()) {
            return true;
        }
        int leftEndValue = dominos.get(0).getLeftValue();
        int rightEndValue = dominos.get(dominos.size() - 1).getRightValue();
        return domino.getLeftValue() == rightEndValue || domino.getRightValue() == rightEndValue ||
                domino.getLeftValue() == leftEndValue || domino.getRightValue() == leftEndValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Domino domino : dominos) {
            sb.append(domino).append(" ");
        }
        return sb.toString().trim();
    }

    public List<Domino> getDominos() {
        return new ArrayList<>(dominos);
    }
}

