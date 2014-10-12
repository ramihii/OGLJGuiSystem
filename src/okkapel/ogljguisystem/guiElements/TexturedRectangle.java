package okkapel.ogljguisystem.guiElements;

import okkapel.kkplglutil.rendering.RenderBufferGenerator;
import okkapel.kkplglutil.rendering.RenderSectionGenerator;
import okkapel.kkplglutil.util.Colour;
import okkapel.kkplglutil.util.TexIcon;
import okkapel.kkplglutil.util.Texture;
import okkapel.ogljguisystem.GuiElement;

public class TexturedRectangle extends GuiElement {

	private TexIcon icon;
	private Colour color;
	
	public TexturedRectangle(TexIcon icon, Colour color) {
		this.icon = icon;
		this.color = color;
	}
	
	@Override
	protected void setupRendering(RenderSectionGenerator rsg) {
		rsg.addRectWUV(x, y, width, height, icon, color);
	}

}
