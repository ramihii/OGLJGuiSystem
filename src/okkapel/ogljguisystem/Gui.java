package okkapel.ogljguisystem;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import okkapel.ogljguisystem.util.Colour;
import okkapel.ogljguisystem.util.MouseHelper;

import org.lwjgl.BufferUtils;

public class Gui implements IGui {
	
	private int x, y, width, height;
	private Colour bgColor = new Colour(.3f, .3f, .3f, .7f);
	private boolean draggedByMouse = false;
	private boolean minimized = false;
	private boolean maximized = false;
	@SuppressWarnings("unused")
	private boolean resizable = false;
	private List<GuiElement> elements = null;
	
	/*    render data        | render order
	 * 1. guiBackground      | 1.
	 * 2. guiDecoration      | 3.
	 * 3. containedElements  | 2.
	 * */
	protected FloatBuffer renderData;
	protected int backgroundFloatCount = 6*8;
	
	private final int bufferSize = 6*8+6*8*3;
	
	public Gui(int x, int y, int width, int height, boolean resizable, Colour bgColor) {
		this(x, y, width, height, resizable);
		this.bgColor = bgColor;
	}
	
	public Gui(int x, int y, int width, int height, boolean resizable) {
		this(x, y, width, height);
		this.resizable = resizable;
	}
	
	public Gui(int x, int y, int width, int height) { // GL_C3F_V3F
		this.x = x; this.y = y; this.width = width; this.height = height;
		this.elements = new ArrayList<GuiElement>();
	}
	
	/**Elements can only be added before setupRenderData() is executed.*/
	public void addElement(GuiElement e) {
		if(renderData != null) {
			return;
		}
		this.elements.add(e);
	}
	
	/**NOTE: buffer limit must be set to capacity and position set to 0!*/
	private void updateCoords(int offset, float x, float y, float width, float height) {
		renderData.put(offset+5, x).put(offset+6, y);
		renderData.put(offset+8+5, x).put(offset+8+6, y+height);
		renderData.put(offset+2*8+5, x+width).put(offset+2*8+6, y+height);
		
		renderData.put(offset+3*8+5, x+width).put(offset+3*8+6, y+height);
		renderData.put(offset+4*8+5, x+width).put(offset+4*8+6, y);
		renderData.put(offset+5*8+5, x).put(offset+5*8+6, y);
	}
	
	@SuppressWarnings("unused")
	/** NOTE: unfinished*/// TODO: not finished
	private void updateTexCoords(int offset, float u, float v, float width, float height) {
		renderData.put(offset, x).put(offset+1, y);
		renderData.put(offset+8, x).put(offset+8+1, y+height);
		renderData.put(offset+2*8, x+width).put(offset+2*8+1, y+height);
		
		renderData.put(offset+3*8, x+width).put(offset+3*8+1, y+height);
		renderData.put(offset+4*8, x+width).put(offset+4*8+1, y);
		renderData.put(offset+5*8, x).put(offset+5*8+1, y);
	}
	
	@SuppressWarnings("unused")
	private void updateColors(int offset, float x, float y, float width, float height) {
		renderData.put(offset+5, x).put(offset+6, y);
		renderData.put(offset+8+5, x).put(offset+8+6, y+height);
		renderData.put(offset+2*8+5, x+width).put(offset+2*8+6, y+height);
		
		renderData.put(offset+3*8+5, x+width).put(offset+3*8+6, y+height);
		renderData.put(offset+4*8+5, x+width).put(offset+4*8+6, y);
		renderData.put(offset+5*8+5, x).put(offset+5*8+6, y);
	}
	
	@SuppressWarnings("unused")
	/**@param v true to display "maximize" button, false to display "default size"*/
	private void switchDefaultMaximize(boolean v) {
		renderData.limit(bufferSize); // TODO: !
		renderData.position(0);
//		renderData.put(backgroundFloatCount, f)
		renderData.flip();
	}
	
	public void setupRenderData() {
		if(renderData != null) {
			return;
		}
		renderData = BufferUtils.createFloatBuffer(bufferSize); // TODO: dynamic buffer size
		renderData.put(0f).put(0f).put(bgColor.getRed()).put(bgColor.getGreen()).put(bgColor.getBlue()).put(this.x).put(this.y).put(1.99f);
		renderData.put(0f).put(0f).put(bgColor.getRed()).put(bgColor.getGreen()).put(bgColor.getBlue()).put(this.x).put(this.y+this.height).put(1.99f);
		renderData.put(0f).put(0f).put(bgColor.getRed()).put(bgColor.getGreen()).put(bgColor.getBlue()).put(this.x+this.width).put(this.y+this.height).put(1.99f);
		
		renderData.put(0f).put(0f).put(bgColor.getRed()).put(bgColor.getGreen()).put(bgColor.getBlue()).put(this.x+this.width).put(this.y+this.height).put(1.99f);
		renderData.put(0f).put(0f).put(bgColor.getRed()).put(bgColor.getGreen()).put(bgColor.getBlue()).put(this.x+this.width).put(this.y).put(1.99f);
		renderData.put(0f).put(0f).put(bgColor.getRed()).put(bgColor.getGreen()).put(bgColor.getBlue()).put(this.x).put(this.y).put(1.99f);
		
		// TODO: add decoration data
		renderData.put(GuiManager.getGuiDecor(this.x, this.y, this.width));
		
		
		for(int i=0;i<this.elements.size();i++) {
			renderData.put(this.elements.get(i).getRenderData());
		}

		renderData.flip();
	}
	
	public Gui setResizable(boolean v) {
		this.resizable = v;
		return this;
	}
	
	public void dragUpdate() {
		renderUpdate();
		if(this.draggedByMouse || true) {
			this.x += MouseHelper.dragLeft.mouseDeltaX;
			this.y -= MouseHelper.dragLeft.mouseDeltaY;
		}
	}
	
	public void renderInter() {
//		glEnable(GL_TEXTURE_2D);
//		glBindTexture(GL_TEXTURE_2D, TEXTURE_GUI_DECOR);
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
		
		
		glInterleavedArrays(GL_T2F_C3F_V3F, 4*8, this.renderData); // GL_C3F_V3F
		
		glDrawArrays(GL_TRIANGLES, 0, 6);
		
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		
		glBindTexture(GL_TEXTURE_2D, GuiManager.getTexGuiDecor());
		
		glDrawArrays(GL_TRIANGLES, 6, 18);
		
		glBindTexture(GL_TEXTURE_2D, 0);
		
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);
		
//		glBindTexture(GL_TEXTURE_2D, 0);
//		glDisable(GL_TEXTURE_2D);
//		renderGuiDecor();
	}
	
	public void renderUpdate() {
		renderData.limit(bufferSize); // TODO: !
		renderData.position(0);
//		renderData.put(5, this.x).put(6, this.y);
//		renderData.put(8+5, this.x).put(8+6, this.y+this.height);
//		renderData.put(2*8+5, this.x+this.width).put(2*8+6, this.y+this.height);
//		
//		renderData.put(3*8+5, this.x+this.width).put(3*8+6, this.y+this.height);
//		renderData.put(4*8+5, this.x+this.width).put(4*8+6, this.y);
//		renderData.put(5*8+5, this.x).put(5*8+6, this.y);
		
		updateCoords(0, this.x, this.y, this.width, this.height);
		
		
		updateCoords(6*8, this.x+this.width-GuiManager.dragBarHeight, this.y, GuiManager.dragBarHeight, GuiManager.dragBarHeight);
		updateCoords(6*8*2, this.x+this.width-2*GuiManager.dragBarHeight, this.y, GuiManager.dragBarHeight, GuiManager.dragBarHeight);
		updateCoords(6*8*3, this.x+this.width-3*GuiManager.dragBarHeight, this.y, GuiManager.dragBarHeight, GuiManager.dragBarHeight);
		
		renderData.flip();
	}
	
	public void render() {
		
	}
	
	public void addX(int v) {
		this.x += v;
	}
	
	public void addY(int v) {
		this.y += v;
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public Colour getBGColor() {
		return this.bgColor;
	}
	
	public boolean isBeingDraggedByMouse() {
		return this.draggedByMouse;
	}
	
	public void setBeingDragged(boolean dragged) {
		this.draggedByMouse = dragged;
	}
	
	public boolean pointInsideDragBar(int x, int y) {
		return this.x <= x && x < this.x + this.width && this.y <= y && y < this.y + GuiManager.dragBarHeight;
	}
	
	public boolean pointInsideGui(int x, int y) {
		return this.x <= x && x < this.x + this.width && this.y <= y && y < this.y + this.height;
	}
	
	public void setMinimized(boolean v) {
		this.minimized = v;
	}
	
	public void setMaximized(boolean v) {
		this.maximized = v;
	}
	
	public boolean isMinimized() {
		return this.minimized;
	}
	
	public boolean isMaximized() {
		return this.maximized;
	}

}
