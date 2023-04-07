/**
 * Like an entity
 * @param {number} entityId
 * @param {string} type - type of entity, plural form
 */
const like = (entityId, type) => {
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;

    fetch(`/api/${type}/${entityId}/likes`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            [header]: token,
        }
    }).then(response => {
        if (response.status === 201) {
            const likeCount = document.getElementById(`like-count-${entityId}`);
            likeCount.innerText = parseInt(likeCount.innerText) + 1;
        }
    }).catch(error => {
        console.log(error);
    })
}