package main;

/**
 * Coordinates on a 2D map with a one meter granularity.
 */
public class Position {
    private int x;

    private int y;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Position [x=" + x + ", y=" + y + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x.
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y.
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set.
     */
    public void setY(int y) {
        this.y = y;
    }
}
