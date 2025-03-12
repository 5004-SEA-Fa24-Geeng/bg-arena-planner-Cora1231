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
    public Filters() {
        // Default constructor
    }

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

        for (String cmd : cmds) {
            cmd = cmd.trim().toLowerCase().replaceAll("\\s+", "");

            if (cmd.contains("name")) {
                gameList = filterByName(cmd, gameList);
            } else if (cmd.contains("maxplayers")) {
                gameList = filterByMaxPlayer(cmd, gameList);
            } else if (cmd.contains("minplayers")) {
                gameList = filterByMinPlayer(cmd, gameList);
            } else if (cmd.contains("minplaytime")) {
                gameList = filterByMinTime(cmd, gameList);
            } else if (cmd.contains("maxplaytime")) {
                gameList = filterByMaxTime(cmd, gameList);
            } else if (cmd.contains("difficulty")) {
                gameList = filterByDifficulty(cmd, gameList);
            } else if (cmd.contains("rating")) {
                gameList = filterByRating(cmd, gameList);
            } else if (cmd.contains("yearpublished")) {
                gameList = filterByYearPublished(cmd, gameList);
            } else if (cmd.contains("rank")) {
                gameList = filterByRank(cmd, gameList);
            }
        }

        return new ArrayList<>(new LinkedHashSet<>(gameList));
    }

    /**
     * Filters board games by a given numerical property.
     */
    private List<BoardGame> filterByNumeric(String cmd, List<BoardGame> list, String field) {
        Pattern pattern = Pattern.compile("\\s*(~=|==|!=|>=|<=|<|>)\\s*(.*)");
        Matcher matcher = pattern.matcher(cmd);
        List<BoardGame> res = new ArrayList<>();

        if (matcher.find()) {
            String operator = matcher.group(1);
            double value = Double.parseDouble(matcher.group(2));

            for (BoardGame s : list) {
                double fieldValue = switch (field) {
                    case "rating" -> s.getRating();
                    case "difficulty" -> s.getDifficulty();
                    case "yearpublished" -> s.getYearPublished();
                    case "rank" -> s.getRank();
                    case "maxplayers" -> s.getMaxPlayers();
                    case "minplayers" -> s.getMinPlayers();
                    case "minplaytime" -> s.getMinPlayTime();
                    case "maxplaytime" -> s.getMaxPlayTime();
                    default -> throw new IllegalArgumentException("Invalid field: " + field);
                };

                switch (operator) {
                    case "==" -> { if (fieldValue == value) res.add(s); }
                    case "!=" -> { if (fieldValue != value) res.add(s); }
                    case ">" -> { if (fieldValue > value) res.add(s); }
                    case "<" -> { if (fieldValue < value) res.add(s); }
                    case ">=" -> { if (fieldValue >= value) res.add(s); }
                    case "<=" -> { if (fieldValue <= value) res.add(s); }
                }
            }
        }
        return res;
    }

    public List<BoardGame> filterByRating(String cmd, List<BoardGame> list) {
        return filterByNumeric(cmd, list, "rating");
    }

    public List<BoardGame> filterByDifficulty(String cmd, List<BoardGame> list) {
        return filterByNumeric(cmd, list, "difficulty");
    }

    public List<BoardGame> filterByYearPublished(String cmd, List<BoardGame> list) {
        return filterByNumeric(cmd, list, "yearpublished");
    }

    public List<BoardGame> filterByRank(String cmd, List<BoardGame> list) {
        return filterByNumeric(cmd, list, "rank");
    }

    public List<BoardGame> filterByMaxPlayer(String cmd, List<BoardGame> list) {
        return filterByNumeric(cmd, list, "maxplayers");
    }

    public List<BoardGame> filterByMinPlayer(String cmd, List<BoardGame> list) {
        return filterByNumeric(cmd, list, "minplayers");
    }

    public List<BoardGame> filterByMinTime(String cmd, List<BoardGame> list) {
        return filterByNumeric(cmd, list, "minplaytime");
    }

    public List<BoardGame> filterByMaxTime(String cmd, List<BoardGame> list) {
        return filterByNumeric(cmd, list, "maxplaytime");
    }

    /**
     * Filters board games by name.
     */
    public List<BoardGame> filterByName(String name, List<BoardGame> list) {
        Pattern pattern = Pattern.compile("name\\s*(~=|==|!=)\\s*(.*)");
        Matcher matcher = pattern.matcher(name);
        List<BoardGame> res = new ArrayList<>();

        if (matcher.find()) {
            String operator = matcher.group(1);
            String value = matcher.group(2).trim().toLowerCase().replaceAll("\\s+", "");

            for (BoardGame s : list) {
                String gameName = s.getName().trim().toLowerCase().replaceAll("\\s+", "");
                switch (operator) {
                    case "==" -> { if (gameName.equals(value)) res.add(s); }
                    case "!=" -> { if (!gameName.equals(value)) res.add(s); }
                    default -> { if (gameName.contains(value)) res.add(s); }
                }
            }
        }
        res.sort(Comparator.comparing(o -> o.getName().toLowerCase()));
        return res;
    }
}
