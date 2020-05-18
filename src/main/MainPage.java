package main;

import dao.*;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField; 

public interface MainPage {
	//登录页面（主页面）
    public static void main(String args[]) {
    	//创建窗口
    	JFrame frame = new JFrame("实验室管理系统_登录");
        frame.setSize(500,300);
        Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((displaySize.width - 500)/2,
        		(displaySize.height - 300)/2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();    
        frame.add(panel);
        panel.setLayout(null);
        
        //标题文本
        JLabel title = new JLabel("實驗室管理系統");
        Font font = new Font("楷体", Font.PLAIN, 30);
        title.setFont(font);
        title.setBounds(125,50,245,30);
        panel.add(title);
        
        //ID输入提示文本
        JLabel userLabel = new JLabel("用户ID：");
        userLabel.setBounds(110,100,80,25);
        panel.add(userLabel);

        //ID输入框
        JTextField userText = new JTextField(20);
        userText.setBounds(190,100,165,25);
        panel.add(userText);

        //密码输入提示文本
        JLabel passwordLabel = new JLabel("用户密码：");
        passwordLabel.setBounds(110,135,80,25);
        panel.add(passwordLabel);

        //密码输入框
        JPasswordField passwordText = new JPasswordField(16);
        passwordText.setBounds(190,135,165,25);
        panel.add(passwordText);

        //登录按钮
        JButton mLoginButton = new JButton("管理员登录");
        mLoginButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
            		if (ManagerDao.mIdentify(Integer.parseInt(userText.getText()), 
            				new String(passwordText.getPassword()))) {
            			frame.dispose();
            			BussinessPage.managerBussiness(Integer.parseInt(userText.getText()));
            			JOptionPane.showMessageDialog(null,"登录成功！");
            		} else {
            			JOptionPane.showMessageDialog(null,"ID或密码错误！");
            		}
        		} catch (Exception ex) {
        			JOptionPane.showMessageDialog(null,"ID输入有误！请输入数字ID.");
        		}
        	}
        });
        mLoginButton.setBounds(110,170,120,25);
        panel.add(mLoginButton);
        JButton rLoginButton = new JButton("研究员登录");
        rLoginButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
            		if (ResearcherDao.rIdentify(Integer.parseInt(userText.getText()), 
            				new String(passwordText.getPassword()))) {
            			frame.dispose();
            			BussinessPage.researcherBussiness(Integer.parseInt(userText.getText()));
            			JOptionPane.showMessageDialog(null,"登录成功！");
            		} else {
            			JOptionPane.showMessageDialog(null,"ID或密码错误！");
            		}
        		} catch (Exception ex) {
        			JOptionPane.showMessageDialog(null,"ID输入有误！请输入数字ID.");
        		}
        	}
        });
        rLoginButton.setBounds(235,170,120,25);
        panel.add(rLoginButton);
        
        frame.setResizable(false);
        frame.setVisible(true);
    }
}