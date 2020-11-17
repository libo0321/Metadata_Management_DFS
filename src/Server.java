import java.io.*;
import java.net.*;
import Basic.Folder;
import Basic.File;

import javax.swing.table.TableStringConverter;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8888);
            System.out.println("Server launched...");

            Socket[] sockets = new Socket[3];
            sockets[0] = new Socket("127.0.0.1", 8000);
            sockets[1] = new Socket("127.0.0.1", 8001);
            sockets[2] = new Socket("127.0.0.1", 8002);
            System.out.println("Connected to metadata server 1");
            System.out.println("Connected to metadata server 2");
            System.out.println("Connected to metadata server 3");

            PrintWriter[] outs = new PrintWriter[3];
            outs[0] = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sockets[0].getOutputStream())), true);
            outs[1] = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sockets[1].getOutputStream())), true);
            outs[2] = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sockets[2].getOutputStream())), true);

            BufferedReader[] ins = new BufferedReader[3];
            ins[0] = new BufferedReader(new InputStreamReader(sockets[0].getInputStream()));
            ins[1] = new BufferedReader(new InputStreamReader(sockets[1].getInputStream()));
            ins[2] = new BufferedReader(new InputStreamReader(sockets[2].getInputStream()));

            Socket s = ss.accept();
            System.out.println("Client : " + InetAddress.getLocalHost() + " connected");


            String message = "";
            String current_directory = "/";
            Folder root = new Folder(current_directory);
            FileTree tree = new FileTree(root);
            int id = 0;

            while (true) {
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
                String[] cmd = br.readLine().split(" ");
                switch (cmd[0]) {
                    case "chkdist":
                        System.out.println(cmd[0]);
                        break;

                    case "mkdir":
                        if (cmd.length != 2)
                            message = "Name of directory expected, got "+(cmd.length-1)+" variables";
                        else {
                            if (tree.has_directory(current_directory, cmd[1])) {
                                message = "Directory " + cmd[1] + " already exists in current directory\n";
                            } else {
                                id ++;
                                int server_id = (id-1)%3;
                                String abs_path = current_directory+cmd[1]+"/";
                                String cmd_reconstructed = cmd[0]+" "+id;  // 给subserver的是id
                                outs[server_id].println(cmd_reconstructed);
                                tree.createNew(abs_path, id);
                                message = "Directory " + cmd[1] + "/ created in current directory";
                            }
                        }
                        System.out.println(message);
                        break;

                    case "touch":
                        if (cmd.length != 2)
                            message = "Name of file expected, got "+(cmd.length-1)+" variables";
                        else {
                            if (tree.has_file(current_directory, cmd[1])) {
                                message = "File " + cmd[1] + " already exists in the current directory\n";
                            } else {
                                id++;
                                int server_id = (id-1)%3;
                                String abs_path = current_directory+cmd[1];
                                String cmd_reconstructed = cmd[0]+" "+id;
                                outs[server_id].println(cmd_reconstructed);
                                tree.createNew(abs_path, id);
                                message = "File " + cmd[1] + " created in current directory";
                            }
                        }
                        System.out.println(message);
                        break;

                    case "rmdir":
                        if (cmd.length != 2)
                            message = "Name of directory expected, got "+(cmd.length-1)+" variables";
                        else
                            if(tree.has_directory(current_directory, cmd[1])) {
                                String abs_path = current_directory + cmd[1] + "/";
                                int item_id = tree.getIdByPath(abs_path);
                                int server_id = (item_id-1) % 3;

                                String cmd_reconstructed = cmd[0] + " " + item_id;
                                outs[server_id].println(cmd_reconstructed);
                                tree.rm_dir(current_directory, cmd[1]);
                                message = "Directory removed";
                            }
                            else
                                message = "Directory doesn't exist";
                        System.out.println(message);
                        break;

                    case "rm":
                        if (cmd.length != 2)
                            message = "Name of file expected, got "+(cmd.length-1)+" variables";
                        else
                            if(tree.has_file(current_directory, cmd[1])) {
                                String abs_path = current_directory + cmd[1];
                                int item_id = tree.getIdByPath(abs_path);
                                int server_id = (item_id-1) % 3;

                                String cmd_reconstructed = cmd[0] + " " + item_id;
                                outs[server_id].println(cmd_reconstructed);
                                tree.rm(current_directory, cmd[1]);
                                message = "File removed";
                            }
                            else
                                message = "File doesn't exist";
                        System.out.println(message);
                        break;

                    case "chmod":
                        if (cmd.length != 3)
                            message = "Name of file and mode expected, got "+(cmd.length-1)+" variables";
                        else
                            if(tree.has_file(current_directory, cmd[1])){
                                String abs_path = current_directory+cmd[1];
                                int id_item = tree.getIdByPath(abs_path);
                                int server_id = (id_item-1)%3;
                                String cmd_reconstructed = cmd[0]+" "+id_item+" "+cmd[2];
                                outs[server_id].println(cmd_reconstructed);
                                message = "File " + cmd[1] + " changed mode";
                            }
                        System.out.println(message);
                        break;

                    case "tree":
                        message = cmd[0];
                        System.out.println(cmd[0]);
                        break;

                    case "fulltree":
                        message = cmd[0];
                        System.out.println(cmd[0]);
                        break;

                    case "ls":
                        if (cmd.length != 1)
                            message = "No variable expected, got "+(cmd.length-1)+" variables";
                        else
                            message = tree.ls(current_directory);
                        System.out.println(message);
                        break;

                    case "stat":
                        if (cmd.length != 2)
                            message = "Name of file of directory expected, got "+(cmd.length-1)+" variables";
                        else
                        if(tree.has_file(current_directory, cmd[1])){
                            String abs_path = current_directory+cmd[1];
                            int id_item = tree.getIdByPath(abs_path);
                            int server_id = (id_item-1)%3;
                            String cmd_reconstructed = cmd[0]+" "+id_item;
                            outs[server_id].println(cmd_reconstructed);
                            message = ins[server_id].readLine();
                        }
                        System.out.println(message);
                        break;

                    case "cd":
                        if (cmd.length != 2)
                            message = "Position expected, got "+(cmd.length-1)+" variables";
                        else
                            if(cmd[1].equals("..")) {
                                if (!current_directory.equals("/"))
                                    current_directory = current_directory.substring(0, current_directory.lastIndexOf('/',current_directory.length()-2) + 1);
                                message = "Current Directory changed to " + current_directory;
                            }
                            else
                                if(tree.has_directory(current_directory, cmd[1])) {
                                    current_directory = current_directory+cmd[1]+"/";
                                    message = "Current Directory changed to " + current_directory;
                                }
                                else
                                    message = "Directory doesn't exist";
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