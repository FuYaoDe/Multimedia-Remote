package Server;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class ControlPPT {
	
	public ControlPPT(){ //建構子
	} 
	/*------------------------------------------------------------
	 * 模擬鍵盤的ALT+DOWN
	 *------------------------------------------------------------*/
	public void VolumeReduce(){ // 降低音量
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_DOWN);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	/*------------------------------------------------------------
	 * 模擬鍵盤的ALT+UP
	 *------------------------------------------------------------*/
	public void VolumeIncrease(){ // 增加音量
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_UP);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_UP);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	/*------------------------------------------------------------
	 * 模擬鍵盤的ALT+F4
	 *------------------------------------------------------------*/
	public void Close(){ // 關閉簡報
		try {
			Runtime.getRuntime().exec("taskkill.exe /F /IM POWERPNT.EXE");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*------------------------------------------------------------
	 * 模擬鍵盤的F5
	 *------------------------------------------------------------*/
	public void Slide(){ // 進入投影片模式
		try {

			System.out.println("robot.keyPress");
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_F5);
			robot.keyRelease(KeyEvent.VK_F5);
			robot.keyPress(KeyEvent.VK_F5);
			robot.keyRelease(KeyEvent.VK_F5);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/*------------------------------------------------------------
	 * 模擬鍵盤的ESC
	 *------------------------------------------------------------*/
	public void Esc(){ // 結束簡報 ESC
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ESCAPE);
			robot.keyRelease(KeyEvent.VK_ESCAPE);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/*------------------------------------------------------------
	 * 模擬鍵盤的PageUP
	 *------------------------------------------------------------*/
	public void PageUP(){ // 上一頁
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_PAGE_UP);
			robot.keyRelease(KeyEvent.VK_PAGE_UP);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/*------------------------------------------------------------
	 * 模擬鍵盤的PageDown
	 *------------------------------------------------------------*/
	public void PageDown(){ // 下一頁
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
}
