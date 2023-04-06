/**
 * Like an entity
 * @param {number} entityId
 * @param {string} type - type of entity, plural form
 */
const like = (entityId, type) => {
    let pathname = window.location.pathname;
    let parts = pathname.split("/");
    let youthCouncilId = parts[2];

    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;

    fetch(`/api/${type}/${entityId}/likes`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            [header]: token,
        }
    })
}