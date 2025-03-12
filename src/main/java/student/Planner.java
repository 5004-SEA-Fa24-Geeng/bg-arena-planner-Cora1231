package student;


import java.util.*;
import java.util.stream.Stream;

import static student.GameData.MIN_PLAYERS;


public class Planner implements IPlanner {
    Set<BoardGame> games;
    List<BoardGame> curList;
    public Planner(Set<BoardGame> games) {
        // TODO Auto-generated method stub
        this.games = games;
        curList = new ArrayList<>(games);
    }

    @Override
    public Stream<BoardGame> filter(String filter) {
        // TODO Auto-generated method stu.b
        Filters filters = new Filters();
        System.out.println("this is filter "+filter);
        curList = filters.controller(filter,curList);
        curList.sort(((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName())));
        return curList.stream();
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        Filters filters = new Filters();
        List<BoardGame> list = filters.controller(filter,curList);
        System.out.println("this is sortOn "+sortOn.getColumnName());
        System.out.println("this is sortOn "+sortOn.name());

        curList = sortGames(list,sortOn.name().toLowerCase(),true);
        return curList.stream();
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        Filters filters = new Filters();
        List<BoardGame> list = filters.controller(filter,curList);

        curList = sortGames(list,sortOn.name().toLowerCase(),ascending);
        if(curList.isEmpty()) {
            return null;
        }
        return curList.stream();
    }

    public static List<BoardGame> sortGames(List<BoardGame> gameList, String column, boolean asc) {
        Comparator<BoardGame> comparator = null;

        switch (column) {
            case "rating":
                comparator = Comparator.comparingDouble(BoardGame::getRating);
                break;
            case "name":
                comparator = Comparator.comparing(game -> game.getName().replaceAll("\\s+", "").toLowerCase());
                break;
            case "yearpublished":
                comparator = Comparator.comparingInt(BoardGame::getYearPublished);
                break;
            case "rank":
                comparator = Comparator.comparingInt(BoardGame::getRank);
                break;
            case "difficulty":
                comparator = Comparator.comparingDouble(BoardGame::getDifficulty);
                break;
            case "minplayers":
                comparator = Comparator.comparingInt(BoardGame::getMinPlayers);
                break;
            case "maxplayers":
                comparator = (a, b) -> Integer.compare(a.getMinPlayers(), b.getMinPlayers());
                break;
            case "maxplaytime":
                comparator = Comparator.comparingInt(BoardGame::getMaxPlayTime);
                break;
            case "minplaytime":
                comparator = Comparator.comparingInt(BoardGame::getMinPlayTime);
                break;
            default:
                System.out.println("Invalid column name: " + column);
                return new ArrayList<>() ;
        }

        if (!asc) {
            comparator = comparator.reversed();
        }

        gameList.sort(comparator);
        return gameList;
    }

    @Override
    public void reset() {
        curList = new ArrayList<>(games);
    }

    public Stream<BoardGame> getCurList() {
        return curList.stream();
    }


}
