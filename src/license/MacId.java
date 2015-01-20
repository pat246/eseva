package license;

public enum MacId {
	ANANDRAO_THORAT("94-DE-80-54-70-CC\000"), PRASHANT_THORAT("28-37-37-22-e4-3c");

	private String madId;

	private MacId(String macId) {
		this.madId = macId;
	}

	public String getMadId() {
		return this.madId;
	}

	public void setMadId(String madId) {
		this.madId = madId;
	}
}
