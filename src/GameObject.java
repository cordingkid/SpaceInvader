import java.awt.*;

import javax.swing.*;

public abstract class GameObject {
	protected int posX;
	protected int posY;
	protected Image image;
	
	public GameObject(int x,int y,Image i) {
		this.posX=x;
		this.posY=y;
		this.image=i;
	}
	public int getPosX() {return this.posX;}
	public int getPosY() {return this.posY;}
	public Image getImage() {return this.image;}
	public void leftPos() {this.posX--;}
	public void rightPos() {this.posX++;}
	public void downPos() {this.posY++;}
	public void upPos() {this.posY--;}
	public void setPositioning(int x,int y) {this.posX=x;this.posY=y;}
}
