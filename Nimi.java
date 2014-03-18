package mang;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Nimi extends JFrame {

	private String nimi;
	private JPanel jp = new JPanel();
	private JTextField text = new JTextField(10);
	private JLabel label = new JLabel();
	private int aegMöödas;

	public Nimi(int aegMöödas) {
		this.setAegMöödas(aegMöödas);
		setTitle("Sisesta nimi!");
		setSize(200, 50);
		setLocationRelativeTo(null);
		label.setLocation(20, 50);
		label.setText("Sisesta nimi ja vajuta enter!");
		jp.add(text);
		jp.add(label);

		add(jp);
		setUndecorated(true);
		setVisible(true);

		text.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mang.Hull.setLäbi();

				if (text.getText().length() < 3) {
					label.setText("Paluks vähemalt 3 tähemärki.");
					text.setText("");
				} else if (text.getText().length() > 15) {
					label.setText("Liiga pikk nimi!");
					text.setText("");
				} else {
					setNimi(text.getText());
					try {
						URL url = new URL(
								"ftp://u546441182:lammas100@ftp.hull.bl.ee/skoorid.txt");
						URLConnection urlc = url.openConnection();
						urlc.setDoOutput(true);
						InputStream ins = urlc.getInputStream();
						BufferedReader in = new BufferedReader(
								new InputStreamReader(ins));
						String inputLine;
						ArrayList<String> temp = new ArrayList<String>();
						while ((inputLine = in.readLine()) != null) {
							temp.add(inputLine
									+ System.getProperty("line.separator"));
						}
						in.close();
						urlc = url.openConnection();
						OutputStream outs = urlc.getOutputStream();
						BufferedWriter out = new BufferedWriter(
								new OutputStreamWriter(outs));
						
						int size = temp.size();
						String last = temp.get(size-1);
						
						if (size == 0 || saaNumber(last) > getAegMöödas()) {
							temp.add(getAegMöödas()+ " " + nimi
									+ System.getProperty("line.separator"));
						} else {	
							for (int i = 0; i < size; i++) {
								if (saaNumber(temp.get(i)) < getAegMöödas()) {
									temp.add(i, getAegMöödas()+ " " + nimi
											+ System.getProperty("line.separator"));
									break;
								}
							}
						}
						for (int i = 0; i < size+1; i++) {
							out.write(temp.get(i));
							}
						out.close();
					} catch (MalformedURLException f) {
						// TODO Auto-generated catch block
						f.printStackTrace();
					} catch (IOException s) {
						// TODO Auto-generated catch block
						s.printStackTrace();
					}
					dispose();
				}
			}

		});
	}
	
	public int saaNumber(String rida) {
		String[] jupid = rida.split(" ");
		return Integer.parseInt(jupid[0]);
	}

	public String getNimi() {
		return nimi;
	}

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	public int getAegMöödas() {
		return aegMöödas;
	}

	public void setAegMöödas(int aegMöödas) {
		this.aegMöödas = aegMöödas;
	}
}
