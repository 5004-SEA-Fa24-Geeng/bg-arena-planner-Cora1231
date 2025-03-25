import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import student.BoardGame;
import student.GameData;
import student.IPlanner;
import student.Planner;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlanner {
    private Set<BoardGame> gameSet;
    private Planner planner;

    @BeforeEach
    public void setUp() {
        gameSet = new HashSet<>();
        gameSet.add(new BoardGame("Catan", 7, 1, 4, 60, 90, 2.5, 100, 8.1, 1995));
        gameSet.add(new BoardGame("Terraforming Mars", 8, 1, 5, 90, 120, 3.2, 200, 8.5, 2016));
        gameSet.add(new BoardGame("Gloomhaven", 8, 1, 4, 120, 150, 3.8, 300, 9.0, 2017));
        planner = new Planner(gameSet);
    }

    @Test
    public void testFilterByExactName() {
        List<BoardGame> result = planner.filter("name == Catan").collect(Collectors.toList());
        assertEquals(1, result.size());
        assertEquals("Catan", result.get(0).getName());
    }

    @Test
    public void testSortByRatingDescending() {
        List<BoardGame> result = planner.filter("", GameData.RATING, false).collect(Collectors.toList());
        assertEquals("Gloomhaven", result.get(0).getName());
        assertEquals("Terraforming Mars", result.get(1).getName());
        assertEquals("Catan", result.get(2).getName());
    }

    @Test
    public void testSortByNameAscending() {
        List<BoardGame> result = planner.filter("", GameData.NAME, true).collect(Collectors.toList());
        assertEquals("Catan", result.get(0).getName());
        assertEquals("Gloomhaven", result.get(1).getName());
        assertEquals("Terraforming Mars", result.get(2).getName());
    }


    @Test
    public void testFilterNoMatches() {
        assertNull(planner.filter("name == NonExistentGame", GameData.RATING, true));
    }

    @Test
    public void testReset() {
        planner.filter("name == Catan");
        planner.reset();
        List<BoardGame> result = planner.getCurList().collect(Collectors.toList());
        assertEquals(3, result.size());
    }

}
