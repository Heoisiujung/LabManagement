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

import dao.ResearcherDao;

public interface ResearcherControl {
	
	public static void researcherControl(JFrame frame, JPanel panel, int page, int amount) throws Exception {
		
		//研究员调度窗口
        frame.setSize(300,220);
        Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((displaySize.width - 300)/2,
        		(displaySize.height - 220)/2);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        frame.add(panel);
        panel.setLayout(null);
        
		//标度
        int x = 5,
        	y = 5,
        	w = 50,
        	h = 15;
        
        //制表文本
        JLabel titleText = new JLabel("当前共有" + amount + "个研究员");
        titleText.setBounds(x, y, 150, h);
        panel.add(titleText);
		JLabel idText = new JLabel("id");
		idText.setBounds(x,y + 20,w,h);
		panel.add(idText);
		JLabel nameText = new JLabel("姓名");
		nameText.setBounds(x + 50,y + 20,w,h);
		panel.add(nameText);
		
		
		//表格填充
		int point = 1,
			pointed = 0;
		while (pointed < 5*(page-1)) {
			if (!ResearcherDao.researcherView(point)[0].isEmpty()) {
				pointed ++;
			}
			point ++;
		}
		for (int i=1; pointed<amount && i<=5;) {
			if (!ResearcherDao.researcherView(point)[0].isEmpty()) {
				JLabel rID = new JLabel(ResearcherDao.researcherView(point)[0]);
				rID.setBounds(x, y+20+20*i, 150, 15);
	    	    panel.add(rID);
	    	    JLabel rName = new JLabel(ResearcherDao.researcherView(point)[1]);
				rName.setBounds(x+50, y+20+20*i, 150, 15);
	    	    panel.add(rName);
	    	    i ++;
				pointed ++;
			}
    	    point ++;
		}
    	JLabel rPage = new JLabel("第"+page+"页 / 共"+((amount-amount/5*5)>0?amount/5+1:amount/5)+"页");
    	rPage.setBounds(x+7, y+140, 150, 15);
    	panel.add(rPage);
		
    	//翻页按钮
    	if ( page > 1) {
    		JButton cPButton1 = new JButton("<");
    		cPButton1.addActionListener(new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				try {
    					researcherControl(new JFrame("研究员调度"),new JPanel(),page-1,amount);
    					frame.dispose();
    				} catch (Exception e1) {
    					//啥子都不做
    				}
    			}
    		});
    		cPButton1.setBounds(x , y + 160, 42, 15);
    		panel.add(cPButton1);
    	}
    	if ( page < ((amount-amount/amount*5)>0?amount/5+1:amount/5)) {
    		JButton cPButton2 = new JButton(">");
    		cPButton2.addActionListener(new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				try {
    					researcherControl(new JFrame("研究员调度"),new JPanel(),page+1,amount);
    					frame.dispose();
    				} catch (Exception e1) {
    					//啥子都不做
    				}
    			}
    		});
    		cPButton2.setBounds(x + 45, y + 160, 42, 15);
    		panel.add(cPButton2);
    	}
		
		//添加研究员文本
		JLabel addRText = new JLabel("添加新研究员");
		addRText.setBounds(x+160, y+20, 100, h);
		panel.add(addRText);
		addRText = new JLabel("新研究员名:");
		addRText.setBounds(x+120, y+42, 100, h);
		panel.add(addRText);
		
		//添加研究员输入框
		JTextField addName = new JTextField(4);
        addName.setBounds(x+200, y+41, 60, 20);
        panel.add(addName);
		
        //添加研究员按钮
        JButton addButton = new JButton("添加");
        addButton.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		if (!addName.getText().isEmpty()) {
        			try {
						ResearcherDao.addResearcher(addName.getText());
						JOptionPane.showMessageDialog(null,"添加成功！");
						researcherControl(new JFrame("研究员调度"),new JPanel(),page,amount+1);
						frame.dispose();
					} catch (Exception e1) {
						//啥子都不做
					}
        		}
        	}
        });
        addButton.setBounds(x+120, y+65, 140, 20);
        panel.add(addButton);
		
        //删除研究员文本
        JLabel delRText = new JLabel("删除研究员");
        delRText.setBounds(x+160, y+102, 100, h);
        panel.add(delRText);
        delRText = new JLabel("待删研究员id:");
        delRText.setBounds(x+120, y+122, 100, h);
        panel.add(delRText);
        
        //删除研究员输入框
        JTextField delID = new JTextField(3);
        delID.setBounds(x+200, y+121, 60, 20);
        panel.add(delID);
        
        //删除研究员按钮
        JButton delButton = new JButton("删除");
        delButton.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		if (!delID.getText().isEmpty()) {
        			try {
        				if (!ResearcherDao.researcherView(Integer.
        						parseInt(delID.getText()))[0].isEmpty()) {
    						ResearcherDao.delResearcher(Integer.parseInt(delID.getText()));
    						JOptionPane.showMessageDialog(null,"删除成功！");
    						researcherControl(new JFrame("研究员调度"),new JPanel(),page,amount-1);
    						frame.dispose();
        				} else {
        					JOptionPane.showMessageDialog(null,"id不存在！");
        				}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null,"请输入数字id！");
					}
        		}
        	}
        });
        delButton.setBounds(x+120, y+145, 140, 20);
        panel.add(delButton);
        
		frame.setResizable(false);
        frame.setVisible(true);
	}
}