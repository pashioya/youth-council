const form = document.querySelector('.needs-validation')
form.addEventListener('submit', event => {
    if (!form.checkValidity()) {
        event.preventDefault()
        event.stopPropagation()
    }

    // check if the passwords match
    const password = document.querySelector('#password')
    const passwordConfirm = document.querySelector('#confirmPassword')
    if (password.value !== passwordConfirm.value) {
        passwordConfirm.setCustomValidity('Passwords do not match')
        event.preventDefault()
        event.stopPropagation()
    }

    form.classList.add('was-validated')
}, false)


