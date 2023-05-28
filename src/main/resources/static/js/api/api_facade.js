import {getCsrfInfo} from "../common/utils.js";

/**
 * Fetches all entities of a given type
 * Supported entities: 'ideas', 'action-points', 'activities', 'news-items', 'elections'
 * @param entityType
 * @returns JSON array of entities
 */
export const fetchEntities = async (entityType) => {
    const response = await fetch(`/api/${entityType}`);
    return await response.json();
}

/**
 * remove an entity
 * @param entityType (ideas, action-points, activities, news-items, elections)
 * @param entityId
 * @returns {Promise<Response>}
 */
export const deleteEntity = async (entityType, entityId) => {
    const response = await fetch(`/api/${entityType}/${entityId}`, {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        }
    });
    if (response.status !== 204) {
        console.error(`Failed to delete ${entityType} with id ${entityId}`);
    }
    return response;
}

/**
 * update an entity
 * @param entityType (ideas, action-points, activities, news-items, elections)
 * @param entity (JSON object compatible with the backend's DTO)
 */
export const updateEntity = async (entityType, entity) => {
    const response = await fetch(`/api/${entityType}/${entity.id}`, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        },
        body: JSON.stringify(entity)
    });
    if (response.status !== 204) {
        console.error(`Failed to update ${entityType} with id ${entity.id}`);
    } else {
        const modal = document.createElement("div");
        modal.className = "alert alert-success";
        modal.setAttribute("role", "alert");
        modal.innerText = "Successfully updated!";
        const container = document.querySelector(".container");
        container.prepend(modal);


    }


}
