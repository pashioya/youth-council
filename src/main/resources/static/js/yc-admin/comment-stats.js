export function getAllYcStatsComment() {
    return fetch('/api/yc-admin/stats/comments')
        .then(response => response.json())
        .then(data => data);
}

function getMonthName(monthIndex) {
    const months = [
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    ];
    return months[monthIndex];
}


// create enum for different filter types (e.g. day, week, month, year)
const commentFilterTypes = {
    DAY: "DAY",
    WEEK: "WEEK",
    MONTH: "MONTH",
    YEAR: "YEAR",
    ALL_TIME: "ALL_TIME"
}

// Set default filter type
let commentFilterType = commentFilterTypes.YEAR;
// Set default filter type to active
document.querySelector(`#comment-growth-${commentFilterType}`).classList.add("active");

// Add event listeners to filter buttons
document.querySelector("#comment-growth-DAY").addEventListener("click", function () {
    commentFilterType = commentFilterTypes.DAY;
    setActiveFilterButton("#comment-growth-DAY");
    updateGraph();
});

document.querySelector("#comment-growth-WEEK").addEventListener("click", function () {
    commentFilterType = commentFilterTypes.WEEK;
    setActiveFilterButton("#comment-growth-WEEK");
    updateGraph();
});

document.querySelector("#comment-growth-MONTH").addEventListener("click", function () {
    commentFilterType = commentFilterTypes.MONTH;
    setActiveFilterButton("#comment-growth-MONTH");
    updateGraph();
});

document.querySelector("#comment-growth-YEAR").addEventListener("click", function () {
    commentFilterType = commentFilterTypes.YEAR;
    setActiveFilterButton("#comment-growth-YEAR");
    updateGraph();
});
document.querySelector("#comment-growth-ALL_TIME").addEventListener("click", function () {
    commentFilterType = commentFilterTypes.ALL_TIME;
    setActiveFilterButton("#comment-growth-ALL_TIME");
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

function countcommentsByYear(comments, filterType) {
    const commentCountData = [];

    if (filterType === commentFilterTypes.ALL_TIME) {
        const commentCountByYear = {};
        comments.forEach(comment => {
            const year = new Date(comment.createdDate).getFullYear().toString();
            if (commentCountByYear[year]) {
                commentCountByYear[year]++;
            } else {
                commentCountByYear[year] = 1;
            }
        });
        commentCountData.push(...Object.entries(commentCountByYear).map(([year, count]) => ({year, count})));
    } else if (filterType === commentFilterTypes.YEAR) {
        const currentDate = new Date();
        const currentYear = currentDate.getFullYear();
        const oneYearAgo = new Date(currentYear - 1, currentDate.getMonth(), currentDate.getDate());

        for (let month = oneYearAgo.getMonth(); month <= currentDate.getMonth(); month++) {
            const startDate = new Date(currentYear - 1, month, 1);
            const endDate = new Date(currentYear, month + 1, 0);
            const count = comments.filter(comment => {
                const commentDate = new Date(comment.createdDate);
                return commentDate >= startDate && commentDate <= endDate;
            }).length;
            commentCountData.push({month: month.toString(), count});
        }


        // for (let year = oneYearAgo.getFullYear(); year <= currentYear; year++) {
        //     let count = 0;
        //
        //     for (let i = 0; i < 12; i++) {
        //         const startDate = new Date(year, i, 1);
        //         const endDate = new Date(year, i + 1, 0);
        //         count += comments.filter(comment => {
        //             const commentDate = new Date(comment.createdDate);
        //             return commentDate >= startDate && commentDate <= endDate;
        //         }).length;
        //     }
        //
        //     commentCountData.push({year: year.toString(), count});
        // }
    } else if (filterType === commentFilterTypes.MONTH) {
        const currentDate = new Date();
        const currentYear = currentDate.getFullYear();
        const currentMonth = currentDate.getMonth();

        const daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();
        for (let day = 1; day <= daysInMonth; day++) {
            const startDate = new Date(currentYear, currentMonth, day, 0, 0, 0);
            const endDate = new Date(currentYear, currentMonth, day, 23, 59, 59);
            const count = comments.filter(comment => {
                const commentDate = new Date(comment.createdDate);
                return commentDate >= startDate && commentDate <= endDate;
            }).length;
            commentCountData.push({day: day.toString(), count});
        }
    } else if (filterType === commentFilterTypes.WEEK) {
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
            const count = comments.filter(comment => {
                const commentDate = new Date(comment.createdDate);
                return commentDate >= startDate && commentDate <= endDate;
            }).length;
            commentCountData.push({day: startDate.toDateString(), count});
        }
    } else if (filterType === commentFilterTypes.DAY) {
        const currentDate = new Date();
        const currentYear = currentDate.getFullYear();
        const currentMonth = currentDate.getMonth();
        const currentDay = currentDate.getDate();

        const hoursInDay = 24;
        for (let hour = 0; hour < hoursInDay; hour++) {
            const startDate = new Date(currentYear, currentMonth, currentDay, hour, 0, 0);
            const endDate = new Date(currentYear, currentMonth, currentDay, hour, 59, 59);
            const count = comments.filter(comment => {
                const commentDate = new Date(comment.createdDate);
                return commentDate >= startDate && commentDate <= endDate;
            }).length;
            commentCountData.push({hour: hour.toString(), count});
        }
    }


    return commentCountData;
}

async function updateGraph() {
    let comments = await getAllYcStatsComment();
    let data = countcommentsByYear(comments, commentFilterType);

    const chartData = {
        labels: data.map(row => {
            if (commentFilterType === commentFilterTypes.DAY) {
                return `${row.hour}:00`;
            }
            if (commentFilterType === commentFilterTypes.YEAR) {
                return getMonthName(parseInt(row.month));
            } else {
                return row.year || row.day;
            }
        }),
        datasets: [
            {
                label: `comment growth (${commentFilterType.toLowerCase()})`,
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
    const chartElement = document.getElementById('comment-growth-graph');
    if (chartElement.chart) {
        chartElement.chart.destroy();
    }

    // Create a new chart instance
    chartElement.chart = new Chart(chartElement, chartOptions);
}


updateGraph();
