package com.example.myproject;

public class Job {

    private String jobID;
    private String job;

    private String jobDesc;
private String jobDate;
private String jobTime;
private String jobPrice;private String jobCategory;

    public Job(){

    }

    public Job(String jobID,String jobCategory,String job,String jobDesc,String jobDate,String jobTime,String jobPrice) {
        this.jobID=jobID;
        this.jobCategory=jobCategory;
        this.job = job;
        this.jobDesc=jobDesc;
        this.jobDate=jobDate;
        this.jobTime=jobTime;
        this.jobPrice=jobPrice;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public String getJobPrice() {
        return jobPrice;
    }

    public void setJobPrice(String jobPrice) {
        this.jobPrice = jobPrice;
    }

    public String getJobTime() {
        return jobTime;
    }

    public void setJobTime(String jobTime) {
        this.jobTime = jobTime;
    }

    public String getJobDate() {
        return jobDate;
    }

    public void setJobDate(String jobDate) {
        this.jobDate = jobDate;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getJob() {
        return job;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public void setJob(String job) {
        this.job = job;
    }
    //    public Job(String job) {
//        this.job = job;
//    }
//
//    public String getJob() {
//        return job;
//    }
//
//    public void setJob(String job) {
//        this.job = job;
//    }
}
