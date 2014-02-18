package com.drjcfitz.rewardyourself.model;

import org.springframework.beans.factory.annotation.Autowired;

public class Merchant {

	private String name;
	private String storeLink;
	private String logoLink;
	private int id;
	private long refDate;
	private boolean enabled;

	@Autowired
	private Reward reward;

	public Merchant() {
	}

	public Merchant(String name, String storeLink, Reward reward, long refDate,
			boolean enabled) {
		super();
		this.name = name;
		this.storeLink = storeLink;
		this.reward = reward;
		this.refDate = refDate;
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStoreLink() {
		return storeLink;
	}

	public void setStoreLink(String storeLink) {
		this.storeLink = storeLink;
	}

	public String getLogoLink() {
		return logoLink;
	}

	public void setLogoLink(String logoLink) {
		this.logoLink = logoLink;
	}

	public long getRefDate() {
		return refDate;
	}

	public void setRefDate(long refDate) {
		this.refDate = refDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (refDate ^ (refDate >>> 32));
		result = prime * result + ((reward == null) ? 0 : reward.hashCode());
		result = prime * result
				+ ((storeLink == null) ? 0 : storeLink.hashCode());
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
		Merchant other = (Merchant) obj;
		if (enabled != other.enabled)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (refDate != other.refDate)
			return false;
		if (reward == null) {
			if (other.reward != null)
				return false;
		} else if (!reward.equals(other.reward))
			return false;
		if (storeLink == null) {
			if (other.storeLink != null)
				return false;
		} else if (!storeLink.equals(other.storeLink))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Merchant [name=" + name + ", storeLink=" + storeLink
				+ ", reward=" + reward + ", refDate=" + refDate + ", enabled="
				+ enabled + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Reward getReward() {
		return reward;
	}

	public void setReward(Reward reward) {
		this.reward = reward;
	}

	// Convenience methods to keep one bean
	public Double getRewardValue() {
		return reward.getRewardValue();
	}

	@Autowired
	public void setRewardValue(Double rewardValue) {
		reward.setRewardValue(rewardValue);
	}

	public String getRewardRate() {
		return reward.getRewardRate();
	}

	public void setRewardRate(String rewardRate) {
		reward.setRewardRate(rewardRate);
	}

	public String getRewardUnit() {
		return reward.getRewardUnit();
	}

	public void setRewardUnit(String rewardUnit) {
		reward.setRewardUnit(rewardUnit);
	}

}
