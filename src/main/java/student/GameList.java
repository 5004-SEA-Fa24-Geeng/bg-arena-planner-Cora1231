package student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameList implements IGameList {
    private static final String DEFAULT_FILENAME = "games_list.txt";

    private Set<BoardGame> games;

    /**
     * Constructor for the GameList.
     */
    public GameList() {
        games = new HashSet<>();
    }

    @Override
    public List<String> getGameNames() {
        return games.stream().map(BoardGame::getName).toList();
    }

    @Override
    public void clear() {
        games.clear();
    }

    @Override
    public int count() {
        return games.size();
    }

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

    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        if (str == null || str.isEmpty() || filtered == null) {
            return;
        }

        List<BoardGame> filteredList = filtered.toList();
        if (filteredList.isEmpty()) {
            return; // No games to add
        }
        if (str.equals("all")) {
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

    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        if (str == null || str.isEmpty()) {
            return;
        }

        List<BoardGame> list = new ArrayList<>(games);

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
