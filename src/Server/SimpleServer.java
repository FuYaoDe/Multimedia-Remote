package Server;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

import javax.swing.InputMap;

public class SimpleServer {
	ServerSocket ss;
	SimpleServer server;
	Thread thread;

	// 建構函式
	public SimpleServer() { }

	public void setServerPort(int port){
		server = new SimpleServer(); 
		System.out.println("監聽PORT："+port);
		server.startServer(port);  //port 8888
	}

	private void startServer(int port) {
		try {
			ss = new ServerSocket(port);
			// 執行緒
			thread = new Thread(new ClientThread(ss));
			thread.start();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

// 執行緒
class ClientThread implements Runnable {
	private ServerSocket ss;
	private Socket cs;
	DataInputStream  in;
	DataOutputStream out;
	BufferedReader br ;
	Log frame = new Log();
	// 建構函式
	public ClientThread(ServerSocket ss) throws Exception {
		this.ss = ss;
		frame.setVisible(true);
	}

	private String getTime(){
		// 格式化
		SimpleDateFormat nowdate = 
				//new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				new java.text.SimpleDateFormat("HH:mm:ss"); 

		// GMT標準時間往後加八小時
		nowdate.setTimeZone(TimeZone.getTimeZone("GMT+8"));

		// 取得目前時間
		String sdate = nowdate.format(new java.util.Date());
		return sdate;
	}

	private void setLogViewText(String msg ,int flag){
		if(flag==1){
			System.out.println(msg+" @"+getTime());
			frame.textPane.setText(
					"\nClient Log： \"" // 訊息前綴
					+msg				// 訊息本體
					+"\""				
					+" @"+getTime()			// 加上時間
					+"\n-------------------------------------------------------------"
					+frame.textPane.getText().replace("-------------------------------------------------------------", ""));			
		}else if (flag==2){
			System.out.println(msg+" @"+getTime());
			frame.textPane.setText(
					"\nServer Log： \"" // 訊息前綴
					+msg				// 訊息本體
					+"\""				
					+" @"+getTime()			// 加上時間
					+"\n-------------------------------------------------------------"
					+frame.textPane.getText().replace("-------------------------------------------------------------", ""));				
		}

	}

	public static String StringtoUnicode(String str) {
		char[] arChar = str.toCharArray();
		int iValue = 0;
		String uStr = "";
		for (int i = 0; i < arChar.length; i++) {
			iValue = (int) str.charAt(i);
			if (iValue <= 256) {
				uStr += "\\u00" + Integer.toHexString(iValue);
			} else {
				uStr += "\\u" + Integer.toHexString(iValue);
			}
		}
		return uStr;
	}

	public static String unicodeToString(String str) {

		if(str!=null){

			Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");    
			Matcher matcher = pattern.matcher(str);
			char ch;
			while (matcher.find()) {
				ch = (char) Integer.parseInt(matcher.group(2), 16);	
				str = str.replace(matcher.group(1), ch + "");    
			}
		}else {

			str="null";

		}

		return str;
	}

	public void run() {
		try {
			while(true) {
				// 接受用戶端的連線請求
				cs = ss.accept();

				// 建立用戶端的輸入串流				
				in  = new DataInputStream (cs.getInputStream());
				// 建立用戶端的輸出串流
				out = new DataOutputStream(cs.getOutputStream());

				br = new BufferedReader(new InputStreamReader(cs.getInputStream()));

				String  line = null;

				// 只在socket有連線狀態才讀取
				if(!cs.isClosed()) {
					line = br.readLine();
				}

				// 輸出未解碼訊息至主控台/log視窗
				//setLogViewText(line);

				// 至客戶端接收的資料
				String FromClient = unicodeToString(line);
				setLogViewText(FromClient,1);

				// 自我回傳
				//out.writeBytes(StringtoUnicode(FromClient));

				ss.getInetAddress();

				ControlWMP wmp = new ControlWMP();
				ControlPPT ppt = new ControlPPT();
				ControlComputer cc = new ControlComputer();
				Folder f = new Folder();
				//電腦控制部分

				if(!(FromClient.equalsIgnoreCase("MRCode_Return")||
						FromClient.equalsIgnoreCase("MRCode_Show_Music")||
						FromClient.equalsIgnoreCase("MRCode_Show_Videos")||
						FromClient.equalsIgnoreCase("MRCode_Show_Documents"))){
					if(line!=null && !line.equals(""))
						out.writeBytes(line);
					//						out.writeBytes(StringtoUnicode("OK"));
				}


				if(FromClient.equalsIgnoreCase("connect")) {
					//					out.writeBytes(StringtoUnicode("connect"));//開始連
					setLogViewText("發現客戶端！",1);
				}

				else if(FromClient.equalsIgnoreCase("MRCode_CC_00")) cc.sleep(); //休眠
				else if(FromClient.equalsIgnoreCase("MRCode_CC_01")) cc.reset(); //重新開機
				else if(FromClient.equalsIgnoreCase("MRCode_CC_02")) cc.powerOff(); //關機

				//				//WMP控制部分
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_00")) wmp.Close(); //關閉播放器
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_01")) wmp.Random(); //隨機播放
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_02")) wmp.Repick(); //重複播放
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_10")) wmp.Pause(); //繼續或暫停
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_11")) wmp.fullScreen(); //全畫面-取消全畫面
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_12")) wmp.PageUP(); //上一首
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_13")) wmp.PageDown(); //下一首
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_14")) wmp.Mute(); //靜音-取消靜音
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_15")) wmp.VolumeIncrease(); //增加音量
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_16")) wmp.VolumeReduce(); //降低音量
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_17")) wmp.FastPlay(); //加速播放
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_18")) wmp.SlowPlay(); //減速播放
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_19")) wmp.StopPlay(); //停止播放

				//PPT控制部分
				else if(FromClient.equalsIgnoreCase("MRCode_PPT_00")) ppt.Close(); //關閉簡報
				else if(FromClient.equalsIgnoreCase("MRCode_PPT_10")) ppt.Slide(); //進入投影片模式
				else if(FromClient.equalsIgnoreCase("MRCode_PPT_11")) ppt.Esc(); //結束簡報 ESC
				else if(FromClient.equalsIgnoreCase("MRCode_PPT_12")) ppt.PageUP(); //上一頁
				else if(FromClient.equalsIgnoreCase("MRCode_PPT_13")) ppt.PageDown(); //下一頁
				else if(FromClient.equalsIgnoreCase("MRCode_PPT_14")) ppt.VolumeIncrease(); //增加音量
				else if(FromClient.equalsIgnoreCase("MRCode_PPT_15")) ppt.VolumeReduce(); //降低音量

				//接收到客戶端廣播欲取得伺服端IP資料
				else if(FromClient.equalsIgnoreCase("MRCode_Return")) {
					ss.getInetAddress();
					out.writeUTF(InetAddress.getLocalHost().getHostAddress());
					//				
					//客戶端要求資料夾內含的檔案資訊傳輸
				} else if(FromClient.equalsIgnoreCase("MRCode_Show_Music")) 
					//					out.writeBytes(f.FolderSelect(1));  //傳回音樂資料夾檔案
				{
					out.writeBytes(StringtoUnicode(f.FolderSelect(1))); 	 //傳回音樂資料夾檔案
					//					frame.textPane.setText(f.FolderSelect(1)+frame.textPane.getText());
					setLogViewText(f.FolderSelect(1),2);
				}
				else if(FromClient.equalsIgnoreCase("MRCode_Show_Videos")) 
					//					out.writeBytes(f.FolderSelect(2));  //傳回影片資料夾檔案
				{
					out.writeBytes(StringtoUnicode(f.FolderSelect(2)));  	//傳回影片資料夾檔案
					//					frame.textPane.setText(f.FolderSelect(2)+frame.textPane.getText());
					setLogViewText(f.FolderSelect(2),2);
				}
				else if(FromClient.equalsIgnoreCase("MRCode_Show_Documents")) 
					//					out.writeBytes(f.FolderSelect(3));  //傳回簡報資料夾檔案
				{
					out.writeBytes(StringtoUnicode(f.FolderSelect(3)));  	//傳回簡報資料夾檔案
					//					frame.textPane.setText(f.FolderSelect(3)+frame.textPane.getText());
					setLogViewText(f.FolderSelect(3),2);
				}
				// 客戶端送來'null'
				else if(FromClient.equalsIgnoreCase("null")){

					setLogViewText("客戶端送來 \"null\", 請檢查網路連線",1);

				}
				//客戶端要求開啟檔案
				else
				{
					System.out.println(f.Strat_File(FromClient.trim()));
					frame.textPane.setText(f.Strat_File(FromClient.trim()) + frame.textPane.getText());
					int Run_ok = f.Strat_File(FromClient.trim());
					if(Run_ok==1){
						out.writeBytes(StringtoUnicode("open_okay//s"));
						setLogViewText("執行檔案成功！",2);
					}else if(Run_ok==-1){
						out.writeBytes(StringtoUnicode("open_failed//s"));
						setLogViewText("執行檔案失敗！",2);
					}else{
						out.writeBytes(StringtoUnicode("open_cmd_error//s"));
						setLogViewText("錯誤的指令！",2);
					}
				}

				//if(!FromClient.equalsIgnoreCase("Connect")) out.writeBytes(StringtoUnicode(FromClient));//送回指令
			}
		} catch (Exception e) {
			e.printStackTrace();//列印異常資訊
			setLogViewText("錯誤:\n"+e.getMessage(),2);
		} finally {//用finally語句塊確保動作執行
			try{
				if(in != null){
					in.close();//關閉輸入串流
				}
				if(out != null){
					out.close();//關閉輸入串流
				}
				if(cs != null){
					cs.close();//關閉Socket連接
				}
			}
			catch(Exception e){
				e.printStackTrace();//列印異常資訊
				setLogViewText("錯誤:\n"+e.getMessage(),2);
			}
		}
	}

}
