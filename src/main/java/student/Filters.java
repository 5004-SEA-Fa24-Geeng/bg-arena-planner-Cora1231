package student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Filters {
    Filters() {}

    public List<BoardGame> controller(String commands,List<BoardGame> gameList){
        String[] cmds = commands.split(",");
        for(String cmd:cmds){
            if(cmd.contains("name~=")){
                String[] ss = cmd.split("name~=");
                String[] parts = ss[1].split(" ");
                gameList =  filterByName(parts[0],gameList);
            }else if(cmd.contains("maxPlayers>=")){
                String[] ss = cmd.split("maxPlayers>=");
                String[] parts = ss[1].split(" ");
                gameList =filterByMaxPlayer(Integer.parseInt(parts[0]),gameList);
            }else if(cmd.contains("minPlayer>=")){
                String[] ss = cmd.split("minPlayer>=");
                String[] parts = ss[1].split(" ");
                gameList =filterByMinPlayer(Integer.parseInt(parts[0]),gameList);
            }else if(cmd.contains("minTime>=")){
                String[] ss = cmd.split("minTime>=");
                String[] parts = cmd.split(" ");
                gameList = filterByMinTime(Integer.parseInt(parts[0]),gameList);
            }
        }
        if(commands.contains("sort:rating")){
            String[] parts = commands.split("sort:rating");
            if (parts.length > 1) {
                String[] sortParts = parts[1].trim().split(" ");
                if (sortParts.length > 1 ){
                   if(sortParts[1].equals("desc")){
                        gameList.sort(((a, b) -> (int) (b.getRating() - a.getRating())));
                    }else if(sortParts[1].equals("asc")){
                       gameList.sort(((a, b) -> (int) (a.getRating() - b.getRating())));
                   }
                }
            }
        }
        return gameList;
    }
    public List<BoardGame> filterByName(String name,List<BoardGame> list) {
        List<BoardGame>res = new ArrayList<>();
        for (BoardGame s : list) {
            if(s.getName().contains(name)) {
                res.add(s);
            }
        }
        return res;
    }

    public List<BoardGame> filterByMaxPlayer(int n,List<BoardGame> list) {
        List<BoardGame> res = new ArrayList<>();
        for (BoardGame s : list) {
            if(s.getMaxPlayers()>=n){
                res.add(s);
            }
        }
        return res;
    }

    public List<BoardGame> filterByMinPlayer(int n,List<BoardGame> list) {
        List<BoardGame> res = new ArrayList<>();
        for (BoardGame s : list) {
            if(s.getMaxPlayers()<=n){
                res.add(s);
            }
        }
        return res;
    }

    public List<BoardGame> filterByMinTime(int n,List<BoardGame> list) {
        List<BoardGame> res = new ArrayList<>();
        for (BoardGame s : list) {
            if(s.getMinPlayTime()>=n){
                res.add(s);
            }
        }
        return res;
    }
}
