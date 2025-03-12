package student;


import java.util.*;
import java.util.stream.Stream;


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
        list.sort((a,b)->a.getYearPublished()== b.getYearPublished() ? a.getName().compareToIgnoreCase(b.getName()) : a.getYearPublished()-b.getYearPublished());
        curList = list;
        System.out.println(curList);
        return curList.stream();
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        Filters filters = new Filters();
        List<BoardGame> list = filters.controller(filter,curList);
        System.out.println("this is filter "+filter);
        System.out.println("this is GameData "+sortOn.name());

        sortGames(list,sortOn.name().toLowerCase(),ascending);
        curList = list;
        return curList.stream();
    }

    public static void sortGames(List<BoardGame> gameList, String column, boolean asc) {
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
                comparator = Comparator.comparingInt(BoardGame::getMaxPlayers);
                break;
            case "maxplaytime":
                comparator = Comparator.comparingInt(BoardGame::getMaxPlayTime);
                break;
            case "minplaytime":
                comparator = Comparator.comparingInt(BoardGame::getMinPlayTime);
                break;
            default:
                System.out.println("Invalid column name: " + column);
                return;
        }

        if (!asc) {
            comparator = comparator.reversed();
        }

        gameList.sort(comparator);
    }

    @Override
    public void reset() {
        curList = new ArrayList<>(games);
    }

    public Stream<BoardGame> getCurList() {
        return curList.stream();
    }


}
