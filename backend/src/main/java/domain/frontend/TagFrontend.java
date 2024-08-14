package domain.frontend;

import domain.Tag;

public class TagFrontend {
	private String name;
	private String value;
	
	
	
	public TagFrontend(Tag e) {
		super();
		this.name = e.getName();
		this.value = e.getValue();
	}
	public TagFrontend() {
		super();
		this.name="";
		this.value="";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	

}
