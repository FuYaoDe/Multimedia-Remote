package Server;

import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

import javax.swing.InputMap;

public class SimpleServer {
	ServerSocket ss;
	SimpleServer server;
	Thread thread;

	// �غc�禡
	public SimpleServer() { }

	public void setServerPort(int port){
		server = new SimpleServer(); 
		System.out.println("��ťPORT�G"+port);
		server.startServer(port);  //port 8888
	}

	private void startServer(int port) {
		try {
			ss = new ServerSocket(port);
			// �����
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

// �����
class ClientThread implements Runnable {
	private ServerSocket ss;
	private Socket cs;
	DataInputStream  in;
	DataOutputStream out;
	BufferedReader br ;
	// �غc�禡
	public ClientThread(ServerSocket ss) throws Exception {
		this.ss = ss;
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

		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");    
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");    
		}
		return str;
	}

	public void run() {
		try {
			while(true) {
				// �����Τ�ݪ��s�u�ШD
				cs = ss.accept();

				// �إߥΤ�ݪ���J��y				
				in  = new DataInputStream (cs.getInputStream());
				// �إߥΤ�ݪ���X��y
				out = new DataOutputStream(cs.getOutputStream());


				br = new BufferedReader(new InputStreamReader(cs.getInputStream()));

				String  line = br.readLine();


				// �ܫȤ�ݱ��������
				//				String FromClient = line;
				String FromClient = unicodeToString(line);
				System.out.println("�Ȥ�ݰe�Ӫ��T���G \""+FromClient+"\"");

				ss.getInetAddress();


				ControlWMP wmp = new ControlWMP();
				ControlPPT ppt = new ControlPPT();
				ControlComputer cc = new ControlComputer();
				Folder f = new Folder();
				//�q�������

				if(FromClient.equalsIgnoreCase("connect")) {
					out.writeBytes(StringtoUnicode("connect"));//�}�l�s
					System.out.println("�o�{�Ȥ�ݡI");
				}
				else if(FromClient.equalsIgnoreCase("MRCode_CC_00")) cc.sleep(); //��v
				else if(FromClient.equalsIgnoreCase("MRCode_CC_01")) cc.reset(); //���s�}��
				else if(FromClient.equalsIgnoreCase("MRCode_CC_02")) cc.powerOff(); //����

				//				//WMP�����
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_00")) wmp.Close(); //��������
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_01")) wmp.Random(); //�H������
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_02")) wmp.Repick(); //���Ƽ���
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_10")) wmp.Pause(); //�~��μȰ�
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_11")) wmp.fullScreen(); //���e��-�������e��
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_12")) wmp.PageUP(); //�W�@��
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_13")) wmp.PageDown(); //�U�@��
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_14")) wmp.Mute(); //�R��-�����R��
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_15")) wmp.VolumeIncrease(); //�W�[���q
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_16")) wmp.VolumeReduce(); //���C���q
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_17")) wmp.FastPlay(); //�[�t����
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_18")) wmp.SlowPlay(); //��t����
				else if(FromClient.equalsIgnoreCase("MRCode_WMP_19")) wmp.StopPlay(); //�����

				//PPT�����
				else if(FromClient.equalsIgnoreCase("MRCode_PPT_00")) ppt.Close(); //����²��
				else if(FromClient.equalsIgnoreCase("MRCode_PPT_10")) ppt.Slide(); //�i�J��v���Ҧ�
				else if(FromClient.equalsIgnoreCase("MRCode_PPT_11")) ppt.Esc(); //����²�� ESC
				else if(FromClient.equalsIgnoreCase("MRCode_PPT_12")) ppt.PageUP(); //�W�@��
				else if(FromClient.equalsIgnoreCase("MRCode_PPT_13")) ppt.PageDown(); //�U�@��
				else if(FromClient.equalsIgnoreCase("MRCode_PPT_14")) ppt.VolumeIncrease(); //�W�[���q
				else if(FromClient.equalsIgnoreCase("MRCode_PPT_15")) ppt.VolumeReduce(); //���C���q

				//				//������Ȥ�ݼs�������o���A��IP���
				else if(FromClient.equalsIgnoreCase("MRCode_Return")) out.writeUTF(ss.getInetAddress().getLocalHost().getHostAddress());
				//				
				//�Ȥ�ݭn�D��Ƨ����t���ɮ׸�T�ǿ�

				else if(FromClient.equalsIgnoreCase("MRCode_Show_Music")) 
					//					out.writeBytes(f.FolderSelect(1));  //�Ǧ^���ָ�Ƨ��ɮ�
					out.writeBytes(StringtoUnicode(f.FolderSelect(1))); 	 //�Ǧ^���ָ�Ƨ��ɮ�
				else if(FromClient.equalsIgnoreCase("MRCode_Show_Videos")) 
					//					out.writeBytes(f.FolderSelect(2));  //�Ǧ^�v����Ƨ��ɮ�
					out.writeBytes(StringtoUnicode(f.FolderSelect(2)));  	//�Ǧ^�v����Ƨ��ɮ�
				else if(FromClient.equalsIgnoreCase("MRCode_Show_Documents")) 
					//					out.writeBytes(f.FolderSelect(3));  //�Ǧ^²����Ƨ��ɮ�
					out.writeBytes(StringtoUnicode(f.FolderSelect(3)));  	//�Ǧ^²����Ƨ��ɮ�

				//�Ȥ�ݭn�D�}���ɮ�
				else
				{
					System.out.println(f.Strat_File(FromClient.trim()));
					int Run_ok = f.Strat_File(FromClient.trim());
					if(Run_ok==1){
						out.writeBytes(StringtoUnicode("open_okay-->"));
						System.out.println("�����ɮצ��\�I");
					}else if(Run_ok==-1){
						out.writeBytes(StringtoUnicode("open_failed-->"));
						System.out.println("�����ɮץ��ѡI");
					}else{
						out.writeBytes(StringtoUnicode("open_cmd_error-->"));
						System.out.println("���~�����O�I");
					}
				}

				//if(!FromClient.equalsIgnoreCase("Connect")) out.writeBytes(StringtoUnicode(FromClient));//�e�^���O
			}
		} catch (Exception e) {
			e.printStackTrace();//�C�L���`��T
		} finally {//��finally�y�y���T�O�ʧ@����
			try{
				if(in != null){
					in.close();//������J��y
				}
				if(out != null){
					out.close();//������J��y
				}
				if(cs != null){
					cs.close();//����Socket�s��
				}
			}
			catch(Exception e){
				e.printStackTrace();//�C�L���`��T
			}
		}
	}

}
