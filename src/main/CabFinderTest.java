package main;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Random;

import main.CabFinder.CabInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Description.
 * @author Your Name
 */
@RunWith(MockitoJUnitRunner.class)
public class CabFinderTest {

    @Test
    public void testComparator() {
        Map<Integer, CabInfo> firstMap = Maps.newHashMap();
        Map<Integer, CabInfo> secondMap = Maps.newTreeMap(new EuclideanDistanceComparator(firstMap));

        Cab cab1 = CabImpl.create(100, new Position(40, 60), true);
        Cab cab2 = CabImpl.create(200, new Position(3000, 7000), true);
        Cab cab3 = CabImpl.create(300, new Position(500, 500), true);
        firstMap.put(cab1.getID(), CabInfo.create(cab1.getID(), cab1, 10));
        firstMap.put(cab2.getID(), CabInfo.create(cab2.getID(), cab2, 90));
        firstMap.put(cab3.getID(), CabInfo.create(cab3.getID(), cab3, 35));
        secondMap.putAll(firstMap);

        System.out.println("unsorted" + firstMap);
        System.err.println("sorted" + secondMap);
    }

    /**
     * Unable to get the mocking work because of a jar mismatch. Didn't have time to resolve the mocks to verify
     * functionalities on all methods.
     */
    // @Test
    public void testGetClosestCabs() {
        final CabFinder finder = Mockito.mock(CabFinder.class);
        final CabApp app = Mockito.mock(CabApp.class);
        final Cab mockCab = Mockito.mock(Cab.class);
        final Cab mockCab1 = Mockito.mock(Cab.class);
        final Position mockPosition = Mockito.mock(Position.class);
        when(mockPosition.getX()).thenReturn(getRandomInt());
        when(mockPosition.getY()).thenReturn(getRandomInt());
        when(mockCab.getCabPosition()).thenReturn(mockPosition);
        when(mockCab.getID()).thenReturn(100);
        when(mockCab1.getCabPosition()).thenReturn(mockPosition);
        when(mockCab1.getID()).thenReturn(200);
        when(app.getCabs()).thenReturn(Lists.newArrayList(mockCab, mockCab1).iterator());
        when(app.getUserPosition()).thenReturn(mockPosition);
        finder.initialize(app, 3);

    }

    @Test
    public void testLoadMultipleCabs() {

        final long startTime = System.currentTimeMillis();
        Map<Integer, CabInfo> firstMap = Maps.newHashMap();
        Map<Integer, CabInfo> secondMap = Maps.newTreeMap(new EuclideanDistanceComparator(firstMap));

        for (int i = 0; i < 100; i++) {
            final Cab cab = CabImpl.create(i, new Position(getRandomInt(), getRandomInt()), true);
            firstMap.put(cab.getID(), CabInfo.create(cab.getID(), cab, getRandomInt()));
        }
        secondMap.putAll(firstMap);

        int maxCabs = 20;

        int counter = 0;
        final List<Cab> cabs = Lists.newArrayList();
        while (counter++ <= maxCabs) {
            for (java.util.Map.Entry<Integer, CabInfo> entry : secondMap.entrySet()) {
                cabs.add(secondMap.get(entry.getKey()).getCab());
            }
        }
        final long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    private int getRandomInt() {
        Random random = new Random();
        int max = 1500;
        int min = 0;
        return min + random.nextInt(max - min + 1);
    }
}
