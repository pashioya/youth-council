import {getAllYcStatsData} from "./all-graphs.js";


let commentCountDisplay = document.getElementById("comments-count")
const comments = await getAllYcStatsData("comments")
let commentCount = comments.length


commentCountDisplay.innerHTML = commentCount

