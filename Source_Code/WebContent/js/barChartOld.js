//http://bl.ocks.org/Caged/6476579
function showBarChart() {
var margin = {top: 40, right: 20, bottom: 30, left: 40},
    width = 560 - margin.left - margin.right,
    height = 300 - margin.top - margin.bottom;

var animatDuration =700;
var animateDelay = 30;

var formatPercent = d3.format(".0%");

var x = d3.scale.ordinal()
    .rangeRoundBands([0, width], .1);

var y = d3.scale.linear()
    .range([height, 0]);

var xAxis = d3.svg.axis()
    .scale(x)
    .orient("bottom");

var yAxis = d3.svg.axis()
    .scale(y)
    .orient("left")
    .tickFormat(formatPercent);

var tip = d3.tip()
  .attr('class', 'd3-tip')
  .offset([-10, 0])
  .html(function(d) {
    return "<strong>Label:</strong> <span style='color:red'>" + d.label + "</span>";
  })

var svg = d3.select("#barChart").append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

svg.call(tip);

d3.tsv("data.csv", type, function(error, data) {
  x.domain(data.map(function(d) { return d.letter; }));
  y.domain([0, d3.max(data, function(d) { return d.label; })]);

  svg.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + height + ")")
      .call(xAxis);

  svg.append("g")
      .attr("class", "y axis")
      .call(yAxis)
    .append("text")
      .attr("transform", "rotate(-90)")
      .attr("y", 6)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
      .text("Label");

  svg.selectAll(".bar")
  .data(data)
.enter().append("rect")
  .attr("class", "bar")
  .attr("x", function(d) { return x(d.letter); })
  .attr("width", x.rangeBand())
  .attr("y", height)
  .attr("height", 0)
  .on('mouseover', tip.show)
  .on('mouseout', tip.hide)

  .transition()
    	.attr('height', function(d) { return height - y(d.label); 
    	})
    	.attr('y',function(d) { return y(d.label); }
    	)
    	.duration(animatDuration)
    	.delay(function(d,i){
    		return i*animateDelay
    	})
    	.ease('elastic');

});

	
function type(d) {
  d.label = +d.label;
  return d;
}

}


