import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class Account {
	public static void main(String[] argv)	{
			Scanner input = new Scanner(System.in);
			System.out.println("Account\n");
			
			while(true){
				int num = -1;
				System.out.print("입력 : ");
				num = input.nextInt();
				
				switch(num){
				
				
				case 0:	// Account 테이블 생성
				{	
					Connection conn;
					Statement stmt;

					try{
						Class.forName("oracle.jdbc.driver.OracleDriver");

						conn = DriverManager.getConnection
							("jdbd:oracle:thin:@localhost:1521:orcl", 
							 "scott", "tiger");
						stmt = conn.createStatement();

						stmt.executeUpdate(
							"CREATE TABLE Account (" +
									"id VARCHAR2(8) primary key, " +
									"owner VARCHAR2(15)," +
									"balance VARCHAR2(8))"
						);

						System.out.println("Table Created!!!");

						stmt.close();

						conn.close();
					}catch(Exception e)
					      {e.printStackTrace();}
					break;
				}
				case 1: // Account 계좌 개설
				{
					Connection conn;
					Statement stmt;
					ResultSet rs;
					int result;
					int falsenum=0;
					
					System.out.print("계좌 번호 입력 : ");
					String number = input.next();
					System.out.print("예금주 입력 : ");
					String owner = input.next();
					System.out.print("잔액 입력 : ");
					String balance = input.next();

					try{
						Class.forName("oracle.jdbc.driver.OracleDriver");

						conn = DriverManager.getConnection
							("jdbd:oracle:thin:@localhost:1521:orcl", 
							"scott", "tiger");
						
						stmt = conn.createStatement();
						rs = stmt.executeQuery("SELECT * FROM Account");
						while(rs.next()){
							String id = rs.getString(8);		

							if (id == number){
								System.out.println("만들고자 하는 계좌가 존재합니다.");
								falsenum=1;
								break;
							}
						}
						
						if(falsenum != 1){
						stmt = conn.createStatement();
							result = stmt.executeUpdate( "INSERT INTO Account VALUES(" + number+ "," + owner+ ","+ balance+")" 
							);
							System.out.println(result + " Row inserted");
						}

						stmt.close();

						conn.close();
					}catch(Exception e)
					       {e.printStackTrace();}
					break;
				}
				case 2: // 잔액 조회
				{
					Connection conn;
					Statement stmt;
					ResultSet rs;

					try{
						Class.forName("oracle.jdbc.driver.OracleDriver");

						conn = DriverManager.getConnection 
							("jdbd:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
						stmt = conn.createStatement();
						rs = stmt.executeQuery("SELECT * FROM Acoount");
						while(rs.next()){
							String id = rs.getString(8);		
							String owner = rs.getString("owner");	
							String balance = rs.getString(8);

							System.out.println(" id : " + id + " owner : " + owner+ " balance :"+ balance);
						}
						rs.close();
						stmt.close();
						conn.close();
					}catch(Exception e)
					      {e.printStackTrace();}
					
					break;
				}
				case 3: // 입금
				{
					Connection conn;
					Statement stmt;
					int result;
					
					System.out.println("계좌 번호 입력 : ");
					String snumber = input.next();
					System.out.println("입금액 입력 : ");
					String smoney = input.next();

					try{
						Class.forName("oracle.jdbc.driver.OracleDriver");
						conn = DriverManager.getConnection
							("jdbd:oracle:thin:@localhost:1521:orcl", "scott", "tiger");			
						stmt = conn.createStatement();
						result = stmt.executeUpdate(
							"UPDATE Acoount " +	"SET  = balance+smoney " + "WHERE number=snumber"	);
						System.out.println(result + " Row updated");
						stmt.close();
						conn.close();
					}catch(Exception e){e.printStackTrace();}
					break;
				}
				case 4:	// 출금
				{
					Connection conn;
					Statement stmt;
					ResultSet rs;
					int result;
					int successnum=0;
					
					int su_money;
					int su_smoney;
					
					System.out.println("계좌 번호 입력 : ");
					String snumber = input.next();
					System.out.println("출금액 입력 : ");
					String smoney = input.next();

					try{
						Class.forName("oracle.jdbc.driver.OracleDriver");
						conn = DriverManager.getConnection
							("jdbd:oracle:thin:@localhost:1521:orcl", "scott", "tiger");			
						stmt = conn.createStatement();
						rs = stmt.executeQuery("SELECT * FROM Acoount");
						while(rs.next()){
							String id = rs.getString(8);		
							String money = rs.getString(8);	

							if (id == snumber){
								System.out.println("출금하고자하는 계좌를 확인했습니다.\n");
								su_money =Integer.parseInt(money); // 계좌의 잔액
								su_smoney =Integer.parseInt(smoney);	// 출금액
								if(su_money >= su_smoney){	// 잔액 > 출금액
									successnum = 1;
								}
								break;
							}
						}
						if(successnum == 1){
							stmt.close();
							stmt = conn.createStatement();
							result = stmt.executeUpdate(
								"UPDATE Acoount " +	"SET  = balance-smoney " + "WHERE id=snumber"	);
							System.out.println(result + " Row updated");
						}
						stmt.close();
						conn.close();
					}catch(Exception e){e.printStackTrace();}
					break;
				}
				case 5:	// 계좌이체
					break;
				case 6:// 프로그램 종료
					break;
					
				default:
					break;
					
				}
				
			}
	}

}

