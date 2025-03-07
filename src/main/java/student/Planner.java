package student;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


public class Planner implements IPlanner {
    Set<BoardGame> games;
    Stream<BoardGame> curList;
    public Planner(Set<BoardGame> games) {
        // TODO Auto-generated method stub
        this.games = games;
        curList = games.stream();
    }

    @Override
    public Stream<BoardGame> filter(String filter) {
        // TODO Auto-generated method stu.b
        Filters filters = new Filters();
        curList = filters.controller(filter,games.stream().toList()).stream();
        return curList;
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        Filters filters = new Filters();
        List<BoardGame> list = filters.controller(filter,games.stream().toList());
        list.sort((a,b)->a.getYearPublished()== b.getYearPublished() ? a.getName().compareToIgnoreCase(b.getName()) : a.getYearPublished()-b.getYearPublished());
        curList = list.stream();
        System.out.println(curList);
        return curList;
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        Filters filters = new Filters();
        List<BoardGame> list = filters.controller(filter,games.stream().toList());
        list.sort((a,b)->a.getYearPublished()== b.getYearPublished() ? a.getName().compareToIgnoreCase(b.getName()) : a.getYearPublished()-b.getYearPublished());
        curList = list.stream();
        return curList;
    }

    @Override
    public void reset() {
        curList = games.stream();
    }

    public Stream<BoardGame> getCurList() {
        return curList;
    }


}
