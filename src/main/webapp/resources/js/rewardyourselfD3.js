var window_width = $(window).width();
var body_width = $('body').width();
var svg_dim = [body_width, 0.75/2*body_width];
var margin = {top:30, right:40, bottom:50, left:50};
var legend_width = 50;
var color = d3.scale.category20c();
var portals = d3.map({"ebates":{"portal":"Ebates", "unitValue":1.0},
		"united":{"portal":"United MileagePlus", "unitValue":3.5},
		"marriott":{"portal":"Marriott Rewards", "unitValue":1.25},
		"upromise":{"portal":"UPromise", "unitValue":1.0}});

var div_tooltip = d3.select("body")
	.append("div")
	.attr("class", "tooltip")
	.attr("id", "div_tooltip")
	.style("opacity", 0)
	.on("click", function() {
		div_tooltip.transition()
			.duration(500)
			.style("opacity", 0);
	});

var canvas = d3.select("#trendPlot")
		.append("svg")
		.attr({'width':window_width,
			'height':svg_dim[1]});

var group = canvas.append("g")
		.attr('class','plot')
		.attr("transform", "translate("+(window_width-svg_dim[0])/2+",0)");

xAxis_gr = group.append('g')
	.attr({'class':'xAxis axis',
		'transform':'translate(0,' + (svg_dim[1] - margin.bottom) + ')'});
	
yAxis_gr = group.append('g')
	.attr({'class':'yAxis axis',
		'transform':'translate('+margin.left+',0)'});

yAxis_label = yAxis_gr.append('g').attr('class','label').append('text');
yAxis_label.text('Equivalent Cash Back (%)')
	.attr({'y':-margin.left, 
			'x':-svg_dim[1]/2, 
			'text-anchor':'middle', 
			'transform':'rotate(-90)',
			'visibility':'hidden'});

var xScale = d3.time.scale().domain([new Date(0),new Date()]).range([margin.left, svg_dim[0] - legend_width - margin.right * 2]).nice()
var yScale = d3.scale.linear().domain([0,1]).range([svg_dim[1] - margin.bottom, margin.top]).nice()
var xAxis = d3.svg.axis().orient("bottom")
var yAxis = d3.svg.axis().orient("left")


function update(dataTrans) {

	var orderedData = orderData(dataTrans);
	//Define axes

	xScale.domain([longToDate(minDate(orderedData)), 
			new Date()]);
	yScale.domain([minReward(orderedData), maxReward(orderedData)]);
	xAxis.scale(xScale);
	yAxis.scale(yScale);
	group.selectAll('g.xAxis').call(xAxis);
	group.selectAll('g.xAxis text')	
        .style('text-anchor', 'end')
        .attr({'dx':'-.8em',
        		'dy':'.15em',
				'transform':'rotate(-65)'});
	group.selectAll('g.yAxis').call(yAxis);
	yAxis_label.attr('visibility','visible');

	var gr = group.selectAll('g.plot g.merchant')
					.data(parseData(orderedData,false));
	
	gr.enter()
		.append('g')
		.attr('class', function(d) {return d.key+' merchant';});

	var linesGroup = d3.selectAll('g.merchant').select('g.lines')[0][0]==null ? d3.selectAll('g.merchant').append('g').attr('class','lines') : 
		d3.selectAll('g.merchant').select('g.lines');

	var circleGroup = d3.selectAll('g.merchant').select('g.circles')[0][0]==null ? d3.selectAll('g.merchant').append('g').attr('class','circles') : 
		d3.selectAll('g.merchant').select('g.circles');
		
	
	gr.each(function(d,i) {

		var gr_circle =	d3.select(this)
							.select('g.circles')
							.selectAll('.circle')
							.data(d.value); 

		gr_circle.transition()
			.duration(1000)
			.attr({'cx':function(dat) {
						return xScale(dat.refDate);},
					'cy':function(dat) {
						return yScale(dat.reward.rewardValue*portals.get(d.key).unitValue);}});

		gr_circle.enter()
				.append('circle')
				.attr({'r':'5', 
					'cx':function(dat) {
						return xScale(dat.refDate);},
					'cy':function(dat) {
						return yScale(dat.reward.rewardValue*portals.get(d.key).unitValue);},
					'stroke':color(portalKeys.indexOf(d.key)),
					'stroke-width':'2',
					'fill':'white',
					'class':'circle'});

		gr_circle.on('mouseover', function(dat,ind) {
				div_tooltip.transition() // fade in
					.duration(200)
					// Fill the tooltip background like the corresponding arc
					.style({'background':color(portalKeys.indexOf(d.key)),
							'opacity': 0.9,
							'left': function() {
								return d3.event.pageX + "px";
							},'top':function() {
								return d3.event.pageY-20+"px";
							}})
					.text(portals.get(d.key).portal + ':' 
					+ dat.reward.rewardValue
					+ dat.reward.rewardUnit
					+ dat.reward.rewardRate
					+ " on " + new Date(dat.refDate).toDateString() );
			});

		gr_circle.exit().transition().remove();

		var gr_line = d3.select(this)
						.selectAll('g.lines')
						.selectAll('.line')
						.data(d.value);

		gr_line.transition()
			.duration(1000)
			.attr('d', d3.svg.line()
					.x(function(dat) {return xScale(dat.refDate);})
					.y(function(dat) {return yScale(portals.get(d.key).unitValue*dat.reward.rewardValue);})
					.interpolate('step-before')(d.value));

		gr_line.enter()
				.append('path')
				.attr('d', d3.svg.line()
					.x(function(dat) {return xScale(dat.refDate);})
					.y(function(dat) {return yScale(portals.get(d.key).unitValue*dat.reward.rewardValue);})
					.interpolate('step-before')(d.value))
				.attr({'fill':'none',
						'stroke':color(portalKeys.indexOf(d.key)), 
						'stroke-width':'4',
						'class':'line'});
		gr_line.exit().transition().remove();
	});

	d3.selectAll('g.legend').remove();
	var legend_gr = gr.select('g.plot g.legend')
				.data(parseData(dataTrans,true));

	legend_gr.enter()
		.append('g')
		.attr('class','legend');

	legend_gr.each(function(d,i) {
		d3.select(this)
			.append('rect')
			.style({'fill':color(portalKeys.indexOf(d.key))})
			.attr({'x':svg_dim[0]-legend_width-margin.right -20,
					'y':i*1.5+'em',
					'width':20,
					'height':10, 
					'transform':'translate(0,'+margin.top+')',
					'class':'legend'});

		d3.select(this)
			.append('text')
			.attr({'x':svg_dim[0]-legend_width-margin.right, 
					'dy':'0.6em', 
					'dx':'0.5em',
					'y':i*1.5+'em',
					'text-anchor':'start',
					'transform':'translate(0,'+margin.top+')',
					'class':'legend'})
			.text(portals.get(d.key).portal);
	});
}


function orderData(inputData) {
	var updateDate = Date.parse(new Date());
	//set a an additional point that brings the data value to the present time
	portalKeys = d3.keys(inputData);
	for (var i=0;i<portalKeys.length;i++) {
		storeKeys = d3.keys(inputData[portalKeys[i]][0]);
		if (inputData[portalKeys[i]].length) {	
			endData = inputData[portalKeys[i]][inputData[portalKeys[i]].length-1];
			inputData[portalKeys[i]]
				.push({'name':endData['name'],
						'storeLink':endData['storeLink'],
						'refDate':updateDate,
						'reward':endData['reward'],
						'id':inputData[portalKeys[i]].length});
		}
		inputData[portalKeys[i]].sort(function(a,b) {return a.id-b.id;})
	}
	return inputData;
}


var nestedReward = function(dataIn, index) { 
	if (dataIn.reward===undefined) {
		return nestedReward(dataIn.value[index]);
	} else {
		return dataIn.reward.rewardValue;
	}
}
var nestedDate = function(dataIn, index) {
	if (dataIn.refDate===undefined) {
		return nestedDate(dataIn.value[index]);
	} else {
		return dataIn.refDate;
	}
}
var parseData = function(dataIn, removeNull) { parsedData = d3.entries(dataIn); if (removeNull) {return parsedData.filter(function(d) {if (d.value.length) {return d;}});} return parsedData;}
var minReward = function(dataIn) { 
	return d3.min(parseData(dataIn,true),function(d,i) {
		return portals.get(d.key).unitValue*d3.min(d.value,function(m,l){ 
			return nestedReward(m,l);});
	});
}
var minDate = function(dataIn) { 
	return d3.min(parseData(dataIn,true),function(d,i) { 
		return d3.min(d.value, function(m,l) {
			return nestedDate(m,l);});
	})
}

var maxReward = function(dataIn) { 
	return d3.max(parseData(dataIn,true),function(d,i) {
		return portals.get(d.key).unitValue*d3.max(d.value,function(m,l){ 
			return nestedReward(m,l);});
	});
}

function longToDate(longVal) {
	return new Date(longVal);
}