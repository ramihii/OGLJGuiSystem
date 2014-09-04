OGLJGuiSystem.jar

What you need to know to use OGLJGuiSystem:
	-You need to load the texture "gui_decoration.png"
	-You need to execute GuiManager.init(<Gui Decoration Texture>, <Window Width>, <Window Height>)
	-Execute method okkapel.ogljguisystem.util.MouseHelper.update() on every frame
	-Execute method GuiManager.mouseUpdate() on every frame (after ...MouseHelper.update())
	-In order to render the guis, execute method GuiManager.renderGuis()
	
Features to be implemented soon:
	-Gui buttons(closing, minimizing, maximizing the guis)
	-Resizing guis
