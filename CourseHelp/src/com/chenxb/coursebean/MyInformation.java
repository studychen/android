package com.chenxb.coursebean;

public class MyInformation {
	    private final String number; 
	    private final String name; 
	    private final String colleage;      
	    private final String subject;      
	    private final String teacher;      
	    private final String requirePoint;      //额定学分： 30.0     已完成：2.5
	    private final String completePoint;      
	    private final String requireDegreePoint;      
	    private final String completeDegreePoint;      //学位课额定学分：19.0    已完成：2.0
	    private final String gpa;      //平均分


	    public static class Builder{
	    	
	  	    private  String number;  
	  	    private  String name; 
	  	    private  String colleage;      
	  	    private  String subject;      
	  	    private  String teacher;      
	  	    private  String requirePoint;      //额定学分： 30.0     已完成：2.5
	  	    private  String completePoint;      
	  	    private  String requireDegreePoint;      
	  	    private  String completeDegreePoint;      //学位课额定学分：19.0    已完成：2.0
	  	    private  String gpa;      //学位课额定学分：19.0    已完成：2.0
	 
	         public Builder(String number){
	             this.number = number;
	         }
	 
	         public Builder name(String name){this.name = name; return this;}
	         public Builder number(String number){this.number = number; return this;}
	         public Builder colleage(String colleage){this.colleage = colleage; return this;}
	         public Builder subject(String subject){this.subject = subject; return this;}
	         public Builder teacher(String teacher){this.teacher = teacher; return this;}
	         public Builder requirePoint(String requirePoint){this.requirePoint = requirePoint; return this;}
	         public Builder completePoint(String completePoint){this.completePoint = completePoint; return this;}
	         public Builder requireDegreePoint(String requireDegreePoint){this.requireDegreePoint = requireDegreePoint; return this;}
	         public Builder completeDegreePoint(String completeDegreePoint){this.completeDegreePoint = completeDegreePoint; return this;}
	         public Builder gpa(String gpa){this.gpa = gpa; return this;}
	 
	         public MyInformation builder(){
	             return new MyInformation(this);
	         }

			@Override
			public String toString() {
				return "Builder [学号=" + number + ", 姓名=" + name
						+  ", 学院=" + colleage + ", 专业=" + subject
						+ ", 导师=" + teacher  
						+ ", 额定学分=" + requirePoint
						+ ", 已完成=" + completePoint
						+ ", 学位课额定学分=" + requireDegreePoint
						+ ",  已完成=" + completeDegreePoint  
						+ ",  平均分=" + gpa + "]";
			}
	    }
	    
	    private MyInformation(Builder builder){
		  	number = builder.number;
		  	name = builder.name;
		  	colleage = builder.colleage;
		  	subject = builder.subject;
		  	teacher = builder.teacher;
		  	requirePoint = builder.requirePoint;
		  	completePoint = builder.completePoint;
		  	requireDegreePoint = builder.requireDegreePoint;
		  	completeDegreePoint = builder.completeDegreePoint;
		  	gpa = builder.gpa;
        }

		public String getNumber() {
			return number;
		}

		public String getName() {
			return name;
		}

		public String getColleage() {
			return colleage;
		}

		public String getSubject() {
			return subject;
		}

		public String getTeacher() {
			return teacher;
		}

		public String getRequirePoint() {
			return requirePoint;
		}

		public String getCompletePoint() {
			return completePoint;
		}

		public String getRequireDegreePoint() {
			return requireDegreePoint;
		}

		public String getCompleteDegreePoint() {
			return completeDegreePoint;
		}

		public String getGpa() {
			return gpa;
		}

		@Override
		public String toString() {
			return "Builder [学号=" + number + ", 姓名=" + name
					+  ", 学院=" + colleage + ", 专业=" + subject
					+ ", 导师=" + teacher  
					+ ", 额定学分=" + requirePoint
					+ ", 已完成=" + completePoint
					+ ", 学位课额定学分=" + requireDegreePoint
					+ ",  已完成=" + completeDegreePoint  
					+ ",  平均分=" + gpa + "]";
		}

}
