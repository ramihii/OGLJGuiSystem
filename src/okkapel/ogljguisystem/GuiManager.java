package okkapel.ogljguisystem;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import okkapel.kkplglutil.rendering.GLHandler;
import okkapel.kkplglutil.rendering.GLRenderMethod;
import okkapel.kkplglutil.rendering.RenderBufferGenerator;
import okkapel.kkplglutil.util.Texture;
import okkapel.ogljguisystem.util.MouseHelper;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL15;

import static org.lwjgl.opengl.GL11.*;

public class GuiManager {
	
	public static final int dragBarHeight = 25;
	
	private static Texture TEXTURE_GUI_DECOR = null;
	private static int WINDOW_WIDTH = -1;
	private static int WINDOW_HEIGHT = -1;
	
	
	public static int getWindowWidth() {
		return WINDOW_WIDTH;
	}
	
	public static int getWindowHeight() {
		return WINDOW_HEIGHT;
	}
	
	public static Texture getTexGuiDecor() {
		return TEXTURE_GUI_DECOR;
	}
	
	/**Note: active guis are normally displayed guis or minimized.*/
	public List<Gui> activeGuis;
	private int draggedGuiId = -1;
	
	public static void init(Texture gui_decor_tex, int window_width, int window_height) {
		TEXTURE_GUI_DECOR = gui_decor_tex;
		WINDOW_WIDTH = window_width;
		WINDOW_HEIGHT = window_height;
	}
	
	/**THIS HAS TO BE CALLED EVERY TIME THE GAME WINDOW IS RESIZED OR GUIS WILL START TO BREAK!*/
	public static void onResize(int newWindowWidth, int newWindowHeight) {
		WINDOW_WIDTH = newWindowWidth;
		WINDOW_HEIGHT = newWindowHeight;
	}
	
	public GuiManager() {
		activeGuis = new ArrayList<Gui>(0);
	}
	
	public Gui createGui(int x, int y, int width, int height) {
		RenderBufferGenerator rbg = RenderBufferGenerator.INSTANCE;
		addGuiDecoration(rbg, x, y, width, height);
		Gui ret = new Gui(0, 0, width, height);
		ret.setupRendering(rbg);
		int vcount = rbg.getVertexCount();
		System.out.println("Texdstgss: " + vcount);
		ret.guiRptr = GLHandler.createROBJ(rbg.createBuffer(), GL15.GL_DYNAMIC_DRAW, TEXTURE_GUI_DECOR, vcount, GLRenderMethod.VERTEX_BUFFER_OBJECT);
		return ret;
	}
	
	public void addGuiDecoration(RenderBufferGenerator rbg, int x, int y, int width, int height) {
		rbg.addRect2D(x+width-dragBarHeight, y, x+width, y+dragBarHeight, 1f, 1f, 1f, 1f, 1f, 0.75f, 1f, 1f, 0f);
		rbg.addRect2D(x+width-dragBarHeight*2, y, x+width-dragBarHeight, y+dragBarHeight, 1f, 1f, 1f, 1f, 1f, 0f, 1f, 0.25f, 0f);
		rbg.addRect2D(x+width-dragBarHeight*3, y, x+width-dragBarHeight*2, y+dragBarHeight, 1f, 1f, 1f, 1f, 1f, 0.5f, 1f, 0.75f, 0f);
	}
	
	public void renderGuis() {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		Gui iter = null;
		
		for(int i=activeGuis.size()-1;i>-1;i--) {
			iter = activeGuis.get(i);
			GLHandler.renderRendPtr(iter.guiRptr); // TODO: allow custom textures
			
			GLHandler.renderRPTRPartiallyWOTex(iter.guiRptr, 18, 6);
			GLHandler.renderRPTRPartially(iter.guiRptr, 0, 18);
		}
		
		glDisable(GL_BLEND);
	}
	
	private boolean leftWasDown = false;
	private boolean middleWasDown = false;
	private boolean rightWasDown = false;
	
	public void mouseUpdate() {
		// LEFT
		if(leftWasDown) {
			if(!MouseHelper.dragLeft.isButtonDown()) {
				mouseUp(0);
				leftWasDown = false;
			}
		} else {
			if(MouseHelper.dragLeft.isButtonDown()) {
				mouseDown(0);
				leftWasDown = true;
			}
		}
		
		// MIDDLE
		if(middleWasDown) {
			if(!MouseHelper.dragMiddle.isButtonDown()) {
				mouseUp(2);
				middleWasDown = false;
			}
		} else {
			if(MouseHelper.dragMiddle.isButtonDown()) {
				mouseDown(2);
				middleWasDown = true;
			}
		}
		
		// RIGHT
		if(rightWasDown) {
			if(!MouseHelper.dragRight.isButtonDown()) {
				mouseUp(1);
				rightWasDown = false;
			}
		} else {
			if(MouseHelper.dragRight.isButtonDown()) {
				mouseDown(1);
				rightWasDown = true;
			}
		}
		if(MouseHelper.dragLeft.mouseDeltaX != 0 || MouseHelper.dragLeft.mouseDeltaY != 0) {
			mouseDrag(0);
		}
		if(MouseHelper.dragMiddle.mouseDeltaX != 0 || MouseHelper.dragMiddle.mouseDeltaY != 0) {
			mouseDrag(2);
		}
		if(MouseHelper.dragRight.mouseDeltaX != 0 || MouseHelper.dragRight.mouseDeltaY != 0) {
			mouseDrag(1);
		}
	}
	
	/**0 = left, 1 = right, 2 = middle*/
	private void mouseDrag(int button) {
		if(button == 0 && draggedGuiId != -1) {
			this.activeGuis.get(draggedGuiId).dragUpdate();
		}
	}
	
	/**0 = left, 1 = right, 2 = middle*/
	private void mouseUp(int button) {
		if(button == 0) {
			if(draggedGuiId != -1) {
				this.activeGuis.get(this.draggedGuiId).renderUpdate();
			}
			this.draggedGuiId = -1;
		}
	}
	
	/**0 = left, 1 = right, 2 = middle*/
	private void mouseDown(int button) {
		if(button == 0) {
			for(int i=0; i < this.activeGuis.size(); i++) {
				if(this.activeGuis.get(i).pointInsideGui(Mouse.getX(), WINDOW_HEIGHT-Mouse.getY())) {
					if(i != 0) {
						Gui buffer = this.activeGuis.get(i);
						this.activeGuis.add(0, buffer);
						this.activeGuis.remove(i+1);
					}
					if(this.activeGuis.get(0).pointInsideDragBar(Mouse.getX(), WINDOW_HEIGHT-Mouse.getY())) {
						this.draggedGuiId = 0;
					}
					break;
				}
			}
		}
	}
	
	public void openGui(Gui gui) {
		this.draggedGuiId++;
		this.activeGuis.add(0, gui);
	}
	
	public void closeGui(int id) {
		if(id > -1 && id < this.activeGuis.size()) {
			this.activeGuis.remove(id);
			if(this.draggedGuiId > id) {
				this.draggedGuiId--;
			}
		}
	}
}
