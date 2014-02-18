package com.drjcfitz.rewardyourself.model;

public class Reward {

	private Double rewardValue;
	private String rewardRate;
	private String rewardUnit;
	
	public Reward() {
	}
	
	public Reward(Double rewardValue, String rewardUnit) {
		super();
		this.rewardValue = rewardValue;
		this.rewardUnit = rewardUnit;
	}

	public Reward(Double rewardValue, String rewardRate, String rewardUnit) {
		super();
		this.rewardValue = rewardValue;
		this.rewardRate = rewardRate;
		this.rewardUnit = rewardUnit;
	}

	

	@Override
	public String toString() {
		return "Reward [rewardValue=" + rewardValue + ", rewardRate="
				+ rewardRate + ", rewardUnit=" + rewardUnit + "]";
	}

	public Double getRewardValue() {
		return rewardValue;
	}

	public void setRewardValue(Double rewardValue) {
		this.rewardValue = rewardValue;
	}

	public String getRewardRate() {
		return rewardRate;
	}

	public void setRewardRate(String rewardRate) {
		this.rewardRate = rewardRate;
	}

	public String getRewardUnit() {
		return rewardUnit;
	}

	public void setRewardUnit(String rewardUnit) {
		this.rewardUnit = rewardUnit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((rewardRate == null) ? 0 : rewardRate.hashCode());
		result = prime * result
				+ ((rewardUnit == null) ? 0 : rewardUnit.hashCode());
		result = prime * result
				+ ((rewardValue == null) ? 0 : rewardValue.hashCode());
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
		Reward other = (Reward) obj;
		if (rewardRate == null) {
			if (other.rewardRate != null)
				return false;
		} else if (!rewardRate.equals(other.rewardRate))
			return false;
		if (rewardUnit == null) {
			if (other.rewardUnit != null)
				return false;
		} else if (!rewardUnit.equals(other.rewardUnit))
			return false;
		if (rewardValue == null) {
			if (other.rewardValue != null)
				return false;
		} else if (!rewardValue.equals(other.rewardValue))
			return false;
		return true;
	}

	

}