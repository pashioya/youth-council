export function getAllYcStatsuser() {
    return fetch('/api/yc-admin/stats/users')
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
const userFilterTypes = {
    DAY: "DAY",
    WEEK: "WEEK",
    MONTH: "MONTH",
    YEAR: "YEAR",
    ALL_TIME: "ALL_TIME"
}

// Set default filter type
let userFilterType = userFilterTypes.YEAR;
// Set default filter type to active
document.querySelector(`#user-growth-${userFilterType}`).classList.add("active");

// Add event listeners to filter buttons
document.querySelector("#user-growth-DAY").addEventListener("click", function () {
    userFilterType = userFilterTypes.DAY;
    setActiveFilterButton("#user-growth-DAY");
    updateGraph();
});

document.querySelector("#user-growth-WEEK").addEventListener("click", function () {
    userFilterType = userFilterTypes.WEEK;
    setActiveFilterButton("#user-growth-WEEK");
    updateGraph();
});

document.querySelector("#user-growth-MONTH").addEventListener("click", function () {
    userFilterType = userFilterTypes.MONTH;
    setActiveFilterButton("#user-growth-MONTH");
    updateGraph();
});

document.querySelector("#user-growth-YEAR").addEventListener("click", function () {
    userFilterType = userFilterTypes.YEAR;
    setActiveFilterButton("#user-growth-YEAR");
    updateGraph();
});
document.querySelector("#user-growth-ALL_TIME").addEventListener("click", function () {
    userFilterType = userFilterTypes.ALL_TIME;
    setActiveFilterButton("#user-growth-ALL_TIME");
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

function countusersByYear(users, filterType) {
    const userCountData = [];

    if (filterType === userFilterTypes.ALL_TIME) {
        const userCountByYear = {};
        users.forEach(user => {
            const year = new Date(user.createdDate).getFullYear().toString();
            if (userCountByYear[year]) {
                userCountByYear[year]++;
            } else {
                userCountByYear[year] = 1;
            }
        });
        userCountData.push(...Object.entries(userCountByYear).map(([year, count]) => ({year, count})));
    } else if (filterType === userFilterTypes.YEAR) {
        const currentDate = new Date();
        const currentYear = currentDate.getFullYear();
        const oneYearAgo = new Date(currentYear - 1, currentDate.getMonth(), currentDate.getDate());

        for (let month = oneYearAgo.getMonth(); month <= currentDate.getMonth(); month++) {
            const startDate = new Date(currentYear - 1, month, 1);
            const endDate = new Date(currentYear, month + 1, 0);
            const count = users.filter(user => {
                const userDate = new Date(user.createdDate);
                return userDate >= startDate && userDate <= endDate;
            }).length;
            userCountData.push({month: month.toString(), count});
        }


        // for (let year = oneYearAgo.getFullYear(); year <= currentYear; year++) {
        //     let count = 0;
        //
        //     for (let i = 0; i < 12; i++) {
        //         const startDate = new Date(year, i, 1);
        //         const endDate = new Date(year, i + 1, 0);
        //         count += users.filter(user => {
        //             const userDate = new Date(user.createdDate);
        //             return userDate >= startDate && userDate <= endDate;
        //         }).length;
        //     }
        //
        //     userCountData.push({year: year.toString(), count});
        // }
    } else if (filterType === userFilterTypes.MONTH) {
        const currentDate = new Date();
        const currentYear = currentDate.getFullYear();
        const currentMonth = currentDate.getMonth();

        const daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();
        for (let day = 1; day <= daysInMonth; day++) {
            const startDate = new Date(currentYear, currentMonth, day, 0, 0, 0);
            const endDate = new Date(currentYear, currentMonth, day, 23, 59, 59);
            const count = users.filter(user => {
                const userDate = new Date(user.createdDate);
                return userDate >= startDate && userDate <= endDate;
            }).length;
            userCountData.push({day: day.toString(), count});
        }
    } else if (filterType === userFilterTypes.WEEK) {
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
            const count = users.filter(user => {
                const userDate = new Date(user.createdDate);
                return userDate >= startDate && userDate <= endDate;
            }).length;
            userCountData.push({day: startDate.toDateString(), count});
        }
    } else if (filterType === userFilterTypes.DAY) {
        const currentDate = new Date();
        const currentYear = currentDate.getFullYear();
        const currentMonth = currentDate.getMonth();
        const currentDay = currentDate.getDate();

        const hoursInDay = 24;
        for (let hour = 0; hour < hoursInDay; hour++) {
            const startDate = new Date(currentYear, currentMonth, currentDay, hour, 0, 0);
            const endDate = new Date(currentYear, currentMonth, currentDay, hour, 59, 59);
            const count = users.filter(user => {
                const userDate = new Date(user.createdDate);
                return userDate >= startDate && userDate <= endDate;
            }).length;
            userCountData.push({hour: hour.toString(), count});
        }
    }


    return userCountData;
}

async function updateGraph() {
    let users = await getAllYcStatsuser();
    let data = countusersByYear(users, userFilterType);

    const chartData = {
        labels: data.map(row => {
            if (userFilterType === userFilterTypes.DAY) {
                return `${row.hour}:00`;
            }
            if (userFilterType === userFilterTypes.YEAR) {
                return getMonthName(parseInt(row.month));
            } else {
                return row.year || row.day;
            }
        }),
        datasets: [
            {
                label: `user growth (${userFilterType.toLowerCase()})`,
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
    const chartElement = document.getElementById('user-growth-graph');
    if (chartElement.chart) {
        chartElement.chart.destroy();
    }

    // Create a new chart instance
    chartElement.chart = new Chart(chartElement, chartOptions);
}


updateGraph();
