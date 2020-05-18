package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.EquipmentDao;

public interface EquipManage {
	
	//设备管理
	public static void equipManage(JFrame frame, JPanel panel, int page, int species, boolean privacy) throws Exception {
		
		//设备查询
        equipView(frame, panel, page, species, privacy);
        
        //标度
        int x = 5,
        	y = 5,
        	h = 15;
        
		x-=220;
		y+=180;
		//注册新设备文本
		JLabel registerEText = new JLabel("注册新设备");
		registerEText.setBounds(x+260, y+5, 100, h);
		panel.add(registerEText);
		registerEText = new JLabel("新设备名:");
		registerEText.setBounds(x+220, y+27, 100, h);
		panel.add(registerEText);
		registerEText = new JLabel("初始设备数:");
		registerEText.setBounds(x+220, y+47, 100, h);
		panel.add(registerEText);
		
		//注册新设备输入框
		JTextField eNameInput = new JTextField(8);
		eNameInput.setBounds(x+300, y+26, 60, 20);
		panel.add(eNameInput);
		JTextField eAmountInput = new JTextField(4);
		eAmountInput.setBounds(x+300, y+46, 60, 20);
		panel.add(eAmountInput);
		
		//注册新设备按钮
		JButton registerEButton = new JButton("注册");
		registerEButton.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent e) {
		        if ((!eNameInput.getText().isEmpty()) && (!eAmountInput.getText().isEmpty())) {
		        	try {
		        		Integer.parseInt(eAmountInput.getText());
						EquipmentDao.registerEquip(eNameInput.getText(),Integer.parseInt(eAmountInput.getText()));
						JOptionPane.showMessageDialog(null,"添加成功！");
						equipManage(new JFrame("设备管理"),new JPanel(),page,species+1,privacy);
						frame.dispose();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null,"请输入数字个数！");
					}
		        }
		    }
		});
		registerEButton.setBounds(x+220, y+68, 140, 20);
		panel.add(registerEButton);
		
		//注销设备文本
		JLabel logoutEText = new JLabel("注销设备");
		logoutEText.setBounds(x+260, y+110, 100, h);
		panel.add(logoutEText);
		logoutEText = new JLabel("待销设备ID:");
		logoutEText.setBounds(x+220, y+132, 100, h);
		panel.add(logoutEText);
		
		//注销设备输入框
		JTextField logoutEInput = new JTextField(8);
		logoutEInput.setBounds(x+300, y+131, 60, 20);
		panel.add(logoutEInput);
		
		//注销设备按钮
		JButton logoutEButton = new JButton("注销");
		logoutEButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (!EquipmentDao.equipView(Integer.
							parseInt(logoutEInput.getText()))[0].isEmpty()) {
						if (EquipmentDao.logoutEquip(Integer.parseInt(logoutEInput.getText()))) {
							JOptionPane.showMessageDialog(null,"注销成功！");
							equipManage(new JFrame("设备管理"),new JPanel(),page,species-1,privacy);
							frame.dispose();
						} else {
							JOptionPane.showMessageDialog(null,"该类设备尚有剩余，无法注销！");
						}
					} else {
						JOptionPane.showMessageDialog(null,"id不存在！");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,"请输入数字id！");
				}
			}
		});
		logoutEButton.setBounds(x+220, y+153, 140, 20);
		panel.add(logoutEButton);
		
		x+=10;
		//调度设备文本
		JLabel controlEText = new JLabel("调度设备");
		controlEText.setBounds(x+410, y+5, 100, h);
		panel.add(controlEText);
		controlEText = new JLabel("待调设备ID:");
		controlEText.setBounds(x+370, y+27, 100, h);
		panel.add(controlEText);
		controlEText = new JLabel("从(labID):");
		controlEText.setBounds(x+370, y+47, 100, h);
		panel.add(controlEText);
		controlEText = new JLabel("到(labID):");
		controlEText.setBounds(x+370, y+67, 100, h);
		panel.add(controlEText);
		controlEText = new JLabel("调度数量");
		controlEText.setBounds(x+370, y+87, 100, h);
		panel.add(controlEText);
		
		//调度设备输入框
		JTextField eCIDInput = new JTextField(4);
		eCIDInput.setBounds(x+450, y+26, 60, 20);
		panel.add(eCIDInput);
		JTextField fromLIDInput = new JTextField(4);
		fromLIDInput.setBounds(x+450, y+46, 60, 20);
		panel.add(fromLIDInput);
		JTextField toLIDInput = new JTextField(4);
		toLIDInput.setBounds(x+450, y+66, 60, 20);
		panel.add(toLIDInput);
		JTextField cAmountInput = new JTextField(4);
		cAmountInput.setBounds(x+450, y+86, 60, 20);
		panel.add(cAmountInput);
		
		//调度设备按钮
		JButton controlEButton = new JButton("调度");
		controlEButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int eCID = Integer.parseInt(eCIDInput.getText());
					int fromLID = Integer.parseInt(fromLIDInput.getText());
					int toLID = Integer.parseInt(toLIDInput.getText());
					int amount = Integer.parseInt(cAmountInput.getText());
					if (fromLID == toLID) {
						JOptionPane.showMessageDialog(null,"相同的labID！");
					} else if (EquipmentDao.equipView(eCID)[0].isEmpty()) {
						JOptionPane.showMessageDialog(null,"设备ID不存在！");
					} else if (fromLID<0 || fromLID>4 || toLID<0 || toLID>4) {
						JOptionPane.showMessageDialog(null,"labID不存在！");
					} else if (amount > EquipmentDao.equipDistribute
		    	    		(Integer.parseInt(EquipmentDao.equipView(eCID)[0]),fromLID)) {
						JOptionPane.showMessageDialog(null,"设备不足！");
					} else {
						EquipmentDao.setEquipment(eCID, amount, fromLID, toLID);
						JOptionPane.showMessageDialog(null,"调度成功！");
						equipManage(new JFrame("设备管理"),new JPanel(),page,species,privacy);
						frame.dispose();
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,"请输入数字ID及调度数量！");
				}
			}
		});
		controlEButton.setBounds(x+370, y+108, 140, 20);
		panel.add(controlEButton);
		
		x+=155;
		//设备添加文本
		JLabel addEText = new JLabel("添加设备");
		addEText.setBounds(x+410, y+5, 100, h);
		panel.add(addEText);
		addEText = new JLabel("待添设备ID:");
		addEText.setBounds(x+370, y+27, 100, h);
		panel.add(addEText);
		addEText = new JLabel("待添设备数:");
		addEText.setBounds(x+370, y+47, 100, h);
		panel.add(addEText);
		
		//设备添加输入框
		JTextField addEIDInput = new JTextField(4);
		addEIDInput.setBounds(x+450, y+26, 60, 20);
		panel.add(addEIDInput);
		JTextField addEAmountInput = new JTextField(4);
		addEAmountInput.setBounds(x+450, y+46, 60, 20);
		panel.add(addEAmountInput);
		
		//设备添加按钮
		JButton addEButton = new JButton("添加");
		addEButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int eCID = Integer.parseInt(addEIDInput.getText());
					int amount = Integer.parseInt(addEAmountInput.getText());
					if (!EquipmentDao.equipView(eCID)[0].isEmpty()) {
						EquipmentDao.addEquipment(eCID, amount);
						JOptionPane.showMessageDialog(null,"调度成功！");
						equipManage(new JFrame("设备管理"),new JPanel(),page,species,privacy);
						frame.dispose();
					} else {
						JOptionPane.showMessageDialog(null,"设备ID不存在！");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,"请输入数字ID及调度数量！");
				}
			}
		});
		addEButton.setBounds(x+370, y+68, 140, 20);
		panel.add(addEButton);
		
		y+=85;
		//设备裁减文本
		JLabel delEText = new JLabel("裁剪设备");
		delEText.setBounds(x+410, y+5, 100, h);
		panel.add(delEText);
		delEText = new JLabel("待裁设备ID:");
		delEText.setBounds(x+370, y+27, 100, h);
		panel.add(delEText);
		delEText = new JLabel("待裁设备数:");
		delEText.setBounds(x+370, y+47, 100, h);
		panel.add(delEText);
		
		//设备裁减输入框
		JTextField delEIDInput = new JTextField(4);
		delEIDInput.setBounds(x+450, y+26, 60, 20);
		panel.add(delEIDInput);
		JTextField delEAmountInput = new JTextField(4);
		delEAmountInput.setBounds(x+450, y+46, 60, 20);
		panel.add(delEAmountInput);
		
		//设备裁减按钮
		JButton delEButton = new JButton("裁减");
		delEButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int eCID = Integer.parseInt(delEIDInput.getText());
					int amount = Integer.parseInt(delEAmountInput.getText());
					if (EquipmentDao.equipView(eCID)[0].isEmpty()) {
						JOptionPane.showMessageDialog(null,"设备ID不存在！");
					} else if (amount > EquipmentDao.equipDistribute
		    	    		(Integer.parseInt(EquipmentDao.equipView(eCID)[0]),0)) {
						JOptionPane.showMessageDialog(null,"空闲设备数不足！");
					} else {
						EquipmentDao.delEquipment(eCID, amount);
						JOptionPane.showMessageDialog(null,"裁减成功！");
						equipManage(new JFrame("设备管理"),new JPanel(),page,species,privacy);
						frame.dispose();
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,"请输入数字ID及调度数量！");
				}
			}
		});
		delEButton.setBounds(x+370, y+68, 140, 20);
		panel.add(delEButton);	
	}
	
	public static void equipView(JFrame frame, JPanel panel, int page, int species, boolean privacy) throws Exception {
		
        //标度
		int x = 480,
			y = 400;
		
		//设备管理窗口
		if (!privacy) {
			y -= 180;
		}
        frame.setSize(x,y);
        Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((displaySize.width - x)/2,
        		(displaySize.height - y)/2);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        frame.add(panel);
        panel.setLayout(null);
        
        //标度
        int w = 50,
        	h = 15;
        x = 5;
        y = 5;
        
        //制表文本
        JLabel titleText = new JLabel("当前共有" + species + "种设备");
        titleText.setBounds(x, y, 150, h);
        panel.add(titleText);
		JLabel idText = new JLabel("id");
		idText.setBounds(x,y + 20,w,h);
		panel.add(idText);
		JLabel nameText = new JLabel("设备名");
		nameText.setBounds(x + 50,y + 20,w,h);
		panel.add(nameText);
		JLabel amountText = new JLabel("总数");
		amountText.setBounds(x + 150,y + 20,w,h);
		panel.add(amountText);
		amountText = new JLabel("labID:");
		amountText.setBounds(x + 160,y,w,h);
		panel.add(amountText);
		for (int i=0; i<=4; i++) {
			amountText = new JLabel(Integer.toString(i));
			amountText.setBounds(x + 200+50*i,y,w,h);
			panel.add(amountText);
		}
		amountText = new JLabel("闲置");
		amountText.setBounds(x + 200,y + 20,w,h);
		panel.add(amountText);
		amountText = new JLabel("有机");
		amountText.setBounds(x + 250,y + 20,w,h);
		panel.add(amountText);
		amountText = new JLabel("无机");
		amountText.setBounds(x + 300,y + 20,w,h);
		panel.add(amountText);
		amountText = new JLabel("物化");
		amountText.setBounds(x + 350,y + 20,w,h);
		panel.add(amountText);
		amountText = new JLabel("分析");
		amountText.setBounds(x + 400,y + 20,w,h);
		panel.add(amountText);
        
		//表格填充
		int point = 1,
			pointed = 0;
		while (pointed < 5*(page-1)) {
			if (!EquipmentDao.equipView(point)[0].isEmpty()) {
				pointed ++;
			}
			point ++;
		}
				for (int i=1; pointed<species && i<=5;) {
					if (!EquipmentDao.equipView(point)[0].isEmpty()) {
						JLabel eID = new JLabel(EquipmentDao.equipView(point)[0]);
						eID.setBounds(x, y+20+20*i, 150, 15);
			    	    panel.add(eID);
			    	    JLabel eName = new JLabel(EquipmentDao.equipView(point)[1]);
						eName.setBounds(x+50, y+20+20*i, 150, 15);
			    	    panel.add(eName);
			    	    JLabel eAmount = new JLabel(EquipmentDao.equipAmount(point)+"");
			    	    eAmount.setBounds(x+150, y+20+20*i, 150, 15);
			    	    panel.add(eAmount);
			    	    for (int j=0; j<=4; j++) {
				    	    eAmount = new JLabel(Integer.toString(EquipmentDao.equipDistribute
				    	    		(Integer.parseInt(EquipmentDao.equipView(point)[0]),j)));
				    	    eAmount.setBounds(x+200+50*j, y+20+20*i, 150, 15);
				    	    panel.add(eAmount);
			    	    }
			    	    i ++;
						pointed ++;
					}
		    	    point ++;
				}
		JLabel ePage = new JLabel("第"+page+"页 / 共"+((species-species/5*5)>0?species/5+1:species/5)+"页");
		ePage.setBounds(x+192, y+140, 150, 15);
		panel.add(ePage);
        
		//翻页按钮
		if (page > 1) {
			JButton cPButton1 = new JButton("<");
			cPButton1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						equipManage(new JFrame("设备管理"),new JPanel(),page-1,species,privacy);
						frame.dispose();
					} catch (Exception e1) {
						//啥子都不做
					}
				}
			});
			cPButton1.setBounds(x + 185 , y + 160, 42, 15);
			panel.add(cPButton1);
		}
		if (page < ((species-species/species*5)>0?species/5+1:species/5)) {
			JButton cPButton2 = new JButton(">");
			cPButton2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						equipManage(new JFrame("设备管理"),new JPanel(),page+1,species,privacy);
						frame.dispose();
					} catch (Exception e1) {
						//啥子都不做
					}
				}
			});
			cPButton2.setBounds(x + 230, y + 160, 42, 15);
			panel.add(cPButton2);
		}
		
		frame.setResizable(false);
        frame.setVisible(true); 
	}
}