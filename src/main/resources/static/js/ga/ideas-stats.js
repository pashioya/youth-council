import {manageFilters,dataTypes,filterTypes} from "../yc-admin/all-graphs.js";
import {getAllGaStatsData, updateGraph} from "./ga-stats.js";


updateGraph(dataTypes.IDEAS, filterTypes.YEAR);
manageFilters(dataTypes.IDEAS);


let ideaCountDisplay = document.getElementById("ideas-count")

const ideas = await getAllGaStatsData("ideas")
let ideaCount = ideas.length

ideaCountDisplay.innerHTML = ideaCount

