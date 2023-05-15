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
