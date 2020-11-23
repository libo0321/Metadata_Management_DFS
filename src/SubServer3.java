import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import Basic.BasicClass;
import Basic.Folder;
import Basic.File;

public class SubServer3 {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8002);
            System.out.println("MetadataServer 3 launched....");
            Socket s = ss.accept();
            System.out.println("Cluster server connected");

            Map<Integer, BasicClass> datas = new HashMap<>();
            String message="";

            while (true) {
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
                String[] cmd = br.readLine().split(" ");
                switch (cmd[0]) {
                    case "chkdist":
                        System.out.println(cmd[0]);
                        break;

                    case "mkdir":
                        Folder folder = new Folder(cmd[1]);
                        datas.put(Integer.parseInt(cmd[1]),folder);
                        System.out.println(message);
                        break;

                    case "touch":
                        File file = new File(cmd[1]);
                        datas.put(Integer.parseInt(cmd[1]),file);
                        System.out.println(message);
                        break;


                    case "rmdir":
                    case "rm":
                        datas.remove(Integer.parseInt(cmd[1]));
                        System.out.println(message);
                        break;

                    case "chmod":
                        File item1 = (File) datas.get(Integer.parseInt(cmd[1]));
                        item1.chmod(cmd[2]);
                        System.out.println(message);
                        break;

                    case "stat":
                        BasicClass item2 = datas.get(Integer.parseInt(cmd[1]));
                        message = item2.toString();
                        System.out.println(message);
                        break;

                    case "exit":
                        message = cmd[0];
                        System.exit(0);
                }

                writer.println(message);
                writer.flush();
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}