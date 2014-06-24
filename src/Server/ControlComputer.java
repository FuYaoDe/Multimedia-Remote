package Server;

public class ControlComputer {
	
	public ControlComputer(){ //建構子
	} 
	/*------------------------------------------------------------
	 * 模擬鍵盤的ALT+DOWN
	 *------------------------------------------------------------*/
	public void sleep(){ // 休眠
		try{
			Runtime.getRuntime().exec("rundll32.exe powrprof.dll SetSuspendState");  //用於執行DOS指令
		}catch(Exception e){}
	}
	
	/*------------------------------------------------------------
	 * 模擬鍵盤的ALT+UP
	 *------------------------------------------------------------*/
	public void reset(){ // 重新開機
		try{
			Runtime.getRuntime().exec("shutdown.exe -f -r -t 0");  //用於執行DOS指令
		}catch(Exception e){}
	}
	
	/*------------------------------------------------------------
	 * 模擬鍵盤的ALT+F4
	 *------------------------------------------------------------*/
	public void powerOff(){ // 關機
		try{
			Runtime.getRuntime().exec("shutdown.exe -f -s -t 0");  //用於執行DOS指令
		}catch(Exception e){}
	}

}
