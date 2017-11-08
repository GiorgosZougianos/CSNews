package cs.news.tasks;

import java.io.IOException;
import java.util.TimerTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cs.news.Announce;
import cs.news.AnnounceManager;
import cs.news.swing.TrayIcon;

public class ReadAnnouncesTask extends TimerTask {
	private static final String NEWS_HOMEPAGE = "http://cs.uoi.gr/index.php?menu=m5&page=";

	@Override
	public void run() {
		int pageNumber = 1;
		int extracts = 0;
		int announcesRead = 0;
		while (announcesRead < AnnounceManager.MAX_ANNOUNCEMENTS) {
			Document document;
			try {
				final String currentPageLink = NEWS_HOMEPAGE + pageNumber;
				document = Jsoup.connect(currentPageLink).get();
				Elements divs = document.getElementsByClass("newPaging");
				for (Element div : divs) {
					Element hyperLink = div.select("a").first();
					boolean color = hyperLink.select("font").first() != null;
					String date = div.select("h3").first().text().split(" - ")[0];
					String title = hyperLink.text();
					int id = Integer.parseInt(hyperLink.attr("href").split("id=")[1]);
					String pdf = getPDFlink("http://cs.uoi.gr/index.php?menu=m58&id=" + id);
					Announce a = new Announce(date, title, id, false, color, pdf);
					announcesRead++;
					if (announcesRead > AnnounceManager.MAX_ANNOUNCEMENTS)
						break;
					if (!AnnounceManager.announceAlreadyExists(a)) {
						extracts++;
						AnnounceManager.announces.add(a);
					}
				}
				pageNumber++;//next Page
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		AnnounceManager.removeReadAnnounces();
		AnnounceManager.saveAnnounces();
		if (extracts > 0) {
			TrayIcon.getInstance().showMessage("CS News",
					extracts + (extracts == 1 ? " ��� ����������!" : " ���� ������������!"), true);
		}
		TrayIcon.getInstance().reBuild();
	}

	private String getPDFlink(String announceLink) throws IOException {
		Document doc = Jsoup.connect(announceLink).get();
		Element attached = doc.getElementsByClass("newsMoreAttached").first();
		String href = attached.select("a").attr("href");
		return "cs.uoi.gr" + href;
	}
}
