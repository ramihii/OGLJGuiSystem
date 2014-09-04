package okkapel.ogljguisystem;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiElement {
	
	private List<Float> renderData = new ArrayList<Float>(); 
	
	protected float x, y, width, height;
	protected boolean hasChanged = false;
	
	public abstract void setupRendering();
	
	// TODO: change return type
	public float[] getRenderData() {
		float[] ret = new float[renderData.size()];
		for(int i=0;i<ret.length;i++) {
			ret[i] = renderData.get(i).floatValue();
		}
		return ret;
	}
	
	public boolean getHasChanged() {
		return this.hasChanged;
	}
	
	public void setHasChanged(boolean value) {
		this.hasChanged = value;
	}
	
	public void addText(String text) {}
	
	public void addColoredSquare(float x, float y, float width, float height) {
		
	}
}
