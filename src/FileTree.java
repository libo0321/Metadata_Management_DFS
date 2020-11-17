import java.util.HashMap;
import java.util.Map;

import Basic.*;

public class FileTree {
    private Map<String,Integer> tree;


    public FileTree(BasicClass root){
        tree = new HashMap<>();
        tree.put(root.getPath(),0);  // root directory
    }

    public int length(){
        return tree.size();
    }

    public void createNew(BasicClass f,int id){
        tree.put(f.getPath(),id);
    }

    public void createNew(String path,int id){
        tree.put(path, id);
    }

    public int getIdByPath(String abs_path){
        return tree.get(abs_path);
    }

    public boolean has_directory(String current, String name){
        return tree.containsKey(current+name+"/");
    }

    public boolean has_directory(String abs_path){
        return tree.containsKey(abs_path);
    }

    public boolean has_file(String current, String name){
        return tree.containsKey(current+name);
    }

    public void rm_dir(String current, String name){
        String abs_path = current+name+"/";
        if (has_directory(current,name)) {
            tree.remove(abs_path);
        }
    }

    public void rm(String current, String name){
        String abs_path = current+name;
        if (has_file(current,name)) {
            tree.remove(abs_path);
        }
    }

    public String ls(String current){
        int length = current.length();
        StringBuilder message = new StringBuilder();
        for (String key : tree.keySet())
            if (key.contains(current)) {
                message.append(key.substring(length));
                message.append("   ");
            }
        return message.toString();
    }

    public void print() {
        for (String key : tree.keySet()) {
            System.out.println(key);
        }
    }


}
