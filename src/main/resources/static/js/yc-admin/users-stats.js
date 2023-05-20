import {getAllYcStatsData} from "./comment-stats.js"
import {dataTypes, filterTypes, manageFilters, updateGraph} from "./all-graphs.js";



let userCountDisplay = document.getElementById("user-count")
const users = await getAllYcStatsData("users")
let userCount = users.length


updateGraph(dataTypes.USERS, filterTypes.YEAR);
manageFilters(dataTypes.USERS);

userCountDisplay.innerHTML = userCount

