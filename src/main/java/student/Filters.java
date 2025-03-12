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
    public Filters() {}

    /**
     * Applies multiple filtering and sorting commands to a list of board games.
     *
     * @param commands  The filtering/sorting commands in string format.
     * @param gameList  The list of board games to be filtered.
     * @return The filtered and sorted list of board games.
     */
    public List<BoardGame> controller(String commands, List<BoardGame> gameList) {
        String[] cmds = commands.split(",");

        if (gameList.isEmpty())
            return gameList;

        // Keeping GameData variables for filtering
        GameData minplayers = GameData.fromString("minplayers");
        GameData maxplayers = GameData.fromString("maxplayers");
        GameData mintime = GameData.fromString("minplaytime");
        GameData maxtime = GameData.fromString("maxplaytime");
        GameData difficulty = GameData.fromString("difficulty");
        GameData yearpublished = GameData.fromString("yearpublished");
        GameData rating = GameData.fromString("rating");
        GameData rank  = GameData.fromString("rank");

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
            }else if (cmd.contains("rank") || cmd.contains(rank.name().toLowerCase())) {
                cmd = cmd.replace(rank.name().toLowerCase(), "rank");
                gameList = filterByRank(cmd, gameList);
            }
        }

        // Sorting by rating if specified
        if (commands.contains("sort:rating")) {
            if (commands.contains("sort:rating desc")) {
                gameList.sort((a, b) -> Double.compare(b.getRating(), a.getRating()));
            } else if (commands.contains("sort:rating asc")) {
                gameList.sort((a, b) -> Double.compare(a.getRating(), b.getRating()));
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
        Pattern pattern = Pattern.compile("rating\\s*(~=|==|!=|>=|<=|<|>)\\s*(\\d+(\\.\\d+)?)");
        Matcher matcher = pattern.matcher(cmd);
        List<BoardGame> res = new ArrayList<>();

        if (matcher.find()) {
            String operator = matcher.group(1);
            double value = Double.parseDouble(matcher.group(2));

            for (BoardGame s : list) {
                double rating = s.getRating();
                switch (operator) {
                    case "==" -> { if (rating == value) res.add(s); }
                    case "!=" -> { if (rating != value) res.add(s); }
                    case ">"  -> { if (rating > value) res.add(s); }
                    case "<"  -> { if (rating < value) res.add(s); }
                    case ">=" -> { if (rating >= value) res.add(s); }
                    case "<=" -> { if (rating <= value) res.add(s); }
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
        Pattern pattern = Pattern.compile("yearpublished\\s*(~=|==|!=|>=|<=|<|>)\\s*(\\d+)");
        Matcher matcher = pattern.matcher(cmd);
        List<BoardGame> res = new ArrayList<>();

        if (matcher.find()) {
            String operator = matcher.group(1);
            int value = Integer.parseInt(matcher.group(2));

            for (BoardGame s : list) {
                int year = s.getYearPublished();
                switch (operator) {
                    case "==" -> { if (year == value) res.add(s); }
                    case "!=" -> { if (year != value) res.add(s); }
                    case ">"  -> { if (year > value) res.add(s); }
                    case "<"  -> { if (year < value) res.add(s); }
                    case ">=" -> { if (year >= value) res.add(s); }
                    case "<=" -> { if (year <= value) res.add(s); }
                }
            }
        }
        return res;
    }

    public List<BoardGame> filterByRank(String cmd, List<BoardGame> list) {
        Pattern pattern = Pattern.compile("yearpublished\\s*(~=|==|!=|>=|<=|<|>)\\s*(\\d+)");
        Matcher matcher = pattern.matcher(cmd);
        List<BoardGame> res = new ArrayList<>();

        if (matcher.find()) {
            String operator = matcher.group(1);
            int value = Integer.parseInt(matcher.group(2));

            for (BoardGame s : list) {
                int year = s.getRank();
                switch (operator) {
                    case "==" -> { if (year == value) res.add(s); }
                    case "!=" -> { if (year != value) res.add(s); }
                    case ">"  -> { if (year > value) res.add(s); }
                    case "<"  -> { if (year < value) res.add(s); }
                    case ">=" -> { if (year >= value) res.add(s); }
                    case "<=" -> { if (year <= value) res.add(s); }
                }
            }
        }
        return res;
    }

    /**
     * Filters board games by name.
     *
     * @param name The name substring to filter by.
     * @param list The list of board games.
     * @return A list of board games matching the name filter.
     */
    public List<BoardGame> filterByName(String name, List<BoardGame> list) {
        Pattern pattern = Pattern.compile("name\\s*(~=|==|!=|>=|<=|<|>)\\s*(.*)");
        Matcher matcher = pattern.matcher(name);
        List<BoardGame> res = new ArrayList<>();
        if (matcher.find()) {
            String operator = matcher.group(1);  // Either ~= or ==
            String value = matcher.group(2);     // Extracted value
            String anotherString = value.toLowerCase().trim().replaceAll("\\s+", "");
            switch (operator) {
                case "==" -> {
                    for (BoardGame s : list) {

                        if (s.getName().trim().toLowerCase().replaceAll("\\s+", "").equals(anotherString)) {
                            res.add(s);
                        }
                    }
                }
                case "!=" -> {
                    for (BoardGame s : list) {
                        if (! s.getName().trim().toLowerCase().replaceAll("\\s+", "").equals(anotherString)) {
                            res.add(s);
                        }
                    }
                }
                case "<" -> {
                    for (BoardGame s : list) {
                        if (s.getName().trim().toLowerCase().replaceAll("\\s+", "").compareTo(anotherString) < 0) {
                            res.add(s);
                        }
                    }
                }
                case ">=" -> {
                    for (BoardGame s : list) {
                        if (s.getName().trim().toLowerCase().replaceAll("\\s+", "").compareTo(anotherString) >= 0) {
                            res.add(s);
                        }
                    }
                }
                case "<=" -> {
                    for (BoardGame s : list) {
                        if (s.getName().trim().toLowerCase().replaceAll("\\s+", "").compareTo(anotherString) <= 0) {
                            res.add(s);
                        }
                    }
                }
                case ">" -> {
                    for (BoardGame s : list) {
                        if (s.getName().trim().toLowerCase().replaceAll("\\s+", "").compareTo(anotherString) > 0) {
                            res.add(s);
                        }
                    }
                }
                default -> {
                    for (BoardGame s : list) {
                        if (s.getName().trim().toLowerCase().replaceAll("\\s+", "").contains(value.toLowerCase().replaceAll("\\s+", ""))) {
                            res.add(s);
                        }
                    }
                }
            }
        }
        res.sort(((o1, o2) -> o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase())));
        return res;
    }

    public List<BoardGame> filterByMaxPlayer(String cmd, List<BoardGame> list) {
        Pattern pattern = Pattern.compile("maxplayers\\s*(~=|==|!=|>=|<=|<|>)\\s*(.*)");
        Matcher matcher = pattern.matcher(cmd);
        List<BoardGame> res = new ArrayList<>();
        if (matcher.find()) {
            String operator = matcher.group(1);  // Either ~= or ==
            String value = matcher.group(2);     // Extracted value
            if (operator.equals("==")) {
                for (BoardGame s : list) {
                    if (s.getMaxPlayers()== Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals(">")){
                for (BoardGame s : list) {
                    if (s.getMaxPlayers() > Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if(operator.equals("!=")){
                for (BoardGame s : list) {
                    if (s.getMaxPlayers() != Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals("<")){
                for (BoardGame s : list) {
                    if (s.getMaxPlayers() < Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals(">=")){
                for (BoardGame s : list) {
                    if (s.getMaxPlayers() >= Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals("<=")){
                for (BoardGame s : list) {
                    if (s.getMaxPlayers() <= Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }
        }
        return res;
    }


    public List<BoardGame>  filterByMinPlayer(String cmd, List<BoardGame> list) {
        Pattern pattern = Pattern.compile("minplayers\\s*(~=|==|!=|>=|<=|<|>)\\s*(.*)");
        Matcher matcher = pattern.matcher(cmd);
        List<BoardGame> res = new ArrayList<>();
        if (matcher.find()) {
            String operator = matcher.group(1);  // Either ~= or ==
            String value = matcher.group(2);     // Extracted value
            if (operator.equals("==")) {
                for (BoardGame s : list) {
                    if (s.getMinPlayers()== Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals(">")){
                for (BoardGame s : list) {
                    if (s.getMinPlayers() > Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if(operator.equals("<")){
                for (BoardGame s : list) {
                    if (s.getMinPlayers() < Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals(">=")){
                for (BoardGame s : list) {
                    if (s.getMinPlayers() >= Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals("!=")){
                for (BoardGame s : list) {
                    if (s.getMinPlayers() != Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals("<=")){
                for (BoardGame s : list) {
                    if (s.getMinPlayers() <= Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }
        }
        return res;
    }


    public List<BoardGame> filterByMinTime(String cmd, List<BoardGame> list) {
        Pattern pattern = Pattern.compile("minplaytime\\s*(~=|==|!=|>=|<=|<|>)\\s*(.*)");
        Matcher matcher = pattern.matcher(cmd);
        List<BoardGame> res = new ArrayList<>();
        if (matcher.find()) {
            String operator = matcher.group(1);  // Either ~= or ==
            String value = matcher.group(2);     // Extracted value
            if (operator.equals("==")) {
                for (BoardGame s : list) {
                    if (s.getMinPlayTime()== Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals(">")){
                for (BoardGame s : list) {
                    if (s.getMinPlayTime() > Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if(operator.equals("<")){
                for (BoardGame s : list) {
                    if (s.getMinPlayTime() < Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals("!=")){
                for (BoardGame s : list) {
                    if (s.getMaxPlayers() != Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals(">=")){
                for (BoardGame s : list) {
                    if (s.getMinPlayTime() >= Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals("<=")){
                for (BoardGame s : list) {
                    if (s.getMinPlayTime() <= Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }
        }
        return res;
    }

    public List<BoardGame> filterByMaxTime(String cmd, List<BoardGame> list) {
        Pattern pattern = Pattern.compile("maxplaytime\\s*(~=|==|!=|>=|<=|<|>)\\s*(.*)");
        Matcher matcher = pattern.matcher(cmd);
        List<BoardGame> res = new ArrayList<>();
        if (matcher.find()) {
            String operator = matcher.group(1);  // Either ~= or ==
            String value = matcher.group(2);     // Extracted value
            if (operator.equals("==")) {
                for (BoardGame s : list) {
                    if (s.getMaxPlayTime()== Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals(">")){
                for (BoardGame s : list) {
                    if (s.getMaxPlayTime() > Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if(operator.equals("<")){
                for (BoardGame s : list) {
                    if (s.getMaxPlayTime() < Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals("!=")){
                for (BoardGame s : list) {
                    if (s.getMaxPlayers() != Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals(">=")){
                for (BoardGame s : list) {
                    if (s.getMaxPlayTime() >= Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals("<=")){
                for (BoardGame s : list) {
                    if (s.getMaxPlayTime() <= Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }
        }
        return res;
    }

    public List<BoardGame> filterByDifficulty(String cmd, List<BoardGame> list) {
        Pattern pattern = Pattern.compile("difficulty\\s*(~=|==|!=|>=|<=|<|>)\\s*(.*)");
        Matcher matcher = pattern.matcher(cmd);
        List<BoardGame> res = new ArrayList<>();
        if (matcher.find()) {
            String operator = matcher.group(1);  // Either ~= or ==
            String value = matcher.group(2);     // Extracted value
            if (operator.equals("==")) {
                for (BoardGame s : list) {
                    if (s.getDifficulty()== Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals(">")){
                for (BoardGame s : list) {
                    if (s.getDifficulty() > Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            } else if(operator.equals("<")){
                for (BoardGame s : list) {
                    if (s.getDifficulty() < Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals("!=")){
                for (BoardGame s : list) {
                    if (s.getDifficulty() != Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals(">=")){
                for (BoardGame s : list) {
                    if (s.getDifficulty() >= Integer.parseInt(value)) {
                        res.add(s);
                    }
                }
            }else if(operator.equals("<=")){
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
