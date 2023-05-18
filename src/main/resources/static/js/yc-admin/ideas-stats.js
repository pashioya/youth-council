export function getAllYcStatsIdeas() {
    return fetch('/api/yc-admin/stats/ideas')
        .then(response => response.json())
        .then(data => data);
}


// create enum for different filter types (e.g. day, week, month, year)
const ideaFilterTypes = {
    DAY: "DAY",
    WEEK: "WEEK",
    MONTH: "MONTH",
    YEAR: "YEAR",
    ALL_TIME: "ALL_TIME"
}

// Set default filter type
let ideaFilterType = ideaFilterTypes.YEAR;
// Set default filter type to active
document.querySelector(`#idea-growth-${ideaFilterType}`).classList.add("active");

// Add event listeners to filter buttons
document.querySelector("#idea-growth-DAY").addEventListener("click", function () {
    ideaFilterType = ideaFilterTypes.DAY;
    setActiveFilterButton("#idea-growth-DAY");
    updateGraph();
});

document.querySelector("#idea-growth-WEEK").addEventListener("click", function () {
    ideaFilterType = ideaFilterTypes.WEEK;
    setActiveFilterButton("#idea-growth-WEEK");
    updateGraph();
});

document.querySelector("#idea-growth-MONTH").addEventListener("click", function () {
    ideaFilterType = ideaFilterTypes.MONTH;
    setActiveFilterButton("#idea-growth-MONTH");
    updateGraph();
});

document.querySelector("#idea-growth-YEAR").addEventListener("click", function () {
    ideaFilterType = ideaFilterTypes.YEAR;
    setActiveFilterButton("#idea-growth-YEAR");
    updateGraph();
});
document.querySelector("#idea-growth-ALL_TIME").addEventListener("click", function () {
    ideaFilterType = ideaFilterTypes.ALL_TIME;
    setActiveFilterButton("#idea-growth-ALL_TIME");
    updateGraph();
});

function setActiveFilterButton(buttonId) {
    const activeClass = "active";
    document.querySelectorAll(".filter-button").forEach(function (button) {
        if (button.id === buttonId) {
            button.classList.add(activeClass);
        } else {
            button.classList.remove(activeClass);
        }
    });
}

function countideasByYear(ideas, filterType) {
    const ideaCountData = [];

    if (filterType === ideaFilterTypes.ALL_TIME) {
        const ideaCountByYear = {};
        ideas.forEach(idea => {
            const year = new Date(idea.createdDate).getFullYear().toString();
            if (ideaCountByYear[year]) {
                ideaCountByYear[year]++;
            } else {
                ideaCountByYear[year] = 1;
            }
        });
        ideaCountData.push(...Object.entries(ideaCountByYear).map(([year, count]) => ({year, count})));
    } else if (filterType === ideaFilterTypes.YEAR) {
        const currentDate = new Date();
        const currentYear = currentDate.getFullYear();
        const oneYearAgo = new Date(currentYear - 1, currentDate.getMonth(), currentDate.getDate());

        for (let year = oneYearAgo.getFullYear(); year <= currentYear; year++) {
            let count = 0;

            for (let i = 0; i < 12; i++) {
                const startDate = new Date(year, i, 1);
                const endDate = new Date(year, i + 1, 0);
                count += ideas.filter(idea => {
                    const ideaDate = new Date(idea.createdDate);
                    return ideaDate >= startDate && ideaDate <= endDate;
                }).length;
            }

            ideaCountData.push({year: year.toString(), count});
        }
    } else if (filterType === ideaFilterTypes.MONTH) {
        const currentDate = new Date();
        const currentYear = currentDate.getFullYear();
        const currentMonth = currentDate.getMonth();

        const daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();
        for (let day = 1; day <= daysInMonth; day++) {
            const startDate = new Date(currentYear, currentMonth, day, 0, 0, 0);
            const endDate = new Date(currentYear, currentMonth, day, 23, 59, 59);
            const count = ideas.filter(idea => {
                const ideaDate = new Date(idea.createdDate);
                return ideaDate >= startDate && ideaDate <= endDate;
            }).length;
            ideaCountData.push({day: day.toString(), count});
        }
    } else if (filterType === ideaFilterTypes.WEEK) {
        const currentDate = new Date();
        const currentYear = currentDate.getFullYear();
        const currentMonth = currentDate.getMonth();
        const currentDay = currentDate.getDate();

        const startOfWeek = new Date(currentYear, currentMonth, currentDay - currentDate.getDay());
        const endOfWeek = new Date(currentYear, currentMonth, currentDay + (6 - currentDate.getDay()));
        const daysInWeek = 7;

        for (let i = 0; i < daysInWeek; i++) {
            const startDate = new Date(startOfWeek.getFullYear(), startOfWeek.getMonth(), startOfWeek.getDate() + i, 0, 0, 0);
            const endDate = new Date(startOfWeek.getFullYear(), startOfWeek.getMonth(), startOfWeek.getDate() + i, 23, 59, 59);
            const count = ideas.filter(idea => {
                const ideaDate = new Date(idea.createdDate);
                return ideaDate >= startDate && ideaDate <= endDate;
            }).length;
            ideaCountData.push({day: startDate.toDateString(), count});
        }
    } else if (filterType === ideaFilterTypes.DAY) {
        const currentDate = new Date();
        const currentYear = currentDate.getFullYear();
        const currentMonth = currentDate.getMonth();
        const currentDay = currentDate.getDate();

        const hoursInDay = 24;
        for (let hour = 0; hour < hoursInDay; hour++) {
            const startDate = new Date(currentYear, currentMonth, currentDay, hour, 0, 0);
            const endDate = new Date(currentYear, currentMonth, currentDay, hour, 59, 59);
            const count = ideas.filter(idea => {
                const ideaDate = new Date(idea.createdDate);
                return ideaDate >= startDate && ideaDate <= endDate;
            }).length;
            ideaCountData.push({hour: hour.toString(), count});
        }
    }


    return ideaCountData;
}

async function updateGraph() {
    let ideas = await getAllYcStatsIdeas();
    let data = countideasByYear(ideas, ideaFilterType);

    const chartData = {
        labels: data.map(row => {
            if (ideaFilterType === ideaFilterTypes.DAY) {
                return `${row.hour}:00`;
            } else {
                return row.year || row.day;
            }
        }),
        datasets: [
            {
                label: `idea growth (${ideaFilterType.toLowerCase()})`,
                data: data.map(row => row.count),
                fill: false,
                borderColor: 'rgb(75, 192, 192)',
                tension: 0.4
            }
        ]
    };

    const chartOptions = {
        type: 'line',
        data: chartData
    };

    // Destroy existing chart instance if it exists
    const chartElement = document.getElementById('idea-growth-graph');
    if (chartElement.chart) {
        chartElement.chart.destroy();
    }

    // Create a new chart instance
    chartElement.chart = new Chart(chartElement, chartOptions);
}


updateGraph();
