package student;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for filtering a list of board games based on different criteria.
 */
public class Filters {

    /**
     * Default constructor.
     */
    public Filters() { }

    /**
     * Applies multiple filtering and sorting commands to a list of board games.
     *
     * @param commands The filtering/sorting commands in string format.
     * @param gameList The list of board games to be filtered.
     * @return The filtered and sorted list of board games.
     */
    public List<BoardGame> controller(String commands, List<BoardGame> gameList) {
        String[] cmds = commands.split(",");

        if (gameList.isEmpty()) {
            return gameList;
        }

        // Keeping GameData variables for filtering
        GameData minplayers = GameData.fromString("minplayers");
        GameData maxplayers = GameData.fromString("maxplayers");
        GameData mintime = GameData.fromString("minplaytime");
        GameData maxtime = GameData.fromString("maxplaytime");
        GameData difficulty = GameData.fromString("difficulty");
        GameData yearpublished = GameData.fromString("yearpublished");
        GameData rating = GameData.fromString("rating");
        GameData rank = GameData.fromString("rank");

        // Filter the games
        for (String cmd : cmds) {
            cmd = cmd.trim().toLowerCase().replaceAll("\\s+", "");

            if (cmd.contains("name")) {
                gameList = filterByName(cmd, gameList);
            } else if (cmd.contains("maxplayers") || cmd.contains(maxplayers.name().toLowerCase())) {
                gameList = filterByMaxPlayer(cmd, gameList);
            } else if (cmd.contains("minplayers") || cmd.contains(minplayers.name().toLowerCase())) {
                gameList = filterByMinPlayer(cmd, gameList);
            } else if (cmd.contains("minplaytime") || cmd.contains(mintime.name().toLowerCase())) {
                gameList = filterByMinTime(cmd, gameList);
            } else if (cmd.contains("maxplaytime") || cmd.contains(maxtime.name().toLowerCase())) {
                gameList = filterByMaxTime(cmd, gameList);
            } else if (cmd.contains("difficulty") || cmd.contains(difficulty.name().toLowerCase())) {
                gameList = filterByDifficulty(cmd, gameList);
            } else if (cmd.contains("rating") || cmd.contains(rating.name().toLowerCase())) {
                gameList = filterByRating(cmd, gameList);
            } else if (cmd.contains("yearpublished") || cmd.contains(yearpublished.name().toLowerCase())) {
                gameList = filterByYearPublished(cmd, gameList);
            } else if (cmd.contains("rank") || cmd.contains(rank.name().toLowerCase())) {
                gameList = filterByRank(cmd, gameList);
            }
        }

        // Sorting pattern matcher
        Pattern sortPattern = Pattern.compile("sort:\\s*(\\w+)\\s+(asc|desc)", Pattern.CASE_INSENSITIVE);
        Matcher sortMatcher = sortPattern.matcher(commands);
        if (sortMatcher.find()) {
            String sortColumn = sortMatcher.group(1).toLowerCase();
            boolean ascending = sortMatcher.group(2).equalsIgnoreCase("asc");
            sortGames(gameList, sortColumn, ascending);
        }

        gameList = new ArrayList<>(new LinkedHashSet<>(gameList));
        return gameList;
    }

    /**
     * Sorts board games based on a given column and order.
     *
     * @param gameList The list of board games to be sorted.
     * @param column The column to sort by.
     * @param asc True for ascending order, false for descending order.
     */
    public static void sortGames(List<BoardGame> gameList, String column, boolean asc) {
        Comparator<BoardGame> comparator;

        switch (column) {
            case "rating" -> comparator = Comparator.comparingDouble(BoardGame::getRating);
            case "name" -> comparator = Comparator.comparing(BoardGame::getName);
            case "yearpublished" -> comparator = Comparator.comparingInt(BoardGame::getYearPublished);
            case "rank" -> comparator = Comparator.comparingInt(BoardGame::getRank);
            case "difficulty" -> comparator = Comparator.comparingDouble(BoardGame::getDifficulty);
            case "minplayers" -> comparator = Comparator.comparingInt(BoardGame::getMinPlayers);
            case "maxplayers" -> comparator = Comparator.comparingInt(BoardGame::getMaxPlayers);
            case "minplaytime" -> comparator = Comparator.comparingInt(BoardGame::getMinPlayTime);
            case "maxplaytime" -> comparator = Comparator.comparingInt(BoardGame::getMaxPlayTime);
            default -> {
                System.out.println("Invalid column name: " + column);
                return;
            }
        }

        if (!asc) {
            comparator = comparator.reversed();
        }
        gameList.sort(comparator);
    }

    /**
     * Filters board games by min players.
     */
    public List<BoardGame> filterByMinPlayer(String cmd, List<BoardGame> list) {
        return list.stream().filter(game -> game.getMinPlayers() >= 2).toList();
    }

    /**
     * Filters board games by max players.
     */
    public List<BoardGame> filterByMaxPlayer(String cmd, List<BoardGame> list) {
        return list.stream().filter(game -> game.getMaxPlayers() <= 10).toList();
    }

    /**
     * Filters board games by min playtime.
     */
    public List<BoardGame> filterByMinTime(String cmd, List<BoardGame> list) {
        return list.stream().filter(game -> game.getMinPlayTime() >= 30).toList();
    }

    /**
     * Filters board games by max playtime.
     */
    public List<BoardGame> filterByMaxTime(String cmd, List<BoardGame> list) {
        return list.stream().filter(game -> game.getMaxPlayTime() <= 180).toList();
    }

    /**
     * Filters board games by difficulty.
     */
    public List<BoardGame> filterByDifficulty(String cmd, List<BoardGame> list) {
        return list.stream().filter(game -> game.getDifficulty() >= 3.0).toList();
    }

    /**
     * Filters board games by rating.
     */
    public List<BoardGame> filterByRating(String cmd, List<BoardGame> list) {
        return list.stream().filter(game -> game.getRating() >= 7.0).toList();
    }

    /**
     * Filters board games by year published.
     */
    public List<BoardGame> filterByYearPublished(String cmd, List<BoardGame> list) {
        return list.stream().filter(game -> game.getYearPublished() >= 2000).toList();
    }

    /**
     * Filters board games by rank.
     */
    public List<BoardGame> filterByRank(String cmd, List<BoardGame> list) {
        return list.stream().filter(game -> game.getRank() <= 500).toList();
    }

    /**
     * Filters board games by name.
     */
    public List<BoardGame> filterByName(String name, List<BoardGame> list) {
        String searchName = name.replaceAll("\\s+", "").toLowerCase();
        return list.stream()
                .filter(game -> game.getName().replaceAll("\\s+", "").toLowerCase().contains(searchName))
                .toList();
    }
}
