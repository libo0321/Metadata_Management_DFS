import Basic.*;

import java.util.ArrayList;
import java.util.List;

public class TestFileSystem {

    public static void main(String [] args){
        String rt = "/";
        Folder root = new Folder(rt);
        Folder d1 = new Folder(rt,"d1");
        File f1 = new File(rt,"f1");
        System.out.println(d1);
        System.out.println(f1);


        List<String> test = new ArrayList<>();
        test.add("/sdf/234");
        test.add("ss");
        test.add("wer");

        String sf= "/sdf/234/";
        String[] sfd = sf.split("/");

        System.out.println(sf.length());
        System.out.println(sf.substring(0,sf.lastIndexOf('/',sf.length()-2)+1));







    }
}
