import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *ɨ�ף����������ƿ���֮��һ�汾����߰棬�������Ҽ���ǵĹ��ܣ��޸���mac0S�����Ӧ����windowsʱ��bug
 * @author Minstrel
 * @version 2018.1.2
 */

public class MineSweeper extends JFrame implements MouseListener {

	JFrame frame=new JFrame("Minstrel��s ɨ��");
	JButton reset=new JButton("����");
	Container container=new Container();
	final int row=16;
	final int col=16;
	final int mineCount=40;
	JButton[][] buttons=new JButton[row][col];
	int[][] counts=new int[row][col];
	final int MINECODE=10;
	
	public MineSweeper() {
		frame.setSize(850,920);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		addResetButton();
		addButtons();
		
		addMines();
		
		calcNeibor();
		
		frame.setVisible(true);
	}

	void addResetButton() {
		reset.setBackground(Color.pink);
		reset.setOpaque(true);
		reset.addMouseListener(this);
		frame.add(reset,BorderLayout.NORTH);
	}
	
	void addButtons() {
		frame.add(container,BorderLayout.CENTER);
		container.setLayout(new GridLayout(row,col));
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				JButton button =new JButton();
				button.addMouseListener(this);
				buttons[i][j]=button;
				container.add(button);
			}
		}
	}
	
	
	
	void addMines() {
		Random rand =new Random();
		int randRow, randCol;
		for(int i=0;i<mineCount;i++) {
			randRow=rand.nextInt(row);
			randCol=rand.nextInt(col);
			
			if(counts[randRow][randCol]==MINECODE) {
				i--;
			}else {
			   counts[randRow][randCol]=MINECODE;
			  }
		}
	}
	
	void calcNeibor() {
		int count;
		for(int i=0;i<row;i++) {
			for(int j=0;j<col;j++) {
				count=0;
				if(counts[i][j]==MINECODE) continue;
				
				if(i>0&&j>0&&counts[i-1][j-1]==MINECODE) count++;
				if(i>0&&counts[i-1][j]==MINECODE) count++;
				if(i>0&&j<(col-1)&&counts[i-1][j+1]==MINECODE) count++;
				if(j>0&&counts[i][j-1]==MINECODE) count++;
				if(j<(col-1)&&counts[i][j+1]==MINECODE) count++;
				if(i<(row-1)&&j>0&&counts[i+1][j-1]==MINECODE) count++;
				if(i<(row-1)&&counts[i+1][j]==MINECODE) count++;
				if(i<(row-1)&&j<(col-1)&&counts[i+1][j+1]==MINECODE) count++;
				
				counts[i][j]=count;
				//buttons[i][j].setText(counts[i][j]+"");
			}
		}
	}
	
	void losegame() {
		for(int i=0;i<row;i++) {
			for(int j=0;j<col;j++) {
				int count=counts[i][j];
				if(count==MINECODE) {
					buttons[i][j].setText("��");
					buttons[i][j].setBackground(Color.red);
					buttons[i][j].setOpaque(true);
					buttons[i][j].setEnabled(false);
				}
				else {
					buttons[i][j].setText(count+"");
					buttons[i][j].setEnabled(false);
				}
			}
		}
	}
	
	void opencell(int i, int j) {
		
		if(buttons[i][j].isEnabled()==false) return;
		
		buttons[i][j].setEnabled(false);
		
		if(counts[i][j]==0) {
			buttons[i][j].setBackground(Color.white);
			buttons[i][j].setOpaque(true);
			if(i>0&&j>0&&counts[i-1][j-1]!=MINECODE) opencell(i-1,j-1);
			if(i>0&&counts[i-1][j]!=MINECODE) opencell(i-1,j);
			if(i>0&&j<(col-1)&&counts[i-1][j+1]!=MINECODE) opencell(i-1,j+1);
			if(j>0&&counts[i][j-1]!=MINECODE) opencell(i,j-1);
			if(j<(col-1)&&counts[i][j+1]!=MINECODE) opencell(i,j+1);
			if(i<(row-1)&&j>0&&counts[i+1][j-1]!=MINECODE) opencell(i+1,j-1);
			if(i<(row-1)&&counts[i+1][j]!=MINECODE) opencell(i+1,j);
			if(i<(row-1)&&j<(col-1)&&counts[i+1][j+1]!=MINECODE) opencell(i+1,j+1);
		}
		else {
			buttons[i][j].setText(counts[i][j]+"");
			buttons[i][j].setBackground(Color.white);
			buttons[i][j].setOpaque(true);
		}
	}
	
	void checkWin() {
		for(int i=0;i<row;i++) {
			for(int j=0;j<col;j++) {
				if(buttons[i][j].isEnabled()==true&&counts[i][j]!=MINECODE) return;
			}
		}
		JOptionPane.showMessageDialog(frame, "��Ӯ�ˣ�");
	}
	
	public static void main(String[] args) {

		MineSweeper boom=new MineSweeper();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JButton button=(JButton)e.getSource();
		if(e.getButton()==MouseEvent.BUTTON1) {
			if(button.equals(reset)) {
				for(int i=0;i<row;i++) {
					for(int j=0;j<col;j++) {
						buttons[i][j].setText("");
						buttons[i][j].setEnabled(true);
						buttons[i][j].setBackground(Color.lightGray);
						buttons[i][j].setOpaque(false);
						counts[i][j]=0;
					}	
				}
				addMines();
				calcNeibor();
			}
			else {
				int count=0;
				for(int i=0;i<row;i++) {
					for(int j=0;j<col;j++) {
						if(button.equals(buttons[i][j])) {
							count=counts[i][j];
							if(count==MINECODE) {
								losegame();
							}
							else{
								opencell(i,j);
								checkWin();
						}
							return;
					}
				}
				
				}
			}
		}
		if(e.getButton()==MouseEvent.BUTTON3) {
			for(int i=0;i<row;i++) {
				for(int j=0;j<col;j++) {
					if(button.equals(buttons[i][j])) {
						if(buttons[i][j].getText()!="��"&&buttons[i][j].getText()!="?"&&buttons[i][j].isEnabled()==true) {
						buttons[i][j].setText("��");
						buttons[i][j].setBackground(Color.LIGHT_GRAY);
						buttons[i][j].setOpaque(true);
						return;
						}
						if(buttons[i][j].getText()=="��") {
							buttons[i][j].setText("?");
							return;
						}
						if(buttons[i][j].getText()=="?") {
							buttons[i][j].setText("");
							buttons[i][j].setOpaque(false);
							return;
						  }
						}
					}
				}
		     }
		}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
}