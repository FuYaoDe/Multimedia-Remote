package Server;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class ControlWMP {
	public static boolean isSlow,isFast;
	
	public ControlWMP(){ //建構子
	} 
	
	/*------------------------------------------------------------
	 * 模擬鍵盤的Ctrl+T
	 *------------------------------------------------------------*/
	public void Repick(){ // 重複播放
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	/*------------------------------------------------------------
	 * 模擬鍵盤的Ctrl+H
	 *------------------------------------------------------------*/
	public void Random(){ // 隨機播放
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_H);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_H);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	/*------------------------------------------------------------
	 * 模擬鍵盤的Ctrl+S
	 *------------------------------------------------------------*/
	public void StopPlay(){ // 停止播放
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_S);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	/*------------------------------------------------------------
	 * 模擬鍵盤的Ctrl+Shift+S
	 *------------------------------------------------------------*/
	public void SlowPlay(){ // 慢速播放
		try {
			if(isFast){ //目前是快速播放
				isFast = false;
				NormalPlay();
			}else{
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_S);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				robot.keyRelease(KeyEvent.VK_S);
				isSlow = true;
			}
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/*------------------------------------------------------------
	 * 模擬鍵盤的Ctrl+Shift+G
	 *------------------------------------------------------------*/
	public void FastPlay(){ // 快速播放
		try {
			if(isSlow){ //目前是慢速播放
				isSlow = false;
				NormalPlay();
			}else{
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_G);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				robot.keyRelease(KeyEvent.VK_G);
				isFast = true;
			}
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	/*------------------------------------------------------------
	 * 模擬鍵盤的Ctrl+Shift+N
	 *------------------------------------------------------------*/
	public void NormalPlay(){ // 正常播放速度
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_N);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			robot.keyRelease(KeyEvent.VK_N);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	/*------------------------------------------------------------
	 * 模擬鍵盤的ALT+F4
	 *------------------------------------------------------------*/
	public void Close(){ //
		try {
			Runtime.getRuntime().exec("taskkill.exe /F /IM wmplayer.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*------------------------------------------------------------
	 * 模擬鍵盤的F7
	 *------------------------------------------------------------*/
	public void Mute(){ // 靜音/取消靜音
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_F7);
			robot.keyRelease(KeyEvent.VK_F7);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/*------------------------------------------------------------
	 * 模擬鍵盤的F9
	 *------------------------------------------------------------*/
	public void VolumeIncrease(){ // 增加音量
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_F9);
			robot.keyRelease(KeyEvent.VK_F9);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/*------------------------------------------------------------
	 * 模擬鍵盤的F8
	 *------------------------------------------------------------*/
	public void VolumeReduce(){ // 降低音量
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_F8);
			robot.keyRelease(KeyEvent.VK_F8);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/*------------------------------------------------------------
	 * 模擬鍵盤的CTRL+P
	 *------------------------------------------------------------*/
	public void Pause(){ // 繼續或暫停
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_P);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_P);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/*------------------------------------------------------------
	 * 模擬鍵盤的ALT+ENTER
	 *------------------------------------------------------------*/
	public void fullScreen(){ //全畫面-取消全畫面
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	

	/*------------------------------------------------------------
	 * 模擬鍵盤的CTRL+B
	 *------------------------------------------------------------*/
	public void PageUP(){ // 上一首
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_B);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_B);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/*------------------------------------------------------------
	 * 模擬鍵盤的CTRL+F
	 *------------------------------------------------------------*/
	public void PageDown(){ // 下一首
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_F);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_F);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
}
