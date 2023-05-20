import {manageFilters,dataTypes,filterTypes} from "../yc-admin/all-graphs.js";
import {getAllGaStatsData, updateGraph} from "./ga-stats.js";

updateGraph(dataTypes.USERS, filterTypes.YEAR);
manageFilters(dataTypes.USERS);

let userCountDisplay = document.getElementById("user-count")
const users = await getAllGaStatsData("users")
let userCount = users.length
userCountDisplay.innerHTML = userCount

