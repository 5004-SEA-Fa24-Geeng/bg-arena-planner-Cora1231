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

        if(gameList.isEmpty())
            return gameList;
        GameData minplayers = GameData.fromString("minplayers");
        GameData maxplayers = GameData.fromString("maxplayers");
        GameData mintime = GameData.fromString("minplaytime");

        for (String cmd : cmds) {
            cmd = cmd.trim().toLowerCase().replaceAll("\\s+", "");
            if (cmd.contains("name")) {
                gameList = filterByName(cmd, gameList);
            } else if (cmd.contains("maxplayers")||cmd.contains(maxplayers.name().toLowerCase())) {
                cmd =cmd.replace(maxplayers.name().toLowerCase(),"maxplayers");
                gameList = filterByMaxPlayer(cmd, gameList);
            } else if (cmd.contains("minplayers") ||cmd.contains(minplayers.name().toLowerCase())) {
                cmd =cmd.replace(minplayers.name().toLowerCase(),"minplayers");
                gameList = filterByMinPlayer(cmd, gameList);

            } else if (cmd.contains("minplaytime")||cmd.contains(mintime.name().toLowerCase())) {
                cmd = cmd.replace(mintime.name().toLowerCase(),"minplaytime");
                gameList = filterByMinTime(cmd, gameList);
            }
        }

        // Sorting by rating if specified
        if (commands.contains("sort:rating")) {
            String[] parts = commands.split("sort:rating");
            if (parts.length > 1) {
                String[] sortParts = parts[1].trim().split(" ");
                if (sortParts.length > 1) {
                    if (sortParts[1].equals("desc")) {
                        gameList.sort((a, b) -> Double.compare(b.getRating(), a.getRating()));
                    } else if (sortParts[1].equals("asc")) {
                        gameList.sort((a, b) -> Double.compare(a.getRating(), b.getRating()));
                    }
                }
            }
        }
        gameList = new ArrayList<>(new LinkedHashSet<>(gameList));
        return gameList;
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
        Pattern pattern = Pattern.compile("mintime\\s*(~=|==|!=|>=|<=|<|>)\\s*(.*)");
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
}
