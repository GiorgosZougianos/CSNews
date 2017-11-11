package cs.news.swing;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cs.news.Timer;
import cs.news.util.BatchWriter;
import cs.news.util.Options;

public class SettingsPanel extends JPanel {
	private static final long serialVersionUID = 6429776879534491034L;
	private static final Object[] DIALOG_OPTIONS = { "����������", "�������" };
	private static final Dimension PANEL_DIMENSION = new Dimension(265, 185);
	private JSpinner announceSpinner = new JSpinner();
	private JCheckBox openInPdfCheckBox = new JCheckBox();
	private JSpinner syncSpinner = new JSpinner();
	private JSpinner remindSpinner = new JSpinner();
	private JCheckBox windowsStartupCheckBox;

	public SettingsPanel() {
		super();
		setPreferredSize(PANEL_DIMENSION);
		setLayout(null);
		JTextField announceSettingsHeader;
		announceSettingsHeader = new JTextField();
		announceSettingsHeader.setEditable(false);
		announceSettingsHeader.setHorizontalAlignment(SwingConstants.CENTER);
		announceSettingsHeader.setText("��������� ������������");
		announceSettingsHeader.setBounds(63, 0, 132, 20);
		announceSettingsHeader.setBorder(BorderFactory.createEmptyBorder());
		add(announceSettingsHeader);
		announceSettingsHeader.setColumns(10);
		JTextField maxAnnouncesTxt;
		maxAnnouncesTxt = new JTextField();
		maxAnnouncesTxt.setHorizontalAlignment(SwingConstants.LEFT);
		maxAnnouncesTxt.setText("�������� �������:");
		maxAnnouncesTxt.setToolTipText("<html>���������� ������� �������� ��� ������� ������������ ������������."
				+ "<br>������������ ����: 10</html>");
		maxAnnouncesTxt.setEditable(false);
		maxAnnouncesTxt.setBorder(BorderFactory.createEmptyBorder());
		maxAnnouncesTxt.setBounds(6, 24, 201, 20);
		add(maxAnnouncesTxt);
		maxAnnouncesTxt.setColumns(10);

		announceSpinner.setBounds(217, 24, 41, 20);
		announceSpinner.setValue(Options.ANNOUNCES_MAX_NUMBER.toInt());
		announceSpinner.setToolTipText("<html>���������� ������� �������� ��� ������� ������������ ������������."
				+ "<br>������������ ����: 10</html>");
		add(announceSpinner);

		openInPdfCheckBox.setBounds(2, 48, 141, 20);
		openInPdfCheckBox.setSelected(Options.OPEN_ANNOUNCEMENTS_IN_PDF.toBoolean());
		openInPdfCheckBox.setText("������� �� ����� PDF.");
		openInPdfCheckBox.setToolTipText("<html>���� ���������� ������� ����������� ��� ������� ��� �����������."
				+ "<br>���������� ����� ��� ������� ��� ������� ��� ������������� �����������.</html>");
		add(openInPdfCheckBox);
		JTextField announcementSyncTxt = new JTextField("�������� ������������:");
		announcementSyncTxt.setToolTipText(
				"<html>���� ���� ����� �� ������� ������� ��� ���� ������������.<br>������������ ����: 30 �����</html>");
		announcementSyncTxt.setHorizontalAlignment(SwingConstants.LEFT);
		announcementSyncTxt.setEditable(false);
		announcementSyncTxt.setBounds(6, 70, 201, 20);
		announcementSyncTxt.setColumns(10);
		announcementSyncTxt.setBorder(BorderFactory.createEmptyBorder());
		add(announcementSyncTxt);

		syncSpinner.setBounds(217, 70, 41, 20);
		syncSpinner.setValue(Options.SYNC_ANNOUNCES_TIME.toInt());
		syncSpinner.setToolTipText(announcementSyncTxt.getToolTipText());//same tool tip as announcementSyncTxt
		add(syncSpinner);

		JTextField announcementRemindTxt = new JTextField("�������� �����������:");
		announcementRemindTxt.setToolTipText(
				"<html>���� ���� ����� �� ������� ���������� �� ��������� ���� ������������.<br>�������������� ��� ���� 0 ��� �������������� ������������.<br>������������ ���� 120 ����� (2 ����).");
		announcementRemindTxt.setEditable(false);
		announcementRemindTxt.setBounds(6, 91, 201, 20);
		announcementRemindTxt.setColumns(10);
		announcementRemindTxt.setBorder(BorderFactory.createEmptyBorder());
		add(announcementRemindTxt);

		remindSpinner.setBounds(217, 91, 41, 20);
		remindSpinner.setValue(Options.REMIND_ANNOUNCES_TIME.toInt());
		remindSpinner.setToolTipText(announcementRemindTxt.getToolTipText());//same tool tip as announcementRemindTxt
		add(remindSpinner);

		JTextField generalSettingsHeader = new JTextField("������� ���������");
		generalSettingsHeader.setHorizontalAlignment(SwingConstants.CENTER);
		generalSettingsHeader.setEditable(false);
		generalSettingsHeader.setBounds(63, 122, 132, 20);
		generalSettingsHeader.setBorder(BorderFactory.createEmptyBorder());
		generalSettingsHeader.setColumns(10);
		add(generalSettingsHeader);

		windowsStartupCheckBox = new JCheckBox("�������� �������� �� �� Windows.");
		windowsStartupCheckBox.setSelected(Options.WINDOWS_STARTUP.toBoolean());
		windowsStartupCheckBox.setBounds(6, 142, 201, 23);
		add(windowsStartupCheckBox);

	}

	public static void open() {
		SettingsPanel panel = new SettingsPanel();
		//Exit == 1, Save == 0
		int answer = JOptionPane.showOptionDialog(null, panel, "���������", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, DIALOG_OPTIONS, 1);
		if (answer == 0) {
			Options.ANNOUNCES_MAX_NUMBER.update(panel.getOptionMaxAnnouncements());
			Options.OPEN_ANNOUNCEMENTS_IN_PDF.update(panel.getOptionPDFAnnouncements());
			Options.SYNC_ANNOUNCES_TIME.update(panel.getOptionSyncAnnouncesTime());
			Options.REMIND_ANNOUNCES_TIME.update(panel.getOptionRemindAnnouncesTime());
			Options.WINDOWS_STARTUP.update(panel.getOptionWindowsStartUp());
			Timer.restart();
			BatchWriter.handleState();
		}
	}

	private int getOptionMaxAnnouncements() {
		return (int) announceSpinner.getValue();
	}

	private boolean getOptionPDFAnnouncements() {
		return openInPdfCheckBox.isSelected();
	}

	private int getOptionSyncAnnouncesTime() {
		return (int) syncSpinner.getValue();
	}

	private int getOptionRemindAnnouncesTime() {
		return (int) remindSpinner.getValue();
	}

	private boolean getOptionWindowsStartUp() {
		return windowsStartupCheckBox.isSelected();
	}
}
