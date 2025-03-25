package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GameListTest {

    private Set<BoardGame> games;

    @BeforeEach
    void setUp() {
        games = new HashSet<>();
        games.add(new BoardGame("17 days", 6, 1, 8, 70, 70, 9.0, 600, 9.0, 2005));
        games.add(new BoardGame("Chess", 7, 2, 2, 10, 20, 10.0, 700, 10.0, 2006));
        games.add(new BoardGame("Go", 1, 2, 5, 30, 30, 8.0, 100, 7.5, 2000));
        games.add(new BoardGame("Go Fish", 2, 2, 10, 20, 120, 3.0, 200, 6.5, 2001));
        games.add(new BoardGame("golang", 4, 2, 7, 50, 55, 7.0, 400, 9.5, 2003));
        games.add(new BoardGame("GoRami", 3, 6, 6, 40, 42, 5.0, 300, 8.5, 2002));
        games.add(new BoardGame("Monopoly", 8, 6, 10, 20, 1000, 1.0, 800, 5.0, 2007));
        games.add(new BoardGame("Tucano", 5, 10, 20, 60, 90, 6.0, 500, 8.0, 2004));
    }

    @Test
    void testGetGameNames() {
        GameList list = new GameList();
        list.addToList("all", games.stream());
        List<String> names = list.getGameNames();
        assertEquals(8, names.size());
        assertTrue(names.contains("Go"));
        assertTrue(names.contains("Tucano"));
    }

    @Test
    void testAddSingleGameByIndex() {
        GameList list = new GameList();
        list.addToList("3", games.stream());
        assertEquals(1, list.count());
    }

    @Test
    void testAddSingleGameByName() {
        GameList list = new GameList();
        list.addToList("Go Fish", games.stream());
        assertEquals(1, list.count());
        assertTrue(list.getGameNames().contains("Go Fish"));
    }

    @Test
    void testAddGameByRange() {
        GameList list = new GameList();
        list.addToList("2-4", games.stream());
        assertEquals(3, list.count());
    }

    @Test
    void testAddAllGames() {
        GameList list = new GameList();
        list.addToList("all", games.stream());
        assertEquals(8, list.count());
    }

    @Test
    void testRemoveByName() {
        GameList list = new GameList();
        list.addToList("all", games.stream());
        list.removeFromList("GoRami");
        assertEquals(7, list.count());
        assertFalse(list.getGameNames().contains("GoRami"));
    }

    @Test
    void testRemoveByIndex() {
        GameList list = new GameList();
        list.addToList("all", games.stream());
        list.removeFromList("2");
        assertEquals(7, list.count());
    }

    @Test
    void testRemoveByRange() {
        GameList list = new GameList();
        list.addToList("all", games.stream());
        list.removeFromList("1-3");
        assertEquals(5, list.count());
    }

    @Test
    void testRemoveAll() {
        GameList list = new GameList();
        list.addToList("all", games.stream());
        list.removeFromList("all");
        assertEquals(0, list.count());
    }

    @Test
    void testCountAndClear() {
        GameList list = new GameList();
        list.addToList("all", games.stream());
        assertEquals(8, list.count());
        list.clear();
        assertEquals(0, list.count());
    }

    @Test
    void testSaveGameToFile() throws Exception {
        GameList list = new GameList();
        list.addToList("all", games.stream());

        String tempFile = "test_games_list.txt";
        list.saveGame(tempFile);

        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
            List<String> lines = reader.lines().toList();
            assertEquals(8, lines.size());
            assertTrue(lines.contains("Go Fish"));
            assertTrue(lines.contains("Monopoly"));
        }

        Files.deleteIfExists(Path.of(tempFile));
    }
}
