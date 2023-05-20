import {manageFilters,dataTypes,filterTypes} from "../yc-admin/all-graphs.js";
import {getAllGaStatsData, updateGraph} from "./ga-stats.js";

updateGraph(dataTypes.COMMENTS, filterTypes.YEAR);
manageFilters(dataTypes.COMMENTS);

let commentCountDisplay = document.getElementById("comments-count")

const comments = await getAllGaStatsData("comments")
let commentCount = comments.length

commentCountDisplay.innerHTML = commentCount

