package com.drjcfitz.rewardyourself.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drjcfitz.rewardyourself.dao.MerchantDao;
import com.drjcfitz.rewardyourself.model.Merchant;

@Service("merchantService")
public class MerchantService {
	
	private MerchantDao merchantDao;
	
	@Autowired
	public void setMerchantDao(MerchantDao merchantDao) {
		this.merchantDao = merchantDao;
	}

	
	public boolean merchExists(Merchant merchant) {
		return merchantDao.merchExistsGlobal(merchant);
	}

	public void create(List<Merchant> listMerchant, String site) {
		merchantDao.createMerchant(listMerchant, site);
	}
	
	public List<Merchant> getMerchantData(String portal, String storeKey) {
		return merchantDao.getMerchantData(portal, storeKey);
	}


	public List<Merchant> getAllMerchantNamesKeys() {
		return merchantDao.getAllMerchantNamesKeys();
	}

	public void updateDbMerchant(List<Merchant> listMerchant, String site) {
		merchantDao.updateDbMerchant(listMerchant, site);
	}
	
	public List<Merchant> getAllMerchantDbData(String portal) {
		return merchantDao.getAllMerchantDbData(portal);
	}
	
	public List<Merchant> getEnabledMerchantData(String portal) {
		return merchantDao.getEnabledMerchantData(portal);
	}

}
