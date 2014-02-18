<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<title>D3 Json</title>
	<script type="text/javascript" src="static/js/jquery-min.js"></script>
	<script type="text/javascript" src="static/js/jquery.mobile-1.4.1.min.js"></script>
	<script type="text/javascript" src="static/js/d3.v3.min.js"></script>
</head>
<body>

	<script>
	//append the svg element to the DOM
	var canvas = d3.select("body")
			.append("svg")
	
	// append a group to the canvas	
	var group = canvas.append("g");

	var dataIn = null;
	function getData(data) {
		dataIn = data;
		dataKeys = d3.keys(dataIn);
	}
	
	function sizeElements() {
		// Store browser window dimensions in variables
		window_width = $(window).width();
		window_height = $(window).height();

		canvas.attr('width', window_width)
			.attr('height', window_height);

		group.attr("transform", "translate("+(window_width/2)+","+(window_height)/2+")");

		
		// Array of radii for donut chart
		var maxDim = Math.min(window_width, window_height);
	}

	sizeElements();

	d3.json('http://localhost:8080/RewardYourself/stores/Cache', function(data) {
		// Data keys correspond to the categories in the header
		dataKeys = d3.keys(data[0]);
		// Use callback for data manipulation
		getData(data);
	});
	
	</script>

	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js" type="text/javascript"></script>
</body>
</html>