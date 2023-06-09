import {deleteUser} from "./delete_user.js";


const deleteButtons = document.querySelectorAll('.delete-user');
deleteButtons.forEach(button => {
        button.addEventListener('click', async () => {
            let id = parseInt(button.getAttribute('data-user-id'));
            console.log(id);
            console.log(typeof id)
            let response = await deleteUser(id);
            if (response.status === 204) {
                let row = button.parentNode.parentNode
                row.remove();
            } else {
                alert("Something went wrong");
            }
        });
    }
);
