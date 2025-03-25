package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class FiltersTest {
    private List<BoardGame> games;
    private Filters filters;

    @BeforeEach
    void setUp() {
        filters = new Filters();
        games = new ArrayList<>();
        games.add(new BoardGame("Chess", 7, 2, 2, 10, 20, 10.0, 700, 10.0, 2006));
        games.add(new BoardGame("Go", 1, 2, 5, 30, 30, 8.0, 100, 7.5, 2000));
        games.add(new BoardGame("Go Fish", 2, 2, 10, 20, 120, 3.0, 200, 6.5, 2001));
        games.add(new BoardGame("Catan", 6, 3, 4, 60, 90, 2.5, 300, 8.2, 1995));
    }

    @Test
    void testFilterByNameEquals() {
        List<BoardGame> result = filters.filterByName("name == Go", games);
        assertEquals(1, result.size());
        assertEquals("Go", result.get(0).getName());
    }

    @Test
    void testFilterByNameContains() {
        List<BoardGame> result = filters.filterByName("name ~= go", games);
        assertEquals(2, result.size()); // "Go" and "Go Fish"
    }

    @Test
    void testFilterByRatingGreaterThan() {
        List<BoardGame> result = filters.filterByRating("rating > 7.0", games);
        assertEquals(3, result.size()); // Go, Chess, Catan
    }

    @Test
    void testFilterByYearPublished() {
        List<BoardGame> result = filters.filterByYearPublished("yearpublished >= 2001", games);
        assertEquals(2, result.size()); // Chess (2006), Go Fish (2001)
    }

    @Test
    void testFilterByMinPlayers() {
        List<BoardGame> result = filters.filterByMinPlayer("minplayers == 2", games);
        assertEquals(3, result.size()); // Chess, Go, Go Fish
    }

    @Test
    void testFilterByMaxPlayers() {
        List<BoardGame> result = filters.filterByMaxPlayer("maxplayers <= 4", games);
        assertEquals(2, result.size()); // Chess, Catan
    }

    @Test
    void testFilterByMinPlayTime() {
        List<BoardGame> result = filters.filterByMinTime("minplaytime >= 30", games);
        assertEquals(2, result.size()); // Go, Go Fish, Catan
    }

    @Test
    void testFilterByMaxPlayTime() {
        List<BoardGame> result = filters.filterByMaxTime("maxplaytime < 100", games);
        assertEquals(3, result.size()); // Chess, Go, Catan
    }

    @Test
    void testFilterByDifficultyLessThan() {
        List<BoardGame> result = filters.filterByDifficulty("difficulty < 5", games);
        assertEquals(2, result.size()); // Go Fish, Catan
    }

    @Test
    void testFilterByRankEquals() {
        List<BoardGame> result = filters.filterByRank("rank == 200", games);
        assertEquals(1, result.size()); // Chess
    }

    @Test
    void testControllerMultipleFilters() {
        List<BoardGame> result = filters.controller("minplayers>=2, rating>=8.0", games);
        assertEquals(2, result.size()); // Chess and Go
        assertTrue(result.stream().allMatch(g -> g.getRating() >= 8.0 && g.getMinPlayers() >= 2));
    }

    @Test
    void testControllerWithNameContains() {
        List<BoardGame> result = filters.controller("name ~= go", games);
        assertEquals(2, result.size()); // Go, Go Fish
    }

    @Test
    void testEmptyListShortCircuit() {
        List<BoardGame> result = filters.controller("rating > 5.0", new ArrayList<>());
        assertTrue(result.isEmpty());
    }
}
