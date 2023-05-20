import {filterData, filterTypes} from "../yc-admin/all-graphs.js";


export const filterTypes = {
    DAY: "DAY",
    WEEK: "WEEK",
    MONTH: "MONTH",
    YEAR: "YEAR",
    ALL_TIME: "ALL_TIME"
}

export const dataTypes = {
    USERS: "users",
    IDEAS: "ideas",
    COMMENTS: "comments"
}

export async function getAllGaStatsData(dataType) {
    return fetch(`/api/ga/stats/${dataType}`)
        .then(response => response.json())
        .then(data => data);
}

export async function updateGraph(dataType, filterType) {
    let data = await getAllGaStatsData(dataType);
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




