import {getAllGaStatsData} from "./ga-stats.js";

let commentCountDisplay = document.getElementById("comments-count")

const comments = await getAllGaStatsData("comments")
let commentCount = comments.length

commentCountDisplay.innerHTML = commentCount

