import java.awt.Image;

public class BulletObject extends GameObject {
	private boolean isbulletOk;
	public BulletObject(int x,int y,Image i){
		super(x,y,i);
		isbulletOk=false;
	}
	public boolean getSign() {
		if(!isbulletOk) {
			this.posX=0;
			this.posY=0;
		}
		return isbulletOk;
		}
	public void changeSign(boolean sign) {this.isbulletOk=sign;}
	public void bulletCheckTest() {
		if(this.isbulletOk) {
			this.upPos();
		}
		if(this.getPosY()<1)
			this.isbulletOk=false;
	}
}
