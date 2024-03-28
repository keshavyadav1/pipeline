package com.nagarro.model;

import java.util.List;

public class Nationality {

	Integer count;
	String name;
	List<Country> country;
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Country> getCountry() {
		return country;
	}
	public void setCountry(List<Country> country) {
		this.country = country;
	}
	
	public static class Country{
		String country_id;
		Float probability;
		public String getCountry_id() {
			return country_id;
		}
		public void setCountry_id(String country_id) {
			this.country_id = country_id;
		}
		public Float getProbability() {
			return probability;
		}
		public void setProbability(Float probability) {
			this.probability = probability;
		}
		
		
	}
}
