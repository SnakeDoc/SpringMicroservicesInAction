package com.thoughtmechanix.licenses.model;

public class License {

	private String id;
	private String productName;
	private String licenseType;
	private String organizationId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getLicenseType() {
		return licenseType;
	}
	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}
	
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	
	// builders
	public License withId(String id) {
		this.setId(id);
		return this;
	}
	
	public License withProductName(String productName) {
		this.setProductName(productName);
		return this;
	}
	
	public License withLicenseType(String licenseType) {
		this.setLicenseType(licenseType);
		return this;
	}
	
	public License withOrganizationId(String organizationId) {
		this.setOrganizationId(organizationId);
		return this;
	}
	
}
