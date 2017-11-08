package cs.news.util;

import java.util.prefs.Preferences;

public enum Options {
	//@formatter:off
	INDEX_OF_LAST_TEACHER_VISITED(-1),
	LAST_TIME_TEACHERS_SYNC(0), //The time of the last synchronize teachers
	WINDOWS_STARTUP(false); //Run app with windows
	//@formatter:on
	private String key;

	private Object defaultValue;

	private Options(Object defaultValue) {
		this.key = this.toString();
		this.defaultValue = defaultValue;
	}

	public boolean toBoolean() {
		return Boolean.parseBoolean(reloadValue());
	}

	public long toLong() {
		return Long.parseLong(reloadValue());
	}

	public int toInt() {
		return Integer.parseInt(reloadValue());
	}

	public String reloadValue() {
		return PrefsHolder.PREFERENCES.get(key, String.valueOf(defaultValue));
	}

	public void update(Object newValue) {
		PrefsHolder.PREFERENCES.put(key, String.valueOf(newValue));
	}

	private static final class PrefsHolder {
		private static final Preferences PREFERENCES = Preferences.userNodeForPackage(PrefsHolder.class);
	}

}
