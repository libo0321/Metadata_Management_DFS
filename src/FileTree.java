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

    public boolean is_parent_directory(String abs_path){
        int count = 0;
        for (String key : tree.keySet()){
            if (key.startsWith(abs_path))
                count++;

        }
        return count!=1;
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
            if (key.startsWith(current)) {
                int key_length = key.length();
                if(key_length>length&&!key.substring(length,key_length-1).contains("/")) {
                    message.append(key.substring(length));
                    message.append("   ");
                }
            }
        return message.toString();
    }

    public String tree(String current, int iter){
        StringBuilder target = new StringBuilder();
        String b = "----";
        int length = current.length();
        for (String key : tree.keySet())
            if (key.startsWith(current)) {
                int key_length = key.length();
                if(key_length>length&&!key.substring(length,key_length-1).contains("/")){

                    target.append(b.repeat(iter)).append(key.substring(length)).append('\n');
                    if (key.charAt(key_length-1)=='/')
                        target.append(tree(key, iter + 1));

                }
            }
        if (iter==0)
            target.append("end_of_tree");
        return target.toString();
    }

    public String fulltree(String current, int iter){
        StringBuilder target = new StringBuilder();
        String b = "----";
        int length = current.length();
        for (String key : tree.keySet())
            if (key.startsWith(current)) {
                int key_length = key.length();
                if(key_length>length&&!key.substring(length,key_length-1).contains("/")){

                    target.append(b.repeat(iter)).append(key).append('\n');
                    if (key.charAt(key_length-1)=='/')
                        target.append(fulltree(key, iter + 1));

                }
            }
        if (iter==0)
            target.append("end_of_full_tree");
        return target.toString();
    }

    public void print() {
        for (String key : tree.keySet()) {
            System.out.println(key);
        }
    }


}
