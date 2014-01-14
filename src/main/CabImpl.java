package main;

/**
 * An Implementation of Cab to use for testing.
 * @author Venki
 */
public class CabImpl implements Cab {

    private final int cabId;
    private Position cabPosition;
    private boolean isAvailable;

    public static CabImpl create(final int cabId, final Position cabPosition, final boolean isAvailable) {
        return new CabImpl(cabId, cabPosition, isAvailable);
    }

    private CabImpl(final int cabId, final Position cabPosition, final boolean isAvailable) {
        this.cabId = cabId;
        this.cabPosition = cabPosition;
        this.isAvailable = isAvailable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getID() {
        return cabId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getCabPosition() {
        return cabPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * @param cabPosition the cabPosition to set.
     */
    private void setCabPosition(Position cabPosition) {
        this.cabPosition = cabPosition;
    }

    /**
     * @param isAvailable the isAvailable to set.
     */
    private void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "CabImpl [cabId=" + cabId + ", cabPosition=" + cabPosition + ", isAvailable=" + isAvailable + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + cabId;
        result = prime * result + ((cabPosition == null) ? 0 : cabPosition.hashCode());
        result = prime * result + (isAvailable ? 1231 : 1237);
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
        CabImpl other = (CabImpl) obj;
        if (cabId != other.cabId)
            return false;
        if (cabPosition == null) {
            if (other.cabPosition != null)
                return false;
        } else if (!cabPosition.equals(other.cabPosition))
            return false;
        if (isAvailable != other.isAvailable)
            return false;
        return true;
    }

}
