//http://bl.ocks.org/Caged/6476579
function showBarChartNew(data) {
	var margin = {
		top : 40,
		right : 20,
		bottom : 30,
		left : 40
	}, width = 900 - margin.left - margin.right, height = 300 - margin.top
			- margin.bottom;

	var x = d3.scale.ordinal().rangeRoundBands([ 0, width ], .3);

	var y = d3.scale.linear().range([ height, 0 ]);

	var xAxis = d3.svg.axis().scale(x).orient("bottom").ticks(7);

	var yAxis = d3.svg.axis().scale(y).orient("left").ticks(10);

	var svg = d3.select("#barChartNew").append("svg").attr("width",
			width + margin.left + margin.right).attr("height",
			height + margin.top + margin.bottom).append("g").attr("transform",
			"translate(" + margin.left + "," + margin.top + ")");

	// d3.csv("bardata.csv", function(error, data) {

	data.forEach(function(d) {
		d.date = d.date;
		d.value = +d.value;
	});

	x.domain(data.map(function(d) {
		return d.date;
	}));

	y.domain([ 0, d3.max(data, function(d) {
		return d.value;
	}) ]);

	svg.append("g").attr("class", "x axis").attr("transform",
			"translate(0," + height + ")").call(xAxis);

	/*
	 * svg.append("g").attr("class", "y axis").call(yAxis).append("text")
	 * .attr("transform", "rotate(-90)").attr("y", 6).attr("dy",
	 * ".71em").style("text-anchor", "end").text("Value (%)");
	 */

	svg.selectAll("bar").data(data).enter().append("rect").attr("x",
			function(d) {
				return x(d.date);
			}).attr("width", x.rangeBand()).attr("y", function(d) {
		if (d.value != 0)
			return y(d.value);
	}).attr("height", function(d) {
		return height - y(d.value);
	}).attr("fill", function(d) {
		if (d.value == 20) {
			return "#EC1E1E";// red
		} else if (d.value == 40) {
			return "#F38E93"; // light red
		} else if (d.value == 50) {
			return "#F4E66F"; // yellow
		} else if (d.value == 80) {
			return "#CDF3A5"; // light green
		}
		return "#9DD464"; // dark green
	});

	svg.selectAll(".text").data(data).enter().append("text").attr("class",
			"label").attr("x", (function(d) {
		return x(d.date) + x.rangeBand() / 2;
	})).attr("y", function(d) {
		return y(d.value) + 1;
	}).attr("dy", ".75em").attr("text-anchor", "middle").attr("font-family",
			"sans-serif").attr("font-size", "12px").attr("fill", "black").text(
			function(d) {
				if (d.value == 20) {
					return "VERY BAD";// red
				} else if (d.value == 40) {
					return "BAD"; // light red
				} else if (d.value == 50) {
					return "NEUTRAL"; // yellow
				} else if (d.value == 80) {
					return "GOOD"; // light green
				}else if (d.value == 100) {
					return "VERY GOOD"; // dark green
				}
				return ""; // no label for empty dimension 
			});

	function type(d) {
		d.frequency = +d.frequency;
		return d;
	}

}
