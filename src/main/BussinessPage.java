package main;

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

import dao.EquipmentDao;
import dao.ManagerDao;
import dao.ResearcherDao;
import gui.EquipManage;
import gui.ResearcherControl;

public interface BussinessPage {
	public static void managerBussiness(int id) {
		//创建窗口
    	JFrame frame = new JFrame("实验室管理系统_业务");
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
        
        //微软雅黑字体
	    font = new Font("微软雅黑", Font.PLAIN, 15);
        
	    //欢迎文本
        JLabel hello;
		try {
			hello = new JLabel("姓名: " + ManagerDao.getManager(id).getManagerName());
		    hello.setFont(font);
	        hello.setBounds(5,5,100,15);
		    panel.add(hello);
		} catch (Exception e) {
			//什么都不做
		}
		
		//用户身份文本
		JLabel identity = new JLabel("身份: 管理员");
		identity.setFont(font);
		identity.setBounds(5,25,100,15);
		panel.add(identity);
		
		//修改密码业务
		JButton cPButton = new JButton("修改密码");
		cPButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//修改密码窗口
		    	JFrame frame = new JFrame("修改密码");
		        frame.setSize(300,200);
		        Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		        frame.setLocation((displaySize.width - 300)/2,
		        		(displaySize.height - 200)/2);
		        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        
		        JPanel panel = new JPanel();
		        frame.add(panel);
		        panel.setLayout(null);
		        
				//密码输入文本
				JLabel oldPasswordText = new JLabel("输入旧密码: ");
				oldPasswordText.setBounds(35,25,100,15);
				panel.add(oldPasswordText);
				JLabel newPasswordText = new JLabel("输入新密码: ");
				newPasswordText.setBounds(35,55,100,15);
				panel.add(newPasswordText);
				JLabel confirmPasswordText = new JLabel("确认新密码: ");
				confirmPasswordText.setBounds(35,85,100,15);
				panel.add(confirmPasswordText);
				
		        //密码输入框
		        JPasswordField oldPasswordField = new JPasswordField(16);
		        oldPasswordField.setBounds(110,25,140,18);
		        panel.add(oldPasswordField);
		        JPasswordField newPasswordField = new JPasswordField(16);
		        newPasswordField.setBounds(110,55,140,18);
		        panel.add(newPasswordField);
		        JPasswordField confirmPasswordField = new JPasswordField(16);
		        confirmPasswordField.setBounds(110,85,140,18);
		        panel.add(confirmPasswordField);
		        
		        //确认修改按钮
		        JButton confirmCPButton = new JButton("确认修改");
		        confirmCPButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							if (!ManagerDao.mIdentify(id,new String(oldPasswordField.getPassword()))) {
			        			JOptionPane.showMessageDialog(null,"旧密码输入有误！");
							} else if (!new String(newPasswordField.getPassword()).
									equals(new String(confirmPasswordField.getPassword()))) {
			        			JOptionPane.showMessageDialog(null,"新密码输入不一致！");
							} else {
								ManagerDao.editPassword(ManagerDao.getManager(id),
										new String(newPasswordField.getPassword()));
								JOptionPane.showMessageDialog(null,"密码修改成功！");
								frame.dispose();
							}
						} catch (Exception e1) {
							//什么也不做
						}
					}
		        });
		        confirmCPButton.setBounds(35,115,215,26);
		        panel.add(confirmCPButton);
		        
		        frame.setResizable(false);
		        frame.setVisible(true);
			}
		});
		cPButton.setBounds(87,127,85,40);
		panel.add(cPButton);
		
		//研究员调度业务
		JButton rCButton = new JButton("研究员调度");
		rCButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ResearcherControl.researcherControl(new JFrame("研究员调度"),new JPanel(),
							1,ResearcherDao.researcherAmount());
				} catch (Exception e1) {
					//什么也不做
				}
			}
		});
		rCButton.setBounds(187,127,105,40);
		panel.add(rCButton);
		
		//设备调度业务
		JButton eCButton = new JButton("设备管理");
		eCButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					EquipManage.equipManage(new JFrame("设备管理"),new JPanel(),
							1,EquipmentDao.equipSpecies(),true);
				} catch (Exception e1) {
					//什么也不做
				}
			}
		});
		eCButton.setBounds(307,127,85,40);
		panel.add(eCButton);

        frame.setResizable(false);
        frame.setVisible(true);
	}
	
	public static void researcherBussiness(int id) {

		//创建窗口
    	JFrame frame = new JFrame("实验室管理系统_业务");
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
        
        //微软雅黑字体
	    font = new Font("微软雅黑", Font.PLAIN, 15);
        
	    //欢迎文本
        JLabel hello;
		try {
			hello = new JLabel("姓名: " + ResearcherDao.getResearcher(id).getResearcherName());
		    hello.setFont(font);
	        hello.setBounds(5,5,100,15);
		    panel.add(hello);
		} catch (Exception e) {
			//什么都不做
		}
		
		//用户身份文本
		JLabel identity = new JLabel("身份: 研究员");
		identity.setFont(font);
		identity.setBounds(5,25,100,15);
		panel.add(identity);
		
		//修改密码业务
		JButton cPButton = new JButton("修改密码");
		cPButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//修改密码窗口
		    	JFrame frame = new JFrame("修改密码");
		        frame.setSize(300,200);
		        Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		        frame.setLocation((displaySize.width - 300)/2,
		        		(displaySize.height - 200)/2);
		        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        
		        JPanel panel = new JPanel();
		        frame.add(panel);
		        panel.setLayout(null);
		        
				//密码输入文本
				JLabel oldPasswordText = new JLabel("输入旧密码: ");
				oldPasswordText.setBounds(35,25,100,15);
				panel.add(oldPasswordText);
				JLabel newPasswordText = new JLabel("输入新密码: ");
				newPasswordText.setBounds(35,55,100,15);
				panel.add(newPasswordText);
				JLabel confirmPasswordText = new JLabel("确认新密码: ");
				confirmPasswordText.setBounds(35,85,100,15);
				panel.add(confirmPasswordText);
				
		        //密码输入框
		        JPasswordField oldPasswordField = new JPasswordField(16);
		        oldPasswordField.setBounds(110,25,140,18);
		        panel.add(oldPasswordField);
		        JPasswordField newPasswordField = new JPasswordField(16);
		        newPasswordField.setBounds(110,55,140,18);
		        panel.add(newPasswordField);
		        JPasswordField confirmPasswordField = new JPasswordField(16);
		        confirmPasswordField.setBounds(110,85,140,18);
		        panel.add(confirmPasswordField);
		        
		        //确认修改按钮
		        JButton confirmCPButton = new JButton("确认修改");
		        confirmCPButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							if (!ResearcherDao.rIdentify(id,new String(oldPasswordField.getPassword()))) {
			        			JOptionPane.showMessageDialog(null,"旧密码输入有误！");
							} else if (!new String(newPasswordField.getPassword()).
									equals(new String(confirmPasswordField.getPassword()))) {
			        			JOptionPane.showMessageDialog(null,"新密码输入不一致！");
							} else {
								ResearcherDao.editPassword(ResearcherDao.getResearcher(id),
										new String(newPasswordField.getPassword()));
								JOptionPane.showMessageDialog(null,"密码修改成功！");
								frame.dispose();
							}
						} catch (Exception e1) {
							//什么也不做
						}
					}
		        });
		        confirmCPButton.setBounds(35,115,215,26);
		        panel.add(confirmCPButton);
		        
		        frame.setResizable(false);
		        frame.setVisible(true);
			}
		});
		cPButton.setBounds(87,132,85,40);
		panel.add(cPButton);
		
		//设备查询业务
		JButton eCButton = new JButton("设备管理");
		eCButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					EquipManage.equipView(new JFrame("设备管理"),new JPanel(),
							1,EquipmentDao.equipSpecies(),false);
				} catch (Exception e1) {
					//什么也不做
				}
			}
		});
		eCButton.setBounds(312,132,85,40);
		panel.add(eCButton);
		
        frame.setResizable(false);
        frame.setVisible(true);
	}
}