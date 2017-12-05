package cs.news.swing;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import cs.news.datamanagers.ScheduleManager;
import cs.news.datamanagers.ScheduleManager.Schedule;
import cs.news.util.Debugger;

public class SchedulesPopUpMenu extends PopupMenu {
	private static final long serialVersionUID = -8038954148162824287L;

	public SchedulesPopUpMenu() {
		super("������� ������������");
		for (Schedule s : Schedule.class.getEnumConstants()) {
			String menuName = s.getKey().substring("��������� ".length());
			MenuItem m = new MenuItem(menuName);
			m.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						s.open();
					} catch (IOException e1) {
						showUnavailableScheduleMsg();
						Debugger.showException(e1, getClass(), false);
					}
				}
			});
			add(m);
		}
		MenuItem feedScheduleMenu = new MenuItem("�������");
		feedScheduleMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					ScheduleManager.openFeedingSchedule();
				} catch (IOException e) {
					showUnavailableScheduleMsg();
					Debugger.showException(e, getClass(), false);
				}
			}
		});
		add(feedScheduleMenu);
	}

	private static void showUnavailableScheduleMsg() {
		TrayIcon.getInstance().showMessage("������� ������������", "�� ��������� ��� ����� ��������� ���� �� ������.", true);
	}
}
