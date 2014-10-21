package Server;

import java.io.File;

public class Folder { //用於取得資料夾內的檔案
	private File root; //File型態物件的root(空)
	GUI_Main GUI = new GUI_Main();
	public String Video_File;
	public String Music_File;
	public String Point_File;
	public Folder(){ } //建構子
	
	/*------------------------------------------------------------
	 * 此方法用於回傳指定資料夾底下所有非資料夾形式的檔案
	 * sel 
	 * 1 : 音樂資料夾
	 * 2 : 影片資料夾
	 * 3 : 簡報資料夾
	 * 回傳一個字串形式的資料，每個檔案以//s進行分割
	 *------------------------------------------------------------*/
	public String FolderSelect(int sel) {
		String File = null;
		switch(sel){ //取得輸入
			case 1: //指定音樂資料夾
				root = new File(GUI.getMusicPath());
				File = printFolder(root,1);
				//File = Music_File;
				break;
			case 2: //指定影片資料夾
				root = new File(GUI.getVideoPath());
				File = printFolder(root,2); 
				//File = Video_File;
				break;
			case 3: //指定簡報資料夾
				root = new File(GUI.getPointPath());
				File = printFolder(root,3); 
				//File = Point_File;
				break;
		}
		if(File==null){
		System.out.print("no file");
			return "NoFile//s";
		}
		else
			return File;
	}
	/*------------------------------------------------------------
	 * 此方法用於以特定程式執行指定資料夾底下的指定檔案
	 * 接收字串型態的Run_Files 
	 * 以//s進行分割
	 * 第一個分割存放這個檔案要用哪種方式執行
	 * MRCode_Run_Music : 執行音樂檔
	 * MRCode_Run_Videos : 執行影片檔
	 * MRCode_Run_Documents : 執行簡報檔
	 * 第二個以後的則為要執行的檔案名稱。
	 * 回傳一個整數形式的資料，0代表錯誤的指令 1代表執行成功 -1代表執行失敗
	 *------------------------------------------------------------*/
	public int Strat_File(String Run_Files){
		String winPath =  System.getenv("WINDIR");
		String ppt_exe[] = {
				"\\Program Files\\Microsoft Office\\OFFICE11\\POWERPNT.EXE ",
				"\\Program Files\\Microsoft Office\\OFFICE12\\POWERPNT.EXE ",
				"\\Program Files\\Microsoft Office\\OFFICE14\\POWERPNT.EXE ",
				"\\Program Files (x86)\\Microsoft Office\\OFFICE11\\POWERPNT.EXE ",
				"\\Program Files (x86)\\Microsoft Office\\OFFICE12\\POWERPNT.EXE ",
				"\\Program Files (x86)\\Microsoft Office\\OFFICE14\\POWERPNT.EXE ",
				"\\Program Files (x86)\\Microsoft Office\\Office15\\POWERPNT.EXE ",
		};
		File myPnt;
		String Full_File;
		String Full_File_WMP="\\Program Files\\Windows Media Player\\wmplayer.exe ";
		String Full_File_WMP2="\\Program Files (x86)\\Windows Media Player\\wmplayer.exe ";
		String Full_File_PPT="\\Program Files\\Microsoft Office\\OFFICE14\\POWERPNT.EXE ";
		
		try {
			String Run_File[] = Run_Files.split("//s");
			myPnt = new File(winPath.substring(0, 2)+Full_File_WMP);
			if(myPnt.exists()){ //撥放器存在
				Full_File_WMP = "\""+winPath.substring(0, 2)+Full_File_WMP+"\"";
			}else{
				myPnt = new File(winPath.substring(0, 2)+Full_File_WMP2);
				if(myPnt.exists()){ //撥放器存在
					Full_File_WMP = "\""+winPath.substring(0, 2)+Full_File_WMP2+"\"";
				}else{
					Full_File_WMP = "";
				}
			}
			boolean Have_PPT = false;
			for(int num=0; num<ppt_exe.length; num++ ){
				myPnt = new File(winPath.substring(0, 2)+ppt_exe[num]);
				if(myPnt.exists()){ //PPT存在
					System.out.println("PPT EXIST");
					Have_PPT = true;
					Full_File_PPT ="\""+ winPath.substring(0, 2)+ppt_exe[num] +"\"";
					break;
				}
			}
			if(!Have_PPT){ //PPT不存在
				Full_File_PPT = "";
				System.out.println("PPT NOT EXIST");
			}
//			System.out.printf("Run_File=%s",Run_File[0]);
//			System.out.printf("Run_File=%s",Run_File[1]);
			if(Run_File[0].equalsIgnoreCase("MRCode_Run_Music")){ //執行音樂資料夾檔案
				if(Full_File_WMP.isEmpty()) return 0;
				for(int i=1; i < Run_File.length; i++)
					Full_File_WMP += "\""+GUI.getMusicPath()+Run_File[i]+"\" ";
					//加上""是為了避免空白或其他因素導致開啟檔案失敗
				Full_File = Full_File_WMP;
			}else if(Run_File[0].equalsIgnoreCase("MRCode_Run_Videos")){//執行影片資料夾檔案
				if(Full_File_WMP.isEmpty()) return 0;
				Full_File_WMP += "\""+GUI.getVideoPath()+Run_File[1]+"\"";
				Full_File = Full_File_WMP;
			}else if(Run_File[0].equalsIgnoreCase("MRCode_Run_Documents")){ //執行PPT資料夾檔案

				System.out.println("MRCode_Run_Documents");
				if(Full_File_PPT.isEmpty()) return 0;
				Full_File_PPT += "\""+GUI.getPointPath()+Run_File[1]+"\"";
				Full_File = Full_File_PPT;
				//Program.findProgram(".ppt").execute(GUI.getPointPath()+Run_File[1]);
				//return 1;
			}else{ //錯誤的指令
				return 0;
			}
			System.out.println("執行的指令："+Full_File);
			Runtime.getRuntime().exec(Full_File);  //用於執行DOS指令
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}
//		try{
//			System.out.println("執行的指令："+Full_File);
//			Runtime.getRuntime().exec(Full_File);  //用於執行DOS指令
//		}catch(Exception e){
//			System.out.println(e.getMessage());
//			return -1;
//		}
		return 1;
	}
	/*------------------------------------------------------------
	 * 讀取指定資料夾底下不包含資料夾的所有檔案
	 * 並使用//s符號結合找到的檔案
	 * 回傳一個字串的資料型態
	 *------------------------------------------------------------*/
	private String printFolder(File root , int sel) {
		String fileExtension[] = null;
		
		 //音樂副檔名過濾(.mp3 .wma .wav)
		String fileExtension1[] = {"mp3", "wma","wav"};
		
		//影片副檔名過濾(.avi .wmv .mpg .mp4)
		String fileExtension2[] = {"avi", "wmv","mpg","mp4"};
		
		//簡報副檔名過濾(.ppt .pptx .pptm .ppsx .pps .ppsm)
		String fileExtension3[] = {"ppt", "pptx","pptm","ppsx","pps","ppsm"};
		
		switch(sel){
			case 1: fileExtension = fileExtension1.clone(); break;
			case 2: fileExtension = fileExtension2.clone(); break;
			case 3: fileExtension = fileExtension3.clone(); break;
		}
		
		//將root.listFiles()整個跑一遍，並將每一次的值讀到controlFolder
		//主要用於抓取指定目錄底下所有的資料夾、檔案
		String File = null;
		try {
			for (File controlFolder : root.listFiles(new ExtensionFileFilter(fileExtension)) ){ 
				// 取出副檔名方法
				if(controlFolder.isFile()){ //如果controlFolder是一個檔案
					//輸出：取得該檔案名稱
					if(File==null)
						File = controlFolder.getName() + "//s";
					else
						File += controlFolder.getName() + "//s"; // //s是結合符號，等候傳送到客戶端可以進行拆解
				}
			}
			System.err.println(File);
			return File;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
