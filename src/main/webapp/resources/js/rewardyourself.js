$(document).ready( function() {
	$('#trendSelector').change(
		function() {
			d3.json('trends' + '/'
					+ $('#trendSelector').val(), function(
					error, json) {
				if (error)
					return console.warn(error);
				update(json);
			});
		});
	
	$("div[data-role='panel']")
		.panel({display:'overlay'})
		.removeClass('ui-panel-display-reveal')
		.addClass('ui-panel-display-overlay');
	
	$("ul#storeList>li>div[data-role='collapsible']").collapsible({
		expand:function(event, ui) {
			var store = $(this).attr('mkey');
			$targetDiv = $(this).children('.ui-collapsible-content');
			$.getJSON('stores/' + store, function(data){
				merchants = [];
				$.each(data, function(key,value){
					if (value.merchantList.length) {
						// merchantList should contain one entry, but $.each is used for conciseness
						var equivPercent = value.equivalentPercentage;
						$.each(value.merchantList,function(index, d) {
							rewardPrefix = (d.reward.rewardUnit==='$') ? '$' : '';
							rewardSuffix = (d.reward.rewardUnit==='none') ? '' : ' '+d.reward.rewardUnit;
							equivRewardVal = (d.reward.rewardUnit ==='$' || d.reward.rewardRate ==='%') ? null : equivPercent*d.reward.rewardValue;
							merchants.push('<div class=\'rwys-merch\'><a href=\'' + d.storeLink + '\'>'+
									' <img src=\'static/img/' + key + '.png\' />' +
									' <p>' + rewardPrefix + d.reward.rewardValue + rewardSuffix + 
									d.reward.rewardRate + '</p></a>'+
									((equivRewardVal == null) ? '' : '<p class="equiv">Equivalent Value: ' + equivRewardVal + '%</p>') + 
									'</div>');							
						});
					}
				});
				$targetDiv.append(merchants.join(""));
			});
		}
	});
	
	$("ul#portalList>li>div[data-role='collapsible']").collapsible({
		expand:function(event, ui) {
			var portal = $(this).attr('pkey');
			$targetDiv = $(this).children('.ui-collapsible-content');
			$.getJSON('portals/' + portal, function(data){
				merchants = [];
				$.each(data, function(key,value){
					if (value.merchantList.length) {
						var equivPercent = value.equivalentPercentage;
						$.each(value.merchantList,function(index, d) {
							rewardPrefix = (d.reward.rewardUnit==='$') ? '$' : '';
							rewardSuffix = (d.reward.rewardUnit==='none') ? '' : ' '+d.reward.rewardUnit;
							equivRewardVal = (d.reward.rewardUnit ==='$' || d.reward.rewardRate ==='%') ? null : equivPercent*d.reward.rewardValue;
							merchants.push('<div mkey=\'' + d.mkey + '\'><a href=\'' + d.storeLink + '\'><span class=\'rwys-portal\'>' +
									d.name + ': ' + rewardPrefix + d.reward.rewardValue + rewardSuffix + d.reward.rewardRate + 
									'</span></a>'+
									((equivRewardVal == null) ? '' : '<p class="equiv">Equivalent Value: ' + equivRewardVal + '%</p>') + 
									'</div>');							
						});
					}
				});
				$targetDiv.append(merchants.join(""));
			});
		}
	});
	
});