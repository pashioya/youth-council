let width = 1200,
    height = 1200,
    active = d3.select(null);


let projection = d3
    .geoNaturalEarth1()
    .translate([width / 120, height / 0.119])
    .scale(11000);

let path = d3.geoPath().projection(projection);

let svg = d3.select("#map").append("svg").attr("id", "map-svg");

svg.append("rect")
    .attr("class", "background")
    .attr("id", "map-background")
    .attr("width", width)
    .attr("height", height)
    .on("click", reset);

let g = svg.append("g").style("stroke-width", ".5px").style("stroke", "black");

let youthCouncilView = document.getElementById("youth-council-view");
let goToYouthCouncilButton = document.getElementById("go-to-youth-council-button");
fetch("api/youth-councils")
    .then((response) => response.json())
    .then((data) => {
            let youthCouncils = data;
            let municipalityNames = [];
            for (let i = 0; i < youthCouncils.length; i++) {
                municipalityNames.push(youthCouncils[i].municipalityName);
            }
            municipalityNames.sort();
            let municipalitySelect = document.getElementById("municipality-select");
            for (let i = 0; i < municipalityNames.length; i++) {
                let option = document.createElement("option");

                option.value = municipalityNames[i];
                option.text = municipalityNames[i];
                municipalitySelect.appendChild(option);
            }

            // add event listener to go to youth council button
            if (goToYouthCouncilButton) {
                goToYouthCouncilButton.addEventListener("click", function () {
                    let youthCouncil = youthCouncils.find(
                        (youthCouncil) => youthCouncil.municipalityName === municipalitySelect.value
                    );
                    if (youthCouncil) {
                        location.replace("http://" + youthCouncil.slug + ".localhost:8080");
                        goToYouthCouncilButton.href = "http://" + youthCouncil.slug + ".localhost:8080";
                    }
                });
            }

            // on change of municipality select, update the map and youth council view
            municipalitySelect.addEventListener("change", function () {
                let municipality = this.value;
                let municipalityPaths = document.querySelectorAll(
                    "path[data_name='" + municipality + "']"
                );
                if (municipalityPaths.length > 0) {
                    let municipalityPath = municipalityPaths[0];
                    clicked.call(municipalityPath);
                }
                // update the youth council view
                let youthCouncil = youthCouncils.find(
                    (youthCouncil) => youthCouncil.municipalityName === municipality
                );
                if (youthCouncil) {
                    youthCouncilView.value = youthCouncil.name;
                    goToYouthCouncilButton.href = "http://" + youthCouncil.slug + ".localhost:8080";
                }
            });

            d3.json("api/youth-councils/map-data").then(function (topology) {
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
                            if (municipalityNames.includes(d.properties.NAME_4)) {
                                return "lightblue";
                            } else {
                                return "#ffcccb";
                            }
                        }
                    })
                    .on("click", clicked);

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
        }
    );


// load and display Belgium map


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

    fetch("api/youth-councils")
        .then((response) => response.json())
        .then((data) => {
            let youthCouncil = data.find(
                (youthCouncil) => youthCouncil.municipalityName === municipality
            );
            if (youthCouncil) {
                youthCouncilView.value = youthCouncil.name;
                goToYouthCouncilButton.href = "/youth-councils/" + youthCouncil.id;
            } else {
                youthCouncilView.value = "";
                goToYouthCouncilButton.href = "";
            }
        });
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

let viewBox = {
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
let isPanning = false;
let startPoint = {x: 0, y: 0};
let endPoint = {x: 0, y: 0};
let scale = 1;

svgContainer.onmousewheel = function (e) {
    e.preventDefault();
    let w = viewBox.w;
    let h = viewBox.h;
    let mx = e.offsetX; //mouse x
    let my = e.offsetY;
    let dw = w * Math.sign(e.deltaY) * 0.05;
    let dh = h * Math.sign(e.deltaY) * 0.05;
    let dx = (dw * mx) / svgSize.w;
    let dy = (dh * my) / svgSize.h;
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
        let dx = (startPoint.x - endPoint.x) / scale;
        let dy = (startPoint.y - endPoint.y) / scale;
        let movedViewBox = {
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
        let dx = (startPoint.x - endPoint.x) / scale;
        let dy = (startPoint.y - endPoint.y) / scale;
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


