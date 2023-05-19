import {updateEntity} from "../../api/api_facade.js";

addEventListener("submit", (event) => {
    event.preventDefault();
    const form = event.target;
    updateActionPoint(form);
});
const updateActionPoint = async (target) => {

    const status = target.querySelector("select[id='status']").value;
    const standardAction = target.querySelector("select[id='standardAction']").value;
    const actionPointId = window.location.pathname.split("/").pop();
    const linkedIdeas = target.querySelector("select[id='linkedIdeas']").selectedOptions;
    const linkedIdeasIds = Array.from(linkedIdeas).map((x) => x.value);
    const title = target.querySelector("input[id='title']").value;
    const description = target.querySelector("textarea[id='description']").value;

    const data = {
        id: actionPointId,
        status: status,
        standardActionId: standardAction,
        linkedIdeasIds: linkedIdeasIds,
        title: title,
        description: description
    };
    await updateEntity("action-points", data);
}

