package okkapel.ogljguisystem.util;

import org.lwjgl.input.Mouse;

public class MouseHelper {
	
	public static MouseDrag dragLeft = new MouseDrag(0);
	public static MouseDrag dragRight = new MouseDrag(1);
	public static MouseDrag dragMiddle = new MouseDrag(2);
	
	public static void update() {
		dragLeft.update(); dragRight.update(); dragMiddle.update();
	}
	
	
	public static class MouseDrag {
		private boolean isMouseDown = false;
		public int mouseDeltaX, mouseDeltaY = 0;
		private int mouseLastPosX, mouseLastPosY = 0;
		private int mouseNowX, mouseNowY;
		public int dragStartX=0, dragStartY=0;
		
		private final int button;
		
		public MouseDrag(int button) {
			this.button = button;
		}
		
		public boolean isButtonDown() {
			return this.isMouseDown;
		}
		
		public void update() {
			if(this.isMouseDown) {
				if(!Mouse.isButtonDown(this.button)) {
					this.isMouseDown = false;
					this.mouseDeltaX = 0; this.mouseDeltaY = 0;
					dragStartX = 0; dragStartY = 0;
				} else {
					this.mouseNowX = Mouse.getX(); this.mouseNowY = Mouse.getY();
					this.mouseDeltaX = this.mouseNowX-this.mouseLastPosX; this.mouseDeltaY = this.mouseNowY-this.mouseLastPosY;
					this.mouseLastPosX = this.mouseNowX; this.mouseLastPosY = this.mouseNowY;
				}
			} else {
				if(Mouse.isButtonDown(this.button)) {
					this.isMouseDown = true;
					this.mouseLastPosX = Mouse.getX(); this.mouseLastPosY = Mouse.getY();
					dragStartX = this.mouseLastPosX; dragStartY = this.mouseLastPosY;
				}
			}
		}
	}
}
