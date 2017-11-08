package cs.news;

import java.io.IOException;

import javax.swing.JOptionPane;

import cs.news.announce.AnnounceManager;
import cs.news.swing.TrayIcon;
import cs.news.teachers.TeacherManager;
import cs.news.util.BatchWriter;
import cs.news.util.Options;

public class Main {

	public static void main(String arguments[]) throws IOException {
		checkOSCompatibility();
		AnnounceManager.loadAnnounces();
		TeacherManager.Initialize();
		TrayIcon.getInstance();
		showHelloMessage(arguments);
		new Timer();
		if (Options.WINDOWS_STARTUP.toBoolean())
			BatchWriter.writeBatch();
	}

	private static void checkOSCompatibility() {
		String OS = System.getProperty("os.name");
		if (!OS.toLowerCase().contains("windows")) {
			JOptionPane.showMessageDialog(null, "This program is not compatible with your Operating System",
					"OS Failure", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	private static void showHelloMessage(String[] arguments) {
		//Check if runs in silent mode
		boolean showMessage = true;
		for (String s : arguments) {
			if (s.contains("sil"))
				showMessage = false;
		}
		if (showMessage)
			TrayIcon.getInstance()
					.showMessage("��������� ����������� Tray",
							"��� ���� ��� ���������� ��� ����������� (www.cs.uoi.gr) "
									+ "�� ������ ����� ���������� ��� �� ��������� ��� ����� \"���� ������������\".",
							false);
	}
}
