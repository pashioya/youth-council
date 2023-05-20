import {getAllYcStatsData} from "./all-graphs.js";

let ideaCountDisplay = document.getElementById("ideas-count")
const ideas = await getAllYcStatsData("ideas")
let ideaCount = ideas.length
ideaCountDisplay.innerHTML = ideaCount

