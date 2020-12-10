package form;

import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class KhachHangDao {
	public static ArrayList<KhachHang> loadKH() throws ClassNotFoundException, SQLException{
		ArrayList<KhachHang> list = new ArrayList<KhachHang>();
			Connection conn = Connect.connect();
			Statement statement = conn.createStatement();
			ResultSet tbKH = statement.executeQuery("select * from khachhang");
			while(tbKH.next()) {
				String maKH = tbKH.getString(1);
				String ten = tbKH.getString(2);
				String email = tbKH.getString(3);
				String soDT = tbKH.getString(4);
				String loaiKH = tbKH.getString(5);
				list.add(new KhachHang(maKH, ten, email, soDT, loaiKH));
			}
			return list;
		
	}
	
	public void delete() {
		
	}
}
