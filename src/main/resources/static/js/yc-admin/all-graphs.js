export async function getAllYcStatsData(dataType) {
    return fetch(`/api/yc-admin/stats/${dataType}`)
        .then(response => response.json())
        .then(data => data);
}

async function updateGraph(dataType, filterType) {
    let data = await getAllYcStatsData(dataType);
    let countData = filterData(data, filterType);
    const chartData = {
        labels: countData.map(row => {
            if (filterType === filterTypes.DAY) {
                return `${row.hour}:00`;
            }
            if (filterType === filterTypes.YEAR) {
                return getMonthName(row.month);
            } else {
                return row.year || row.day;
            }
        }),
        datasets: [
            {
                label: `${dataType} growth (${filterType.toLowerCase()})`,
                data: countData.map(row => row.count),
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

    const chartElement = document.getElementById(`${dataType}-growth-graph`);
    if (chartElement.chart) {
        chartElement.chart.destroy();
    }

    chartElement.chart = new Chart(chartElement, chartOptions);

    function getMonthName(monthIndex) {
        const months = [
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        ];
        return months[monthIndex];
    }
}

function manageFilters(dataType) {
    let filterType = filterTypes.YEAR;   // default filter type
    document.querySelector(`#${dataType}-growth-DAY`).addEventListener("click", function () {
        filterType = filterTypes.DAY;
        setActiveFilterButton(`#${dataType}-growth-DAY`);
        updateGraph(dataType, filterType);
    });

    document.querySelector(`#${dataType}-growth-WEEK`).addEventListener("click", function () {
        filterType = filterTypes.WEEK;
        setActiveFilterButton(`#${dataType}-growth-WEEK`);
        updateGraph(dataType, filterType);
    });

    document.querySelector(`#${dataType}-growth-MONTH`).addEventListener("click", function () {
        filterType = filterTypes.MONTH;
        setActiveFilterButton(`#${dataType}-growth-MONTH`);
        updateGraph(dataType, filterType);
    });

    document.querySelector(`#${dataType}-growth-YEAR`).addEventListener("click", function () {
        filterType = filterTypes.YEAR;
        setActiveFilterButton(`#${dataType}-growth-YEAR`);
        updateGraph(dataType, filterType);
    });

    document.querySelector(`#${dataType}-growth-ALL_TIME`).addEventListener("click", function () {
        filterType = filterTypes.ALL_TIME;
        setActiveFilterButton(`#${dataType}-growth-ALL_TIME`);
        updateGraph(dataType, filterType);
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
}

function filterData(data, filterType) {
    const countData = [];
    if (filterType === filterTypes.ALL_TIME) {
        const countByYear = {};
        data.forEach(item => {
            const year = new Date(item.createdDate).getFullYear().toString();
            if (countByYear[year]) {
                countByYear[year]++;
            } else {
                countByYear[year] = 1;
            }
        });
        countData.push(...Object.entries(countByYear).map(([year, count]) => ({year, count})));
    } else if (filterType === filterTypes.YEAR) {
        const currentDate = new Date();
        const currentYear = currentDate.getFullYear();
        const monthsInYear = 12;

        for (let month = 0; month < monthsInYear; month++) {
            const startDate = new Date(currentYear, month, 1, 0, 0, 0);
            const endDate = new Date(currentYear, month + 1, 0, 23, 59, 59); // Set end date to the last day of the month
            const count = data.filter(item => {
                const itemDate = new Date(item.createdDate);
                return itemDate >= startDate && itemDate <= endDate;
            }).length;
            countData.push({month: month, count});
        }
    } else if (filterType === filterTypes.MONTH) {
        const currentDate = new Date();
        const currentYear = currentDate.getFullYear();
        const currentMonth = currentDate.getMonth();

        const daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();
        for (let day = 1; day <= daysInMonth; day++) {
            const startDate = new Date(currentYear, currentMonth, day, 0, 0, 0);
            const endDate = new Date(currentYear, currentMonth, day, 23, 59, 59);
            const count = data.filter(item => {
                const itemDate = new Date(item.createdDate);
                return itemDate >= startDate && itemDate <= endDate;
            }).length;
            countData.push({day: day.toString(), count});
        }
    } else if (filterType === filterTypes.WEEK) {
        const currentDate = new Date();
        const currentYear = currentDate.getFullYear();
        const currentMonth = currentDate.getMonth();
        const currentDay = currentDate.getDate();

        const startOfWeek = new Date(currentYear, currentMonth, currentDay - currentDate.getDay());
        const daysInWeek = 7;

        for (let i = 0; i < daysInWeek; i++) {
            const startDate = new Date(startOfWeek.getFullYear(), startOfWeek.getMonth(), startOfWeek.getDate() + i, 0, 0, 0);
            const endDate = new Date(startOfWeek.getFullYear(), startOfWeek.getMonth(), startOfWeek.getDate() + i, 23, 59, 59);
            const count = data.filter(item => {
                const itemDate = new Date(item.createdDate);
                return itemDate >= startDate && itemDate <= endDate;
            }).length;
            countData.push({day: startDate.toDateString(), count});
        }
    } else if (filterType === filterTypes.DAY) {
        const currentDate = new Date();
        const currentYear = currentDate.getFullYear();
        const currentMonth = currentDate.getMonth();
        const currentDay = currentDate.getDate();

        const hoursInDay = 24;
        for (let hour = 0; hour < hoursInDay; hour++) {
            const startDate = new Date(currentYear, currentMonth, currentDay, hour, 0, 0);
            const endDate = new Date(currentYear, currentMonth, currentDay, hour, 59, 59);
            const count = data.filter(item => {
                const itemDate = new Date(item.createdDate);
                return itemDate >= startDate && itemDate < endDate;
            }).length;
            countData.push({hour: hour.toString(), count});
        }
    }

    return countData;
}

const filterTypes = {
    DAY: "DAY",
    WEEK: "WEEK",
    MONTH: "MONTH",
    YEAR: "YEAR",
    ALL_TIME: "ALL_TIME"
}

const dataTypes = {
    USERS: "users",
    IDEAS: "ideas",
    COMMENTS: "comments"
}


updateGraph(dataTypes.USERS, filterTypes.YEAR);
updateGraph(dataTypes.IDEAS, filterTypes.YEAR);
updateGraph(dataTypes.COMMENTS, filterTypes.YEAR);

manageFilters(dataTypes.USERS);
manageFilters(dataTypes.IDEAS);
manageFilters(dataTypes.COMMENTS);
