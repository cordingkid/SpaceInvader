import java.awt.Image;

public class EnemyObject extends GameObject {
	public EnemyObject(int x,int y,Image i){
		super(x,y,i);
	}
	public void enemySideMoving(boolean isTurn) {
		if(isTurn)
			this.rightPos();
		else
			this.leftPos();
	}
	public boolean enemyMoveCheck(boolean isTurn,int rightSide,int leftSide) {
		if(isTurn) {
			if(this.getPosX()>rightSide-10)
				isTurn=false;
		}else {
			if(this.getPosX()<leftSide)
				isTurn=true;
		}
		return isTurn;
	}
}
