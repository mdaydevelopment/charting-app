package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyledDocument;

import api.ChartingInterface;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.Map;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChartingFrame extends JFrame {

	private ChartingInterface charts;

	private JPanel contentPane;
	private JTextField txtClientIdQ;
	private JTextField txtFirstNameQ;
	private JTextField txtLastNameQ;
	private JTextField txtPhoneQ;
	private JTextField txtEmailQ;
	private JTextField txtDateOfBirthQ;
	private JTextField txtPaidQ;
	private JTextField txtSessionsQ;
	private JTextField txtLastContactQ;
	private JTextField txtLastSessionQ;
	private JTextField txtClientIdC;
	private JTextField txtFirstNameC;
	private JTextField txtLastNameC;
	private JTextField txtPhoneC;
	private JTextField txtEmailC;
	private JTextField txtLastSessionC;
	private JTextField txtLastContactC;
	private JTextField txtSessionsC;
	private JLabel lblIgnoreC;
	private JTextField txtPaidC;
	private JTextField txtDateOfBirthC;
	private JTextField txtSearchTextC;
	private JTextField txtClientId;
	private JTextField txtLastUpdated;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtDateOfBirth;
	private JTextField txtPhone;
	private JTextField txtEmail;
	private JTextField txtOccupation;
	private JTextField txtStreet;
	private JTextField txtCity;
	private JTextField txtState;
	private JTextField txtZip;
	private JTextField txtReferredBy;
	private JTextField txtPhysician;
	private JTextField txtPhysicianPhone;
	private JCheckBox chckbxIgnore;
	private JTextField txtDateS;
	private JTextField txtTimeS;
	private JTextField txtMinutesS;
	private JTextField txtPaidS;
	private JTextPane txtpnAccidentsOrSurgeries;
	private JTextPane txtpnAllergies;
	private JTextPane txtpnConditions;
	private JTextPane txtpnComplaintS;
	private JTextPane txtpnTreatmentS;
	private JTextPane txtpnOtherNotesS;
	private JTextField txtCondition;
	private JTextField txtDescription;

	/**
	 * Create the frame.
	 */
	public ChartingFrame() throws Exception {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				try {
					charts.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		setTitle("Catchy Name Here");
		charts = new ChartingInterface();
		System.out.println("Charting interface loaded successfully");

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1253, 769);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("Sort");
		menuBar.add(menu);

		JMenuItem menuItem = new JMenuItem("Last Session");
		menuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				charts.sortByLastSession();
				refreshCard();
			}
		});
		menu.add(menuItem);

		JMenuItem menuItem_1 = new JMenuItem("Last Contact");
		menuItem_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				charts.sortByLastContact();
				refreshCard();
			}
		});
		menu.add(menuItem_1);

		JMenuItem menuItem_2 = new JMenuItem("Total Sessions");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				charts.sortByTotalSession();
				refreshCard();
			}
		});
		menu.add(menuItem_2);

		JMenuItem menuItem_3 = new JMenuItem("Total Paid");
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				charts.sortByTotalPaid();
				refreshCard();
			}
		});
		menu.add(menuItem_3);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblClientIdQ = new JLabel("Client ID");
		lblClientIdQ.setBounds(16, 422, 114, 15);
		contentPane.add(lblClientIdQ);

		txtClientIdQ = new JTextField();
		txtClientIdQ.setEditable(false);
		txtClientIdQ.setText(String.valueOf(charts.getTopClientID()));
		txtClientIdQ.setColumns(10);
		txtClientIdQ.setBounds(16, 449, 114, 19);
		contentPane.add(txtClientIdQ);

		JLabel lblFirstNameQ = new JLabel("First Name");
		lblFirstNameQ.setBounds(16, 480, 114, 15);
		contentPane.add(lblFirstNameQ);

		txtFirstNameQ = new JTextField();
		txtFirstNameQ.setEditable(false);
		txtFirstNameQ.setText(charts.getTopFName());
		txtFirstNameQ.setColumns(10);
		txtFirstNameQ.setBounds(16, 507, 114, 19);
		contentPane.add(txtFirstNameQ);

		JLabel lblLastNameQ = new JLabel("Last Name");
		lblLastNameQ.setBounds(16, 538, 114, 15);
		contentPane.add(lblLastNameQ);

		txtLastNameQ = new JTextField();
		txtLastNameQ.setEditable(false);
		txtLastNameQ.setText(charts.getTopLName());
		txtLastNameQ.setColumns(10);
		txtLastNameQ.setBounds(16, 565, 114, 19);
		contentPane.add(txtLastNameQ);

		JLabel lblPhoneQ = new JLabel("Phone");
		lblPhoneQ.setBounds(16, 596, 114, 15);
		contentPane.add(lblPhoneQ);

		txtPhoneQ = new JTextField();
		txtPhoneQ.setEditable(false);
		txtPhoneQ.setText(charts.getTopPhone());
		txtPhoneQ.setColumns(10);
		txtPhoneQ.setBounds(16, 623, 114, 19);
		contentPane.add(txtPhoneQ);

		JLabel lblEmailQ = new JLabel("Email");
		lblEmailQ.setBounds(16, 654, 69, 15);
		contentPane.add(lblEmailQ);

		txtEmailQ = new JTextField();
		txtEmailQ.setEditable(false);
		txtEmailQ.setText(charts.getTopEmail());
		txtEmailQ.setColumns(10);
		txtEmailQ.setBounds(16, 681, 114, 19);
		contentPane.add(txtEmailQ);

		txtDateOfBirthQ = new JTextField();
		txtDateOfBirthQ.setEditable(false);
		txtDateOfBirthQ.setText(charts.getTopDob().toString());
		txtDateOfBirthQ.setColumns(10);
		txtDateOfBirthQ.setBounds(142, 681, 114, 19);
		contentPane.add(txtDateOfBirthQ);

		JLabel lblDateOfBirthQ = new JLabel("Date of Birth");
		lblDateOfBirthQ.setBounds(142, 654, 114, 15);
		contentPane.add(lblDateOfBirthQ);

		txtPaidQ = new JTextField();
		txtPaidQ.setEditable(false);
		txtPaidQ.setText(String.valueOf(charts.getTopPaid()));
		txtPaidQ.setColumns(10);
		txtPaidQ.setBounds(142, 623, 114, 19);
		contentPane.add(txtPaidQ);

		JLabel lblPaidQ = new JLabel("Paid");
		lblPaidQ.setBounds(142, 596, 114, 15);
		contentPane.add(lblPaidQ);

		txtSessionsQ = new JTextField();
		txtSessionsQ.setEditable(false);
		txtSessionsQ.setText(String.valueOf(charts.getTopSessions()));
		txtSessionsQ.setColumns(10);
		txtSessionsQ.setBounds(142, 565, 114, 19);
		contentPane.add(txtSessionsQ);

		JLabel lblSessionsQ = new JLabel("Sessions");
		lblSessionsQ.setBounds(142, 538, 114, 15);
		contentPane.add(lblSessionsQ);

		txtLastContactQ = new JTextField();
		txtLastContactQ.setEditable(false);
		txtLastContactQ.setText(charts.getTopLastContact().toString());
		txtLastContactQ.setColumns(10);
		txtLastContactQ.setBounds(142, 507, 114, 19);
		contentPane.add(txtLastContactQ);

		JLabel lblLastContactQ = new JLabel("Last Contact");
		lblLastContactQ.setBounds(142, 480, 114, 15);
		contentPane.add(lblLastContactQ);

		txtLastSessionQ = new JTextField();
		txtLastSessionQ.setEditable(false);
		txtLastSessionQ.setText(charts.getTopLastSession().toString());
		txtLastSessionQ.setColumns(10);
		txtLastSessionQ.setBounds(142, 449, 114, 19);
		contentPane.add(txtLastSessionQ);

		JLabel lblLastSessionQ = new JLabel("Last Session");
		lblLastSessionQ.setBounds(142, 422, 114, 15);
		contentPane.add(lblLastSessionQ);

		txtClientIdC = new JTextField();
		txtClientIdC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				charts.jumpToID(Integer.valueOf(txtClientIdC.getText()));
				refreshCard();
			}
		});
		txtClientIdC.setText(String.valueOf(charts.getCardClientID()));
		txtClientIdC.setColumns(10);
		txtClientIdC.setBounds(20, 80, 114, 19);
		contentPane.add(txtClientIdC);

		txtFirstNameC = new JTextField();
		txtFirstNameC.setEditable(false);
		txtFirstNameC.setText(charts.getCardFName());
		txtFirstNameC.setColumns(10);
		txtFirstNameC.setBounds(20, 138, 114, 19);
		contentPane.add(txtFirstNameC);

		txtLastNameC = new JTextField();
		txtLastNameC.setEditable(false);
		txtLastNameC.setText(charts.getCardLName());
		txtLastNameC.setColumns(10);
		txtLastNameC.setBounds(20, 196, 114, 19);
		contentPane.add(txtLastNameC);

		JLabel lblClientIdC = new JLabel("Client ID");
		lblClientIdC.setBounds(20, 53, 114, 15);
		contentPane.add(lblClientIdC);

		JLabel lblFirstNameC = new JLabel("First Name");
		lblFirstNameC.setBounds(20, 111, 114, 15);
		contentPane.add(lblFirstNameC);

		JLabel lblLastNameC = new JLabel("Last Name");
		lblLastNameC.setBounds(20, 169, 114, 15);
		contentPane.add(lblLastNameC);

		JLabel lblPhoneC = new JLabel("Phone");
		lblPhoneC.setBounds(20, 227, 114, 15);
		contentPane.add(lblPhoneC);

		txtPhoneC = new JTextField();
		txtPhoneC.setEditable(false);
		txtPhoneC.setText(charts.getCardPhone());
		txtPhoneC.setColumns(10);
		txtPhoneC.setBounds(20, 254, 114, 19);
		contentPane.add(txtPhoneC);

		JLabel lblEmailC = new JLabel("Email");
		lblEmailC.setBounds(20, 285, 69, 15);
		contentPane.add(lblEmailC);

		txtEmailC = new JTextField();
		txtEmailC.setEditable(false);
		txtEmailC.setText(charts.getCardEmail());
		txtEmailC.setColumns(10);
		txtEmailC.setBounds(20, 312, 114, 19);
		contentPane.add(txtEmailC);

		JLabel lblLastSessionC = new JLabel("Last Session");
		lblLastSessionC.setBounds(146, 53, 114, 15);
		contentPane.add(lblLastSessionC);

		txtLastSessionC = new JTextField();
		txtLastSessionC.setEditable(false);
		txtLastSessionC.setText(charts.getCardLastSession().toString());
		txtLastSessionC.setColumns(10);
		txtLastSessionC.setBounds(146, 80, 114, 19);
		contentPane.add(txtLastSessionC);

		JLabel lblLastContactC = new JLabel("Last Contact");
		lblLastContactC.setBounds(146, 111, 114, 15);
		contentPane.add(lblLastContactC);

		txtLastContactC = new JTextField();
		txtLastContactC.setEditable(false);
		txtLastContactC.setText(charts.getCardLastContact().toString());
		txtLastContactC.setColumns(10);
		txtLastContactC.setBounds(146, 138, 114, 19);
		contentPane.add(txtLastContactC);

		JLabel lblSessionsC = new JLabel("Sessions");
		lblSessionsC.setBounds(146, 169, 114, 15);
		contentPane.add(lblSessionsC);

		txtSessionsC = new JTextField();
		txtSessionsC.setEditable(false);
		txtSessionsC.setText(String.valueOf(charts.getCardSessions()));
		txtSessionsC.setColumns(10);
		txtSessionsC.setBounds(146, 196, 114, 19);
		contentPane.add(txtSessionsC);

		JLabel lblPaidC = new JLabel("Paid");
		lblPaidC.setBounds(146, 227, 114, 15);
		contentPane.add(lblPaidC);

		txtPaidC = new JTextField();
		txtPaidC.setEditable(false);
		txtPaidC.setText(String.valueOf(charts.getCardPaid()));
		txtPaidC.setColumns(10);
		txtPaidC.setBounds(146, 254, 114, 19);
		contentPane.add(txtPaidC);

		JLabel lblDateOfBirthC = new JLabel("Date of Birth");
		lblDateOfBirthC.setBounds(146, 285, 114, 15);
		contentPane.add(lblDateOfBirthC);

		txtDateOfBirthC = new JTextField();
		txtDateOfBirthC.setEditable(false);
		txtDateOfBirthC.setText(charts.getCardDob().toString());
		txtDateOfBirthC.setColumns(10);
		txtDateOfBirthC.setBounds(146, 312, 114, 19);
		contentPane.add(txtDateOfBirthC);

		txtSearchTextC = new JTextField();
		txtSearchTextC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				charts.searchCards(txtSearchTextC.getText());
				refreshCard();
			}
		});
		txtSearchTextC.setText("Search Text");
		txtSearchTextC.setBounds(280, 121, 114, 19);
		contentPane.add(txtSearchTextC);
		txtSearchTextC.setColumns(10);

		JButton btnNextC = new JButton("Next");
		btnNextC.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				charts.nextCard();
				refreshCard();
			}
		});
		btnNextC.setBounds(278, 147, 116, 25);
		contentPane.add(btnNextC);

		JButton btnPreviousC = new JButton("Previous");
		btnPreviousC.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				charts.previousCard();
				refreshCard();
			}
		});
		btnPreviousC.setBounds(278, 176, 116, 25);
		contentPane.add(btnPreviousC);

		JButton btnFirstC = new JButton("First");
		btnFirstC.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				charts.firstCard();
				refreshCard();
			}
		});
		btnFirstC.setBounds(278, 205, 116, 25);
		contentPane.add(btnFirstC);

		JButton btnLastC = new JButton("Last");
		btnLastC.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				charts.lastCard();
				refreshCard();
			}
		});
		btnLastC.setBounds(278, 234, 116, 25);
		contentPane.add(btnLastC);

		JButton btnViewC = new JButton("View");
		btnViewC.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					charts.getClientC();
					refreshClient();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnViewC.setBounds(278, 53, 116, 25);
		contentPane.add(btnViewC);

		JLabel lblClientListC = new JLabel("Client List");
		lblClientListC.setFont(new Font("Dialog", Font.BOLD, 16));
		lblClientListC.setHorizontalAlignment(SwingConstants.CENTER);
		lblClientListC.setBounds(20, 12, 110, 29);
		contentPane.add(lblClientListC);

		JLabel lblCallListQ = new JLabel("Call List");
		lblCallListQ.setFont(new Font("Dialog", Font.BOLD, 16));
		lblCallListQ.setHorizontalAlignment(SwingConstants.CENTER);
		lblCallListQ.setBounds(16, 381, 110, 29);
		contentPane.add(lblCallListQ);

		JLabel lblClientId = new JLabel("Client ID");
		lblClientId.setBounds(439, 53, 114, 15);
		contentPane.add(lblClientId);

		txtClientId = new JTextField();
		txtClientId.setEditable(false);
		txtClientId.setText("Client ID");
		txtClientId.setBounds(439, 80, 114, 19);
		contentPane.add(txtClientId);
		txtClientId.setColumns(10);

		JLabel lblLastUpdated = new JLabel("Last Updated");
		lblLastUpdated.setBounds(691, 111, 114, 15);
		contentPane.add(lblLastUpdated);

		txtLastUpdated = new JTextField();
		txtLastUpdated.setEditable(false);
		txtLastUpdated.setText("Last Updated");
		txtLastUpdated.setBounds(691, 138, 114, 19);
		contentPane.add(txtLastUpdated);
		txtLastUpdated.setColumns(10);

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(565, 53, 114, 15);
		contentPane.add(lblFirstName);

		txtFirstName = new JTextField();
		txtFirstName.setText("First Name");
		txtFirstName.setBounds(565, 80, 114, 19);
		contentPane.add(txtFirstName);
		txtFirstName.setColumns(10);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(691, 53, 114, 15);
		contentPane.add(lblLastName);

		txtLastName = new JTextField();
		txtLastName.setText("Last Name");
		txtLastName.setBounds(691, 80, 114, 19);
		contentPane.add(txtLastName);
		txtLastName.setColumns(10);

		JLabel lblDateOfBirth = new JLabel("Date Of Birth");
		lblDateOfBirth.setBounds(691, 227, 114, 15);
		contentPane.add(lblDateOfBirth);

		txtDateOfBirth = new JTextField();
		txtDateOfBirth.setText("Date of Birth");
		txtDateOfBirth.setBounds(691, 254, 114, 19);
		contentPane.add(txtDateOfBirth);
		txtDateOfBirth.setColumns(10);

		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setBounds(439, 111, 114, 15);
		contentPane.add(lblPhone);

		txtPhone = new JTextField();
		txtPhone.setText("Phone");
		txtPhone.setBounds(439, 138, 114, 19);
		contentPane.add(txtPhone);
		txtPhone.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(565, 111, 114, 15);
		contentPane.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setText("Email");
		txtEmail.setBounds(565, 138, 114, 19);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);

		JLabel lblOccupation = new JLabel("Occupation");
		lblOccupation.setBounds(565, 227, 114, 15);
		contentPane.add(lblOccupation);

		txtOccupation = new JTextField();
		txtOccupation.setText("Occupation");
		txtOccupation.setBounds(565, 254, 114, 19);
		contentPane.add(txtOccupation);
		txtOccupation.setColumns(10);

		JLabel lblStreet = new JLabel("Street");
		lblStreet.setBounds(439, 169, 114, 15);
		contentPane.add(lblStreet);

		txtStreet = new JTextField();
		txtStreet.setText("Street");
		txtStreet.setBounds(439, 196, 114, 19);
		contentPane.add(txtStreet);
		txtStreet.setColumns(10);

		JLabel lblCity = new JLabel("City");
		lblCity.setBounds(565, 169, 114, 15);
		contentPane.add(lblCity);

		txtCity = new JTextField();
		txtCity.setText("City");
		txtCity.setBounds(565, 196, 114, 19);
		contentPane.add(txtCity);
		txtCity.setColumns(10);

		JLabel lblState = new JLabel("State");
		lblState.setBounds(691, 169, 69, 15);
		contentPane.add(lblState);

		txtState = new JTextField();
		txtState.setText("State");
		txtState.setBounds(691, 196, 114, 19);
		contentPane.add(txtState);
		txtState.setColumns(10);

		JLabel lblZip = new JLabel("Zip");
		lblZip.setBounds(439, 227, 114, 15);
		contentPane.add(lblZip);

		txtZip = new JTextField();
		txtZip.setText("Zip");
		txtZip.setBounds(439, 254, 114, 19);
		contentPane.add(txtZip);
		txtZip.setColumns(10);

		JLabel lblReferredBy = new JLabel("Referred By");
		lblReferredBy.setBounds(439, 285, 114, 15);
		contentPane.add(lblReferredBy);

		txtReferredBy = new JTextField();
		txtReferredBy.setText("Referred By");
		txtReferredBy.setBounds(439, 312, 114, 19);
		contentPane.add(txtReferredBy);
		txtReferredBy.setColumns(10);

		chckbxIgnore = new JCheckBox("Ignore");
		chckbxIgnore.setBounds(691, 679, 131, 23);
		contentPane.add(chckbxIgnore);

		JLabel lblAccidentsOrSurgeries = new JLabel("Accidents or Surgeries");
		lblAccidentsOrSurgeries.setBounds(439, 343, 240, 15);
		contentPane.add(lblAccidentsOrSurgeries);

		txtpnAccidentsOrSurgeries = new JTextPane();
		txtpnAccidentsOrSurgeries.setText("Accidents or Surgeries");
		txtpnAccidentsOrSurgeries.setBounds(439, 370, 366, 50);
		contentPane.add(txtpnAccidentsOrSurgeries);

		JLabel lblAllergies = new JLabel("Allergies");
		lblAllergies.setBounds(439, 432, 240, 15);
		contentPane.add(lblAllergies);

		txtpnAllergies = new JTextPane();
		txtpnAllergies.setText("Allergies");
		txtpnAllergies.setBounds(439, 459, 366, 50);
		contentPane.add(txtpnAllergies);

		JLabel lblPhysician = new JLabel("Physician");
		lblPhysician.setBounds(565, 285, 114, 15);
		contentPane.add(lblPhysician);

		txtPhysician = new JTextField();
		txtPhysician.setText("Physician");
		txtPhysician.setBounds(565, 312, 114, 19);
		contentPane.add(txtPhysician);
		txtPhysician.setColumns(10);

		JLabel lblPhysisianPhone = new JLabel("MD Phone");
		lblPhysisianPhone.setBounds(691, 285, 114, 15);
		contentPane.add(lblPhysisianPhone);

		txtPhysicianPhone = new JTextField();
		txtPhysicianPhone.setText("Physician Phone");
		txtPhysicianPhone.setBounds(691, 312, 114, 19);
		contentPane.add(txtPhysicianPhone);
		txtPhysicianPhone.setColumns(10);

		JLabel lblAdd = new JLabel("Conditions");
		lblAdd.setBounds(439, 521, 114, 15);
		contentPane.add(lblAdd);

		txtpnConditions = new JTextPane();
		txtpnConditions.setText("Conditions");
		txtpnConditions.setBounds(439, 582, 366, 87);
		contentPane.add(txtpnConditions);

		JButton btnNew = new JButton("New");
		btnNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				charts.newClient();
				refreshClient();
			}
		});
		btnNew.setBounds(439, 678, 116, 25);
		contentPane.add(btnNew);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					charts.setCliFName(txtFirstName.getText());
					charts.setCliLName(txtLastName.getText());
					charts.setCliPhone(txtPhone.getText());
					charts.setCliEmail(txtEmail.getText());
					charts.setCliStreet(txtStreet.getText());
					charts.setCliCity(txtCity.getText());
					charts.setCliState(txtState.getText());
					charts.setCliZip(txtZip.getText());
					charts.setCliOccupation(txtOccupation.getText());
					charts.setCliDob(Date.valueOf(txtDateOfBirth.getText()));
					charts.setCliAcdntSgrs(txtpnAccidentsOrSurgeries.getText());
					charts.setCliAllergies(txtpnAllergies.getText());
					charts.setCliIgnore(chckbxIgnore.isSelected());
					charts.submitClient();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				refreshClient();
			}
		});
		btnSubmit.setBounds(563, 678, 116, 25);
		contentPane.add(btnSubmit);

		JButton btnAddCondition = new JButton("Add");
		btnAddCondition.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					charts.addCondition(txtCondition.getText(), txtDescription.getText());
				} catch (NullPointerException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				refreshClient();
			}
		});
		btnAddCondition.setBounds(563, 516, 116, 25);
		contentPane.add(btnAddCondition);

		JButton btnContactQ = new JButton("Contact");
		btnContactQ.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					charts.contactTop();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				refreshTop();
			}
		});
		btnContactQ.setBounds(268, 484, 116, 25);
		contentPane.add(btnContactQ);

		JButton btnSkipQ = new JButton("Skip");
		btnSkipQ.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				charts.skipTop();
				refreshTop();
			}
		});
		btnSkipQ.setBounds(268, 516, 116, 25);
		contentPane.add(btnSkipQ);

		JButton btnIgnoreQ = new JButton("Ignore");
		btnIgnoreQ.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					charts.ignoreTop();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				refreshTop();
			}
		});
		btnIgnoreQ.setBounds(268, 548, 116, 25);
		contentPane.add(btnIgnoreQ);

		JLabel lblClient = new JLabel("Client");
		lblClient.setHorizontalAlignment(SwingConstants.CENTER);
		lblClient.setFont(new Font("Dialog", Font.BOLD, 16));
		lblClient.setBounds(439, 12, 110, 29);
		contentPane.add(lblClient);

		JLabel lblDateS = new JLabel("Date");
		lblDateS.setBounds(850, 53, 114, 15);
		contentPane.add(lblDateS);

		txtDateS = new JTextField();
		txtDateS.setText("Date");
		txtDateS.setBounds(850, 80, 114, 19);
		contentPane.add(txtDateS);
		txtDateS.setColumns(10);

		JLabel lblTimeS = new JLabel("Time");
		lblTimeS.setBounds(976, 53, 69, 15);
		contentPane.add(lblTimeS);

		txtTimeS = new JTextField();
		txtTimeS.setText("Time");
		txtTimeS.setBounds(976, 80, 114, 19);
		contentPane.add(txtTimeS);
		txtTimeS.setColumns(10);

		JLabel lblMinutes = new JLabel("Minutes");
		lblMinutes.setBounds(850, 111, 114, 15);
		contentPane.add(lblMinutes);

		txtMinutesS = new JTextField();
		txtMinutesS.setText("Minutes");
		txtMinutesS.setBounds(850, 138, 114, 19);
		contentPane.add(txtMinutesS);
		txtMinutesS.setColumns(10);

		JLabel lblPaid = new JLabel("Paid");
		lblPaid.setBounds(976, 111, 69, 15);
		contentPane.add(lblPaid);

		txtPaidS = new JTextField();
		txtPaidS.setText("Paid");
		txtPaidS.setBounds(976, 138, 114, 19);
		contentPane.add(txtPaidS);
		txtPaidS.setColumns(10);

		JLabel lblComplaintS = new JLabel("Complaint");
		lblComplaintS.setBounds(850, 169, 114, 15);
		contentPane.add(lblComplaintS);

		txtpnComplaintS = new JTextPane();
		txtpnComplaintS.setText("Complaint");
		txtpnComplaintS.setBounds(850, 196, 366, 135);
		contentPane.add(txtpnComplaintS);

		JLabel lblTreatment = new JLabel("Treatment");
		lblTreatment.setBounds(850, 343, 114, 15);
		contentPane.add(lblTreatment);

		txtpnTreatmentS = new JTextPane();
		txtpnTreatmentS.setText("Treatment");
		txtpnTreatmentS.setBounds(850, 370, 366, 135);
		contentPane.add(txtpnTreatmentS);

		JLabel lblOtherNotesS = new JLabel("Other Notes");
		lblOtherNotesS.setBounds(850, 521, 114, 15);
		contentPane.add(lblOtherNotesS);

		txtpnOtherNotesS = new JTextPane();
		txtpnOtherNotesS.setText("Other Notes");
		txtpnOtherNotesS.setBounds(850, 548, 366, 87);
		contentPane.add(txtpnOtherNotesS);

		JButton btnNext = new JButton("Next");
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				charts.nextSession();
				refreshSession();
			}
		});
		btnNext.setBounds(1100, 48, 116, 25);
		contentPane.add(btnNext);

		JButton btnPrevious = new JButton("Previous");
		btnPrevious.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				charts.previousSession();
				refreshSession();
			}
		});
		btnPrevious.setBounds(1100, 77, 116, 25);
		contentPane.add(btnPrevious);

		JButton btnFirst = new JButton("First");
		btnFirst.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				charts.firstSession();
				refreshSession();
			}
		});
		btnFirst.setBounds(1100, 106, 116, 25);
		contentPane.add(btnFirst);

		JButton btnLast = new JButton("Last");
		btnLast.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				charts.lastSession();
				refreshSession();
			}
		});
		btnLast.setBounds(1102, 135, 116, 25);
		contentPane.add(btnLast);

		JButton btnNewS = new JButton("New");
		btnNewS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				charts.newSession();
				refreshSession();
			}
		});
		btnNewS.setBounds(850, 649, 116, 25);
		contentPane.add(btnNewS);

		JButton btnSubmitS = new JButton("Submit");
		btnSubmitS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				charts.setSesDate(Date.valueOf(txtDateS.getText()));
				charts.setSesTime(txtTimeS.getText());
				charts.setSesMinutes(Integer.parseInt(txtMinutesS.getText()));
				charts.setSesPaid(Integer.parseInt(txtPaidS.getText()));
				charts.setSesComplaint(txtpnComplaintS.getText());
				charts.setSesTreatment(txtpnTreatmentS.getText());
				charts.setSesNotes(txtpnOtherNotesS.getText());
				try {
					charts.submitSession();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				refreshSession();
			}
		});
		btnSubmitS.setBounds(976, 649, 116, 25);
		contentPane.add(btnSubmitS);

		JButton btnRemove = new JButton("Remove");
		btnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					charts.removeCondition(txtCondition.getText());
				} catch (NullPointerException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRemove.setBounds(689, 516, 116, 25);
		contentPane.add(btnRemove);

		JLabel lblSession = new JLabel("Session");
		lblSession.setHorizontalAlignment(SwingConstants.CENTER);
		lblSession.setFont(new Font("Dialog", Font.BOLD, 16));
		lblSession.setBounds(850, 12, 110, 29);
		contentPane.add(lblSession);

		lblIgnoreC = new JLabel("Ignore");
		lblIgnoreC.setBounds(20, 343, 110, 15);
		contentPane.add(lblIgnoreC);

		JButton btnRefreshC = new JButton("Refresh");
		btnRefreshC.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					charts.refreshArray();
					refreshCard();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRefreshC.setBounds(278, 264, 116, 25);
		contentPane.add(btnRefreshC);

		JButton btnRefreshQ = new JButton("Refresh");
		btnRefreshQ.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					charts.getNewQueue();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				refreshTop();
			}
		});
		btnRefreshQ.setBounds(268, 582, 116, 25);
		contentPane.add(btnRefreshQ);
		
		txtCondition = new JTextField();
		txtCondition.setText("Condition");
		txtCondition.setBounds(439, 551, 114, 19);
		contentPane.add(txtCondition);
		txtCondition.setColumns(10);
		
		txtDescription = new JTextField();
		txtDescription.setText("Description");
		txtDescription.setBounds(565, 551, 240, 19);
		contentPane.add(txtDescription);
		txtDescription.setColumns(10);
		
		JButton btnViewC_1 = new JButton("View");
		btnViewC_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					charts.getClientQ();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				refreshClient();
			}
		});
		btnViewC_1.setBounds(268, 427, 116, 25);
		contentPane.add(btnViewC_1);
	}

	private void refreshCard() {
		txtClientIdC.setText(String.valueOf(charts.getCardClientID()));
		txtFirstNameC.setText(charts.getCardFName());
		txtLastNameC.setText(charts.getCardLName());
		txtPhoneC.setText(charts.getCardPhone());
		txtEmailC.setText(charts.getCardEmail());
		txtLastSessionC.setText(charts.getCardLastSession().toString());
		txtLastContactC.setText(charts.getCardLastContact().toString());
		txtSessionsC.setText(String.valueOf(charts.getCardSessions()));
		txtPaidC.setText(String.valueOf(charts.getCardPaid()));
		txtDateOfBirthC.setText(charts.getCardDob().toString());
		if (charts.getCardIgnore()) {
			lblIgnoreC.setText("Ignored");
		} else {
			lblIgnoreC.setText("Not Ignored");
		}
	}

	private void refreshTop() {
		txtClientIdQ.setText(String.valueOf(charts.getTopClientID()));
		txtFirstNameQ.setText(charts.getTopFName());
		txtLastNameQ.setText(charts.getTopLName());
		txtPhoneQ.setText(charts.getTopPhone());
		txtEmailQ.setText(charts.getTopEmail());
		txtLastSessionQ.setText(charts.getTopLastSession().toString());
		txtLastContactQ.setText(charts.getTopLastContact().toString());
		txtSessionsQ.setText(String.valueOf(charts.getTopSessions()));
		txtPaidQ.setText(String.valueOf(charts.getTopPaid()));
		txtDateOfBirthQ.setText(charts.getTopDob().toString());
	}

	private void refreshClient() {
		try {
			txtClientId.setText(String.valueOf(charts.getCliClientID()));
			txtFirstName.setText(charts.getCliFName());
			txtLastName.setText(charts.getCliLName());
			txtPhone.setText(charts.getCliPhone());
			txtEmail.setText(charts.getCliEmail());
			txtStreet.setText(charts.getCliStreet());
			txtCity.setText(charts.getCliCity());
			txtState.setText(charts.getCliState());
			txtZip.setText(charts.getCliZip());
			txtDateOfBirth.setText(String.valueOf(charts.getCliDob()));
			txtLastUpdated.setText(String.valueOf(charts.getCliDate()));
			txtOccupation.setText(charts.getCliOccupation());
			txtReferredBy.setText(String.valueOf(charts.getCliReferredBy()));
			txtPhysician.setText(String.valueOf(charts.getCliPhysicianID()));
			txtpnAccidentsOrSurgeries.setText(charts.getCliAcdntSgrs());
			txtpnAllergies.setText(charts.getCliAllergies());
			txtpnConditions.setText(null);
			StyledDocument txt = txtpnConditions.getStyledDocument();
			Map<String, String> conds = charts.getCliConds();
			for (Map.Entry<String, String> cond : conds.entrySet()) {
				txt.insertString(txt.getLength(), cond.getKey().toString() + ", " + cond.getValue() + "\n", null);
			}
			chckbxIgnore.setSelected(charts.getCliIgnore());
			refreshSession();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void refreshSession() {
		try {
			txtDateS.setText(String.valueOf(charts.getSesDate()));
			txtTimeS.setText(charts.getSesTime());
			txtMinutesS.setText(String.valueOf(charts.getSesMinutes()));
			txtPaidS.setText(String.valueOf(charts.getSesPaid()));
			txtpnComplaintS.setText(charts.getSesComplaint());
			txtpnTreatmentS.setText(charts.getSesTreatment());
			txtpnOtherNotesS.setText(charts.getSesNotes());
		} catch (Exception el) {
			el.printStackTrace();
		}

	}
}
