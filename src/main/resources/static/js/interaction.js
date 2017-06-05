/**
 * Created by junyuan on 06/05/2017.
 */

var width = 800, height = 200;

var force = d3.layout.force()
    .charge(-50).linkDistance(30).size([width, height]);

var svg = d3.select("#graph").append("svg")
    .attr("width", "100%").attr("height", "100%")
    .attr("pointer-events", "all");

var tooltip_div = d3.select("#graph").append("div")
    .attr("class", "tooltip")
    .style("opacity", 0);

d3.json("/graph", function(error, graph) {
    if (error) return;

    force.nodes(graph.nodes).links(graph.links).start();

    var link = svg.selectAll(".link")
        .data(graph.links).enter()
        .append("line").attr("class", "link");

    var node = svg.selectAll(".node")
        .data(graph.nodes).enter()
        .append("circle")
        .attr("class", function (d) { return "node "+d.label })
        .attr("id", function (d) {
            return d.label.replace(' ', '-').toLocaleLowerCase();
        })
        .attr("r", 10)
        .call(force.drag);

    // html title attribute
    node.append("title")
        .text(function (d) { return d.title; })

    // force feed algo ticks
    force.on("tick", function() {
        link.attr("x1", function(d) { return d.source.x - 100; })
            .attr("y1", function(d) { return d.source.y; })
            .attr("x2", function(d) { return d.target.x - 100; })
            .attr("y2", function(d) { return d.target.y; });

        node.attr("cx", function(d) { return d.x - 100; })
            .attr("cy", function(d) { return d.y; });
    });

    node.on("mouseover", function (d) {
        tooltip_div.transition()
            .duration(200)
            .style("opacity", .9);
        tooltip_div.html(d.title)
            .style("left", (d3.event.pageX) + "px")
            .style("top", (d3.event.pageY - 11) + "px");
    })
});

var tagbtn = d3.selectAll(".add-tag"),
    tagbtn0 = d3.selectAll(".search-tag"),
    search_input = $("#search-text"),
    add_input = $("#add-text");


tagbtn.on("click", function (d) {
    console.log(this);
    var original = add_input.val();
    console.log(original)
    add_input.val(original + " " + this.innerHTML + " ");
});

tagbtn0.on("click", function (d) {
    console.log(this);
    var original = search_input.val();
    console.log(original)
    search_input.val(original + " " + this.innerHTML + " ");
});

