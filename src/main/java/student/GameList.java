package student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

/**
 * Class for managing a list of board games.
 */
public class GameList implements IGameList {
    /** Default filename used when saving game lists. */
    private static final String DEFAULT_FILENAME = "games_list.txt";

    /** Set containing all board games. */
    private Set<BoardGame> games;

    /**
     * Constructor for the GameList.
     */
    public GameList() {
        games = new HashSet<>();
    }

    /**
     * Retrieves the names of all board games in the list.
     *
     * @return A list of board game names.
     */
    @Override
    public List<String> getGameNames() {
        return games.stream().map(BoardGame::getName).toList();
    }

    /**
     * Clears the list of board games.
     */
    @Override
    public void clear() {
        games.clear();
    }

    /**
     * Returns the number of board games in the list.
     *
     * @return The count of board games.
     */
    @Override
    public int count() {
        return games.size();
    }

    /**
     * Saves the current list of board games to a file.
     *
     * @param filename The name of the file to save to.
     */
    @Override
    public void saveGame(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            filename = DEFAULT_FILENAME;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (BoardGame game : games) {
                writer.write(game.getName());
                writer.newLine();
            }
            System.out.println("Games list saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Adds board games from a filtered stream based on input criteria.
     *
     * @param str      The input string specifying which games to add.
     * @param filtered The stream of board games to filter from.
     * @throws IllegalArgumentException if an invalid format is provided.
     */
    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        if (str == null || str.isEmpty() || filtered == null) {
            return;
        }

        List<BoardGame> filteredList = filtered.toList();
        if (filteredList.isEmpty()) {
            return; // No games to add
        }

        if ("all".equals(str)) {
            games.addAll(filteredList);
        }

        // Handle range format (e.g., "1-3")
        if (str.contains("-")) {
            String[] arr = str.split("-");
            if (arr.length == 2) {
                try {
                    int start = Integer.parseInt(arr[0]);
                    int end = Integer.parseInt(arr[1]);
                    for (int i = Math.max(0, start - 1); i < Math.min(end, filteredList.size()); i++) {
                        games.add(filteredList.get(i));
                    }
                    return;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid format for range input: " + str);
                }
            }
        }

        // Handle single index input
        try {
            int idx = Integer.parseInt(str);
            if (idx >= 1 && idx <= filteredList.size()) {
                games.add(filteredList.get(idx - 1));
            }
        } catch (NumberFormatException e) {
            // Handle direct name matching
            for (BoardGame game : filteredList) {
                if (game.getName().equalsIgnoreCase(str)) {
                    games.add(game);
                    return;
                }
            }
        }
    }

    /**
     * Removes board games from the list based on input criteria.
     *
     * @param str The input string specifying which games to remove.
     * @throws IllegalArgumentException if an invalid format is provided.
     */
    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        if (str == null || str.isEmpty()) {
            return;
        }

        List<BoardGame> list = new ArrayList<>(games);

        if ("all".equals(str)) {
            games.clear();
            return;
        }

        // Handle range format (e.g., "1-3")
        if (str.contains("-")) {
            String[] arr = str.split("-");
            if (arr.length == 2) {
                try {
                    int start = Integer.parseInt(arr[0]);
                    int end = Integer.parseInt(arr[1]);

                    // Iterate in reverse order to prevent index shifting issues
                    for (int i = Math.min(end, list.size()) - 1; i >= Math.max(0, start - 1); i--) {
                        list.remove(i);
                    }

                    games = new HashSet<>(list);
                    return;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid format for range input: " + str);
                }
            }
        }

        // Handle single index removal
        try {
            int idx = Integer.parseInt(str);
            if (idx >= 1 && idx <= list.size()) {
                list.remove(idx - 1); // Convert 1-based to 0-based index
                games = new HashSet<>(list);
                return;
            }
        } catch (NumberFormatException e) {
            // Handle name-based removal
            games.removeIf(game -> game.getName().equalsIgnoreCase(str));
        }
    }
}
