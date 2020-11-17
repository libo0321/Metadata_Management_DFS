package Basic;

public class Folder extends BasicClass{

    public Folder(String absolute_path){
        super(absolute_path, Type.Folder);
    }
    public Folder(String current_path, String name) {super(current_path+name+"/",Type.Folder);}

    @Override
    public String toString() {
        return "id: "+id+"   path: "+path+"    type: "+type.toString();
    }
}
