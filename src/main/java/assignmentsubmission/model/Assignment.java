package assignmentsubmission.model;

public class Assignment {
	
	  	private int aid;
	  	private String stdname;
	  	private String aName;
	  	private String date;
	  	
		public Assignment(int aid, String stdname, String aName, String date) {
			
			super();
			this.aid = aid;
			this.stdname = stdname;
			this.aName = aName;
			this.date = date;
		}

		public int getAid() {
			return aid;
		}

		public String getStdname() {
			return stdname;
		}

		public String getaName() {
			return aName;
		}

		public String getDate() {
			return date;
		}


	  	
}