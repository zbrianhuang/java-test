package webScraper;

public class Course {
	private String courseID;
	private String courseName;
	private String[] genEds;
	private int credits;
	private Section[] sections;
	private String description;
	
	
	class Section{
		private boolean virtual;
		private int sectionNum;
		private String prof;
		private String[] startTimes;
		private String[] endTimes;
		private String[] locations;
		private String meetingDays;
		
		public Section(boolean v, int sn, String[] sT, String[] eT, String[] l, String mD) {
			this.virtual = v;
			this.sectionNum=sn;
			this.startTimes = sT;
			this.endTimes = eT;
			this.locations = l;
			this.meetingDays = mD;
		}
	}
	public Course(String courseID, String courseName, String[] genEds, int credits, Section[] sections, String description) {
		this.courseID = courseID;
		this.courseName = courseName;
		this.genEds = genEds;
		this.credits = credits;
		this.sections = sections;
		this.description = description;
		
		
	}
}
