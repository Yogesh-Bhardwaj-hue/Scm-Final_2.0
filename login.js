// Add at beginning of file
function checkStorageAvailability() {
    try {
        const storage = window.localStorage;
        const testKey = '__storage_test__';
        storage.setItem(testKey, testKey);
        storage.removeItem(testKey);
        return true;
    } catch (e) {
        console.error('LocalStorage not available. Login functionality may not work.');
        return false;
    }
}

// Call this function before using localStorage
checkStorageAvailability();

//Changes made to the original code:
// 1.  Client-side Email & Password Validation   
// 2. Clear Password Fields on Failed Login/Signup
// 3. Show Feedback After Logout

document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    const signupForm = document.getElementById('signupForm');
    const messageElement = document.getElementById('message');
    const logoutButton = document.getElementById('logoutButton');
    const loginButton = document.getElementById('loginButton');

    // Email and password validation functions
    function validateEmail(email) {
        return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    }
    function validatePassword(password) {
        return password.length >= 6;
    }

    function checkLoggedIn() {
        const currentUser = JSON.parse(localStorage.getItem('currentUser'));
        if (currentUser) {
            loginButton.style.display = 'none';
            document.body.classList.add('logged-in');
            if (logoutButton) {
                logoutButton.style.display = 'block';
            }
            if (window.location.pathname.includes('login.html') || window.location.pathname.includes('signup.html')) {
                window.location.href = 'index.html';
            }
        } else {
            document.body.classList.remove('logged-in');
            if (logoutButton) {
                loginButton.style.display = 'block';
                logoutButton.style.display = 'none';
            }
        }
    }

    checkLoggedIn();

    document.querySelectorAll('.toggle-password').forEach(icon => {
        icon.addEventListener('click', function() {
            const input = this.previousElementSibling;
            if (input.type === 'password') {
                input.type = 'text';
                this.classList.replace('fa-eye', 'fa-eye-slash');
            } else {
                input.type = 'password';
                this.classList.replace('fa-eye-slash', 'fa-eye');
            }
        });
    });

    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;

            // Client-side validation
            if (!validateEmail(email)) {
                showMessage('Please enter a valid email address.', false);
                document.getElementById('password').value = '';
                return;
            }
            if (!validatePassword(password)) {
                showMessage('Password must be at least 6 characters.', false);
                document.getElementById('password').value = '';
                return;
            }

            const users = JSON.parse(localStorage.getItem('users')) || [];
            const user = users.find(u => u.email === email && u.password === password);

            if (user) {
                showMessage('Login successful! Redirecting...', true);
                localStorage.setItem('currentUser', JSON.stringify(user));
                setTimeout(() => { 
                    window.location.href = 'index.html';
                }, 1500);
            } else {
                showMessage('Invalid email or password', false);
                loginForm.classList.add('shake');
                setTimeout(() => loginForm.classList.remove('shake'), 600);
                document.getElementById('password').value = '';
            }
        });
    }

    if (signupForm) {
        signupForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const name = document.getElementById('name').value;
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            // Client-side validation
            if (!validateEmail(email)) {
                showMessage('Please enter a valid email address.', false);
                document.getElementById('password').value = '';
                document.getElementById('confirmPassword').value = '';
                return;
            }
            if (!validatePassword(password)) {
                showMessage('Password must be at least 6 characters.', false);
                document.getElementById('password').value = '';
                document.getElementById('confirmPassword').value = '';
                return;
            }

            if (password !== confirmPassword) {
                showMessage('Passwords do not match', false);
                document.getElementById('password').value = '';
                document.getElementById('confirmPassword').value = '';
                return;
            }

            const users = JSON.parse(localStorage.getItem('users')) || [];

            if (users.some(user => user.email === email)) {
                showMessage('Email already registered', false);
                document.getElementById('password').value = '';
                document.getElementById('confirmPassword').value = '';
                return;
            }

            users.push({ name, email, password, registrationDate: new Date().toISOString() });
            localStorage.setItem('users', JSON.stringify(users));

            showMessage('Account created successfully! Redirecting to login...', true);
            setTimeout(() => { window.location.href = 'login.html'; }, 2000);
        });
    }

    if (logoutButton) {
        logoutButton.addEventListener('click', function() {
            localStorage.removeItem('currentUser');
            checkLoggedIn();
            showMessage('You have been logged out.', true);
            setTimeout(() => {
                window.location.href = 'login.html';
            }, 1200);
        });
    }

    function showMessage(message, isSuccess) {
        if (messageElement) {
            messageElement.textContent = message;
            messageElement.className = isSuccess ? 'message success-message' : 'message error-message';
            messageElement.classList.add('show-message');
            setTimeout(() => {
                messageElement.classList.remove('show-message');
            }, 3000);
        }
    }
});


