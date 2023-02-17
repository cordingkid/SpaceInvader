import java.awt.Image;

public class EnemyBulletObject extends GameObject {
	private boolean isEnemybulletOk;
	public EnemyBulletObject(int x,int y,Image i){
		super(x,y,i);
		isEnemybulletOk=false;
	}
	public boolean getSign() {
		if(!isEnemybulletOk) {
			this.posX=0;
			this.posY=0;
		}
		return isEnemybulletOk;
		}
	public void changeSign(boolean sign) {this.isEnemybulletOk=sign;}
	public void enemyBulletCheckTest(int botomSide) {
		if(this.getSign()) {
			this.downPos();  //�Ѿ� ��������
		}
		if(this.getPosY()>botomSide)
			this.changeSign(false);
	}
}
