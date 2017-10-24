package utility;

public class Statistics {
	private int year;
	private int count_value;
	private int sectionID;
	private int month;
	
	public void initPerYearPostStats(int year, int count_value) {
		this.year = year;
		this.count_value = count_value;
	}
	
	public void initSectionsSortedByPopularity(int sectionID, int count_value) {
		this.sectionID = sectionID;
		this.count_value = count_value;
	}

	public void initPerMonthSectionActivity(int month, int count_value) {
		this.month = month; 
		this.count_value = count_value;
	}
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getCount_value() {
		return count_value;
	}

	public void setCount_value(int count_value) {
		this.count_value = count_value;
	}

	public int getSectionID() {
		return sectionID;
	}

	public void setSectionID(int sectionID) {
		this.sectionID = sectionID;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}
}
