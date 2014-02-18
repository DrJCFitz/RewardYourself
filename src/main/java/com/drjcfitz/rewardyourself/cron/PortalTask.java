package com.drjcfitz.rewardyourself.cron;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.drjcfitz.rewardyourself.dao.MerchantDao;
import com.drjcfitz.rewardyourself.model.Merchant;
import com.drjcfitz.rewardyourself.model.Portal;
import com.drjcfitz.rewardyourself.process.ProcessMerchant;
import com.drjcfitz.rewardyourself.service.MerchantService;

public class PortalTask {

	@Autowired
	private Portal ebatesProcess;
	@Autowired
	private Portal unitedProcess;
	@Autowired
	private Portal marriottProcess;
	@Autowired
	private Portal upromiseProcess;
	
//	@Autowired
//	private List<Portal> portalList = new ArrayList<Portal>(Arrays.asList(new Portal[] {ebatesProcess, unitedProcess, marriottProcess}));

	private MerchantService merchantService;
	
	@Autowired
	public void setMerchantService(MerchantService merchantService) {
		this.merchantService = merchantService;
	}
	
	final ProcessMerchant procMerch = new ProcessMerchant();

	public PortalTask() {
	}
	
	public void updateDb() throws IOException {
		List<Portal> portalList = new ArrayList<Portal>(Arrays.asList(new Portal[] {ebatesProcess, unitedProcess, marriottProcess}));
		for (Portal portal : portalList) {
			Document doc = Jsoup.connect(portal.getStoresUrl()).get();
			List<Merchant> list = procMerch.listStores(doc, portal); 
			merchantService.create(list, portal.getProfile());
		}
	}
	
	public HashMap<String, List<Merchant>> getStoreData(String storeName, List<Portal> portalList){
		HashMap<String, List<Merchant>> storeDataMap = new HashMap<String, List<Merchant>>();
		for (Portal portal : portalList) {
			storeDataMap.put(portal.getProfile(),
					merchantService.getMerchantData(portal.getProfile(), storeName));
		}
		
		return storeDataMap;
	}

}
