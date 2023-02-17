import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;

public class javaProjectGrid {
	
	private final int ENEMY=8;
	private final int BULLET=10;
	private final int INIT_SPEED=40;
	private final int MAX_SPEED=10;
	private final int LEFT_SIDE=1;
	private final int RHIGHT_SIDE=760;
	private final int BOTTOM_SIDE=560;
	private final int TOP_SIDE=1;
	
	
	private PlayerObject player;
	private ArrayList<BonusObject> bonus;
	private ArrayList<EnemyObject> enemy;
	private ArrayList<BulletObject> bullet;
	private ArrayList<EnemyBulletObject> enemyBullet;
	private int bulletCount;
	
	private int score;
	private int speed;
	private int speedCounter;
	private boolean forceDown;
	private boolean isEnemyShoot;
	private int speedBoost;
	private boolean isTurn;
	private Random random = new Random();
	private int randomNum;
	private boolean isGameWin;
	private boolean isGameOver;
	private boolean isGameLose;
	private boolean isCheckCrash;
	private long bonusTime;
	private int topScore;
	
	
	
	public javaProjectGrid() {
		this.initObject();
	}
	public void initObject() {
		bullet =new ArrayList<BulletObject>();
		enemy =new ArrayList<EnemyObject>();
		enemyBullet =new ArrayList<EnemyBulletObject>();
		bonus=new ArrayList<BonusObject>();
		
		player=new PlayerObject(370,500,(new ImageIcon("image/진짜제발.png").getImage().getScaledInstance(50,30,java.awt.Image.SCALE_SMOOTH)));
		
		
		
		for(int i=0;i<ENEMY;i++) {
			if(i%2==0)
				enemy.add(new EnemyObject(20+(50*i),30,new ImageIcon("image/외계인.png").getImage().getScaledInstance(50,30,java.awt.Image.SCALE_SMOOTH)));
			else
				enemy.add(new EnemyObject(20+(50*i),70,new ImageIcon("image/외계인.png").getImage().getScaledInstance(50,30,java.awt.Image.SCALE_SMOOTH)));
		}
		
		for(int i=0;i<BULLET;i++) {
			bullet.add(new BulletObject(0,0,new ImageIcon("image/미사일.png").getImage().getScaledInstance(10,10,java.awt.Image.SCALE_SMOOTH)));
		}
		for(int i=0;i<ENEMY;i++) {
			enemyBullet.add(new EnemyBulletObject(0,0,new ImageIcon("image/적미사일.png").getImage().getScaledInstance(10,10,java.awt.Image.SCALE_SMOOTH)));
		}
		score=0;
		
		speed=INIT_SPEED;
		speedCounter=0;
		forceDown=false;
		isEnemyShoot=false;
		speedBoost=0;
		isTurn=true;
		
		
		isCheckCrash= false;
		isGameWin=false;
		isGameOver=false;
		isGameLose=false;
		this.bonusTime=System.currentTimeMillis();
		readTopScore();
	}
	public void gameTiming() {

		try {
			Thread.sleep(3);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		speedCounter++;
		forceDown=(speedCounter==speed);
	}

	//�Ѿ�
	public void playerShooting() {
		bulletCount=0;
		for(int i=0;i<bullet.size();i++) {
			if(bullet.get(bulletCount).getSign())
				bulletCount++;
		}
		if(bulletCount<bullet.size()) {
			bullet.get(bulletCount).setPositioning(player.getPosX()+20,player.getPosY());
			bullet.get(bulletCount).changeSign(true);
		}
	}
	
	public void bulletCheck() {  //�Ѿ��� ���� �¾Ҵ��� ���� ��Ѵ��� Ʈ���̸� �ö󰡰� �ϱ������Լ�
		Iterator<BulletObject> itrB= bullet.iterator();           //�Ѿ� �׸���
		while(itrB.hasNext()) {
			BulletObject b=itrB.next();
			b.bulletCheckTest();
		}
	}
	
	public void enemyMoving() {//�� �������ְ� �ϴ��Լ�
		enemyMoveCheck();  //������ ������ üũ�ؼ�
		Iterator<EnemyObject> itr= enemy.iterator();
		while (itr.hasNext()) {
			EnemyObject e=itr.next();
			e.enemySideMoving(isTurn);
			if(forceDown) {  //����Ÿ�ֿ̹��� ���� �ٿ��� Ʈ�簡 �Ǹ� �� �������� �ϰ� ���� ������
					e.downPos();
				speedBoost++;
				if(speedBoost%10==0)
					if(speed>=MAX_SPEED)
						speed--;
				speedCounter=0;
				if(e.getPosY()>BOTTOM_SIDE) {
					isGameLose=true;
					isGameOver=true;
				}
			}
		}
	}
	private void enemyMoveCheck() {
		if(enemy.size()>0) {  //turn�� Ʈ���϶� ������ �޽��϶� �������� üũ �ϴ��Լ� 
			if(isTurn) 
				isTurn=enemy.get(enemy.size()-1).enemyMoveCheck(isTurn, RHIGHT_SIDE, LEFT_SIDE);
			else 
				isTurn=enemy.get(0).enemyMoveCheck(isTurn, RHIGHT_SIDE, LEFT_SIDE);
		}
	}
	
	public void enemyRemove() {  //�� �� �Ѿ˿� ������ ���� ���� �ִ� �Լ�
		Iterator<EnemyObject> itr= enemy.iterator();
		while (itr.hasNext()) {
			EnemyObject e=itr.next();
			for(int j=0;j<BULLET;j++){
				if(e.getPosY()+e.getImage().getHeight(null)==bullet.get(j).getPosY()) {
					if(e.getPosX()<=bullet.get(j).getPosX() && e.getPosX()+e.getImage().getWidth(null)>=bullet.get(j).getPosX()) {
						bullet.get(j).changeSign(false);
						itr.remove();
						score+=10;
						break;
					}
				}
			}
		}
		if(enemy.size()==0) {
			isGameWin=true;
			isGameOver=true;
		}
	}
	
	private void enemyBulletShoot() {  //���� �����ϰ� ���� ����ϴ��Լ�
		if(enemy.size()>0) {
			randomNum=random.nextInt(20+enemy.size());
			if(randomNum<enemy.size()) {
				if(enemyBullet.get(randomNum).getSign()==false) {
					enemyBullet.get(randomNum).setPositioning(enemy.get(randomNum).getPosX()+20,
							enemy.get(randomNum).getPosY()+30);
					enemyBullet.get(randomNum).changeSign(true);
				}
			}
		}
	}
	
	public void enemyBulletCheck() {  //���� ������� �������� �ϸ鼭 �Ѿ� �����ְ� �÷��̾������ ���� ���� ��
		enemyBulletShoot();
		Iterator<EnemyBulletObject> EitrB= enemyBullet.iterator();
		while (EitrB.hasNext()) {
			EnemyBulletObject eb=EitrB.next();
			eb.enemyBulletCheckTest(BOTTOM_SIDE);
			if(eb.getPosY()==player.getPosY()) {
				if(player.getPosX()<=eb.getPosX() && player.getPosX()+player.getImage().getWidth(null)>=eb.getPosX()) {
					eb.changeSign(false);
					isGameLose=true;
					isGameOver=true;
				}
			}
		}
		
	}
	public void crashObject() {
		Iterator<EnemyObject> itr= enemy.iterator();
		while (itr.hasNext()) {
			EnemyObject e=itr.next();
			if ( Math.abs( ((e.getPosX()  + 20) / 2 )  - ( (player.getPosX() + 20) / 2 ))  <  ( 20 / 2 + 20 / 2 )  
					&& Math.abs( ( (e.getPosY() + 20) / 2 )  - ( (player.getPosY() + 20) / 2 ))  <  ( 20 / 2 + 10/ 2 ) ) {
				isCheckCrash=true;
				break;
			}
			else
				isCheckCrash=false;
		}
		if(isCheckCrash) {
			isGameLose=true;
			isGameOver=true;
		}
		
		//�浹 ����
		
		//		if ( Math.abs( ( x1 + w1 / 2 )  - ( x2 + w2 / 2 ))  <  ( w2 / 2 + w1 / 2 )  
		//				&& Math.abs( ( y1 + h1 / 2 )  - ( y2 + h2 / 2 ))  <  ( h2 / 2 + h1/ 2 ) ){
	}
	
	public void AppearanceBonus() {
		if((System.currentTimeMillis()-bonusTime)>=7000) {
			if(bonus.size()==0) {
				bonus.add(new BonusObject(0,0,new ImageIcon("image/보너스우주선.png").getImage().getScaledInstance(60,40,java.awt.Image.SCALE_SMOOTH)));
			}
			bonusTime=System.currentTimeMillis();
		}
		Iterator<BonusObject> itrBonus= bonus.iterator();
		while (itrBonus.hasNext()) {
			BonusObject bonus=itrBonus.next();
			bonus.rightPos();
				if(bonus.getPosX()+bonus.getImage().getWidth(null)>RHIGHT_SIDE) {
					itrBonus.remove();
				}
				else {
					for(int i=0;i<BULLET;i++) {
					if(bonus.getPosY()+bonus.getImage().getHeight(null)==bullet.get(i).getPosY()) {
						if(bonus.getPosX()<=bullet.get(i).getPosX() &&
								bonus.getPosX()+bonus.getImage().getWidth(null)>=bullet.get(i).getPosX()) {
							itrBonus.remove();
							bullet.get(i).changeSign(false);//���� �� ������ �Ѿ˵� �����ִ� �Լ�
							score+=100;
							break;
							}
						}
					}
				}

		}
	}
	
			
	public boolean isGameOver() {return isGameOver;}
	
	public String wimOrLose() {
		if(isGameWin)
			return "You Win!!";
		else 
			return "You Lose!!";
		}
	
	public int getScore() {return score;}
	
	public int getTopScore() {
		if(topScore<=score)
			topScore=score;
		return topScore;
		}
	
	public PlayerObject getPlayer() {return player;}
	
	public ArrayList<EnemyObject> getEnemy() {return enemy;}
	
	public ArrayList<BulletObject> getBullet() {return bullet;}
	
	public ArrayList<EnemyBulletObject> getEnemyBullet() {return enemyBullet;}
	
	public ArrayList<BonusObject> getBonusObject() {return bonus;}
	
	
	
	public void writerTopScore() {
		try {  //ó���κ�
			BufferedWriter out= new BufferedWriter(new FileWriter("Game.txt"));
			out.write(String.valueOf(topScore));
			out.newLine();
			out.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void readTopScore() {
		try {
			String str;
			BufferedReader in= new BufferedReader(new FileReader("Game.txt"));
			str=in.readLine();
			if(str!=null) {
				topScore=Integer.parseInt(str);
				}
			in.close();
		}catch (FileNotFoundException e){
			topScore=0;
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void movePlayerDown(){
		if(player.getPosY()<BOTTOM_SIDE)
		player.downPos();
		}
			
	public void movePlayerLeft(){
		if(player.getPosX()>LEFT_SIDE)
		player.leftPos();
		}
	
	
	public void movePlayerRight(){
		if(player.getPosX()<RHIGHT_SIDE)
		player.rightPos();
		}
	
	
	public void movePlayerUp(){
		if(player.getPosY()>TOP_SIDE)
		player.upPos();
	}
		
}
