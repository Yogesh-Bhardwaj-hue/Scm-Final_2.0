document.addEventListener('DOMContentLoaded', function() {
    // Profile navigation    
    const profileNavLink = document.getElementById('profileNavLink');
    const profileSection = document.getElementById('profile-section');
    const mainSections = document.querySelectorAll('main > section');
    
    if (profileNavLink) {
        profileNavLink.addEventListener('click', function(e) {
            e.preventDefault();
            // Hide all main sections
            mainSections.forEach(sec => sec.style.display = 'none');
            // Show profile section
            profileSection.style.display = 'block';
            window.scrollTo(0, profileSection.offsetTop);
            loadProfile();
        });
    }
    
    // Profile logic
    function renderProfile(user) {
        profileName.textContent = user.name || "User Name";
        profileEmail.innerHTML = '<i class="fas fa-envelope"></i> ' + (user.email || "user@example.com");
        profileDate.innerHTML = '<i class="fas fa-calendar-alt"></i> Joined: ' + (user.registrationDate ? new Date(user.registrationDate).toLocaleDateString() : "--");
        profileDOB.innerHTML = '<i class="fas fa-birthday-cake"></i> Date of Birth: ' + (user.dob || "--");
        profileAge.innerHTML = '<i class="fas fa-user"></i> Age: ' + (user.age || "--");
        profileCountry.innerHTML = '<i class="fas fa-flag"></i> Country: ' + (user.country || "--");
        profileCity.innerHTML = '<i class="fas fa-city"></i> City: ' + (user.city || "--");
    }

    function loadProfile() {
        const currentUser = JSON.parse(localStorage.getItem('currentUser'));
        const profileName = document.getElementById('profileName');
        const profileEmail = document.getElementById('profileEmail');
        const profileDate = document.getElementById('profileDate');
        const profileDOB = document.getElementById('profileDOB');
        const profileAge = document.getElementById('profileAge');
        const profileCountry = document.getElementById('profileCountry');
        const profileCity = document.getElementById('profileCity');
        const avatarImg = document.getElementById('avatarImg');
        const editBtn = document.getElementById('editBtn');
        const logoutBtn = document.getElementById('logoutBtnProfile');
        const editSection = document.getElementById('editSection');
        const editName = document.getElementById('editName');
        const editEmail = document.getElementById('editEmail');
        const editDOB = document.getElementById('editDOB');
        const editAge = document.getElementById('editAge');
        const editCountry = document.getElementById('editCountry');
        const editCity = document.getElementById('editCity');
        const saveBtn = document.getElementById('saveBtn');
        const cancelBtn = document.getElementById('cancelBtn');
        const profileMsg = document.getElementById('profileMsg');
        
        if (!currentUser) {
            window.location.href = "/login/";
            return;
        }
        
        renderProfile(currentUser);
        
        editBtn.onclick = function() {
            editSection.style.display = "block";
            editName.value = currentUser.name || "";
            editEmail.value = currentUser.email || "";
            editDOB.value = currentUser.dob || "";
            editAge.value = currentUser.age || "";
            editCountry.value = currentUser.country || "";
            editCity.value = currentUser.city || "";
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
            const newDOB = editDOB.value;
            const newAge = editAge.value.trim();
            const newCountry = editCountry.value.trim();
            const newCity = editCity.value.trim();
            
            if (!newName || !newEmail) {
                profileMsg.style.color = "#dc3545";
                profileMsg.textContent = "Name and email cannot be empty.";
                return;
            }
            
            if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(newEmail)) {
                profileMsg.style.color = "#dc3545";
                profileMsg.textContent = "Invalid email format.";
                return;
            }
            
            let users = JSON.parse(localStorage.getItem('users')) || [];
            if (users.some(u => u.email === newEmail && u.email !== currentUser.email)) {
                profileMsg.style.color = "#dc3545";
                profileMsg.textContent = "Email already in use.";
                return;
            }
            
            users = users.map(u => {
                if (u.email === currentUser.email) {
                    return {
                         ...u,
                         name: newName,
                         email: newEmail,
                         dob: newDOB,
                         age: newAge,
                         country: newCountry,
                         city: newCity
                     };
                }
                return u;
            });
            
            localStorage.setItem('users', JSON.stringify(users));
            
            const updatedUser = {
                 ...currentUser,
                 name: newName,
                 email: newEmail,
                 dob: newDOB,
                 age: newAge,
                 country: newCountry,
                 city: newCity
             };
            localStorage.setItem('currentUser', JSON.stringify(updatedUser));
            
            renderProfile(updatedUser);
            
            profileMsg.style.color = "#28a745";
            profileMsg.textContent = "Profile updated successfully!";
            editSection.style.display = "none";
        };
        
        logoutBtn.onclick = function() {
            localStorage.removeItem('currentUser');
            window.location.href = "/login/";
        };
    }
    
    // Enable navigation back to other sections from Profile
    const navLinks = document.querySelectorAll('.nav-links a:not(#profileNavLink)');
    const mainSections = document.querySelectorAll('main > section');
    
    navLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            const targetId = this.getAttribute('href');
            if (targetId && targetId.startsWith('#')) {
                // Hide all sections
                mainSections.forEach(sec => sec.style.display = 'none');
                // Show only the selected section
                const targetSection = document.querySelector(targetId);
                if (targetSection) {
                    targetSection.style.display = 'block';
                    window.scrollTo(0, targetSection.offsetTop);
                }
            }
        });
    });
});