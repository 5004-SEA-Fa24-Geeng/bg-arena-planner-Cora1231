package student;

import java.util.*;
import java.util.stream.Stream;

/**
 * The Planner class implements IPlanner and provides functionality
 * for filtering and sorting a collection of board games.
 */
public class Planner implements IPlanner {
    /** Set of all available board games. */
    private final Set<BoardGame> games;

    /** Current working list of board games after filtering. */
    private List<BoardGame> curList;

    /**
     * Constructs a Planner with a given set of board games.
     *
     * @param games The set of board games.
     */
    public Planner(Set<BoardGame> games) {
        this.games = games;
        curList = new ArrayList<>(games);
    }

    /**
     * Filters board games based on the given filter string.
     *
     * @param filter The filter conditions.
     * @return A stream of filtered board games.
     */
    @Override
    public Stream<BoardGame> filter(String filter) {
        Filters filters = new Filters();
        System.out.println("This is filter: " + filter);
        curList = filters.controller(filter, curList);
        curList.sort(Comparator.comparing(game -> game.getName().replaceAll("\\s+", "").toLowerCase()));
        return curList.stream();
    }

    /**
     * Filters and sorts board games based on a specific attribute.
     *
     * @param filter The filter conditions.
     * @param sortOn The attribute to sort on.
     * @return A stream of filtered and sorted board games.
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        Filters filters = new Filters();
        List<BoardGame> list = filters.controller(filter, curList);
        curList = sortGames(list, sortOn.name().toLowerCase(), true);
        return curList.stream();
    }

    /**
     * Filters and sorts board games based on a specific attribute and sorting order.
     *
     * @param filter The filter conditions.
     * @param sortOn The attribute to sort on.
     * @param ascending True for ascending order, False for descending order.
     * @return A stream of filtered and sorted board games.
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        Filters filters = new Filters();
        List<BoardGame> list = filters.controller(filter, curList);
        curList = sortGames(list, sortOn.name().toLowerCase(), ascending);
        if (curList.isEmpty()) {
            return null;
        }
        return curList.stream();
    }

    /**
     * Sorts the list of board games based on the specified attribute and order.
     *
     * @param gameList The list of board games to be sorted.
     * @param column   The attribute to sort by.
     * @param asc      True for ascending order, False for descending order.
     * @return The sorted list of board games.
     */
    public static List<BoardGame> sortGames(List<BoardGame> gameList, String column, boolean asc) {
        Comparator<BoardGame> comparator;

        switch (column) {
            case "rating":
                comparator = Comparator.comparingDouble(BoardGame::getRating);
                break;
            case "name":
                comparator = Comparator.comparing(game -> game.getName().replaceAll("\\s+", "").toLowerCase());
                break;
            case "yearpublished", "year_published":
                comparator = Comparator.comparingInt(BoardGame::getYearPublished);
                break;
            case "rank":
                comparator = Comparator.comparingInt(BoardGame::getRank);
                break;
            case "difficulty":
                comparator = Comparator.comparingDouble(BoardGame::getDifficulty);
                break;
            case "minplayers", "min_players":
                comparator = Comparator.comparingInt(BoardGame::getMinPlayers);
                break;
            case "maxplayers", "max_players":
                comparator = Comparator.comparingInt(BoardGame::getMaxPlayers); // ✅ FIXED
                break;
            case "maxplaytime", "max_playtime":
                comparator = Comparator.comparingInt(BoardGame::getMaxPlayTime);
                break;
            case "minplaytime", "min_playtime":
                comparator = Comparator.comparingInt(BoardGame::getMinPlayTime);
                break;
            default:
                System.out.println("Invalid column name: " + column);
                return new ArrayList<>();
        }

        if (!asc) {
            comparator = comparator.reversed();
        }

        gameList.sort(comparator);
        return gameList;
    }

    /**
     * Resets the filtered list back to the original game set.
     */
    @Override
    public void reset() {
        curList = new ArrayList<>(games);
    }

    /**
     * Returns the current list of board games as a stream.
     *
     * @return A stream of the current list of board games.
     */
    public Stream<BoardGame> getCurList() {
        return curList.stream();
    }
}
