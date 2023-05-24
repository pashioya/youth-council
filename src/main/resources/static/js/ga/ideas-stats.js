import {getAllGaStatsData} from "./ga-stats.js";

let ideaCountDisplay = document.getElementById("ideas-count")

const ideas = await getAllGaStatsData("ideas")
let ideaCount = ideas.length

ideaCountDisplay.innerHTML = ideaCount

