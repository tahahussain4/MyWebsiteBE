package model;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.mongodb.Mongo;

public class Project {
	ObjectId id;
	String name;
	ArrayList<String> skills;
	String description;
	Integer yearFrom;
	Integer yearTo;
	
	public Project(String name,ArrayList<String> skills, String description,int yearFrom,int yearTo,ObjectId id) {
		super();
		this.id = id;
		this.name = name;
		this.skills = skills;
		this.description = description;
		this.yearFrom = yearFrom;
		this.yearTo = yearTo;
	}
	
	public Project() {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<String> getSkills() {
		return skills;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setSkills(ArrayList<String> skills) {
		this.skills = skills;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Number getYearFrom() {
		return yearFrom;
	}

	public void setYearFrom(Integer yearFrom) {
		this.yearFrom = yearFrom;
	}

	public Number getYearTo() {
		return yearTo;
	}

	public void setYearTo(Integer yearTo) {
		this.yearTo = yearTo;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((skills == null) ? 0 : skills.hashCode());
		result = prime * result + ((yearFrom == null) ? 0 : yearFrom.hashCode());
		result = prime * result + ((yearTo == null) ? 0 : yearTo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (skills == null) {
			if (other.skills != null)
				return false;
		} else if (!skills.equals(other.skills))
			return false;
		if (yearFrom == null) {
			if (other.yearFrom != null)
				return false;
		} else if (!yearFrom.equals(other.yearFrom))
			return false;
		if (yearTo == null) {
			if (other.yearTo != null)
				return false;
		} else if (!yearTo.equals(other.yearTo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", skills=" + skills + ", description="
				+ description + "]";
	}
	
}
