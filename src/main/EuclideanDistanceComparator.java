package main;

import java.util.Comparator;
import java.util.Map;

import main.CabFinder.CabInfo;

/**
 * Description.
 * @author Your Name
 */
public class EuclideanDistanceComparator implements Comparator<Integer> {

    private final Map<Integer, CabInfo> cabInfoMap;

    public EuclideanDistanceComparator(final Map<Integer, CabInfo> cabInfoMap) {
        this.cabInfoMap = cabInfoMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(Integer o1, Integer o2) {
        if (cabInfoMap.get(o1).getDistance() > cabInfoMap.get(o2).getDistance()) {
            return 1;
        } else if (cabInfoMap.get(o1).getDistance() < cabInfoMap.get(o2).getDistance()) {
            return -1;
        }
        return 0;
    }

}