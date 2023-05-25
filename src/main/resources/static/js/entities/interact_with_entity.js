const header = document.querySelector('meta[name="_csrf_header"]').content;
const token = document.querySelector('meta[name="_csrf"]').content;
/**
 * Like an entity
 * @param {number} entityId
 * @param {string} type - type of entity, plural form
 * @param {boolean} isLikedByUser - true if the entity is already liked by the user
 */
const toggleLike = (entityId, type) => {
    const likeContainer = document.getElementById(`like-container-${entityId}`);
    const isLikedByUser = likeContainer.children[0].style.fill === 'red';
    isLikedByUser ? removeLike(entityId, type, likeContainer) : addLike(entityId, type, likeContainer);
}

/**
 * Compute add like action
 * @param {number} entityId
 * @param {string} type - type of entity, plural form
 * @param {HTMLElement} likeContainer - container of the like button
 */
const addLike = (entityId, type, likeContainer) => {
    fetch(`/api/${type}/${entityId}/likes`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            [header]: token,
        }
    }).then(response => {
        if (response.status === 201) {
            const likeIcon = likeContainer.children[0];
            likeIcon.style.fill = 'red';
            const likeNumber = likeContainer.children[1];
            likeNumber.innerText = parseInt(likeNumber.innerText) + 1;
        }
    }).catch(error => {
        console.error(error)
            })
}

/**
 * Compute remove like action
 * @param {number} entityId
 * @param {string} type - type of entity, plural form
 * @param {HTMLElement} likeContainer - container of the like button
 */

const removeLike = (entityId, type,likeContainer) => {
    fetch(`/api/${type}/${entityId}/likes`, {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json',
            [header]: token,
        }
    }).then(response => {
        if (response.status === 204) {
            const likeIcon = likeContainer.children[0];
            likeIcon.style.fill = 'black';
            const likeNumber = likeContainer.children[1];
            likeNumber.innerText = parseInt(likeNumber.innerText) - 1;
        }
    }).catch(error => {
        console.error(error)
            })
}


// COMMENTS

/**
 * Add a comment to an entity
 * @param {number} entityId
 * @param {string} type - type of entity, plural form (backend endpoint)
 */
const addComment = (entityId, type) => {
    const commentContent = document.getElementById('add-comment-' + entityId).textContent;
    if (commentContent.trim() === "") {
        return;
    }

    fetch(
        "/api/"+type+"/" +
        entityId +
        "/comments",
        {
            method: "POST",
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json",
                [header]: token,
            },
            body: JSON.stringify({
                "content": commentContent,
            }),
        }
    ).then((response) => {
        if (response.status === 201) {
            location.reload();
        }
    });
    }


