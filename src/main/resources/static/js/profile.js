import {deleteUser} from "./delete/delete_user.js";

const deleteButtons = document.querySelectorAll('.delete-user');
deleteButtons.forEach(button => {
        button.addEventListener('click', async () => {
            let id = parseInt(button.getAttribute('data-user-id'));
            let response = await deleteUser(id);
            if (response.status === 200) {
                window.location.href = "/dashboard/users";
            } else {
                alert("Something went wrong");
            }
        });
    }
);