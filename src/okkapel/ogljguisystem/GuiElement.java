package okkapel.ogljguisystem;

import java.util.ArrayList;
import java.util.List;

import okkapel.kkplglutil.rendering.GLRenderObj;
import okkapel.kkplglutil.rendering.GLRenderObjPointer;
import okkapel.kkplglutil.rendering.RenderBufferGenerator;
import okkapel.kkplglutil.rendering.RenderSection;
import okkapel.kkplglutil.rendering.RenderSectionGenerator;
import okkapel.kkplglutil.rendering.util.RRect;

public abstract class GuiElement {
	
	protected List<GuiElement> containedElements = null;
	
	protected List<RRect> renderData = null;
	
	/** The x and y coordinates inside the gui, offsets from the top-left corner of the gui */
	protected int x, y; 
	protected int width, height;
	
	protected boolean hasChanged = false;
	
	protected int renderBufferOffset, renderBufferLength;
	
	protected Gui ownerGui;
	
	protected RenderSection rsect;
	
	public GuiElement(/*GuiElement parent*/) {
//		this.parent = parent;
		containedElements = new ArrayList<GuiElement>();
	}
	
	protected abstract void setupRendering(RenderSectionGenerator rsg);
	
	protected void updateRendering(int x, int y, GLRenderObj robj) {
		for(int i=0;i<renderData.size();i++) {
			renderData.get(i).renderDataUpdate(x, y, robj);
		}
		for(int i=0;i<containedElements.size();i++) {
			containedElements.get(i).updateRendering(x + this.x, y + this.y, robj); // D: recursion
		}
	}
	
	protected void renderGuiElement(GLRenderObjPointer rptr) {
		rsect.renderSection(rptr);
	}
	
	public boolean getHasChanged() {
		return this.hasChanged;
	}
	
	public void addElement(GuiElement elem) {
		containedElements.add(elem);
	}
	
	public void setHasChanged(boolean value) {
		this.hasChanged = value;
	}
	
}
