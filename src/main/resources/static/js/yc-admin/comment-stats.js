import {dataTypes, filterTypes, manageFilters, updateGraph} from "./all-graphs.js";

export async function getAllYcStatsData(dataType) {
    return fetch(`/api/yc-admin/stats/${dataType}`)
        .then(response => response.json())
        .then(data => data);
}


let commentCountDisplay = document.getElementById("comments-count")
const comments = await getAllYcStatsData("comments")
let commentCount = comments.length


updateGraph(dataTypes.COMMENTS, filterTypes.YEAR);
manageFilters(dataTypes.COMMENTS);
commentCountDisplay.innerHTML = commentCount

