package domain.frontend;

import java.util.ArrayList;
import java.util.List;

public class SearchResultFrontend {

	private String type;
	private List<Object> results;

	public SearchResultFrontend() {
		super();
		this.type = "";
		this.results = new ArrayList<>();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Object> getResults() {
		return results;
	}

	public void setResults(List<Object> results) {
		this.results = results;
	}

}
