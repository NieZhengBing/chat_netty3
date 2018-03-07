package com.nzb.netty3.client.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.nzb.netty3.client.Client;
import com.nzb.netty3.client.swing.constant.ButtonCommand;
import com.nzb.netty3.common.core.model.Request;
import com.nzb.netty3.common.module.ModuleId;
import com.nzb.netty3.common.module.chat.ChatCmd;
import com.nzb.netty3.common.module.chat.request.PrivateChatRequest;
import com.nzb.netty3.common.module.chat.request.PublicChatRequest;
import com.nzb.netty3.common.module.player.PlayerCmd;
import com.nzb.netty3.common.module.player.request.LoginRequest;
import com.nzb.netty3.common.module.player.request.RegisterRequest;
import com.nzb.netty3.common.module.player.response.PlayerResponse;

@Component
public class Swingclient extends JFrame implements ActionListener {

	private static final long serialVersionUID = 2347397080271591182L;
	@Autowired
	private Client client;

	private PlayerResponse playerResponse;

	private JTextField playerName;

	private JTextField password;

	private JButton loginButton;

	private JButton register;

	private JTextArea chatContext;

	private JTextField message;

	private JTextField targetPlayer;

	private JButton sendButton;

	private JLabel tips;

	public Swingclient() throws HeadlessException {
		super();
		getContentPane().setLayout(null);

		JLabel lblIp = new JLabel("role");
		lblIp.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblIp.setBounds(76, 40, 54, 15);
		getContentPane().add(lblIp);

		playerName = new JTextField();
		playerName.setBounds(139, 37, 154, 21);
		getContentPane().add(playerName);
		playerName.setColumns(10);

		JLabel label = new JLabel("password");
		label.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label.setBounds(76, 71, 54, 15);
		getContentPane().add(label);

		password = new JTextField();
		password.setColumns(10);
		password.setBounds(139, 68, 154, 21);
		getContentPane().add(password);

		loginButton = new JButton("Login");
		loginButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		loginButton.setActionCommand(ButtonCommand.LOGIN);
		loginButton.addActionListener(this);
		loginButton.setBounds(315, 37, 93, 23);
		getContentPane().add(loginButton);

		register = new JButton("Register");
		register.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		register.setActionCommand(ButtonCommand.REGISTER);
		register.addActionListener(this);
		register.setBounds(315, 67, 93, 23);
		getContentPane().add(register);

		chatContext = new JTextArea();
		chatContext.setLineWrap(true);
		JScrollPane scrollBar = new JScrollPane(chatContext);
		scrollBar.setBounds(76, 96, 93, 403);
		scrollBar.setSize(336, 300);
		getContentPane().add(scrollBar);

		JLabel label_7 = new JLabel("message");
		label_7.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label_7.setBounds(76, 411, 54, 15);
		getContentPane().add(label_7);

		message = new JTextField();
		message.setBounds(139, 408, 222, 21);
		getContentPane().add(message);
		message.setColumns(10);

		JLabel jblid = new JLabel("Role");
		jblid.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		jblid.setBounds(382, 407, 67, 23);
		getContentPane().add(jblid);

		targetPlayer = new JTextField();
		targetPlayer.setBounds(139, 438, 133, 21);
		getContentPane().add(targetPlayer);
		targetPlayer.setColumns(10);

		sendButton = new JButton("Send");
		sendButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		sendButton.setBounds(382, 407, 67, 23);
		sendButton.setActionCommand(ButtonCommand.SEND);
		sendButton.addActionListener(this);
		getContentPane().add(sendButton);

		tips = new JLabel();
		tips.setForeground(Color.RED);
		tips.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		tips.setBounds(76, 488, 200, 15);
		getContentPane().add(tips);

		int width = 500;
		int heigh = 600;
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - heigh) / 2;
		this.setLocation(w, h);
		this.setTitle("chat tool");
		this.setSize(width, heigh);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
	}

	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case ButtonCommand.LOGIN:
			try {
				LoginRequest loginRequest = new LoginRequest();
				loginRequest.setPlayerName(playerName.getText());
				loginRequest.setPassword(password.getText());

				Request request = Request.valueOf(ModuleId.PLAYER, PlayerCmd.LOGIN, loginRequest.getBytes());
				client.sendRequest(request);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case ButtonCommand.REGISTER:
			try {
				RegisterRequest registerRequest = new RegisterRequest();
				registerRequest.setPlayerName(playerName.getText());
				registerRequest.setPassword(password.getText());
				Request request = Request.valueOf(ModuleId.PLAYER, PlayerCmd.REGISTER_AND_LOGIN,
						registerRequest.getBytes());
				client.sendRequest(request);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			break;
		case ButtonCommand.SEND:
			try {
				if (StringUtils.isEmpty(targetPlayer.getText()) && !StringUtils.isEmpty(message.getText())) {
					PublicChatRequest publicChatRequest = new PublicChatRequest();
					publicChatRequest.setContext(message.getText());

					Request request = Request.valueOf(ModuleId.CHAT, ChatCmd.PUBLIC_CHAT, publicChatRequest.getBytes());
					client.sendRequest(request);
				} else {
					if (StringUtils.isEmpty(message.getText())) {
						tips.setText("message is not empty");
						return;
					}
					long playerId = 0;
					try {
						playerId = Long.parseLong(targetPlayer.getText());
					} catch (NumberFormatException ex) {
						tips.setText("player id must be number");
						return;
					}

					PrivateChatRequest privateChatRequest = new PrivateChatRequest();
					privateChatRequest.setContext(message.getText());
					privateChatRequest.setTargetPlayerId(playerId);

					Request request = Request.valueOf(ModuleId.CHAT, ChatCmd.PRIVATE_CHAT,
							privateChatRequest.getBytes());
					client.sendRequest(request);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				tips.setText("can not connect to server");
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			client.shutdown();
		}
	}

	public JTextArea getChatContext() {
		return chatContext;
	}

	public JLabel getTips() {
		return tips;
	}

	public PlayerResponse getPlayerResponse() {
		return playerResponse;
	}

	public void setPlayerResponse(PlayerResponse playerResponse) {
		this.playerResponse = playerResponse;
	}

}
