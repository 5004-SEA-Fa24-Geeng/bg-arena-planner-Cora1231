package student;

import com.sun.source.tree.UsesTree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameList implements IGameList {
    private static final String DEFAULT_FILENAME = "games_list.txt";

    /**
     * Constructor for the GameList.
     */
    List<BoardGame> games;
    public GameList() {

        games = new ArrayList<>();
    }

    @Override
    public List<String> getGameNames() {
        return games.stream().map(BoardGame::getName).toList();
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        games.clear();
    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
       return games.size();
    }

    @Override
    public void saveGame(String filename) {
        // TODO Auto-generated method stub
        if (filename == null || filename.trim().isEmpty()) {
            filename = DEFAULT_FILENAME;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (BoardGame game : games) {
                writer.write(game.getName());
                writer.newLine();
            }
            System.out.println("Games list saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        //list OfGames HashSet, str operation, Stram<BoardGame> filtered
        if (str == null || str.isEmpty()) {
            return;
        }
        List<BoardGame> filteredList = filtered.toList();

        if(str.contains("-")){
            String[] arr = str.split("-");
            if(arr.length == 2){
                try{
                    int start = Integer.parseInt(arr[0]);
                    int end = Integer.parseInt(arr[1]);
                    for(int i = Math.max(0,start-1); i < Math.min(end,filteredList.size()); i++){
                        games.add(filteredList.get(i));
                    }
                    return;
                }catch (NumberFormatException e){
                    throw new IllegalArgumentException("invalid format");
                }
            }
        }
        try {
            int idx = Integer.parseInt(str);
            if(idx >= 1 && idx <= filteredList.size()){
                games.add(filteredList.get(idx-1));
            }
        } catch (NumberFormatException e) {
            for(BoardGame game : filteredList){
                if(game.getName().equals(str)){
                    games.add(game);
                }
            }

        }


    }

    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        if (str == null || str.isEmpty()) {
            return;
        }

        if(str.contains("-")){
            String[] arr = str.split("-");
            if(arr.length == 2){
                try{
                    int start = Integer.parseInt(arr[0]);
                    int end = Integer.parseInt(arr[1]);
                    for(int i = Math.max(0,start-1); i < Math.min(end,games.size()); i++){
                       games.remove(i);
                    }
                    return;
                }catch (NumberFormatException e){
                    throw new IllegalArgumentException("invalid format");
                }
            }
        }
        try {
            int idx = Integer.parseInt(str);
            if(idx >= 0 && idx < games.size()){
                games.remove(idx);
            }
        } catch (NumberFormatException e) {
            games.remove(str);
        }
    }


}
