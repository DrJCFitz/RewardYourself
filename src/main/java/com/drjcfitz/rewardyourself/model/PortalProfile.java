package com.drjcfitz.rewardyourself.model;

import java.util.HashMap;

public class PortalProfile {
	
	private String profileKey;
	private String portalName;
	private String rootElement;
	private String portalUrl;
	private String allStoresUrl;
	private String merchantElement;
	private String rewardElement;
	private HashMap<String,String> portalRegexp;

	public PortalProfile() {	
	}

	public String getRootElement() {
		return rootElement;
	}

	public void setRootElement(String rootElement) {
		this.rootElement = rootElement;
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

	public String getAllStoresUrl() {
		return allStoresUrl;
	}

	public void setAllStoresUrl(String allStoresUrl) {
		this.allStoresUrl = allStoresUrl;
	}

	public String getMerchantElement() {
		return merchantElement;
	}

	public void setMerchantElement(String merchantElement) {
		this.merchantElement = merchantElement;
	}

	public HashMap<String,String> getPortalRegexp() {
		return portalRegexp;
	}

	public void setPortalRegexp(HashMap<String,String> portalRegexp) {
		this.portalRegexp = portalRegexp;
	}

	public String getPortalName() {
		return portalName;
	}

	public void setPortalName(String portalName) {
		this.portalName = portalName;
	}

	@Override
	public String toString() {
		return "PortalProfile [profileKey=" + profileKey + ", portalName="
				+ portalName + ", rootElement=" + rootElement + ", portalUrl="
				+ portalUrl + ", allStoresUrl=" + allStoresUrl
				+ ", merchantElement=" + merchantElement + ", rewardElement="
				+ rewardElement + ", portalRegexp=" + portalRegexp + "]";
	}

	public String getProfileKey() {
		return profileKey;
	}

	public void setProfileKey(String profileKey) {
		this.profileKey = profileKey;
	}
	
	
}
