var width = 1200,
    height = 1200,
    active = d3.select(null);


var projection = d3
    .geoNaturalEarth1()
    .translate([width / 120, height / 0.119])
    .scale(11000);

var path = d3.geoPath().projection(projection);

var svg = d3.select("#map").append("svg").attr("id", "map-svg");

svg.append("rect")
    .attr("class", "background")
    .attr("id", "map-background")
    .attr("width", width)
    .attr("height", height)
    .on("click", reset);

var g = svg.append("g").style("stroke-width", ".5px").style("stroke", "black");


// load and display Belgium map
d3.json("api/index/map-data").then(function (topology) {
    g.selectAll("path")
        .data(topojson.feature(topology, topology.objects.Gemeenten).features)
        .enter()
        .append("path")
        .attr("d", path)
        .attr("class", "feature")
        .attr("data_name", function (d) {
            return d.properties.NAME_4;
        })
        .attr("data_region", function (d) {
            return d.properties.NAME_1;
        })
        .attr("fill", function (d) {
            if (
                d.properties.NAME_1 === "Wallonie" ||
                d.properties.NAME_1 === "Bruxelles"
            ) {
                return "gray";
            } else {
                return "white";
            }
        })
        .on("click", clicked);

    let municipalities = topology.objects.Gemeenten.geometries;
    let municipalityNames = [];
    for (let i = 0; i < municipalities.length; i++) {
        if (
            municipalities[i].properties.NAME_1 !== "Wallonie" &&
            municipalities[i].properties.NAME_1 !== "Bruxelles"
        ) {
            municipalityNames.push(municipalities[i].properties.NAME_4);
        }
    }
    municipalityNames.sort();
    let municipalitySelect = document.getElementById("municipality-select");
    for (let i = 0; i < municipalityNames.length; i++) {
        let option = document.createElement("option");

        option.value = municipalityNames[i];
        option.text = municipalityNames[i];
        municipalitySelect.appendChild(option);
    }

    g.append("path")
        .datum(
            topojson.mesh(
                topology,
                topology.objects.Gemeenten,
                function (a, b) {
                    return a !== b;
                }
            )
        )
        .attr("class", "mesh")
        .attr("d", path);
});

let municipalitySelect = document.getElementById("municipality-select");
municipalitySelect.addEventListener("change", function () {
    let municipality = municipalitySelect.value;
    let municipalityPath = document.querySelector(
        `path[data_name="${municipality}"]`
    );
    clicked.call(municipalityPath);
});

function clicked() {
    if (active.node() === this) return reset();
    if (
        this.attributes.data_region.value === "Wallonie" ||
        this.attributes.data_region.value === "Bruxelles"
    ) {
        console.log("Invalid Choice");
        return;
    }

    active.classed("active", false);
    active = d3.select(this).classed("active", true);

    let municipality = this.attributes.data_name.value;
    let municipalitySelect = document.getElementById("municipality-select");
    municipalitySelect.value = municipality;
}

function reset() {
    active.classed("active", false);
    active = d3.select(null);

    g.transition()
        .duration(750)
        .style("stroke-width", ".5px")
        .attr("transform", "");
}

d3.select(self.frameElement).style("height", height + "px");

const svgImage = document.querySelector("#map-svg");
const svgContainer = document.querySelector("#map-svg");

var viewBox = {
    x: 0,
    y: 0,
    w: svgImage.clientWidth,
    h: svgImage.clientHeight,
};
svgImage.setAttribute(
    "viewBox",
    `${viewBox.x} ${viewBox.y} ${viewBox.w} ${viewBox.h}`
);
const svgSize = {w: svgImage.clientWidth, h: svgImage.clientHeight};
var isPanning = false;
var startPoint = {x: 0, y: 0};
var endPoint = {x: 0, y: 0};
var scale = 1;

svgContainer.onmousewheel = function (e) {
    e.preventDefault();
    var w = viewBox.w;
    var h = viewBox.h;
    var mx = e.offsetX; //mouse x
    var my = e.offsetY;
    var dw = w * Math.sign(e.deltaY) * 0.05;
    var dh = h * Math.sign(e.deltaY) * 0.05;
    var dx = (dw * mx) / svgSize.w;
    var dy = (dh * my) / svgSize.h;
    viewBox = {
        x: viewBox.x + dx,
        y: viewBox.y + dy,
        w: viewBox.w - dw,
        h: viewBox.h - dh,
    };
    scale = svgSize.w / viewBox.w;

    svgImage.setAttribute(
        "viewBox",
        `${viewBox.x} ${viewBox.y} ${viewBox.w} ${viewBox.h}`
    );
};

svgContainer.onmousedown = function (e) {
    isPanning = true;
    startPoint = {x: e.x, y: e.y};
};

svgContainer.onmousemove = function (e) {
    if (isPanning) {
        endPoint = {x: e.x, y: e.y};
        var dx = (startPoint.x - endPoint.x) / scale;
        var dy = (startPoint.y - endPoint.y) / scale;
        var movedViewBox = {
            x: viewBox.x + dx,
            y: viewBox.y + dy,
            w: viewBox.w,
            h: viewBox.h,
        };
        svgImage.setAttribute(
            "viewBox",
            `${movedViewBox.x} ${movedViewBox.y} ${movedViewBox.w} ${movedViewBox.h}`
        );
    }
};

svgContainer.onmouseup = function (e) {
    if (isPanning) {
        endPoint = {x: e.x, y: e.y};
        var dx = (startPoint.x - endPoint.x) / scale;
        var dy = (startPoint.y - endPoint.y) / scale;
        viewBox = {
            x: viewBox.x + dx,
            y: viewBox.y + dy,
            w: viewBox.w,
            h: viewBox.h,
        };
        svgImage.setAttribute(
            "viewBox",
            `${viewBox.x} ${viewBox.y} ${viewBox.w} ${viewBox.h}`
        );
        isPanning = false;
    }
};

svgContainer.onmouseleave = function () {
    isPanning = false;
};

const slider = document.getElementById("zoomRange");
const zoomValue = document.getElementById("zoomValue");

slider.oninput = function () {
    if (scale < 1) {

    } else {
        if (this.value > 100) {
            this.value = 100;
        }

        zoomValue.innerText = `${this.value}%`;
        svgContainer.style.transform = `scale(${(this.value + 1) / 100})`;
    }
}