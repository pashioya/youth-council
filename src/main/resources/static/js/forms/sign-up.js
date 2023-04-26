const form = document.querySelector('.needs-validation')
const submitButton = document.querySelector('#submit')

async function checkUsername(username) {
    const response = await fetch('/api/users')
    const users = await response.json()
    for (let user of users) {
        if (user.username === username) {
            return 'Username already exists'
        }
    }
    return null
}

function checkEmailValidity(email) {
    if (email.indexOf("@") === -1) {
        return false
    }
    return email.indexOf(".") !== -1;

}

async function submitForm(event) {
    if (!form.checkValidity()) {
        event.preventDefault()
        event.stopPropagation()
    }
    // check if the email is an actual email
    const email = document.querySelector('#email')
    if (!checkEmailValidity(email.value)) {
        email.setCustomValidity('Please enter a valid email address')
        email.parentElement.querySelector('.invalid-feedback').innerText = 'Please enter a valid email address'
        event.preventDefault()
        event.stopPropagation()
    } else {
        email.setCustomValidity('')
    }

    // check if the username already exists
    const username = document.querySelector('#username')
    const usernameError = await checkUsername(username.value)
    if (usernameError) {
        username.setCustomValidity(usernameError)
        username.parentElement.querySelector('.invalid-feedback').innerText = usernameError
        event.preventDefault()
        event.stopPropagation()
    } else {
        username.setCustomValidity('')
    }
    // check if the passwords match
    const password = document.querySelector('#password')
    const passwordConfirm = document.querySelector('#confirmPassword')
    if (password.value !== passwordConfirm.value) {
        passwordConfirm.setCustomValidity('Passwords do not match')
        passwordConfirm.parentElement.querySelector('.invalid-feedback').innerText = 'Passwords do not match'
        event.preventDefault()
        event.stopPropagation()
    } else {
        passwordConfirm.setCustomValidity('')
    }

    form.classList.add('was-validated')
}

submitButton.addEventListener('click', submitForm)


