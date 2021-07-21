package Mindmap;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class Mindmap_Frame extends JFrame{
	
	Container contentPane;
	
	String TAS;
	
	static JTextField Text_TF = new JTextField("");
	static JTextField X_TF = new JTextField("");
	static JTextField Y_TF = new JTextField("");
	static JTextField W_TF = new JTextField("");
	static JTextField H_TF = new JTextField("");
	static JTextField Color_TF = new JTextField("");
	static Dimension MMP_dim = new Dimension();
	
	static JLabel Color_Palette = new JLabel();
	
	static Mindmap_MMP MMP;
	
	static JScrollPane MMSP;
	
	File now_File ;
	boolean now_File_exist = false;
	
	JTextArea TA;
	
	JFileChooser JFC = new JFileChooser();
	
	public Mindmap_Frame()  {
		
		setSize(1280, 720);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		contentPane = getContentPane();
		
		//
		JMenuBar JMB = new JMenuBar();
		
		JMenu New_Menu = new JMenu("새로 만들기");
		JMenu Open_Menu = new JMenu("열기");
		JMenu Save_Menu = new JMenu("저장");
		JMenu Saveas_Menu = new JMenu("다른 이름으로 저장");
		JMenu Exit_Menu = new JMenu("닫기");
		JMenu Apply_Menu = new JMenu("적용");
		JMenu Change_Menu = new JMenu("변경");
		
		JMB.add(New_Menu);
		JMB.add(Open_Menu);
		JMB.add(Save_Menu);
		JMB.add(Saveas_Menu);
		JMB.add(Exit_Menu);
		JMB.add(Apply_Menu);
		JMB.add(Change_Menu);
		
		New_Menu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				IO_New();
			}
			
			@Override
			public void menuDeselected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Open_Menu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				IO_Open();
			}
			
			@Override
			public void menuDeselected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Save_Menu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				IO_Save();
			}
			
			@Override
			public void menuDeselected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Saveas_Menu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				IO_Saveas();
			}
			
			@Override
			public void menuDeselected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Exit_Menu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
			@Override
			public void menuDeselected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Apply_Menu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				Rectangle bound = MMSP.getViewport().getViewRect();
				MMSP.getHorizontalScrollBar().setValue((MMSP.getHorizontalScrollBar().getMaximum()-bound.width)/2);
				MMSP.getVerticalScrollBar().setValue((MMSP.getVerticalScrollBar().getMaximum()-bound.height)/2);
				MMSP.getViewport().revalidate();
				MMSP.repaint();
				
				MMP.Node_set(TA.getText());
				MMP.repaint();
			}
			
			@Override
			public void menuDeselected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Change_Menu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				MMP.Node_Bounds(Integer.parseInt(X_TF.getText()), Integer.parseInt(Y_TF.getText()), Integer.parseInt(W_TF.getText()), Integer.parseInt(H_TF.getText()));
				MMP.Node_Color(Color_TF.getText().trim());
				repaint();
			}
			
			@Override
			public void menuDeselected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		setJMenuBar(JMB);
		//
		JToolBar JTB = new JToolBar();
		
		JButton New_But = new JButton("새로 만들기");
		JButton Open_But = new JButton("열기");
		JButton Save_But = new JButton("저장");
		JButton Saveas_But = new JButton("다른 이름으로 저장");
		JButton Exit_But = new JButton("닫기");
		JButton Apply_But = new JButton("적용");
		JButton Change_But = new JButton("변경");
		
		JTB.add(New_But);
		JTB.add(Open_But);
		JTB.add(Save_But);
		JTB.add(Saveas_But);
		JTB.add(Exit_But);
		JTB.add(Apply_But);
		JTB.add(Change_But);
		
		New_But.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				IO_New();
			}
		});
		
		Open_But.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				IO_Open();
			}
		});
		
		Save_But.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				IO_Save();
			}
		});
		
		Saveas_But.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				IO_Saveas();
			}
		});
		
		Exit_But.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		Apply_But.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Rectangle bound = MMSP.getViewport().getViewRect();
				MMSP.getHorizontalScrollBar().setValue((MMSP.getHorizontalScrollBar().getMaximum()-bound.width)/2);
				MMSP.getVerticalScrollBar().setValue((MMSP.getVerticalScrollBar().getMaximum()-bound.height)/2);
				MMSP.getViewport().revalidate();
				MMSP.repaint();
				
				MMP.Node_set(TA.getText());
				MMP.repaint();
			}
		});
		
		Change_But.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MMP.Node_Bounds(Integer.parseInt(X_TF.getText()), Integer.parseInt(Y_TF.getText()), Integer.parseInt(W_TF.getText()), Integer.parseInt(H_TF.getText()));
				MMP.Node_Color(Color_TF.getText().trim());
				repaint();
			}
		});
		
		JFC.setFileFilter(new FileNameExtensionFilter("json", "json"));
		JFC.setMultiSelectionEnabled(false);
		
		contentPane.add(JTB, BorderLayout.NORTH);
		//
		JPanel JP = new JPanel(null);
		//
		
		JPanel Attribute_Panel = new JPanel(null);
		Font Attribute_font = new Font("굴림", Font.BOLD, 32);
		Attribute_Panel.setBackground(new Color(151, 221, 221));
		//
		
		JLabel Text_Style = new JLabel();
		Text_Style.setBorder(new TitledBorder(new LineBorder(Color.white, 2)));
		Text_Style.setBounds(4, 4, 345, 52);
		
		JLabel Bounds_Style = new JLabel();
		Bounds_Style.setBorder(new TitledBorder(new LineBorder(Color.white, 2)));
		Bounds_Style.setBounds(4, 64, 345, 232);
		
		//
		
		JLabel Text_Label = new JLabel("TEXT:");
		Text_Label.setFont(Attribute_font);
		Text_Label.setBounds(10, 10, 200, 40);
		
		Text_TF.setFont(Attribute_font);
		Text_TF.setEnabled(false);
		Text_TF.setBackground(new Color(166, 166, 166));
		Text_TF.setForeground(Color.white);
		Text_TF.setBounds(115, 10, 228, 40);
		//
		JLabel X_Label = new JLabel("X:");
		X_Label.setFont(Attribute_font);
		X_Label.setBounds(10, 70, 200, 40);

		X_TF.setFont(Attribute_font);
		X_TF.setBounds(115, 70, 228, 40);
		//
		JLabel Y_Label = new JLabel("Y:");
		Y_Label.setFont(Attribute_font);
		Y_Label.setBounds(10, 130, 200, 40);

		Y_TF.setFont(Attribute_font);
		Y_TF.setBounds(115, 130, 228, 40);
		//
		JLabel W_Label = new JLabel("W:");
		W_Label.setFont(Attribute_font);
		W_Label.setBounds(10, 190, 200, 40);

		W_TF.setFont(Attribute_font);
		W_TF.setBounds(115, 190, 228, 40);
		//
		JLabel H_Label = new JLabel("H:");
		H_Label.setFont(Attribute_font);
		H_Label.setBounds(10, 250, 200, 40);

		H_TF.setFont(Attribute_font);
		H_TF.setBounds(115, 250, 228, 40);
		//
		JLabel Color_Label = new JLabel("Color:");
		Color_Label.setFont(Attribute_font);
		Color_Label.setBounds(10, 310, 200, 40);
		
		Color_TF.setText("FFFFFF");
		Color_TF.setFont(Attribute_font);
		Color_TF.setBounds(115, 310, 178, 40);
		
		Color_Palette.setBounds(303, 310, 40, 40);
		Color_Palette.setOpaque(true);
		Color_Palette.setBackground(Color.WHITE);
		//
		JButton Attribute_But = new JButton("변경");
		Attribute_But.setFont(new Font("굴림", Font.BOLD, 48));
		Attribute_But.setBounds(5, getHeight()-165, 360, 60);
		
		//
		Attribute_Panel.add(Text_Style);
		Attribute_Panel.add(Bounds_Style);
		//
		Attribute_Panel.add(Text_Label);
		Attribute_Panel.add(X_Label);
		Attribute_Panel.add(Y_Label);
		Attribute_Panel.add(W_Label);
		Attribute_Panel.add(H_Label);
		Attribute_Panel.add(Color_Label);
		//
		Attribute_Panel.add(Text_TF);
		Attribute_Panel.add(X_TF);
		Attribute_Panel.add(Y_TF);
		Attribute_Panel.add(W_TF);
		Attribute_Panel.add(H_TF);
		Attribute_Panel.add(Color_TF);
		Attribute_Panel.add(Color_Palette);
		//
		Attribute_Panel.add(Attribute_But);
		//
		JPanel TextEditor_Panel = new JPanel(null);
		Font TextEditor_font = new Font("Arial", Font.BOLD, 28);
		
		//TextEditor_Panel.setBounds(0, 0, 350, this.getHeight()-95);
		TextEditor_Panel.setBackground(new Color(151, 221, 221));
		
		TA = new JTextArea("계절\n\t봄\n\t\t황사\n\t여름\n\t\t장마\n\t\t태풍\n\t가을\n\t\t단풍\n\t겨울\n\t\t폭설");
		TA.setFont(new Font("굴림", Font.PLAIN, 18));
		JScrollPane JSP = new JScrollPane(TA);
		JSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		JSP.setBounds(8, 8, 334, this.getHeight() - 180);
		
		JButton TextEditor_But = new JButton("적용");
		TextEditor_But.setFont(new Font("굴림", Font.BOLD, 48));
		TextEditor_But.setBounds(8, getHeight()-165, 334, 60);
		//
		TextEditor_Panel.add(JSP);
		TextEditor_Panel.add(TextEditor_But);
		//
		MMP = new Mindmap_MMP();
		MMP.repaint();
		MMSP = new JScrollPane(MMP);
		MMSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		MMSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		MMP_dim = new Dimension(2000, 2000);
		MMP.setPreferredSize(MMP_dim);
		
		Attribute_But.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				MMP.Node_Bounds(Integer.parseInt(X_TF.getText()), Integer.parseInt(Y_TF.getText()), Integer.parseInt(W_TF.getText()), Integer.parseInt(H_TF.getText()));
				MMP.Node_Color(Color_TF.getText().trim());
				repaint();
			}
		});
		
		TextEditor_But.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				TAS = TA.getText().trim();
				
				Rectangle bound = MMSP.getViewport().getViewRect();
				MMSP.getHorizontalScrollBar().setValue((MMSP.getHorizontalScrollBar().getMaximum()-bound.width)/2);
				MMSP.getVerticalScrollBar().setValue((MMSP.getVerticalScrollBar().getMaximum()-bound.height)/2);
				MMSP.getViewport().revalidate();
				MMSP.repaint();
				
				MMP.Node_set(TA.getText());
				MMP.repaint();
			}
		});


		JSplitPane Split_1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, TextEditor_Panel, MMSP);
		Split_1.setDividerLocation(350);
		Split_1.setDividerSize(5);
		Split_1.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				// TODO Auto-generated method stub
				JSP.setBounds(8, 8, Split_1.getDividerLocation()-16, getHeight() - 180);
				TextEditor_But.setBounds(8, getHeight()-165, Split_1.getDividerLocation()-16, 60);
			}
		});
		JSplitPane Split_2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, Split_1, Attribute_Panel);
		Split_2.setDividerLocation(getWidth()-375);
		Split_2.setDividerSize(5);
		Split_2.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				// TODO Auto-generated method stub
				Text_Style.setBounds(4, 4, getWidth() - Split_2.getDividerLocation() - 30, 52);
				Bounds_Style.setBounds(4, 63, getWidth() - Split_2.getDividerLocation() - 30, 233);
				Text_TF.setBounds(115, 10, getWidth() - Split_2.getDividerLocation()- 147, 40);
				X_TF.setBounds(115, 70, getWidth() - Split_2.getDividerLocation()- 147, 40);
				Y_TF.setBounds(115, 130, getWidth() - Split_2.getDividerLocation()- 147, 40);
				W_TF.setBounds(115, 190, getWidth() - Split_2.getDividerLocation()- 147, 40);
				H_TF.setBounds(115, 250, getWidth() - Split_2.getDividerLocation()- 147, 40);
				Color_TF.setBounds(115, 310, getWidth() - Split_2.getDividerLocation()- 197, 40);
				Color_Palette.setBounds(getWidth() - Split_2.getDividerLocation() - 72, 310, 40, 40);
				Attribute_But.setBounds(8, getHeight()-165, getWidth() - Split_2.getDividerLocation()- 38, 60);
			}
		});
		Text_Style.setBounds(4, 4, getWidth() - Split_2.getDividerLocation() - 30, 52);
		Bounds_Style.setBounds(4, 63, getWidth() - Split_2.getDividerLocation() - 30, 233);
		Text_TF.setBounds(115, 10, getWidth() - Split_2.getDividerLocation()- 147, 40);
		X_TF.setBounds(115, 70, getWidth() - Split_2.getDividerLocation()- 147, 40);
		Y_TF.setBounds(115, 130, getWidth() - Split_2.getDividerLocation()- 147, 40);
		W_TF.setBounds(115, 190, getWidth() - Split_2.getDividerLocation()- 147, 40);
		H_TF.setBounds(115, 250, getWidth() - Split_2.getDividerLocation()- 147, 40);
		Color_TF.setBounds(115, 310, getWidth() - Split_2.getDividerLocation()- 197, 40);
		Color_Palette.setBounds(getWidth() - Split_2.getDividerLocation() - 72, 310, 40, 40);
		Attribute_But.setBounds(8, getHeight()-165, getWidth() - Split_2.getDividerLocation()- 38, 60);
		
		contentPane.add(Split_2, BorderLayout.CENTER);
		
		MMP.repaint();
		setVisible(true);
		
		addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				
				JSP.setBounds(8, 8, Split_1.getDividerLocation()-16, getHeight() - 180);
				TextEditor_But.setBounds(8, getHeight()-165, Split_1.getDividerLocation()-16, 60);
				
				Text_Style.setBounds(4, 4, getWidth() - Split_2.getDividerLocation() - 30, 52);
				Bounds_Style.setBounds(4, 63, getWidth() - Split_2.getDividerLocation() - 30, 233);
				Text_TF.setBounds(115, 10, getWidth() - Split_2.getDividerLocation()- 147, 40);
				X_TF.setBounds(115, 70, getWidth() - Split_2.getDividerLocation()- 147, 40);
				Y_TF.setBounds(115, 130, getWidth() - Split_2.getDividerLocation()- 147, 40);
				W_TF.setBounds(115, 190, getWidth() - Split_2.getDividerLocation()- 147, 40);
				H_TF.setBounds(115, 250, getWidth() - Split_2.getDividerLocation()- 147, 40);
				Color_TF.setBounds(115, 310, getWidth() - Split_2.getDividerLocation()- 197, 40);
				Color_Palette.setBounds(getWidth() - Split_2.getDividerLocation() - 72, 310, 40, 40);
				Attribute_But.setBounds(8, getHeight()-165, getWidth() - Split_2.getDividerLocation()- 38, 60);

			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		repaint();
	}
	
	public void IO_New() {
		TA.setText("");
		Text_TF.setText("");
		X_TF.setText("");
		Y_TF.setText("");
		W_TF.setText("");
		H_TF.setText("");
		Color_TF.setText("FFFFFF");
		Color_Palette.setBackground(Color.WHITE);
		
		Rectangle bound = MMSP.getViewport().getViewRect();
		MMSP.getHorizontalScrollBar().setValue((MMSP.getHorizontalScrollBar().getMaximum()-bound.width)/2);
		MMSP.getVerticalScrollBar().setValue((MMSP.getVerticalScrollBar().getMaximum()-bound.height)/2);
		MMSP.getViewport().revalidate();
		MMSP.repaint();
		
		MMP.Node_set(TA.getText());
		MMP.repaint();
		
		now_File_exist = false;
	}

	public void IO_Open() {
		
		JSONParser parser = new JSONParser();
		
		if(JFC.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = new File(JFC.getSelectedFile().toString());
			try {
				Object obj = parser.parse(new FileReader(file));
				JSONObject jsonObject = (JSONObject) obj;
				TA.setText((String)jsonObject.get("TAS"));
				
				TAS = TA.getText();
				
				Rectangle bound = MMSP.getViewport().getViewRect();
				MMSP.getHorizontalScrollBar().setValue((MMSP.getHorizontalScrollBar().getMaximum()-bound.width)/2);
				MMSP.getVerticalScrollBar().setValue((MMSP.getVerticalScrollBar().getMaximum()-bound.height)/2);
				MMSP.getViewport().revalidate();
				MMSP.repaint();
				
				MMP.Node_set(TA.getText());
				MMP.repaint();
				
				JSONArray Node_Label_a = (JSONArray) jsonObject.get("Label");
				int n = TAS.split("\n").length;
				if(n == 1 && TAS.split("\n")[0].trim().equals("")) n = 0;
				for(int i = 0; i++ < n;) {
					JSONObject tmp = (JSONObject)Node_Label_a.get(i-1);
					String name = (String)tmp.get("name");
					long X = (Long)tmp.get("X");
					long Y = (Long)tmp.get("Y");
					long W = (Long)tmp.get("W");
					long H = (Long)tmp.get("H");
					long R = (Long)tmp.get("R");
					long G = (Long)tmp.get("G");
					long B = (Long)tmp.get("B");
					MMP.node_Label[i].setBounds((int)X, (int)Y, (int)W, (int)H);
					MMP.node_Label[i].setBackground(new Color((int)R, (int)G, (int)B));
				}
				JSONArray Node_Line_a = (JSONArray) jsonObject.get("Line");
				n = Node_Line_a.size();
				for(int i = 0; i < n; i++) {
					JSONObject tmp = (JSONObject) Node_Line_a.get(i);
					long Parent = (Long)tmp.get("Parent");
					long Child = (Long)tmp.get("Child");
					MMP.node[(int) Parent][(int) Child] = 1;
				}
				MMP.Line_set();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			now_File_exist = true;
			now_File = file;
		}
	}
	
	public void IO_Save() {
		JSONObject jsonObject = new JSONObject();
		JSONArray Node_Label_a = new JSONArray();
		JSONObject Node_Label;

		jsonObject.put("TAS", TAS);
		for(int i = 0; ++i < MMP.node_Label.length;) {
			Node_Label = new JSONObject();
			Node_Label.put("name", MMP.node_Label[i].getText());
			Node_Label.put("X", MMP.node_Label[i].getX());
			Node_Label.put("Y", MMP.node_Label[i].getY());
			Node_Label.put("W", MMP.node_Label[i].getWidth());
			Node_Label.put("H", MMP.node_Label[i].getHeight());
			Node_Label.put("R", MMP.node_Label[i].getBackground().getRed());
			Node_Label.put("G", MMP.node_Label[i].getBackground().getGreen());
			Node_Label.put("B", MMP.node_Label[i].getBackground().getBlue());
			Node_Label_a.add(Node_Label);
		}
		jsonObject.put("Label", Node_Label_a);
		
		JSONArray Node_Line_a = new JSONArray();
		JSONObject Node_Line;
		for(int i = 0; i < MMP.node.length; i++) {
			for(int j = i; j < MMP.node.length; j++) {
				if(MMP.node[i][j]==1) {
					Node_Line = new JSONObject();
					Node_Line.put("Parent", i);
					Node_Line.put("Child", j);
					Node_Line_a.add(Node_Line);
				}
			}
		}
		jsonObject.put("Line", Node_Line_a);
		
		if(now_File_exist == false) {
			if(JFC.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				File file = new File(JFC.getSelectedFile().toString()+".json");
				if(file.exists()==false)
					try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				try {
					FileWriter filew = new FileWriter(file);
					filew.write(jsonObject.toJSONString());
					filew.flush();
					filew.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				now_File_exist = true;
				now_File = file;
			}
		} else {
			try {
				FileWriter filew = new FileWriter(now_File);
				filew.write(jsonObject.toJSONString());
				filew.flush();
				filew.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void IO_Saveas() {
		JSONObject jsonObject = new JSONObject();
		JSONArray Node_Label_a = new JSONArray();
		JSONObject Node_Label;
		
		jsonObject.put("TAS", TAS);
		for(int i = 0; ++i < MMP.node_Label.length;) {
			Node_Label = new JSONObject();
			Node_Label.put("name", MMP.node_Label[i].getText());
			Node_Label.put("X", MMP.node_Label[i].getX());
			Node_Label.put("Y", MMP.node_Label[i].getY());
			Node_Label.put("W", MMP.node_Label[i].getWidth());
			Node_Label.put("H", MMP.node_Label[i].getHeight());
			Node_Label.put("R", MMP.node_Label[i].getBackground().getRed());
			Node_Label.put("G", MMP.node_Label[i].getBackground().getGreen());
			Node_Label.put("B", MMP.node_Label[i].getBackground().getBlue());
			Node_Label_a.add(Node_Label);
		}
		jsonObject.put("Label", Node_Label_a);
		
		JSONArray Node_Line_a = new JSONArray();
		JSONObject Node_Line;
		for(int i = 0; i < MMP.node.length; i++) {
			for(int j = i; j < MMP.node.length; j++) {
				if(MMP.node[i][j]==1) {
					Node_Line = new JSONObject();
					Node_Line.put("Parent", i);
					Node_Line.put("Child", j);
					Node_Line_a.add(Node_Line);
				}
			}
		}
		jsonObject.put("Line", Node_Line_a);
		
		if(JFC.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = new File(JFC.getSelectedFile().toString()+".json");
			if(file.exists()==false)
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			try {
				FileWriter filew = new FileWriter(file);
				filew.write(jsonObject.toJSONString());
				filew.flush();
				filew.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			now_File_exist = true;
			now_File = file;
		}
	}
}

