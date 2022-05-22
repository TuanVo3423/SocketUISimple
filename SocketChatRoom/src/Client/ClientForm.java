package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import FormatClientServer.chatMessageSocket;

import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class ClientForm extends JFrame {
	chatMessageSocket chatMessageSocket;
	private JPanel contentPane;
	private JTextField portTf;
	private JTextField messTf;
	private JTextField serverHostTf;
	JTextPane messPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientForm frame = new ClientForm();
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
	public ClientForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 698, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel clientLb = new JLabel("Client");
		clientLb.setFont(new Font("Tahoma", Font.BOLD, 18));
		clientLb.setBounds(311, 10, 109, 13);
		contentPane.add(clientLb);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 43, 658, 2);
		contentPane.add(separator);
		
		JLabel portLb = new JLabel("Port");
		portLb.setBounds(282, 64, 45, 13);
		contentPane.add(portLb);
		
		portTf = new JTextField();
		portTf.setText("8765");
		portTf.setBounds(337, 61, 157, 19);
		contentPane.add(portTf);
		portTf.setColumns(10);
		
		JButton btnListen = new JButton("Connect");
		btnListen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Socket socket = new Socket(serverHostTf.getText(),Integer.parseInt(portTf.getText()));
					chatMessageSocket = new chatMessageSocket(socket, messPane);
					messPane.setText(messPane.getText()+"\nConnected");
				} catch (NumberFormatException | IOException e1) {
					
					e1.printStackTrace();
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
				if(messTf.getText()==null) {
					return ;
				}
				chatMessageSocket.send(messTf.getText());
				
			}
		});
		
		
		btnSend.setBounds(583, 355, 85, 27);
		contentPane.add(btnSend);
		
		serverHostTf = new JTextField();
		serverHostTf.setText("127.0.0.1");
		serverHostTf.setColumns(10);
		serverHostTf.setBounds(85, 61, 157, 19);
		contentPane.add(serverHostTf);
		
		JLabel serverHostlb = new JLabel("Server Host");
		serverHostlb.setBounds(20, 64, 70, 13);
		contentPane.add(serverHostlb);
	}
}
