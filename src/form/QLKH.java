package form;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class QLKH extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textMa;
	private JTextField textTen;
	private JTextField textSo;
	private JTextField textE;
	DefaultTableModel model = new DefaultTableModel();
	ArrayList<KhachHang> listKH = new ArrayList<KhachHang>();
	JComboBox comboBox = new JComboBox();
	int index = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QLKH frame = new QLKH();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public QLKH() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 629, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblLKH = new JLabel("");
		
		JLabel lblNewLabel = new JLabel("Lo\u1EA1i kh\u00E1ch h\u00E0ng");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 28, 140, 20);
		contentPane.add(lblNewLabel);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String comString = (String) comboBox.getSelectedItem();
				lblLKH.setText(comString);
				try {
					loadTable();
					if(model.getRowCount() > 0) {
						display(0);
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		comboBox.setBounds(160, 28, 140, 22);
		contentPane.add(comboBox);
		
		lblLKH.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLKH.setBounds(327, 28, 140, 20);
		contentPane.add(lblLKH);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 71, 457, 159);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					textMa.setText("");
					textE.setText("");
					textSo.setText("");
					textTen.setText("");
				
			}
		});
		btnNew.setBounds(477, 68, 89, 23);
		contentPane.add(btnNew);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		btnSave.setBounds(477, 102, 89, 23);
		contentPane.add(btnSave);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(477, 136, 89, 23);
		contentPane.add(btnDelete);
		
		JLabel lblMKhchHng = new JLabel("M\u00E3 kh\u00E1ch h\u00E0ng");
		lblMKhchHng.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMKhchHng.setBounds(10, 252, 140, 20);
		contentPane.add(lblMKhchHng);
		
		JLabel lblTnKhchHng = new JLabel("T\u00EAn kh\u00E1ch h\u00E0ng");
		lblTnKhchHng.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTnKhchHng.setBounds(10, 286, 140, 20);
		contentPane.add(lblTnKhchHng);
		
		textMa = new JTextField();
		textMa.setBounds(160, 253, 307, 20);
		contentPane.add(textMa);
		textMa.setColumns(10);
		
		textTen = new JTextField();
		textTen.setColumns(10);
		textTen.setBounds(160, 287, 307, 20);
		contentPane.add(textTen);
		
		textSo = new JTextField();
		textSo.setColumns(10);
		textSo.setBounds(160, 318, 307, 20);
		contentPane.add(textSo);
		
		textE = new JTextField();
		textE.setColumns(10);
		textE.setBounds(160, 349, 307, 20);
		contentPane.add(textE);
		
		JLabel lblNewLabel_2_1 = new JLabel("S\u1ED1 \u0111i\u1EC7n tho\u1EA1i");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2_1.setBounds(10, 321, 140, 20);
		contentPane.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("Email");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2_2.setBounds(10, 352, 140, 20);
		contentPane.add(lblNewLabel_2_2);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int r = table.getSelectedRow();
				String ma = (String) table.getModel().getValueAt(r, 0);
				int in = getIndex(ma);
				if(in != listKH.size()) {
					display(in);
				}

			}
		});
		
		table();
		loadCom();
		try {
			loadTable();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public int getIndex(String ma) {
		int i = 0;
		for (; i < listKH.size(); i++) {
			if(ma.equalsIgnoreCase(listKH.get(i).maKH)) {
				break;
			}
		}
		return i;
	}
	
	public void loadCom() {
		Connection conn;
		try {
			conn = Connect.connect();
			Statement statement = conn.createStatement();
			ResultSet tbCom = statement.executeQuery("select * from loaikhachhang");
			while(tbCom.next()) {
				comboBox.addItem(tbCom.getString(1));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void save() {
		StringBuilder error = new StringBuilder();
		if(textMa.getText().isBlank()) {
			error.append("Bạn không được để trống mã khách hàng!\n");
		} else {
			listKH.forEach((kh) -> {
				if(kh.maKH.equalsIgnoreCase(textMa.getText())) {
					error.append("Khách hàng này đã tồn tại!\n");
				}
			});
		}
		if(textE.getText().isBlank()) {
			error.append("Bạn không được để trống email khách hàng!\n");
		}
		if(textSo.getText().isBlank()) {
			error.append("Bạn không được để trống số điện thoại khách hàng!\n");
		}
		if(textTen.getText().isBlank()) {
			error.append("Bạn không được để trống tên khách hàng!\n");
		}
		
		if(error.toString().isBlank()) {
			try {
				Connection conn = Connect.connect();
				PreparedStatement ps = conn.prepareStatement("Insert into ");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			JOptionPane.showMessageDialog(null, error.toString());
		}
	}
	
	public void table() {
		model.addColumn("Mã khách hàng");
		model.addColumn("Tên khách hàng");
		model.addColumn("Email");
		model.addColumn("Số điện thoại");
		table.setModel(model);
	}
	
	public void loadTable() throws ClassNotFoundException, SQLException {
		listKH = KhachHangDao.loadKH();
		model.setRowCount(0);
		listKH.forEach((kh) -> {
			
			if(kh.loaiKH.equals(comboBox.getSelectedItem())) {
				
				model.addRow(new Object[] {kh.maKH,kh.ten,kh.email,kh.soDT});
			}
		});
	}
	
	public void display(int i) {
		textMa.setText(listKH.get(i).maKH);
		textE.setText(listKH.get(i).email);
		textSo.setText(listKH.get(i).soDT);
		textTen.setText(listKH.get(i).ten);
	}
	
}
