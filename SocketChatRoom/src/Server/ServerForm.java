package Server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import FormatClientServer.chatMessageSocket;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class ServerForm extends JFrame {
	chatMessageSocket chatMessageSocket;
	private JPanel contentPane;
	private JTextField portTf;
	private JTextField messTf;
	JTextPane messPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerForm frame = new ServerForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 698, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel serverLb = new JLabel("Server");
		serverLb.setFont(new Font("Tahoma", Font.BOLD, 18));
		serverLb.setBounds(311, 10, 109, 13);
		contentPane.add(serverLb);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 43, 658, 2);
		contentPane.add(separator);
		
		JLabel portLb = new JLabel("Port");
		portLb.setBounds(20, 64, 45, 13);
		contentPane.add(portLb);
		
		portTf = new JTextField();
		portTf.setText("8765");
		portTf.setBounds(75, 61, 157, 19);
		contentPane.add(portTf);
		portTf.setColumns(10);
		
		JButton btnListen = new JButton("Listen Client");
		btnListen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ServerSocket serverSocket = new ServerSocket(Integer.parseInt(portTf.getText()));
					Thread t = new Thread() {
						public void run()
						{
							try {
								messPane.setText(messPane.getText() +"<br> Listening... ");
								Socket serverSK = serverSocket.accept();
								chatMessageSocket = new chatMessageSocket(serverSK, messPane);
								// start receive message
								
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					};
					t.start();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		btnListen.setBounds(534, 60, 122, 21);
		contentPane.add(btnListen);
		
		messPane = new JTextPane();
		messPane.setBounds(20, 104, 648, 220);
		contentPane.add(messPane);
		
		JLabel messLb = new JLabel("Message : ");
		messLb.setBounds(27, 351, 70, 34);
		contentPane.add(messLb);
		
		messTf = new JTextField();
		messTf.setBounds(85, 356, 472, 26);
		contentPane.add(messTf);
		messTf.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(messTf.getText()==null)
				{
					return;
				}
				chatMessageSocket.send(messTf.getText());
				
			}
		});
		
		
		btnSend.setBounds(583, 355, 85, 27);
		contentPane.add(btnSend);
	}
}
