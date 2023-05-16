import {getCsrfInfo} from "../common/utils.js";

/**
 * Fetches all entities of a given type
 * Supported entities: 'ideas', 'action-points', 'activities', 'news-items'
 * @param entityType
 * @returns JSON array of entities
 */
export const fetchEntities = async (entityType) => {
    const response = await fetch(`/api/${entityType}`);
    return await response.json();
}

/**
 * remove an entity
 * @param entityType (ideas, action-points, activities, news-items)
 * @param entityId
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

}
