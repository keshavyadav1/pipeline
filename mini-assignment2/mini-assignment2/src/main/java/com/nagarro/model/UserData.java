package com.nagarro.model;

public class UserData {
    String gender;
    NameData name;
    DOB dob;
    String nat;
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public NameData getName() {
		return name;
	}
	public void setName(NameData name) {
		this.name = name;
	}
	public DOB getDob() {
		return dob;
	}
	public void setDob(DOB dob) {
		this.dob = dob;
	}
	public String getNat() {
		return nat;
	}
	public void setNat(String nat) {
		this.nat = nat;
	}
	
	
	
	@Override
	public String toString() {
		return "UserData [gender=" + gender + ", name=" + name + ", dob=" + dob + ", nat=" + nat + "]";
	}



	public static class NameData{
		String title;
		String first;
		String last;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getFirst() {
			return first;
		}
		public void setFirst(String first) {
			this.first = first;
		}
		public String getLast() {
			return last;
		}
		public void setLast(String last) {
			this.last = last;
		}
		@Override
		public String toString() {
			return "NameData [title=" + title + ", first=" + first + ", last=" + last + "]";
		}
		
		
	}
	
    public static class DOB{
    	String date;
    	Integer age;
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		@Override
		public String toString() {
			return "DOB [date=" + date + ", age=" + age + "]";
		}
    	
    	
    	
    }
    

}
