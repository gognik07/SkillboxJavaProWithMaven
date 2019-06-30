package lesson8.tasks2;

import junit.framework.TestCase;
import lesson8.tasks2.core.Line;
import lesson8.tasks2.core.Station;

import java.util.ArrayList;
import java.util.List;

public class RouteCalculatorTest extends TestCase {

    private List<Station> route;

    private StationIndex stationIndex;

    private RouteCalculator routeCalculator;

    private Line line1;
    private Line line2;

    private Station station1;
    private Station station2;
    private Station station3;
    private Station station4;


    @Override
    protected void setUp() throws Exception {
        route = new ArrayList<>();
        line1 = new Line(1, "Первая");
        line2 = new Line(2, "Вторая");

        station1 = new Station("Петровская", line1);
        station2 = new Station("Арбузная", line1);
        station3 = new Station("Морковная", line2);
        station4 = new Station("Яблочная", line2);
        route.add(station1);
        route.add(station2);
        route.add(station3);
        route.add(station4);

        line1.addStation(station1);
        line1.addStation(station2);
        line2.addStation(station3);
        line2.addStation(station4);

        stationIndex = new StationIndex();

        stationIndex.addLine(line1);
        stationIndex.addLine(line2);

        for (Station station : route) {
            stationIndex.addStation(station);
        }

        List<Station> intersectingStations = new ArrayList<>();
        intersectingStations.add(station1);
        intersectingStations.add(station2);
        stationIndex.addConnection(intersectingStations);

        intersectingStations = new ArrayList<>();
        intersectingStations.add(station2);
        intersectingStations.add(station3);
        stationIndex.addConnection(intersectingStations);

        intersectingStations = new ArrayList<>();
        intersectingStations.add(station3);
        intersectingStations.add(station4);
        stationIndex.addConnection(intersectingStations);

        routeCalculator = new RouteCalculator(stationIndex);
    }

    @Override
    protected void tearDown() throws Exception {
    }

    public void testCalculateDuration() {
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 8.5;

        assertEquals(expected, actual);
    }

    public void testGetShortestRoute() {
        List<Station> actual = routeCalculator.getShortestRoute(stationIndex.getStation("Петровская"), stationIndex.getStation("Яблочная"));
        List<Station> expected = new ArrayList<>();
        expected.add(station1);
        expected.add(station2);
        expected.add(station3);
        expected.add(station4);

        assertEquals(expected, actual);
    }

}