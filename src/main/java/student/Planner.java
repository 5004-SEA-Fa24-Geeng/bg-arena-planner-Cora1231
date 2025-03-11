package student;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        list.sort((a,b)->a.getYearPublished()== b.getYearPublished() ? a.getName().compareToIgnoreCase(b.getName()) : a.getYearPublished()-b.getYearPublished());
        curList = list;
        return curList.stream();
    }

    @Override
    public void reset() {
        curList = new ArrayList<>(games);
    }

    public Stream<BoardGame> getCurList() {
        return curList.stream();
    }


}
