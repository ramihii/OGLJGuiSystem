package okkapel.ogljguisystem;


import okkapel.kkplglutil.rendering.GLRenderObj;
import okkapel.kkplglutil.rendering.GLRenderObjPointer;
import okkapel.kkplglutil.rendering.RenderBufferGenerator;
import okkapel.kkplglutil.util.Colour;
import okkapel.kkplglutil.util.Texture;
import okkapel.ogljguisystem.util.MouseHelper;


public class Gui extends GuiElement implements IGui {
	
	private int x, y, width, height;
	private Colour bgColor = new Colour(.5f, .3f, .5f, .7f);
	private boolean draggedByMouse = false;
	private boolean minimized = false;
	private boolean maximized = false;
	
	private boolean resizable = false;
	
	protected int backgroundFloatCount = 6*8;
	
	private final int bufferSize = 6*8+6*8*3;
	
	private Texture guiSpecificTexture;
	
	protected GLRenderObjPointer guiRptr;
	
	public Gui(int x, int y, int width, int height, boolean resizable, Colour bgColor) {
		this(x, y, width, height, resizable);
		this.bgColor = bgColor;
	}
	
	public Gui(int x, int y, int width, int height, boolean resizable) {
		this(x, y, width, height);
		this.resizable = resizable;
	}
	
	public Gui(int x, int y, int width, int height) {
		this.x = x; this.y = y; this.width = width; this.height = height;
	}
	
//	/** Elements can only be added before setupRenderData() is called. */
//	public void addElement(GuiElement e) {}
	
	public Gui setResizable(boolean v) {
		this.resizable = v;
		return this;
	}
	
	public void dragUpdate() {
		// render update
		if(this.draggedByMouse || true) {
			this.x += MouseHelper.dragLeft.mouseDeltaX;
			this.y -= MouseHelper.dragLeft.mouseDeltaY;
		}
	}
	
	/** @return false if there's no custom rendering, you need to override this if you have untextured things in the render buffer */
	protected boolean renderGui() {
		return false;
	}

	@Override
	protected void setupRendering(RenderBufferGenerator rbg) {
		rbg.addRect2D(this.x, this.y, this.x+this.width, this.y+this.height, 1f, this.bgColor.getRed(), this.bgColor.getGreen(), this.bgColor.getBlue(), this.bgColor.getAlpha(), 0f, 0f, 0f, 0f);
		
	}
	
	/** Expecting that the gui's coordinates have already been updated. All arguments passed to this method are ignored. */
	@Override
	protected void updateRendering(int x, int y, GLRenderObj robj) {
		for(int i=0;i<this.containedElements.size();i++) {
			containedElements.get(i).updateRendering(this.x, this.y, guiRptr.getRenderObj());
		}
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
