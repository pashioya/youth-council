// create enum for different filter types (e.g. day, week, month, year)
const ideasFilterTypes = {
    DAY: "DAY",
    WEEK: "WEEK",
    MONTH: "MONTH",
    YEAR: "YEAR"
}

// Set default filter type
let ideaFilterType = ideasFilterTypes.YEAR;
// Set default filter type to active
document.querySelector(`#idea-growth-${ideaFilterType}`).classList.add("active");

// Add event listeners to filter buttons
document.querySelector("#idea-growth-DAY").addEventListener("click", function () {
    ideaFilterType = ideasFilterTypes.DAY;
    setActiveFilterButton("#idea-growth-DAY");
    updateGraph();
});

document.querySelector("#idea-growth-WEEK").addEventListener("click", function () {
    ideaFilterType = ideasFilterTypes.WEEK;
    setActiveFilterButton("#idea-growth-WEEK");
    updateGraph();
});

document.querySelector("#idea-growth-MONTH").addEventListener("click", function () {
    ideaFilterType = ideasFilterTypes.MONTH;
    setActiveFilterButton("#idea-growth-MONTH");
    updateGraph();
});

document.querySelector("#idea-growth-YEAR").addEventListener("click", function () {
    ideaFilterType = ideasFilterTypes.YEAR;
    setActiveFilterButton("#idea-growth-YEAR");
    updateGraph();
});

function setActiveFilterButton(buttonId) {
    const activeClass = "active";
    document.getElementById("idea-filter").querySelectorAll(".filter-button").forEach(function (button) {
        if (button.id === buttonId) {
            button.classList.add(activeClass);
        } else {
            button.classList.remove(activeClass);
        }
    });
}


// Function to update graph based on filter type
function updateGraph() {
//     clear graph
    d3.select("#ideas-growth-graph").selectAll("*").remove();

// set the dimensions and margins of the graph
    const margin = {top: 10, right: 30, bottom: 30, left: 60},
        width = document.querySelector(".graph").clientWidth - margin.left - margin.right,
        height = 400 - margin.top - margin.bottom;

// append the svg object to the body of the page
    const svg = d3.select("#ideas-growth-graph")
        .append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", `translate(${margin.left},${margin.top})`);

//Read the data
    d3.json("api/ga/ideas").then(
        // Now I can use this dataset:
        function (data) {

            console.log(data)

            // Calculate cumulative sum of ideas over time
            let cumsum = 0;
            let dates = [];
            for (let idea of data) {
                cumsum += 1; // Increment cumulative sum by 1 for each idea
                console.log(idea.createdDate);
                dates.push({
                    date: new Date(idea.createdDate),
                    value: cumsum  // Set value to cumulative sum
                });
            }

            // Filter data based on filter type
            let x = null;
            switch (ideaFilterType) {
                case ideasFilterTypes.DAY:
                    x = dayFilter();
                    break
                case ideasFilterTypes.WEEK:
                    x = weekFilter();
                    break
                case ideasFilterTypes.MONTH:
                    x = monthFilter();
                    break;
                case ideasFilterTypes.YEAR:
                    x = yearFilter();
                    break;
            }

            // Max value observed:
            const max = d3.max(dates, function (d) {
                return d.value;
            })

            // Add Y axis
            const y = d3.scaleLinear()
                .domain([0, max])
                .range([height, 0]);
            svg.append("g")
                .call(d3.axisLeft(y));

            // Set the gradient
            svg.append("linearGradient")
                .attr("id", "line-gradient")
                .attr("gradientUnits", "userSpaceOnUse")
                .attr("x1", 0)
                .attr("y1", y(0))
                .attr("x2", 0)
                .attr("y2", y(max))
                .selectAll("stop")
                .data([
                    {offset: "0%", color: "blue"},
                    {offset: "100%", color: "red"}
                ])
                .enter().append("stop")
                .attr("offset", function (d) {
                    return d.offset;
                })
                .attr("stop-color", function (d) {
                    return d.color;
                });

            // Add the line
            svg.append("path")
                .datum(dates)
                .attr("fill", "none")
                .attr("stroke", "url(#line-gradient)")
                .attr("stroke-width", 2)
                .attr("d", d3.line()
                    .x(function (d) {
                        return x(d.date)
                    })
                    .y(function (d) {
                        return y(d.value)
                    })
                )

            function setXAxisTicks(tickFormat) {
                const x = d3.scaleTime()
                    .domain(d3.extent(dates, function (d) {
                        return d.date;
                    }))
                    .range([0, width]);

                svg.append("g")
                    .attr("transform", `translate(0, ${height})`)
                    .call(d3.axisBottom(x).ticks(tickFormat));
                return x;
            }

            function monthFilter() {
                return setXAxisTicks(d3.timeMonth);
            }

            function weekFilter() {
                return setXAxisTicks(d3.timeWeek);
            }

            function dayFilter() {
                return setXAxisTicks(d3.timeDay);
            }

            function yearFilter() {
                return setXAxisTicks(d3.timeYear);
            }
        });
}


updateGraph();
