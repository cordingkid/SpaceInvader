

import javax.swing.JOptionPane;

public class javaProjectEngine implements Runnable {
	private javaProjectGrid model;
	private javaProjectView view;
	private boolean[] isKeyType;
	
	public javaProjectEngine(javaProjectGrid model,javaProjectView view) {
		this.model= model;
		this.view= view;
	}
	@Override
	public void run() {
		this.view.update(model);
		
		while(!this.model.isGameOver()) {
			gameTiming();
			gameLogic();
			drawAll();
			}
		endMessage();
		}
	

	private void gameTiming() {
		if(this.view.currentScreen == 1)
		this.model.gameTiming();
	}
	
	private void gameLogic() {
		if(this.view.currentScreen == 1) {
		this.userActionHandler(this.view.getKeyType());
		this.model.bulletCheck();
		this.model.enemyMoving();
		this.model.enemyRemove();
		this.model.enemyBulletCheck();
		this.model.crashObject();
		this.model.AppearanceBonus();
		}
	}
	
	private void resetData() {
		this.model.initObject();
	}
	private void drawAll() {
		this.view.repaint();
	}
	
	private void endMessage() {
		this.model.writerTopScore();
		int result=this.view.dialog();
		if(result == JOptionPane.CLOSED_OPTION) { // ����ڰ� ���� ���� �������
			
			System.exit(0);
		}
		else if(result == JOptionPane.YES_OPTION){ //����ڰ� ���� ����
			resetData();
			new Thread(this).start();
		}
		else {//����ڰ� �ƴϿ� ����
			System.exit(0);
		}
	}
	
	
	private void userActionHandler(boolean[] keyType){
		this.isKeyType=keyType;
		if(isKeyType[0])
			this.model.movePlayerUp();
		 if(isKeyType[1])
			 this.model.movePlayerDown();
		 if(isKeyType[2])
			 this.model.movePlayerLeft();
		 if(isKeyType[3]) 
			 this.model.movePlayerRight();
		 if(isKeyType[4]){
			 this.model.playerShooting();
			 isKeyType[4] = false; 
			 }
		}
}
