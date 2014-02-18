package com.drjcfitz.rewardyourself.process;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.drjcfitz.rewardyourself.model.Merchant;
import com.drjcfitz.rewardyourself.model.Portal;
import com.drjcfitz.rewardyourself.model.Reward;

public class ProcessMerchant {
	
	public ProcessMerchant() {
	}
	
	public List<Merchant> listStores(Document doc, Portal process) {
		List<Merchant> list = new ArrayList<Merchant>();
		Elements storeLinks = doc.select(process.getElement());
		for (Element link : storeLinks) {
			Merchant merchant = new Merchant();
			String reward;
			merchant.setStoreLink(process.getPortalUrl()+link.select(process.getStoreElement()).attr("href"));
			String merchName = link.select(process.getMerchElement()).text().trim();
			
			if (process.getProfile().equals("upromise")) {
				reward = link.text().substring(merchName.length()).toLowerCase().trim();
			} else {
				reward = link.select(process.getRewardElement()).text().trim().toLowerCase();
			}
			merchant.setReward(parseRewardsValue(reward, process.getProfile()));
			
			if (process.getProfile().equals("ebates")) {
				if (merchName.indexOf("Offers") != -1) {
					merchName = merchName.substring(0, merchName.indexOf("Offers") - 1);
				} else if (merchName.indexOf("Coupons") != -1) {
					merchName = merchName.substring(0, merchName.indexOf("Coupons") - 1);
				}
			} else if (process.getProfile().equals("united") || 
					process.getProfile().equals("marriott") ||
					process.getProfile().equals("upromise")) {
				if (merchName.contains(".com")) {
					merchName = merchName.substring(0,  merchName.indexOf(".com"));
				}
			}
			merchant.setName(merchName);
			
			merchant.setEnabled(true);
			merchant.setRefDate(new GregorianCalendar().getTimeInMillis());
			list.add(merchant);
		}  
		return list;
	}
	
	private Reward parseRewardsValue(String rs, String profile) {
		Reward reward = new Reward(0.0, "none", "none");
		String regexp = "";
		if (profile.equals("united") || profile.equals("marriott")) {
			regexp = "((?<cashVal>)(?<val>\\d{1,}))(?<percentVal>).(?<units>\\w*)(?<rate>/\\$)";
			if (!rs.contains("/")) {
				regexp = "((?<cashVal>)(?<val>\\d{1,}))(?<percentVal>).(?<units>\\w*)(?<rate>/\\$)?";
			} else if (rs.contains(".")) {
				System.out.println("decimal");
				regexp = "((?<cashVal>)(?<val>\\d{1,}(\\.\\d{1,2})?))(?<percentVal>).(?<units>\\w*)(?<rate>/\\$)";
			}
		} else {
			regexp = "(?<cashVal>\\$?)(?<val>\\d{1,}(.\\d{1,2})?)(?<percentVal>%?)(\\s*(?<units>\\w*)(?<rate>/\\$)\\d*)?";
		}
		Pattern rewardPattern = Pattern.compile(regexp);
		
		Matcher match = rewardPattern.matcher(rs);
		//System.out.println(rs);
		while (match.find()) {
			if (match.group("val") != null && !match.group("val").isEmpty()) {
				//System.out.println(match.group("val"));
				reward.setRewardValue(Double.valueOf(match.group("val").trim()));
			}
			
			if (match.group("rate") != null) {
				reward.setRewardRate(match.group("rate"));
			}
			
			if (!match.group("cashVal").isEmpty()) {
				reward.setRewardUnit("$");
			} else if (!match.group("percentVal").isEmpty()) {
				reward.setRewardUnit("%");
			} else {
				if (match.group("units").contains("mile")) {
					reward.setRewardUnit("miles");					
				} else if (match.group("units").contains("point")) {
					reward.setRewardUnit("points");
				}
			}
		}
		return reward;
	}


}
