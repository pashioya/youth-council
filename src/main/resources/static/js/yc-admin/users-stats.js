import {getAllYcStatsData} from "./all-graphs.js";

let userCountDisplay = document.getElementById("user-count")
const users = await getAllYcStatsData("users")
let userCount = users.length
userCountDisplay.innerHTML = userCount

