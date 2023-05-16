import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class Consulta2 extends JFrame {
	private JLabel lbpizza, sizepizza;
	private JTextField tfSQL;
	private JButton btclientes, btcardapio, bttelcl, btnomecl;
	private ImageIcon imgpizza;
	private JScrollPane scrollTable;
	private JTable table;
	private BD bd;
	private PreparedStatement st;
	private ResultSet rs;

	public static void main(String args[]) {
		JFrame frame = new Consulta2();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public Consulta2() {
		inicializarComponentes();
		definirEventos();
	}

	public void inicializarComponentes() {
		setLayout(null);
		setTitle("Tabela de consultas");
		setBounds(200, 200, 600, 500);
		setResizable(false);
		Color vermelho = new Color(178,34,34);
		setBackground(vermelho);
		lbpizza = new JLabel("PIZZARIA CGH");
		lbpizza.setBounds(250, 20, 200, 25);

		btclientes = new JButton("Clientes");
		btclientes.setBounds(50, 80, 100, 25);
		btcardapio = new JButton("Cardapio");
		btcardapio.setBounds(150, 80, 100, 25);
		bttelcl = new JButton("Telefone Clientes");
		bttelcl.setBounds(250, 80, 150, 25);
		btnomecl = new JButton("Nome Clientes");
		btnomecl.setBounds(400, 80, 150, 25);
		scrollTable = new JScrollPane();
		scrollTable.setBounds(50, 100, 500, 300);
		imgpizza = new ImageIcon("\\Desktop\\imgs_bd\\pizza.png");
		sizepizza = new JLabel(imgpizza);
		sizepizza.setBounds(0,100,600,600);

		lbpizza.setForeground(Color.black); 
		lbpizza.setFont(new Font("Cooper Black", Font.ITALIC, 16));
		btclientes.setBackground(Color.black);
		btclientes.setForeground(Color.red);
		btcardapio.setBackground(Color.black);
		btcardapio.setForeground(Color.red);
		bttelcl.setBackground(Color.black);
		bttelcl.setForeground(Color.red);
		btnomecl.setBackground(Color.black);
		btnomecl.setForeground(Color.red);
		btclientes.setFont(new Font("AlNile", Font.BOLD, 12));
		btcardapio.setFont(new Font("AlNile", Font.BOLD, 12));
		bttelcl.setFont(new Font("AlNile", Font.BOLD, 12));
		btnomecl.setFont(new Font("AlNile", Font.BOLD, 12));

		
		add(scrollTable);
		add(lbpizza);
		add(sizepizza);
		add(btclientes);
		add(btcardapio);
		add(bttelcl);
		add(btnomecl);
		
		bd = new BD();
	}

	public void definirEventos() {
		btclientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfSQL.getText().equals("")) {
					tfSQL.requestFocus();
					return;
				}
				try{
					if(!bd.getConnection()){
						JOptionPane.showMessageDialog(null,"Falha na conex�o!");
						System.exit(0);
					}
					st = bd.c.prepareStatement(tfSQL.getText());
					rs = st.executeQuery();
					DefaultTableModel tableModel = new DefaultTableModel(
							new String[]{},0){
					};
					int qtdeColunas = rs.getMetaData().getColumnCount();
					for(int indice = 1; indice <= qtdeColunas; indice++){
						tableModel.addColumn(rs.getMetaData().getColumnClassName(indice));
						
					}
					table = new JTable(tableModel);
					DefaultTableModel dtm = (DefaultTableModel) table.getModel();
					
					while(rs.next()){
						try{
							String[] dados = new String[qtdeColunas];
							for(int i = 1; i<=qtdeColunas; i++){
								dados[i-1] = rs.getString(i);
								System.out.println(rs.getString(i));
							}
							dtm.addRow(dados);
							System.out.println();
						}catch (SQLException erro){
							
						}
						scrollTable.setViewportView(table);
					}
					rs.close();
					st.close();
					bd.close();
				}catch (Exception erro){
					JOptionPane.showMessageDialog(null,"Comando Inv�lido"+erro.toString());
				}
			}
		});
}

}