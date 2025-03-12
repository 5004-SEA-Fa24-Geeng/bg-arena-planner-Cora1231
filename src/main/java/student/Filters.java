package student;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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

        // Keeping GameData variables for filtering
        GameData minplayers = GameData.fromString("minplayers");
        GameData maxplayers = GameData.fromString("maxplayers");
        GameData mintime = GameData.fromString("minplaytime");
        GameData maxtime = GameData.fromString("maxplaytime");
        GameData difficulty = GameData.fromString("difficulty");
        GameData yearpublished = GameData.fromString("yearpublished");
        GameData rating = GameData.fromString("rating");
        GameData rank = GameData.fromString("rank");

        for (String cmd : cmds) {
            cmd = cmd.trim().toLowerCase().replaceAll("\\s+", "");
            if (cmd.contains("name")) {
                gameList = filterByName(cmd, gameList);
            } else if (cmd.contains("maxplayers") || cmd.contains(maxplayers.name().toLowerCase())) {
                cmd = cmd.replace(maxplayers.name().toLowerCase(), "maxplayers");
                gameList = filterByMaxPlayer(cmd, gameList);
            } else if (cmd.contains("minplayers") || cmd.contains(minplayers.name().toLowerCase())) {
                cmd = cmd.replace(minplayers.name().toLowerCase(), "minplayers");
                gameList = filterByMinPlayer(cmd, gameList);
            } else if (cmd.contains("minplaytime") || cmd.contains(mintime.name().toLowerCase())) {
                cmd = cmd.replace(mintime.name().toLowerCase(), "minplaytime");
                gameList = filterByMinTime(cmd, gameList);
            } else if (cmd.contains("maxplaytime") || cmd.contains(maxtime.name().toLowerCase())) {
                cmd = cmd.replace(maxtime.name().toLowerCase(), "maxplaytime");
                gameList = filterByMaxTime(cmd, gameList);
            } else if (cmd.contains("difficulty") || cmd.contains(difficulty.name().toLowerCase())) {
                cmd = cmd.replace(difficulty.name().toLowerCase(), "difficulty");
                gameList = filterByDifficulty(cmd, gameList);
            } else if (cmd.contains("rating") || cmd.contains(rating.name().toLowerCase())) {
                cmd = cmd.replace(rating.name().toLowerCase(), "rating");
                gameList = filterByRating(cmd, gameList);
            } else if (cmd.contains("yearpublished") || cmd.contains(yearpublished.name().toLowerCase())) {
                cmd = cmd.replace(yearpublished.name().toLowerCase(), "yearpublished");
                gameList = filterByYearPublished(cmd, gameList);
            } else if (cmd.contains("rank") || cmd.contains(rank.name().toLowerCase())) {
                cmd = cmd.replace(rank.name().toLowerCase(), "rank");
                gameList = filterByRank(cmd, gameList);
            }
        }

        gameList = new ArrayList<>(new LinkedHashSet<>(gameList));
        return gameList;
    }

    /**
     * Filters board games by rating.
     *
     * @param cmd  The rating filtering command (e.g., "rating >= 4.5").
     * @param list The list of board games.
     * @return A list of board games that match the rating filter.
     */
    public List<BoardGame> filterByRating(String cmd, List<BoardGame> list) {
        Pattern pattern = Pattern.compile("\\s*(~=|==|!=|>=|<=|<|>)\\s*(.*)");
        Matcher matcher = pattern.matcher(cmd);
        List<BoardGame> res = new ArrayList<>();

        if (matcher.find()) {
            String operator = matcher.group(1);
            double value = Double.parseDouble(matcher.group(2));

            for (BoardGame s : list) {
                double rating = s.getRating();
                switch (operator) {
                    case "==":
                        if (rating == value) {
                            res.add(s);
                        }
                        break;
                    case "!=":
                        if (rating != value) {
                            res.add(s);
                        }
                        break;
                    case ">":
                        if (rating > value) {
                            res.add(s);
                        }
                        break;
                    case "<":
                        if (rating < value) {
                            res.add(s);
                        }
                        break;
                    case ">=":
                        if (rating >= value) {
                            res.add(s);
                        }
                        break;
                    case "<=":
                        if (rating <= value) {
                            res.add(s);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return res;
    }

    /**
     * Filters board games by year published.
     *
     * @param cmd  The year filtering command (e.g., "yearPublished >= 2000").
     * @param list The list of board games.
     * @return A list of board games that match the year filter.
     */
    public List<BoardGame> filterByYearPublished(String cmd, List<BoardGame> list) {
        Pattern pattern = Pattern.compile("\\s*(~=|==|!=|>=|<=|<|>)\\s*(.*)");
        Matcher matcher = pattern.matcher(cmd);
        List<BoardGame> res = new ArrayList<>();

        if (matcher.find()) {
            String operator = matcher.group(1);
            int value = Integer.parseInt(matcher.group(2));

            for (BoardGame s : list) {
                int year = s.getYearPublished();
                checkCmd(res, operator, value, s, year);
            }
        }
        return res;
    }

    /**
     * Helper method for performing numeric comparisons.
     *
     * @param res        The result list to which a board game is added if the condition is met.
     * @param operator   The comparison operator.
     * @param value      The value to compare against.
     * @param s          The board game.
     * @param fieldValue The field value from the board game.
     */
    private void checkCmd(List<BoardGame> res, String operator, int value, BoardGame s, int fieldValue) {
        switch (operator) {
            case "==":
                if (fieldValue == value) {
                    res.add(s);
                }
                break;
            case "!=":
                if (fieldValue != value) {
                    res.add(s);
                }
                break;
            case ">":
                if (fieldValue > value) {
                    res.add(s);
                }
                break;
            case "<":
                if (fieldValue < value) {
                    res.add(s);
                }
                break;
            case ">=":
                if (fieldValue >= value) {
                    res.add(s);
                }
                break;
            case "<=":
                if (fieldValue <= value) {
                    res.add(s);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Filters board games by rank.
     *
     * @param cmd  The rank filtering command.
     * @param list The list of board games.
     * @return A list of board games that match the rank filter.
     */
    public List<BoardGame> filterByRank(String cmd, List<BoardGame> list) {
        Pattern pattern = Pattern.compile("rank\\s*(~=|==|!=|>=|<=|<|>)\\s*(.*)");
        Matcher matcher = pattern.matcher(cmd);
        List<BoardGame> res = new ArrayList<>();

        if (matcher.find()) {
            String operator = matcher.group(1);
            int value = Integer.parseInt(matcher.group(2));

            for (BoardGame s : list) {
                int rankValue = s.getRank();
                checkCmd(res, operator, value, s, rankValue);
            }
        }
        return res;
    }

    /**
     * Filters board games by name.
     *
     * @param name The name filtering command.
     * @param list The list of board games.
     * @return A list of board games matching the name filter.
     */
    public List<BoardGame> filterByName(String name, List<BoardGame> list) {
        Pattern pattern = Pattern.compile("name\\s*(~=|==|!=|>=|<=|<|>)\\s*(.*)");
        Matcher matcher = pattern.matcher(name);
        List<BoardGame> res = new ArrayList<>();

        if (matcher.find()) {
            String operator = matcher.group(1);
            String value = matcher.group(2);
            String anotherString = value.toLowerCase().trim().replaceAll("\\s+", "");

            switch (operator) {
                case "==":
                    for (BoardGame s : list) {
                        if (s.getName().trim().toLowerCase().replaceAll("\\s+", "").equals(anotherString)) {
                            res.add(s);
                        }
                    }
                    break;
                case "!=":
                    for (BoardGame s : list) {
                        if (!s.getName().trim().toLowerCase().replaceAll("\\s+", "").equals(anotherString)) {
                            res.add(s);
                        }
                    }
                    break;
                case "<":
                    for (BoardGame s : list) {
                        if (s.getName().trim().toLowerCase().replaceAll("\\s+", "").compareTo(anotherString) < 0) {
                            res.add(s);
                        }
                    }
                    break;
                case ">=":
                    for (BoardGame s : list) {
                        if (s.getName().trim().toLowerCase().replaceAll("\\s+", "").compareTo(anotherString) >= 0) {
                            res.add(s);
                        }
                    }
                    break;
                case "<=":
                    for (BoardGame s : list) {
                        if (s.getName().trim().toLowerCase().replaceAll("\\s+", "").compareTo(anotherString) <= 0) {
                            res.add(s);
                        }
                    }
                    break;
                case ">":
                    for (BoardGame s : list) {
                        if (s.getName().trim().toLowerCase().replaceAll("\\s+", "").compareTo(anotherString) > 0) {
                            res.add(s);
                        }
                    }
                    break;
                default:
                    for (BoardGame s : list) {
                        if (s.getName().trim().toLowerCase().replaceAll("\\s+", "")
                                .contains(value.toLowerCase().replaceAll("\\s+", ""))) {
                            res.add(s);
                        }
                    }
                    break;
            }
        }
        res.sort((o1, o2) -> o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()));
        return res;
    }

    /**
     * Filters board games by maximum number of players.
     *
     * @param cmd  The max players filtering command.
     * @param list The list of board games.
     * @return A list of board games that match the max players filter.
     */
    public List<BoardGame> filterByMaxPlayer(String cmd, List<BoardGame> list) {
        Pattern pattern = Pattern.compile("maxplayers\\s*(~=|==|!=|>=|<=|<|>)\\s*(.*)");
        Matcher matcher = pattern.matcher(cmd);
        List<BoardGame> res = new ArrayList<>();

        if (matcher.find()) {
            String operator = matcher.group(1);
            String value = matcher.group(2);

            if (operator.equals("==")) {
                for (BoardGame s : list) {
                    if (s.getMaxPlayers() == Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals(">")) {
                for (BoardGame s : list) {
                    if (s.getMaxPlayers() > Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals("!=")) {
                for (BoardGame s : list) {
                    if (s.getMaxPlayers() != Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals("<")) {
                for (BoardGame s : list) {
                    if (s.getMaxPlayers() < Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals(">=")) {
                for (BoardGame s : list) {
                    if (s.getMaxPlayers() >= Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals("<=")) {
                for (BoardGame s : list) {
                    if (s.getMaxPlayers() <= Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }
        }
        return res;
    }

    /**
     * Filters board games by minimum number of players.
     *
     * @param cmd  The min players filtering command.
     * @param list The list of board games.
     * @return A list of board games that match the min players filter.
     */
    public List<BoardGame> filterByMinPlayer(String cmd, List<BoardGame> list) {
        Pattern pattern = Pattern.compile("minplayers\\s*(~=|==|!=|>=|<=|<|>)\\s*(.*)");
        Matcher matcher = pattern.matcher(cmd);
        List<BoardGame> res = new ArrayList<>();

        if (matcher.find()) {
            String operator = matcher.group(1);
            String value = matcher.group(2);

            if (operator.equals("==")) {
                for (BoardGame s : list) {
                    if (s.getMinPlayers() == Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals(">")) {
                for (BoardGame s : list) {
                    if (s.getMinPlayers() > Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals("<")) {
                for (BoardGame s : list) {
                    if (s.getMinPlayers() < Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals(">=")) {
                for (BoardGame s : list) {
                    if (s.getMinPlayers() >= Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals("!=")) {
                for (BoardGame s : list) {
                    if (s.getMinPlayers() != Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals("<=")) {
                for (BoardGame s : list) {
                    if (s.getMinPlayers() <= Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }
        }
        return res;
    }

    /**
     * Filters board games by minimum play time.
     *
     * @param cmd  The min play time filtering command.
     * @param list The list of board games.
     * @return A list of board games that match the min play time filter.
     */
    public List<BoardGame> filterByMinTime(String cmd, List<BoardGame> list) {
        Pattern pattern = Pattern.compile("minplaytime\\s*(~=|==|!=|>=|<=|<|>)\\s*(.*)");
        Matcher matcher = pattern.matcher(cmd);
        List<BoardGame> res = new ArrayList<>();

        if (matcher.find()) {
            String operator = matcher.group(1);
            String value = matcher.group(2);

            if (operator.equals("==")) {
                for (BoardGame s : list) {
                    if (s.getMinPlayTime() == Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals(">")) {
                for (BoardGame s : list) {
                    if (s.getMinPlayTime() > Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals("<")) {
                for (BoardGame s : list) {
                    if (s.getMinPlayTime() < Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals("!=")) {
                for (BoardGame s : list) {
                    if (s.getMaxPlayers() != Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals(">=")) {
                for (BoardGame s : list) {
                    if (s.getMinPlayTime() >= Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals("<=")) {
                for (BoardGame s : list) {
                    if (s.getMinPlayTime() <= Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }
        }
        return res;
    }

    /**
     * Filters board games by maximum play time.
     *
     * @param cmd  The max play time filtering command.
     * @param list The list of board games.
     * @return A list of board games that match the max play time filter.
     */
    public List<BoardGame> filterByMaxTime(String cmd, List<BoardGame> list) {
        Pattern pattern = Pattern.compile("maxplaytime\\s*(~=|==|!=|>=|<=|<|>)\\s*(.*)");
        Matcher matcher = pattern.matcher(cmd);
        List<BoardGame> res = new ArrayList<>();

        if (matcher.find()) {
            String operator = matcher.group(1);
            String value = matcher.group(2);

            if (operator.equals("==")) {
                for (BoardGame s : list) {
                    if (s.getMaxPlayTime() == Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals(">")) {
                for (BoardGame s : list) {
                    if (s.getMaxPlayTime() > Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals("<")) {
                for (BoardGame s : list) {
                    if (s.getMaxPlayTime() < Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals("!=")) {
                for (BoardGame s : list) {
                    if (s.getMaxPlayers() != Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals(">=")) {
                for (BoardGame s : list) {
                    if (s.getMaxPlayTime() >= Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals("<=")) {
                for (BoardGame s : list) {
                    if (s.getMaxPlayTime() <= Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }
        }
        return res;
    }

    /**
     * Filters board games by difficulty.
     *
     * @param cmd  The difficulty filtering command.
     * @param list The list of board games.
     * @return A list of board games that match the difficulty filter.
     */
    public List<BoardGame> filterByDifficulty(String cmd, List<BoardGame> list) {
        Pattern pattern = Pattern.compile("difficulty\\s*(~=|==|!=|>=|<=|<|>)\\s*(.*)");
        Matcher matcher = pattern.matcher(cmd);
        List<BoardGame> res = new ArrayList<>();

        if (matcher.find()) {
            String operator = matcher.group(1);
            String value = matcher.group(2);

            if (operator.equals("==")) {
                for (BoardGame s : list) {
                    if (s.getDifficulty() == Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals(">")) {
                for (BoardGame s : list) {
                    if (s.getDifficulty() > Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals("<")) {
                for (BoardGame s : list) {
                    if (s.getDifficulty() < Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals("!=")) {
                for (BoardGame s : list) {
                    if (s.getDifficulty() != Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals(">=")) {
                for (BoardGame s : list) {
                    if (s.getDifficulty() >= Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if (operator.equals("<=")) {
                for (BoardGame s : list) {
                    if (s.getDifficulty() <= Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }
        }
        return res;
    }
}
