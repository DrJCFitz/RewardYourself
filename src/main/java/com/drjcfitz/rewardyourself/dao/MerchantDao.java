package com.drjcfitz.rewardyourself.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.drjcfitz.rewardyourself.model.Merchant;
import com.drjcfitz.rewardyourself.model.Reward;

@Component("merchantDao")
public class MerchantDao {
	private NamedParameterJdbcTemplate jdbc;

	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	@Transactional
	public void createMerchant(List<Merchant> merchant, String site) {
		List<SqlParameterSource> params = new ArrayList<SqlParameterSource>();
		for (Merchant merch : merchant) {
			params.add(new BeanPropertySqlParameterSource(merch));
		}

		jdbc.batchUpdate("insert ignore into merchant (name, mkey) values (:name, :mkey)",
					params.toArray(new SqlParameterSource[0]));
		jdbc.batchUpdate("update " + site + " set enabled=false where " + 
					"(mkey=:mkey and (rewardValue<>:rewardValue or rewardUnit<>:rewardUnit))", 
					params.toArray(new SqlParameterSource[0]));
		jdbc.batchUpdate("insert ignore into " + site + 
				" (mkey, storeLink, rewardValue, rewardUnit, rewardRate, enabled, refDate)" +
				" values (:mkey, :storeLink, :rewardValue, :rewardUnit, :rewardRate, :enabled, :refDate)", 
				params.toArray(new SqlParameterSource[0]));
	}
	
	//@Transactional
	public void updateDbMerchant(List<Merchant> merchant, String site) {
		List<SqlParameterSource> params = new ArrayList<SqlParameterSource>();
		for (Merchant merch : merchant) {
			params.add(new BeanPropertySqlParameterSource(merch));
		}

		//jdbc.batchUpdate("insert ignore into merchant (name, mkey) values (:name, :mkey)",
		//			params.toArray(new SqlParameterSource[0]));
		jdbc.batchUpdate("update " + site + " set mkey=:mkey where " + 
					"id=:id", params.toArray(new SqlParameterSource[0]));
	}

	public List<Merchant> getAllMerchantNamesKeys() {
		return jdbc.query("select name, mkey from merchant order by name", new RowMapper<Merchant>() {

			public Merchant mapRow(ResultSet rs, int row) throws SQLException {
				Merchant merchant = new Merchant();
				merchant.setName(rs.getString("name"));
				merchant.setMkey(rs.getString("mkey"));
				return merchant;
			}
		});
	}

	public List<Merchant> getMerchantData(String portal, String storeMkey) {
		// Using RowMapper over BeanPropertyRowMapper since Reward bean set methods throwing nullpointer exception
		return jdbc.query("select a.name, b.mkey, b.id, b.storeLink, b.rewardValue, b.rewardUnit, b.rewardRate, b.refDate, b.enabled " +
				"from merchant as a right join " + portal + " as b on a.mkey=b.mkey where b.mkey=\""+storeMkey+"\"",
				new RowMapper<Merchant>(){
					public Merchant mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Reward reward = new Reward(rs.getDouble("rewardValue"), 
								rs.getString("rewardRate"),
								rs.getString("rewardUnit"));
						return new Merchant(rs.getString("name"),
								rs.getString("mkey"),
								rs.getString("storeLink"),
								reward,
								rs.getLong("refDate"),
								rs.getBoolean("enabled"),
								rs.getInt("id"));
					}});
	}

	public List<Merchant> getAllMerchantDbData(String portal) {
		// Using RowMapper over BeanPropertyRowMapper since 'Reward' bean set methods throwing nullpointer exception
		return jdbc.query("select name, mkey, storeLink, rewardValue, rewardRate, rewardUnit, "+
					"refDate, enabled, id "+
					"from "+portal,
						new RowMapper<Merchant>(){

							public Merchant mapRow(ResultSet rs, int rowNum)
									throws SQLException {
								Reward reward = new Reward(rs.getDouble("rewardValue"), 
										rs.getString("rewardRate"),
										rs.getString("rewardUnit"));
								
								return new Merchant(rs.getString("name"),
										rs.getString("mkey"),
										rs.getString("storeLink"),
										reward,
										rs.getLong("refDate"),
										rs.getBoolean("enabled"),
										rs.getInt("id"));
							}});
	}

	public List<Merchant> getEnabledMerchantData(String portal) {
		// Using RowMapper over BeanPropertyRowMapper since 'Reward' bean set methods throwing nullpointer exception
		return jdbc.query("select a.name, a.mkey, " +
				"b.storeLink, b.rewardValue, b.rewardUnit, b.rewardRate, b.refDate, b.enabled " +
				"from merchant as a right join " + portal + " as b on a.mkey=b.mkey where b.enabled=true",
				new RowMapper<Merchant>(){
					public Merchant mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Reward reward = new Reward(rs.getDouble("rewardValue"), 
								rs.getString("rewardRate"),
								rs.getString("rewardUnit"));
						
						return new Merchant(rs.getString("name"),
								rs.getString("mkey"),
								rs.getString("storeLink"),
								reward,
								rs.getLong("refDate"),
								rs.getBoolean("enabled"));
					}});
	}

	public List<Merchant> getActiveMerchants(String site) {
		return jdbc
				.query("select name, enabled, refDate, rewards, rewardType, storeLink from merchant as a,"
						+ site + " as b where a.mkey=b.mkey and b.enabled=true",
						new BeanPropertyRowMapper<Merchant>());/*   {
							public Merchant mapRow(ResultSet rs, int rowNum)
									throws SQLException {

								Merchant merch = new Merchant();
								merch.setName(rs.getString("name"));
								merch.setEnabled(rs.getBoolean("enabled"));
								merch.setReward(new Reward(rs.getDouble("rewards"),
										rs.getString("rewardType")));
								merch.setStoreLink(rs.getString("storeLink"));
								merch.setRefDate(rs.getLong("refDate"));

								return merch;
							}
						});*/
	}

	public boolean merchExistsGlobal(Merchant merchant) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("mkey", merchant.getName().toLowerCase());
		return jdbc.queryForObject(
				"select count(*) from merchant where mkey=:mkey", params,
				Integer.class) > 0;
	}

	public boolean merchExistsSite(Merchant merchant, String site) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("mkey", merchant.getName().toLowerCase());
		return jdbc.queryForObject("select count(*) from " + site
				+ " where mkey=:mkey", params, Integer.class) > 0;
	}

}
