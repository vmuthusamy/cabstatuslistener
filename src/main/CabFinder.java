package main;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Implementation of {@link CabStatusListener}
 * @author Venki.
 *         <p />
 *         Design Assumptions will be documented here. Runtime Analysis.
 *         <p />
 *         For initialize methods it's O(n) we have to loop through all cabs to find the shortlisted candidates. <br />
 *         For getNearestCabs() putAll - O(1) for each key value pair present in shortlisted map. log(n) to retrieve
 *         from a treemap and n*log(n) for entire retrieval. <br />
 *         For onPositionChanged() && OnCabAvailabilityChanged() O(1) for check key existence. O(1) for removing the
 *         entry. O(1) for a put <br />
 *         I used 2 maps, one to shortlist the qualified cabs and second to filter the closest in distance from the
 *         qualifying map.
 */

public class CabFinder implements CabStatusListener {

    private int maxCabs;
    private Position userPosition;
    private final Map<Integer, CabInfo> cabsMeetingCriteria = Maps.newHashMap();
    private Map<Integer, CabInfo> closestCabs;

    /**
     * Initiates CabFinder. Called only once per app startup.
     * @app An application object providing services implemented by the rest of the application.
     * @maxCabs Nearest number of cabs that can be returned to the user
     */
    public void initialize(final CabApp app, final int maxCabs) {
        assert app != null : "app:null";
        assert maxCabs > 0 : "maxCabs: should be greater than 0";
        this.maxCabs = maxCabs;
        this.userPosition = app.getUserPosition();
        Iterator<Cab> allCabsIterator = app.getCabs();
        while (allCabsIterator.hasNext()) {
            final Cab cab = allCabsIterator.next();
            final Position cabPosition = cab.getCabPosition();
            final double euclideanDistance = getEuclideanDistance(userPosition, cabPosition);
            if (cab.isAvailable() && euclideanDistance <= 1000) {
                cabsMeetingCriteria.put(cab.getID(), CabInfo.create(cab.getID(), cab, euclideanDistance));
            }

        }

    }

    /**
     * Gets nearest cabs within 1km of the current user’s location. These must be the *nearest possible* @maxCabs in the
     * 1km area.
     * @return An unordered list of the nearest cabs.
     */
    public Cab[] getNearestCabs() {
        this.closestCabs = Maps.newTreeMap(new EuclideanDistanceComparator(cabsMeetingCriteria));
        closestCabs.putAll(cabsMeetingCriteria);
        int currentCount = 0;
        List<Cab> cabArrayListToBeReturnedCabs = Lists.newArrayList();
        while (currentCount++ <= maxCabs) {
            for (Entry<Integer, CabInfo> entry : closestCabs.entrySet()) {
                cabArrayListToBeReturnedCabs.add(closestCabs.get(entry.getKey()).getCab());
            }
        }
        return (Cab[]) cabArrayListToBeReturnedCabs.toArray();
    }

    /**
     * Asynchronous Callback per CabStatusListener (see below). Called when the position of a cab has changed.
     */

    @Override
    public void onCabPositionChanged(Cab cab) {
        assert cab != null : "cab:null";
        if (cabsMeetingCriteria.containsKey(cab.getID())) {
            cabsMeetingCriteria.remove(cab.getID());
        }
        if (isCabMatchingCriteria(cab)) {
            cabsMeetingCriteria.put(cab.getID(),
                    CabInfo.create(cab.getID(), cab, getEuclideanDistance(userPosition, cab.getCabPosition())));
        }

    }

    /**
     * Asynchronous Callback per CabStatusListener (see below). Called when a cab’s availability changes.
     * @cab The cab whose availability has changed
     * @isAvailable true if the cab is now available, false otherwise
     */
    @Override
    public void onCabAvailabilityChanged(Cab cab, boolean isAvailable) {
        assert cab != null : "cab:null";
        if (!isAvailable) {
            if (cabsMeetingCriteria.containsKey(cab.getID())) {
                cabsMeetingCriteria.remove(cab.getID());
            }
        }
        if (isCabMatchingCriteria(cab)) {
            cabsMeetingCriteria.put(cab.getID(),
                    CabInfo.create(cab.getID(), cab, getEuclideanDistance(userPosition, cab.getCabPosition())));
        }
    }

    /**
     * The Object to represent Cab information.
     * <p />
     * This object is used for identifying the closest cabs near the user.
     * @author Venki
     */
    public static class CabInfo {
        private int cabId;
        private double distance;
        private final Cab cab;

        /**
         * Creates an instance of {@link CabInfo} from the params provided
         * @param cabId the cabId to use
         * @param cab the {@link Cab} to use
         * @param distance the double representing the euclidean distance between cab and the user.
         * @return the {@link CabInfo cabInfo instance}.
         */
        public static CabInfo create(final int cabId, final Cab cab, final double distance) {
            return new CabInfo(cabId, cab, distance);
        }

        private CabInfo(final int cabId, final Cab cab, final double distance) {
            this.cabId = cabId;
            this.cab = cab;
            this.distance = distance;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "CabInfo [cabId=" + cabId + ", distance=" + distance + ", cab=" + cab + "]";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((cab == null) ? 0 : cab.hashCode());
            result = prime * result + cabId;
            long temp;
            temp = Double.doubleToLongBits(distance);
            result = prime * result + (int) (temp ^ (temp >>> 32));
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
            CabInfo other = (CabInfo) obj;
            if (cab == null) {
                if (other.cab != null)
                    return false;
            } else if (!cab.equals(other.cab))
                return false;
            if (cabId != other.cabId)
                return false;
            if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance))
                return false;
            return true;
        }

        /**
         * @return the cabId.
         */
        public int getCabId() {
            return cabId;
        }

        /**
         * @param cabId the cabId to set.
         */
        public void setCabId(int cabId) {
            this.cabId = cabId;
        }

        /**
         * @return the distance.
         */
        public double getDistance() {
            return distance;
        }

        /**
         * @param distance the distance to set.
         */
        public void setDistance(double distance) {
            this.distance = distance;
        }

        /**
         * @return the cab.
         */
        public Cab getCab() {
            return cab;
        }
    }

    private double getEuclideanDistance(final Position a, final Position b) {
        assert a != null;
        assert b != null;
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    private boolean isCabMatchingCriteria(final Cab cab) {
        assert cab != null : "cab:null";
        final double distance = getEuclideanDistance(userPosition, cab.getCabPosition());
        return cab.isAvailable() && distance <= 1000;
    }

}
