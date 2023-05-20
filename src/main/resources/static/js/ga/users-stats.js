import {getAllGaStatsData} from "./ga-stats.js";


let userCountDisplay = document.getElementById("user-count")
const users = await getAllGaStatsData("users")
let userCount = users.length
userCountDisplay.innerHTML = userCount

