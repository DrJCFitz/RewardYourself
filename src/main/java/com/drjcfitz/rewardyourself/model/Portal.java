package com.drjcfitz.rewardyourself.model;

import java.util.List;

public class Portal {

	private String portalKey;
	private String portalName;
	private Double equivalentPercentage;
	private List<Merchant> merchantList;

	public Portal() {
	}

	public Portal(String portalName, Double equivalentPercentage,
			List<Merchant> merchantList) {
		super();
		this.portalName = portalName;
		this.equivalentPercentage = equivalentPercentage;
		this.merchantList = merchantList;
	}

	public Portal(String portalName, String portalKey,
			Double equivalentPercentage, List<Merchant> merchantList) {
		super();
		this.portalName = portalName;
		this.portalKey = portalKey;
		this.equivalentPercentage = equivalentPercentage;
		this.merchantList = merchantList;
	}

	public String getPortalKey() {
		return portalKey;
	}

	public void setPortalKey(String portalKey) {
		this.portalKey = portalKey;
	}

	public Double getEquivalentPercentage() {
		return equivalentPercentage;
	}

	public void setEquivalentPercentage(Double equivalentPercentage) {
		this.equivalentPercentage = equivalentPercentage;
	}

	public List<Merchant> getMerchantList() {
		return merchantList;
	}

	public void setMerchantList(List<Merchant> merchantList) {
		this.merchantList = merchantList;
	}

	public String getPortalName() {
		return portalName;
	}

	public void setPortalName(String portalName) {
		this.portalName = portalName;
	}

	@Override
	public String toString() {
		return "Portal [portalName=" + portalName + ", equivalentPercentage="
				+ equivalentPercentage + ", merchantList=" + merchantList + "]";
	}

}
