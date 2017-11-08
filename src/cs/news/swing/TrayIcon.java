package cs.news.swing;

import static cs.news.util.InternetChecker.AvailableInternetConnection;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import cs.news.AnnounceManager;

public class TrayIcon extends java.awt.TrayIcon {
	private static final String TRAY_NAME = "CSE News";

	public TrayIcon() {
		super(createImage(""));
		setImageAutoSize(true);
		reBuild();
		addToSystem();
	}

	public void reBuild() {
		setPopupMenu(new TrayPopUp());
		if (!AvailableInternetConnection()) {
			setToolTip("��� ������� ������� ��� Internet.");
			setImage(createImage("--"));
		} else {
			int unread = AnnounceManager.getNumOfUnreadAnnounces();
			setToolTip(TRAY_NAME + "\n" + +unread + (unread == 1 ? " ��� ����������" : " ���� ������������"));
			setImage(createImage(unread == 0 ? "" : String.valueOf(unread)));
		}
	}

	private static Image createImage(String writtenText) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(TrayIcon.class.getResourceAsStream("/cs/news/resources/favicon.png"));
			Graphics g = img.getGraphics();
			g.setFont(new Font("Arial", Font.PLAIN | Font.BOLD, 452));
			g.setColor(Color.ORANGE);
			g.drawString(writtenText, -10, 380);
			g.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public void showMessage(String title, String message, boolean isWarning) {
		displayMessage(title, message, isWarning ? TrayIcon.MessageType.WARNING : TrayIcon.MessageType.INFO);
	}

	private void addToSystem() {
		try {
			if (SystemTray.isSupported()) {
				SystemTray traySystem = SystemTray.getSystemTray();
				traySystem.add(this);
			}
		} catch (Exception e) {
			System.out.println("Error adding the tray.");
		}
	}

	public static TrayIcon getInstance() {
		return SingletonHolder._instance;
	}

	private static class SingletonHolder {
		protected static final TrayIcon _instance = new TrayIcon();
	}
}
