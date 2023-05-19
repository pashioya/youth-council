export async function getAllCommentsByUserID(userID) {
    const response = await fetch(`/api/comments/${userID}`);
    if (response.ok) {
        const json = await response.text();
        return json ? JSON.parse(json) : []; // Parse the JSON only if it's not empty
    } else {
        throw new Error('Failed to fetch comments');
    }
}

export async function getAllIdeasByUserID(userID) {
    const response = await fetch(`/api/ideas/user/${userID}`);
    if (response.ok) {
        const json = await response.text();
        return json ? JSON.parse(json) : []; // Parse the JSON only if it's not empty
    } else {
        throw new Error('Failed to fetch ideas');
    }
}

const sectionElement = document.querySelector('[data-userId]');
const userId = sectionElement.getAttribute('data-userId');
try {
    let comments = await getAllCommentsByUserID(userId);

    if (comments.length !== 0) {
        comments.slice(0, 5).forEach(comment => {
            const date = new Date(comment.createdDate);
            const month = date.getMonth() + 1;
            const day = date.getDate();
            const formattedDate = `${day}/${month}`;
            const card = document.createElement('div');
            card.classList.add('card', 'p-3', 'mb-2');
            card.innerHTML = `
                <div class="d-flex justify-content-between align-items-center">
                    <div class="user d-flex flex-row align-items-center">
                        <span> <small class="font-weight-bold">${comment.content}</small></span>
                    </div>
                    <small>${formattedDate}</small>
                </div>
                <div class="action d-flex justify-content-between mt-2 align-items-center">
                    <div class="w-50 d-flex justify-content-between">
                        <i class="bi bi-trash-fill text-danger"></i>
                    </div>
                    <div class="icons align-items-center">
                        <i class="fa fa-star text-warning"></i>
                        <i class="fa fa-check-circle-o check-icon"></i>
                    </div>
                </div>
            </div>
            `;
            document.getElementById('recent-comments').appendChild(card);
        });
    } else {
        const noComment = document.createElement('p')
        noComment.classList.add('text-center', 'text-muted');
        noComment.innerHTML = 'No comments yet';
        document.getElementById('recent-comments').appendChild(noComment);
    }
} catch (error) {
    console.error(error);
}

try {
    let ideas = await getAllIdeasByUserID(userId);
    if (ideas.length !== 0) {
        ideas.slice(0, 5).forEach(idea => {
            const date = new Date(idea.dateAdded);
            const month = date.getMonth() + 1;
            const day = date.getDate();
            const formattedDate = `${day}/${month}`;
            const card = document.createElement('div');
            card.classList.add('card', 'p-3', 'mb-2');
            card.innerHTML = `
                <div class="d-flex justify-content-between align-items-center">
                    <div class="user d-flex flex-row align-items-center">
                        <span> <small class="font-weight-bold">${idea.description}</small></span>
                    </div>
                    <small class="ms-2">${formattedDate}</small>
                </div>
                <div class="action d-flex justify-content-between mt-2 align-items-center">
                    <div class="w-50 d-flex justify-content-between">
                        <i class="bi bi-trash-fill text-danger"></i>
                    </div>
                    <div class="icons align-items-center">
                        <i class="fa fa-star text-warning"></i>
                        <i class="fa fa-check-circle-o check-icon"></i>
                    </div>
                </div>
            `;
            document.getElementById('recent-ideas').appendChild(card);
        });
    } else {
        const noIdea = document.createElement('p')
        noIdea.classList.add('text-center', 'text-muted');
        noIdea.innerHTML = 'No ideas yet';
        document.getElementById('recent-ideas').appendChild(noIdea);
    }
} catch (error) {
    console.error(error);
}