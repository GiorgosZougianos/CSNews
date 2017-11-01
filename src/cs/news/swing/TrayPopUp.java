package cs.news.swing;

import java.awt.CheckboxMenuItem;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import cs.news.Announce;
import cs.news.AnnounceManager;
import cs.news.Main;
import cs.news.util.BatchWriter;

public class TrayPopUp extends PopupMenu {
	private static final long serialVersionUID = -1233037255379467666L;
	private static final int MAX_CHARACTERS_IN_MENU_ITEMS = 70;

	public TrayPopUp() {
		super();
		SeperatedPopUpMenu genika = new SeperatedPopUpMenu("������");
		SeperatedPopUpMenu foititwn = new SeperatedPopUpMenu("��������");
		SeperatedPopUpMenu omilies = new SeperatedPopUpMenu("�������/���������");
		SeperatedPopUpMenu prokiriksis = new SeperatedPopUpMenu("�����������");
		SeperatedPopUpMenu adiavasta = new SeperatedPopUpMenu("���� ������������");
		for (Announce a : AnnounceManager.announces.getSortedAnnounces()) {
			MenuItem m = new MenuItem(adjustTitleLength(a));
			m.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					AnnounceManager.openAnnouncement(a);
					if (!a.isRead())
						a.setRead(true);
					TrayIcon.getInstance().reBuild();
				}
			});
			if (a.isColorized())
				m.setFont(new Font(null, Font.BOLD, 12));
			if (!a.isRead()) {
				adiavasta.addItem(m);
				continue;
			}
			if (a.getType().contains("������")) {
				genika.addItem(m);
			} else if (a.getType().contains("��������")) {
				foititwn.addItem(m);
			} else if (a.getType().contains("�����������")) {
				prokiriksis.addItem(m);
			} else {
				omilies.addItem(m);
			}
		}
		//If there is nothing new, add an item to inform user.
		if (adiavasta.getItemCount() <= 1) { //Only the first seperator
			adiavasta.removeAll();
			MenuItem nothing = new MenuItem("����� ��� ����������");
			nothing.setEnabled(false);
			adiavasta.add(nothing);
		}
		MenuItem markarisma = new MenuItem("���������� ���� �� �����������");
		markarisma.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Announce a : AnnounceManager.announces.values()) {
					a.setRead(true);
				}
				AnnounceManager.saveAnnounces();
				TrayIcon.getInstance().reBuild();
			}
		});

		MenuItem eksodos = new MenuItem("������");
		eksodos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AnnounceManager.saveAnnounces();
				System.exit(0);
			}
		});
		MenuItem arxikh = new MenuItem("������� ������� (www.cs.uoi.gr)");
		arxikh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					Runtime.getRuntime().exec(new String[] { "cmd", "/c", ("start chrome www.cs.uoi.gr") });
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		CheckboxMenuItem windowsStartUp = new CheckboxMenuItem("�������� �������� ���� �� �� Windows");
		windowsStartUp.setState(Main.PREFERENCES.getBoolean("WINDOWSSTARTUP", true));
		windowsStartUp.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Main.PREFERENCES.putBoolean("WINDOWSSTARTUP", windowsStartUp.getState());
				if (windowsStartUp.getState())//true, add the registry
					BatchWriter.writeBatch();
				else
					BatchWriter.deleteBatch();
			}
		});
		add(arxikh);
		addSeparator();
		add(adiavasta);
		addSeparator();
		add(genika);
		add(foititwn);
		add(omilies);
		add(prokiriksis);
		addSeparator();
		add(windowsStartUp);
		add(markarisma);
		add(eksodos);
	}

	private static String adjustTitleLength(Announce a) {
		String title = a.getDate();
		title += " - " + a.getTitle();
		if (title.length() > MAX_CHARACTERS_IN_MENU_ITEMS) {
			title = title.substring(0, Math.min(title.length(), MAX_CHARACTERS_IN_MENU_ITEMS));
			title += "...";
		}
		return title;
	}
}