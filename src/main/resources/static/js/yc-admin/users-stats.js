import {getAllYcStatsData} from "./comment-stats.js"

let userCountDisplay = document.getElementById("user-count")
const users = await getAllYcStatsData("users")
let userCount = users.length
userCountDisplay.innerHTML = userCount

