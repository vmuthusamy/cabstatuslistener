package main;

/**
 * Representation of a {@link Cab}.
 * @author Venki
 */
public interface Cab {

    /**
     * Unique identifier of a cab.
     */

    int getID();

    /**
     * Gets the current position of the cab
     */

    Position getCabPosition();

    /**
     * Returns whether or not the cab is available
     */

    boolean isAvailable();

}
