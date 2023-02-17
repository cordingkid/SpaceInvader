import javax.swing.*;

public class javaProject2 {
	private javaProjectEngine controller;
	private javaProjectGrid model;
	private javaProjectView view;
	private JFrame jFrame;
	private int width;
	private int height;
	
	public javaProject2(int width,int height) {
		this.width= width;
		this.height= height;
		initMVC();
		initJFrame();
		new Thread(controller).start();
	}
	
	private void initMVC() {
		view = new javaProjectView(width, height);
		model = new javaProjectGrid();
		controller = new javaProjectEngine(model, view);
	}
	private void initJFrame() {
		jFrame= new JFrame();
		jFrame.add(view);
		jFrame.pack();
		jFrame.setTitle("Let's play Space Invaders");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
		jFrame.setLocationRelativeTo(null);
		}
	public static void main(String[] args) {new javaProject2(1000,600);}

}
