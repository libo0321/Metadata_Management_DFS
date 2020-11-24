import java.io.*;
import java.net.*;


public class Client{
	public static void main(String[] args) {
		String cmd;
		String message;
		int serverPort=8888;

		try {
//		Socket socket=new Socket(InetAddress.getByName(null),serverPort);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			Socket socket = new Socket("127.0.0.1", serverPort);
			System.out.println("Connection established :");
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (true) {
				cmd = br.readLine();
				switch (cmd.split(" ")[0]) {

					case "mkdir":

					case "touch":

					case "rmdir":

					case "rm":

					case "chmod":

					case "ls":

					case "cd":
						out.println(cmd);
						message = in.readLine();
						System.out.println(message);
						break;

					case "stat":
						out.println(cmd);
						message = in.readLine();
						System.out.println(message);
						message = in.readLine();
						System.out.println(message);
						break;

					case "tree":
						out.println(cmd);
						message = in.readLine();
						while(!message.equals("end_of_tree")){
							System.out.println(message);
							message = in.readLine();
						}
						break;

					case "fulltree":
						out.println(cmd);
						message = in.readLine();
						while(!message.equals("end_of_full_tree")){
							System.out.println(message);
							message = in.readLine();
						}
						break;

					case "exit":
						socket.close();
						System.exit(0);

					default:
						System.out.println("unknown command");
						break;
				}
			}
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
}

