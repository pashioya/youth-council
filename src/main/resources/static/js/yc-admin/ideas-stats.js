import {getAllYcStatsData} from "./comment-stats.js"
import {dataTypes, filterTypes, manageFilters, updateGraph} from "./all-graphs.js";

let ideaCountDisplay = document.getElementById("ideas-count")
const ideas = await getAllYcStatsData("ideas")
let ideaCount = ideas.length

updateGraph(dataTypes.IDEAS, filterTypes.YEAR);
manageFilters(dataTypes.IDEAS);

ideaCountDisplay.innerHTML = ideaCount

