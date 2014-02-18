package com.drjcfitz.rewardyourself.model;

public class Portal {
	
	private String profile;
	private String element;
	private String portalUrl;
	private String storesUrl;
	private String storeElement;
	private String merchElement;
	private String rewardElement;
	private String rewardRegexp;

	public Portal() {
		
	}
	
	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getMerchElement() {
		return merchElement;
	}

	public void setMerchElement(String merchElement) {
		this.merchElement = merchElement;
	}

	public String getPortalUrl() {
		return portalUrl;
	}

	public void setPortalUrl(String portalUrl) {
		this.portalUrl = portalUrl;
	}

	public String getRewardElement() {
		return rewardElement;
	}

	public void setRewardElement(String rewardElement) {
		this.rewardElement = rewardElement;
	}

	public String getStoreElement() {
		return storeElement;
	}

	public void setStoreElement(String storeElement) {
		this.storeElement = storeElement;
	}

	public String getRewardRegexp() {
		return rewardRegexp;
	}

	public void setRewardRegexp(String rewardRegexp) {
		this.rewardRegexp = rewardRegexp;
	}

	public String getStoresUrl() {
		return storesUrl;
	}

	public void setStoresUrl(String storesUrl) {
		this.storesUrl = storesUrl;
	}


}
