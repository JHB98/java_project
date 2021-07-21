package Mindmap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

class Mindmap_MMP extends JPanel implements MouseListener, MouseMotionListener{
	
	int now_x, now_y, now_w, now_h, now_L, now_D;
	
	double pi = Math.PI;
	
	int[][] node;
	String[] node_text;
	JLabel[] node_Label;
	
	JLabel selected_Label = new JLabel();
	JLabel selected_Label_before = new JLabel();;
	
	LinkedList<Color> node_Colors = new LinkedList<>();
	
	LinkedList<int[]> Line = new LinkedList();
	
	public void Label_Selected(JLabel A) {
		selected_Label_before = selected_Label;
		selected_Label = A;
		if(selected_Label == selected_Label_before) {
			
		} else {
			selected_Label_before.setBackground(new Color(255-selected_Label_before.getBackground().getRed(), 255-selected_Label_before.getBackground().getGreen(), 255-selected_Label_before.getBackground().getBlue()));
			selected_Label.setBackground(new Color(255-selected_Label.getBackground().getRed(), 255-selected_Label.getBackground().getGreen(), 255-selected_Label.getBackground().getBlue()));
			
		}
	}
	
	public void Text_Set() {
		Mindmap_Frame.Text_TF.setText(selected_Label.getText());
		Mindmap_Frame.X_TF.setText(selected_Label.getX()+"");
		Mindmap_Frame.Y_TF.setText(selected_Label.getY()+"");
		Mindmap_Frame.W_TF.setText(selected_Label.getWidth()+"");
		Mindmap_Frame.H_TF.setText(selected_Label.getHeight()+"");
		Mindmap_Frame.Color_Palette.setBackground(new Color(255-selected_Label.getBackground().getRed(), 255-selected_Label.getBackground().getGreen(), 255-selected_Label.getBackground().getBlue()));
		Mindmap_Frame.Color_TF.setText(String.format("%02X%02X%02X", 255-selected_Label.getBackground().getRed(), 255-selected_Label.getBackground().getGreen(), 255-selected_Label.getBackground().getBlue()));
		
	}
	
	public void Node_Color(String str) {
		String str_r, str_g, str_b;
		str_r = (str.charAt(0)+""+str.charAt(1)).toUpperCase();
		str_g = (str.charAt(2)+""+str.charAt(3)).toUpperCase();
		str_b = (str.charAt(4)+""+str.charAt(5)).toUpperCase();
		selected_Label.setBackground(new Color(255-Integer.parseInt(str_r, 16), 255-Integer.parseInt(str_g, 16), 255-Integer.parseInt(str_b, 16)));
		Mindmap_Frame.Color_Palette.setBackground(new Color(255-selected_Label.getBackground().getRed(), 255-selected_Label.getBackground().getGreen(), 255-selected_Label.getBackground().getBlue()));
	}
	
	public void Node_Bounds(int x, int y, int w, int h) {
		selected_Label.setBounds(x, y, w, h);
		
		Line_set();
		
		repaint();
	}
	
	public void Node_set(String str) {
		removeAll();
		
		
		int m = 0, n = str.trim().split("\n").length;
		LinkedList<Integer> node_Link = new LinkedList();
		
		node = new int[n][n];
		String[] S = str.trim().split("\n");
		if(n == 1 && S[0].trim().equals(""))
			n = 0;
		node_text = new String[n+1];
		node_Label = new JLabel[n+1];
		
		for(int i = 0; i++<n;) {
			node_text[i] = S[i-1].trim();
			node_Label[i] = new JLabel(S[i-1].trim());
			node_Label[i].setHorizontalAlignment(JLabel.CENTER);
			node_Label[i].setOpaque(true);
			node_Label[i].setBorder(new TitledBorder(new LineBorder(Color.white, 2)));
			node_Label[i].setBounds(i*50, 300, 50, 50);
			node_Label[i].addMouseListener(this);
			node_Label[i].addMouseMotionListener(this);
			add(node_Label[i]);
		}
		if(n!=0) {
			node_Label[1].setBounds(getWidth()/2 - 25, getHeight()/2 - 25, 50, 50);
			node_Label[1].setBackground(node_Colors.get(0));
		}
		
		node_Link.add(0);
		
		for(int i = 1; i < n; i++) {
			m = node_Link.size();
			if(m < S[i].split("\t").length) {
				node_Link.add(i);
			} else if(m == S[i].split("\t").length) {
				node_Link.removeLast();
				node_Link.add(i);
			} else {
				for(; m-- > S[i].split("\t").length;) {
					node_Link.removeLast();
				}
				node_Link.removeLast();
				node_Link.add(i);
			}
			int a = node_Link.get(S[i].split("\t").length-1), b = node_Link.get(S[i].split("\t").length-2);
			node[b][a] = 1;
		}
		
		LinkedList<Integer> Parent = new LinkedList();
		
		int ch[] = new int[n];
		double degree[] = new double[n];
		int Parent_node = 0;
		
		for(int i = 0; i < n; i++) {
			Parent.clear();
			for(int j = i; j<n; j++) {
				if(node[i][j] == 1 && ch[j] != 1) {
					Parent.add(j);
					ch[j] = 1;
				}
			}
			int a = 1, j2, cnt;
			double li;
			
			degree[0] = 0;
			for(int j : Parent) {
				for(int k = 0; k < j; k++) {
					if(node[k][j] == 1) {
						Parent_node = k;
						break;
					}
				}
				li = 240;
				cnt = 0;
				j2 = j;
				for(int k = 0; k < j2; k++) {
					if(node[k][j2] == 1) {
						li*=2.0/3;
						j2 = k;
						k = -1;
						cnt++;
					}
				}
				
				node_Label[j+1].setBackground(node_Colors.get(cnt >= node_Colors.size()?node_Colors.size()-1:cnt));
				degree[j] = degree[Parent_node]+pi+2*pi*a/(Parent.size()+(Parent_node==0?0:1));
				node_Label[j+1].setLocation((int)(node_Label[i+1].getX()+li*Math.cos(degree[j])), (int)(node_Label[i+1].getY()+li*Math.sin(degree[j])));
				a++;
			}
		}
		if(n!=0)
			selected_Label = node_Label[1];
		
		Line_set();
		
		repaint();
	}
	
	public void Line_set() {
		Line.clear();
		
		for(int i = 0; i < node.length; i++) {
			for(int j = 0; j < node.length; j++) {
				if(node[i][j] == 1) {
					int[] A = new int[4];
					A[0] = node_Label[i+1].getX()+node_Label[i+1].getWidth()/2;
					A[1] = node_Label[i+1].getY()+node_Label[i+1].getHeight()/2;
					A[2] = node_Label[j+1].getX()+node_Label[j+1].getWidth()/2;
					A[3] = node_Label[j+1].getY()+node_Label[j+1].getHeight()/2;
					Line.add(A);
				}
			}
		}
	}
	
	public Mindmap_MMP() {
		setLayout(null);
		setBackground(new Color(73, 199, 196));
		
		selected_Label.setBackground(Color.white);
		selected_Label_before.setBackground(Color.white);
		
		node_Colors.add(new Color(120, 199, 82));
		node_Colors.add(new Color(246, 119, 111));
		node_Colors.add(new Color(144, 168, 209));
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		for(int[] l : Line) {
			g.drawLine(l[0], l[1], l[2], l[3]);
		}
	}

	char now_X = 'X', now_Y = 'X';
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if(now_X == 'X' && now_Y == 'X') {
			selected_Label.setLocation(e.getXOnScreen()-now_x, e.getYOnScreen()-now_y);
		} else {
			if(now_X == 'R') {
				selected_Label.setSize(now_w-now_x+e.getXOnScreen() - selected_Label.getX(), selected_Label.getHeight());;
			} else if(now_X == 'L') {
				selected_Label.setBounds(e.getXOnScreen()-now_x, selected_Label.getY(), now_L - e.getXOnScreen() + now_x, selected_Label.getHeight());
			}
			
			if(now_Y == 'D') {
				selected_Label.setSize(selected_Label.getWidth(), now_h-now_y+e.getYOnScreen() - selected_Label.getY());;
			} else if(now_Y == 'U') {
				selected_Label.setBounds(selected_Label.getX(), e.getYOnScreen()-now_y, selected_Label.getWidth(), now_D - e.getYOnScreen() + now_y);
			}
		}
		
		Text_Set();
		
		Line_set();
		
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

		Label_Selected((JLabel) e.getComponent());
		
		now_x = e.getXOnScreen() - e.getComponent().getX();
		now_y = e.getYOnScreen() - e.getComponent().getY();
		now_w = e.getComponent().getWidth();
		now_h = e.getComponent().getHeight();
		now_L = e.getComponent().getX()+e.getComponent().getWidth();
		now_D = e.getComponent().getY()+e.getComponent().getHeight();
		
		if(e.getX() > e.getComponent().getWidth()-5 && e.getX() < e.getComponent().getWidth() + 5) {
			now_X = 'R';
		} else if(e.getX() > -5 && e.getX() < 5) {
			now_X = 'L';
		}
		if(e.getY() > e.getComponent().getHeight()-5 && e.getY() < e.getComponent().getHeight() + 5) {
			now_Y = 'D';
		} else if(e.getY() > -5 && e.getY() < 5) {
			now_Y = 'U';
		}
		
		Text_Set();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		now_X = 'X';
		now_Y = 'X';
		repaint();
	}

}
