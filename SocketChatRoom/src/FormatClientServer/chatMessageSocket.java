package FormatClientServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JTextPane;

public class chatMessageSocket {
	private Socket socket;	
	private JTextPane jTextPane;
	private PrintWriter out;
	private BufferedReader reader;
	public chatMessageSocket(Socket socket, JTextPane jTextPane) throws IOException {
		this.socket = socket;
		this.jTextPane = jTextPane;
		out = new PrintWriter(socket.getOutputStream());
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		receive();
	}
	
	public void receive()
	{
		Thread t = new Thread() {
			public void run()
			{
				try {
					while(true)
					{
						String line = reader.readLine();
						if(line != null)
						{
							jTextPane.setText(jTextPane.getText()+"<br>"+line);
						}
					}
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
			}
		};
		t.start();
	}
	public void send(String message)
	{
		String currentText = jTextPane.getText();
		jTextPane.setText(currentText+"<br>Send: "+message);
		out.println(message);
		out.flush();
		
	}
	public void close()
	{
		
		try {
			out.close();
			reader.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
