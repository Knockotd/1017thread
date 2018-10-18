import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	public static void main(String[] args) {
		try {
			//서버 소켓 생성
			ServerSocket ss = new ServerSocket(9309);
			//1024번까지는 시스템이 예약됨 / 8080 , 1521, 3306, => 이건 다른 회사 (톰켓, 오라클, MySQL) 가 쓰고 있는 거라서 나중에 충돌이 남. 그 번호를 피해서 쓸 것.
			System.out.println("서버구동");
			while(true) {
				//클라이언트의 접속 대기 - 클라이언트가 접속을 하면 클라이언트와 통할 수 있는 소켓이 리턴되고 그 소켓 정보를 socket에 저장
				Socket socket = ss.accept();
				//클라이언트 정보 출력
				System.out.println("클라이언트 접속 :"+socket.getInetAddress());
				//클라이언트에게서 전송된 문자열을 줄단위로 읽기 위한 스트림 생성
				BufferedReader br =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
				//한 줄 읽기
				String msg = br.readLine();
				System.out.println("보내온 메시지 : "+msg);
				
				
				//문자열을 전송하기 위한 스트림 만들기
				PrintWriter pw = new PrintWriter(socket.getOutputStream());
				//문자열 전송
				pw.println("에코 :"+msg);
				pw.flush();
				
				pw.close();
				br.close();
				socket.close();
				
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
