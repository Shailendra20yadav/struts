package generated;

import java.text.SimpleDateFormat;
import java.util.Date;


public class RunBookEntry {
	
	private String jobName;
	private String jobType="wcs_overnight_jobs.sh";
	private Date jobStartTime;
	private Date jobEndTime;
	private String timeTaken;
	private String status="Successful";
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public RunBookEntry(){
		super();
		this.jobName = new String();
		this.jobStartTime = new Date();
		this.jobEndTime = new Date();
		this.timeTaken = new String();
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public Date getJobStartTime() {
		return jobStartTime;
	}
	public void setJobStartTime(Date jobStartTime) {
		this.jobStartTime = jobStartTime;
	}
	public Date getJobEndTime() {
		return jobEndTime;
	}
	public void setJobEndTime(Date jobEndTime) {
		this.jobEndTime = jobEndTime;
	}
	public String getTimeTaken() {
		return timeTaken;
	}
	public void setTimeTaken(String timeTaken) {
		this.timeTaken = timeTaken;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("Jobname: %s, Job Start Time: %s, Job End Time: %s, Job Time Taken: %s",this.getJobName(),displayDateTime(this.getJobStartTime()), displayDateTime(this.getJobEndTime()),this.getTimeTaken()  );
	}
	private String displayDateTime(Date jobStartTime2) {
		SimpleDateFormat dt2 = new SimpleDateFormat("dd mm yyyy HH:mm:ss");
		return dt2.format(jobStartTime2).toString();
	}
	
	
	

}
