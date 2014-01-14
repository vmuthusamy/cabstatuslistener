package main;

/**
 * The CabStatusListener interface
 */

interface CabStatusListener {

    /**
     * Called when the position of a cab has changed.
     * @cab The cab object
     */

    void onCabPositionChanged(Cab cab);

    /**
     * Called when a cab’s availability changes.
     * @cab The cab object
     * @isAvailable true if the cab is available, false otherwise
     * */

    void onCabAvailabilityChanged(Cab cab, boolean isAvailable);

}