// Profile page logic for loading and updating user profile

document.addEventListener('DOMContentLoaded', function() {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    const profileName = document.getElementById('profileName');
    const profileEmail = document.getElementById('profileEmail');
    const profileDate = document.getElementById('profileDate');
    const avatarImg = document.getElementById('avatarImg');
    const editBtn = document.getElementById('editBtn');
    const logoutBtn = document.getElementById('logoutBtn');
    const editSection = document.getElementById('editSection');
    const editName = document.getElementById('editName');
    const editEmail = document.getElementById('editEmail');
    const saveBtn = document.getElementById('saveBtn');
    const cancelBtn = document.getElementById('cancelBtn');
    const profileMsg = document.getElementById('profileMsg');

    if (!currentUser) {
        window.location.href = "login.html";
        return;
    }

    // Display current user info
    profileName.textContent = currentUser.name || "User Name";
    profileEmail.textContent = currentUser.email || "user@example.com";
    profileDate.textContent = "Joined: " + (currentUser.registrationDate ? new Date(currentUser.registrationDate).toLocaleDateString() : "--");
    // If you have avatar support, set avatarImg.src = currentUser.avatarUrl

    editBtn.onclick = function() {
        editSection.style.display = "block";
        editName.value = currentUser.name || "";
        editEmail.value = currentUser.email || "";
        profileMsg.textContent = "";
        profileMsg.style.color = "#28a745";
    };

    cancelBtn.onclick = function() {
        editSection.style.display = "none";
        profileMsg.textContent = "";
    };

    saveBtn.onclick = function() {
        const newName = editName.value.trim();
        const newEmail = editEmail.value.trim();
        if (!newName || !newEmail) {
            profileMsg.style.color = "#dc3545";
            profileMsg.textContent = "Name and email cannot be empty.";
            return;
        }
        // Basic email validation
        if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(newEmail)) {
            profileMsg.style.color = "#dc3545";
            profileMsg.textContent = "Invalid email format.";
            return;
        }
        // Update in localStorage
        let users = JSON.parse(localStorage.getItem('users')) || [];
        // Check if email is already taken by another user
        if (users.some(u => u.email === newEmail && u.email !== currentUser.email)) {
            profileMsg.style.color = "#dc3545";
            profileMsg.textContent = "Email already in use.";
            return;
        }
        // Update user in users array
        users = users.map(u => {
            if (u.email === currentUser.email) {
                return { ...u, name: newName, email: newEmail };
            }
            return u;
        });
        localStorage.setItem('users', JSON.stringify(users));
        // Update currentUser
        const updatedUser = { ...currentUser, name: newName, email: newEmail };
        localStorage.setItem('currentUser', JSON.stringify(updatedUser));
        profileName.textContent = newName;
        profileEmail.textContent = newEmail;
        profileMsg.style.color = "#28a745";
        profileMsg.textContent = "Profile updated successfully!";
        editSection.style.display = "none";
    };

    logoutBtn.onclick = function() {
        localStorage.removeItem('currentUser');
        window.location.href = "login.html";
    };
});