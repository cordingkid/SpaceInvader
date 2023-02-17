
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


public class javaProjectView extends JPanel{
	private static final double GAME_SCORE_AREA_RATIO= 0.80;
	private int width;
	private int height;
	private int gameWidth;
	private int sideWidth;
	private Image backGroundImage;
	private Image sideImage;
	private JButton btn;
	public int currentScreen= 0;
	private String str;
	private boolean isKeyType[];
	private boolean isSpaceLock;
	//
	
	private javaProjectGrid viewModel;
	
	//private GameObject PlayerObject;
	
	public javaProjectView(int width, int height) {
		startScreen();
		setData();
		initDimensions(width,height);
		addKeyBindings();
		this.setPreferredSize(new Dimension(this.width,this.height));
		}
	
	
	
	private void setData() {
		isKeyType=new boolean[5];
		isSpaceLock=false;
		btn.setSize(1000, 100);
		btn.setLocation(0, 500);
		sideImage=new ImageIcon("image/사이드 화면.jpg").getImage();
	}
	private void startScreen() {
		backGroundImage = new ImageIcon("image/스타트화면.jpg").getImage().getScaledInstance(1000,500,java.awt.Image.SCALE_SMOOTH);
		btn = new JButton("Start Game");
		btn.addActionListener(new ActionListener()
		 {
		 @Override
		 public void actionPerformed(ActionEvent e){
			 btn.setVisible(false);
			 currentScreen = 1;
			 repaint();
		 }});
	}
	public int dialog() { //�̱�ų� ������ �ߴ� Ȯ�� ���̾�α�
		for(int i=0;i<5;i++)
			isKeyType[i]=false;
		int result= JOptionPane.showConfirmDialog(null, "Play Again??", this.viewModel.wimOrLose(), JOptionPane.YES_NO_OPTION);
		return result;
	}
	private void drawStartScreen(Graphics2D g2d) //ó��ȭ�� �׸���
	 {
	 g2d.drawImage(backGroundImage, 0, 20, this);
	 add(btn);
	 }

	public void update(javaProjectGrid model) { //�� ����
		this.viewModel=model;
	}
	
	private void drawBullet(Graphics2D g2d) { //�Ѿ� �׸���
		for(int i=viewModel.getBullet().size()-1;i>=0;i--) {
			if(viewModel.getBullet().get(i).getSign()) {
			g2d.drawImage(viewModel.getBullet().get(i).getImage(),
					viewModel.getBullet().get(i).getPosX(),
					viewModel.getBullet().get(i).getPosY(),this);
			}
		}
	}
	
	private void drawPlayer(Graphics2D g2d) { //�÷��̾� �׸���
		g2d.drawImage(viewModel.getPlayer().getImage(),  //player.getImage
				viewModel.getPlayer().getPosX()
				,viewModel.getPlayer().getPosY(),this);
		}
	
	private void drawEnemy(Graphics2D g2d) {  //�� �׸���
		for(int i=viewModel.getEnemy().size()-1;i>=0;i--) {
		g2d.drawImage(viewModel.getEnemy().get(i).getImage(),
				viewModel.getEnemy().get(i).getPosX(),
				viewModel.getEnemy().get(i).getPosY(),this);
			}
		}
	private void drawEnemyBullet(Graphics2D g2d) {  //�� �Ѿ� �׸���
		for(int i=viewModel.getEnemyBullet().size()-1;i>=0;i--) {
			if(viewModel.getEnemyBullet().get(i).getSign()) {
		g2d.drawImage(viewModel.getEnemyBullet().get(i).getImage(),
				viewModel.getEnemyBullet().get(i).getPosX(),
				viewModel.getEnemyBullet().get(i).getPosY(),this);
				}
			}
		}
	private void drawBonusObject(Graphics2D g2d) {  //���ʽ� ������Ʈ �׸���
		for(int i=viewModel.getBonusObject().size()-1;i>=0;i--) {
			g2d.drawImage(viewModel.getBonusObject().get(i).getImage(),
					viewModel.getBonusObject().get(i).getPosX(),
					viewModel.getBonusObject().get(i).getPosY(),this);
		}
	}
	
	
	
	
	private void initDimensions(int width, int height) {
		this.width= width;
		this.height= height;
		this.gameWidth= (int) Math.floor(width * GAME_SCORE_AREA_RATIO);
		this.sideWidth= width -this.gameWidth-1;
		}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d= (Graphics2D) g;
		if(currentScreen == 0) 
			drawStartScreen(g2d);
		else {
			drawGameGrid(g2d);
			fillSidePanel(g2d);
			drawScore(g2d);
			drawTopScore(g2d);
			drawSideImage(g2d);
			drawPlayer(g2d);
			drawEnemy(g2d);
			drawBullet(g2d);
			drawEnemyBullet(g2d);
			drawBonusObject(g2d);
			}
		}
	
	
	private void fillSidePanel(Graphics2D g2d) {
		g2d.setPaint(new Color(0, 8, 52)); // ��û��
		g2d.fillRect(width -sideWidth, 0, sideWidth, height);
		}
	
	private void drawScore(Graphics2D g2d) {
		g2d.setFont(g2d.getFont().deriveFont(20F));
		g2d.setColor(new Color(255, 233, 0));
		FontMetrics metrics= g2d.getFontMetrics(g2d.getFont());
		g2d.drawString("SCORE: "+Long.toString(this.viewModel.getScore()), this.width-this.sideWidth, metrics.getHeight());
		}
	private void drawSideImage(Graphics2D g2d) {
		g2d.drawImage(sideImage, this.width-this.sideWidth, this.height-200, this);
		}
	
	private void drawTopScore(Graphics2D g2d) {
		g2d.setFont(g2d.getFont().deriveFont(20F));
		g2d.setColor(new Color(255, 233, 0));
		g2d.drawString("TOP SCORE: ", this.width-this.sideWidth,this.height-400);
		g2d.setColor(new Color(255, 0, 0));
		g2d.drawString(Long.toString(this.viewModel.getTopScore()), this.width-this.sideWidth+130,this.height-400);
		}
	
	private void drawGameGrid(Graphics2D g2d) {
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0,0, gameWidth, height);
		}
	
//Ű �Է� ��ºκ�
	
	private void addKeyBindings(){
		getInputMap().put(KeyStroke.getKeyStroke("UP"), "move up");
		 getInputMap().put(KeyStroke.getKeyStroke("released UP"), "released up");
		 this.getActionMap().put("move up", moveUpAction);
		 this.getActionMap().put("released up", moveReleasedUpAction);
		 
		 getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "move down");
		 getInputMap().put(KeyStroke.getKeyStroke("released DOWN"), "released down");
		 this.getActionMap().put("move down", moveDownAction);
		 this.getActionMap().put("released down", moveReleasedDownAction);
		 
		 getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "move left");
		 getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "released left");
		 this.getActionMap().put("move left", moveLeftAction);
		 this.getActionMap().put("released left", moveReleasedLeftAction);
		 
		 getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "move right");
		 getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "released right");
		 this.getActionMap().put("move right", moveRightAction);
		 this.getActionMap().put("released right", moveReleasedRightAction);
		 
		 getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "attack");
		 getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "released space");
		 this.getActionMap().put("attack", shootingAction);
		 this.getActionMap().put("released space", moveReleasedShootingAction);}
	
	
	//up
	private final Action moveUpAction=new AbstractAction(){
		public void actionPerformed(ActionEvent event){
			isKeyType[0]=true;
			}
		};
	private final Action moveReleasedUpAction=new AbstractAction(){
		public void actionPerformed(ActionEvent event){
			isKeyType[0]=false;
			}
		};
	//down
	private final Action moveDownAction=new AbstractAction(){
		public void actionPerformed(ActionEvent event){
			isKeyType[1]=true;
			}
		};
	private final Action moveReleasedDownAction=new AbstractAction(){
		public void actionPerformed(ActionEvent event){
			isKeyType[1]=false;
			}
		};
	//left
	private final Action moveLeftAction=new AbstractAction(){
		public void actionPerformed(ActionEvent event){
			isKeyType[2]=true;
			}
		};
	private final Action moveReleasedLeftAction=new AbstractAction(){
		public void actionPerformed(ActionEvent event){
			isKeyType[2]=false;
			}
		};
	//right
	private final Action moveRightAction=new AbstractAction(){
		public void actionPerformed(ActionEvent event){
			isKeyType[3]=true;
			}
		};
	private final Action moveReleasedRightAction=new AbstractAction(){
		public void actionPerformed(ActionEvent event){
			isKeyType[3]=false;
			}
		};
	//Shooting
	private final Action shootingAction=new AbstractAction(){
		public void actionPerformed(ActionEvent event){
			if(!isSpaceLock) {
			isKeyType[4]=true;
			isSpaceLock=true;
				}
			}
		};
	private final Action moveReleasedShootingAction=new AbstractAction(){
		public void actionPerformed(ActionEvent event){
			isKeyType[4]=false;
			isSpaceLock=false;
			}
		};
	
	public boolean[] getKeyType() {return isKeyType;}

}
