package com.drjcfitz.rewardyourself.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.drjcfitz.rewardyourself.model.Merchant;
import com.drjcfitz.rewardyourself.model.PortalProfile;
import com.drjcfitz.rewardyourself.model.Reward;

public class ProcessMerchant {
	
	public ProcessMerchant() {
	}
	
	public List<Merchant> listStores(PortalProfile profile) throws IOException {
		List<Merchant> list = new ArrayList<Merchant>();
		Document doc = Jsoup.connect(profile.getAllStoresUrl()).get();
		
		Elements storeLinks = doc.select(profile.getRootElement());
		for (Element link : storeLinks) {
			Merchant merchant = new Merchant();
			String reward="";
			merchant.setStoreLink(profile.getPortalUrl()+link.select(profile.getMerchantElement()).attr("href"));
			String merchName = link.select(profile.getMerchantElement()).text().trim();
			
			if (profile.getProfileKey().equals("upromise")) {
				reward = link.text().substring(merchName.length()).toLowerCase().trim();
			} else {
				reward = link.select(profile.getRewardElement()).text().trim().toLowerCase();
			}
			merchant.setReward(parseRewardsValue(reward, profile));
			merchName = merchName.replaceAll(profile.getPortalRegexp().get("exclusionName"), "");
			merchant.setName(merchName);
			merchant.setMkey(parseMerchKey(merchName));
			
			merchant.setEnabled(true);
			merchant.setRefDate(new GregorianCalendar().getTimeInMillis());
			list.add(merchant);
		}  
		return list;
	}
	
	private Reward parseRewardsValue(String rs, PortalProfile profile) {
		Reward reward = new Reward(0.0, "none", "none");
		HashMap<String, String> regexpMap = profile.getPortalRegexp();
		
		rs = rs.replaceAll(regexpMap.get("exclusionReward"), "");

		String regexp = regexpMap.get("prefix") +
				"(?<cashVal>" + regexpMap.get("cashVal") + 
				"(?<val>" + regexpMap.get("rewardVal") +
				"(?<percentVal>" + regexpMap.get("percentVal") + 
				regexpMap.get("unitspace") +
				"(?<units>" + regexpMap.get("units") + 
				"(?<rate>" + regexpMap.get("rate") +
				regexpMap.get("suffix");

		Pattern rewardPattern = Pattern.compile(regexp);
		Matcher match = rewardPattern.matcher(rs);
		while (match.find()) {
			if (match.group("val") != null && !match.group("val").isEmpty()) {
				reward.setRewardValue(Double.valueOf(match.group("val").replaceAll("\\s", "")));
			}
			
			if (match.group("percentVal") != null && !match.group("percentVal").isEmpty()) {
				reward.setRewardRate("%");
			} else if (match.group("rate") != null) {
				reward.setRewardRate(match.group("rate").trim());
			}
			
			if (match.group("cashVal") != null && !match.group("cashVal").isEmpty()) {
				reward.setRewardUnit("$");
			} else if (match.group("units") != null && !match.group("units").isEmpty()) {
				reward.setRewardUnit(match.group("units").trim());
			} else {
				reward.setRewardUnit("none");
			}
		}
		return reward;
	}


	private String parseMerchKey(String merchName) {
		String mkey = merchName.toLowerCase().replaceAll("(inc\\.)?(\\.com)?(\\.net)?(\\.org)?(\\band)?(online)?(shoes)?(outfitters)?\\W?", "");
		switch (mkey) {
			case "wsjwineclub":
				return "wsjwine";
			case "spreadshirtdesigner":
				return "spreadshirt";
			case "speedousa":
				return "speedo";
			case "spanxbysarablakely":
				return "spanx";
			case "spafinderwellness365":
				return "spafinderwellness";
			case "sonystore":
				return "sony";
			case "smashbox":
				return "smashboxcosmetics";
			case "skyo":
				return "skyotextbooks";
			case "samsungelectronicsamerica":
				return "samsung";
			case "sallybeauty":
				return "sallybeautysupply";
			case "saksfifthavenueoff5th":
				return "saksofffifth";
			case "saks":
				return "saksfifthavenue";
			case "rosettastonelanguagesoftware":
				return "rosettastone";
			case "rodalestore":
				return "rodales";
			case "rakutenshoppingformerlybuy":
				return "rakuten";
			case "quickbooks":
				return "quickbooksonline";
			case "performancebike":
				return "performancebicycle";
			case "ps":
				return "psfromaeropostale";
			case "overland":
				return "overlandsheepskinco";
			case "ojon":
				return "ojonhaircare";
			case "northerntoolequipment":
				return "northerntool";
			case "nikestore":
				return "nike";
			case "nfl":
				return "nflshop";
			case "nationalgeographicstore":
				return "nationalgeographic";
			case "nascarstore":
				return "nascar";
			case "midesignmichaelscustominvites":
				return "michaelscustominvitations";
			case "luckybrand":
				return "luckybrandjeans";
			case "lenovothinkpad":
				return "lenovo";
			case "legoshopathome":
				return "lego";
			case "labseries":
				return "labseriesskincareformen";
			case "labseriesformen":
				return "labseriesskincareformen";
			case "kiplingusa":
				return "kipling";
			case "kielssince1851":
				return "kiels";
			case "jaredthegalleriaofjewelry":
				return "jared";
			case "istockphoto":
				return "istock";
			case "irvsluggage":
				return "irvsluggagewarehouse";
			case "ipage":
				return "ipagewebhosting";
			case "intuitquickbooksonline":
				return "quickbooksonline";
			case "intuitquickbooksdesktop":
				return "quickbooksdesktop";
			case "highlights":
				return "highlightsforchildren";
			case "hrblockathome":
				return "hrblock";
			case "gocardusasmartdestinations":
				return "gocardusa";
			case "glasses":
				return "glassesusa";
			case "gevaliakaffe":
				return "gevalia";
			case "gevaliacoffee":
				return "gevalia";
			case "folica":
				return "folicabeautysupply";
			case "effyjewelry":
				return "effyjewelers";
			case "discoverystore":
				return "discoverychannelstore";
			case "dancingdeerbakingco":
				return "dancingdeerbakingcompany";
			case "cymaxstores":
				return "cymax";
			case "cusp":
				return "cuspbyneimanmarcus";
			case "champion":
				return "championusa";
			case "carolwrightsgifts":
				return "carolwrightgifts";
			case "boden":
				return "bodenus";
			case "blissworld":
				return "bliss";
			case "bjs":
				return "bjswholesaleclub";
			case "bjswholesale":
				return "bjswholesaleclub";
			case "baseballrampagesoftballrampage":
				return "baseballrampage";
			case "americaneagle":
				return "americaneagleoutfitters";
			case "afflictionclothing":
				return "affliction";
			case "aerieforamericaneagle":
				return "aerie";
			case "crowneplaza":
				return "crowneplazahotelsresorts";
			case "discounttireamericastire":
				return "discounttire";
			case "drleonards":
				return "drleanardshealthcare";
			case "duncraft":
				return "duncraftwildbirdsuperstore";
			case "duncraftbirdfeeders":
				return "duncraftwildbirdsuperstore";
			case "elsevierstore":
				return "elsevier";
			case "estelauder":
				return "esteelauder";
			case "evesaddiction":
				return "evesaddictionjewelry";
			case "fanniemaycandies":
				return "fanniemay";
			case "holidayinnexpresshotels":
				return "holidayinnexpress";
			case "holidayinn":
				return "holidayinnhotelsresorts";
			case "hotelindigo":
				return "hotelindigohotels";
			case "intercontinental":
				return "intercontinentalhotelsresorts";
			case "adagio":
				return "adagioteas";
			case "ahnu":
				return "ahnufootwear";
			case "alibris":
				return "alibrisbooksmusicmovies";
			case "bates":
				return "batesfootwear";
			case "bodenusa":
				return "bodenus";
			case "bowflexshop":
				return "bowflex";
			case "budget":
				return "budgetrentacar";
			case "budgettruck":
				return "budgettruckrental";
			case "carbonite":
				return "carbonitebackup";
			case "chefs":
				return "chefscatalog";
			case "chegg":
				return "cheggstudy";
			case "clarionhotel":
				return "clarionhotels";
			case "candlewoodsuites":
				return "candlewoodsuiteshotels";
			case "cookieskids":
				return "cookiesthekidsdepartmentstore";
			case "dellhomesystems":
				return "dellhome";
			case "foodsavervacuumsealingsystem":
				return "foodsaver";
			case "ftdflowersgifts":
				return "ftd";
			case "hphomehomeofficestore":
				return "hphomeofficestore";
			case "lancme":
				return "lancome";
			case "levi":
				return "levis";
			case "lifelockidentitytheftprotection":
				return "lifelock";
			case "lumberliquidator":
				return "lumberliquidators";
			case "melissaanddoug":
				return "melissadoug";
			case "microtelinnsuitesbywyndham":
				return "microtelinnsuites";
			case "mlbshop":
				return "mlb";
			case "nascarsuperstore":
				return "nascar";
			case "nbcstore":
				return "nbcuniversalstore";
			case "nortonfromsymantec":
				return "nortonbysymantec";
			case "oneidaltd":
				return "oneida";
			case "panasonicideasforlife":
				return "panasonic";
			case "parkplaza":
				return "parkplazahotelsresorts";
			case "paylesssource":
				return "paylessshoesource";
			case "pipingrock":
				return "pipingrockhealthproducts";
			case "proactivsolution":
				return "proactiv";
			case "radisson":
				return "radissonhotelsresorts";
			case "rakutenshopping":
				return "rakuten";
			case "ruum":
				return "ruumamericankidswear";
			case "sandalsallinclusiveresorts":
				return "sandalsresorts";
			case "staybridgesuiteshotels":
				return "staybridgesuites";
			case "sundancecatalog":
				return "sundance";
			case "thenewyorktimesstore":
				return "thenewyorktimes";
			case "wjswinediscoveryclub":
				return "wsjwine";
			case "wyndhamhotelgroup":
				return "wyndhamhotelsresorts";
			case "yvesrocherus":
				return "yvesrocherusa";
			case "yvessaintlaurent":
				return "yvessaintlaurentbeauty";
			default:
				return mkey;
		}
	}

}
