package okkapel.ogljguisystem;

import org.lwjgl.opengl.GL11;

public class GuiRenderer {
	
	// TODO: use more efficient rendering method
	public static void render(Gui gui) {
		gui.getBGColor().setGLColor();
		if(gui.isMinimized()) {
			GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glVertex3f(gui.getX(), gui.getY(), 1.9f);
			GL11.glVertex3f(gui.getX()+15, gui.getY()+30, 1.9f);
			GL11.glVertex3f(gui.getX()+30, gui.getY(), 1.9f);
			GL11.glEnd();
			return;
		}
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(gui.getX(), gui.getY(), 1.9f);
		GL11.glVertex3f(gui.getX(), gui.getY()+gui.getHeight(), 1.9f);
		GL11.glVertex3f(gui.getX()+gui.getWidth(), gui.getY()+gui.getHeight(), 1.9f);
		GL11.glVertex3f(gui.getX()+gui.getWidth(), gui.getY(), 1.9f);
		
		GL11.glVertex3f(gui.getX(), gui.getY(), 1.9f);
		GL11.glVertex3f(gui.getX(), gui.getY()+20, 1.9f);
		GL11.glVertex3f(gui.getX()+gui.getWidth(), gui.getY()+20, 1.9f);
		GL11.glVertex3f(gui.getX()+gui.getWidth(), gui.getY(), 1.9f);
		GL11.glEnd();
	}
}
