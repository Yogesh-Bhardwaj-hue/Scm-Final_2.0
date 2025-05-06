# BlogMaster Pro

Your Ultimate Blogging Platform

---

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Setup & Installation](#setup--installation)
- [Usage](#usage)
- [Profile Page](#profile-page)
- [Blog Analytics](#blog-analytics)
- [Best Practices Followed](#best-practices-followed)
- [Contributing](#contributing)
- [License](#license)

---

## Project Overview

**BlogMaster Pro** is a modern, responsive blogging platform that empowers users to create, share, and manage blogs with ease. The project demonstrates a full-stack approach, featuring a JavaScript frontend and Django and java backend, with a focus on clean code, user experience, and professional project organization.

---

## Features

- User authentication (sign up, login, logout)
- Responsive and modern UI/UX
- Profile management (edit name, email, date of birth, age, country, city)
- Blog creation, editing, and listing
- Search and filter blogs by category
- Pagination for blogs
- Testimonials, FAQ, and contact sections
- Professional navigation and section transitions
- LocalStorage-based user/session management (for demo)
- Clean, maintainable code and modular structure

---

## Tech Stack

- **Frontend:** HTML5, CSS3, JavaScript (Vanilla), Font Awesome, AOS (Animate On Scroll)
- **Backend:** Django (Python) , Java
- **Other:** LocalStorage (for demo user/session), Responsive Design

---

## Project Structure

```

│
├── index.html
├── style.css
├── script.js
├── login.js
├── profile.js
├── images/
│
| └── ... (all images and avatars)
├── Java/
|── DJango/
│   └── ... (Django backend code)
├── requirements.txt
├── .gitignore
└── README.md
```

---

## Setup & Installation

1. **Clone the Repository**
   ```sh
   git clone https://github.com/yourusername/blogmaster-pro.git
   cd blogmaster-pro
   ```

2. **Frontend (No build tools required)**
   - Open `index.html` directly in your browser.

3. **Backend (Django)**
   - Navigate to the `DJango` folder.
   - Create and activate a virtual environment:
     ```sh
     python -m venv venv
     source venv/bin/activate  # On Windows: venv\Scripts\activate
     ```
   - Install dependencies:
     ```sh
     pip install -r requirements.txt
     ```
   - Set up environment variables in a `.env` file (see `.env.example`).
   - Run migrations and start the server:
     ```sh
     python manage.py migrate
     python manage.py runserver
     ```

4. **Demo Users**
   - Email: rash21@gmail.com
   - Password: 0192837465

---

## Usage

- **Login/Signup:** Use the navigation bar to log in or sign up.
- **Profile:** Click the "Profile" link in the navbar to view or edit your profile.
- **Edit Profile:** Update your name, email, date of birth, age, country, and city. Changes are saved in LocalStorage.
- **Navigate:** Use the navbar to switch between Home, About, Services, Blogs, Testimonials, FAQ, Contact, and Profile.
- **Logout:** Use the logout button in the profile section or navbar.

---

## Profile Page

The profile page allows users to:
- View and edit their personal information (name, email, date of birth, age, country, city)
- See their registration date
- Update their details with real-time validation
- Logout securely

**Navigation:**
You can return to any other section (Home, About, etc.) using the navbar, even after entering the profile page.

---

## Blog Analytics

<!-- BLOG-ANALYTICS:START -->
# Blog Analytics Dashboard
Last updated: Sun May  4 09:48:29 UTC 2025
>>>>>>>>> Temporary merge branch 2

## Overview

📊 **Total Blog Posts:** 6
📝 **Total Words:** 2317
>>>>>>> main
⏱️ **Average Reading Time:** 1 minutes

## Popular Topics

```mermaid
pie title Most Used Topic
    "Java": 6
    "CSS": 5
    "Django": 6
    "JavaScript": 3
    "HTML": 19
    "Python": 4
>>>>>>>>> Temporary merge branch 2
```
<!-- BLOG-ANALYTICS:END -->

---

## Best Practices Followed

- **Professional Project Structure:** Clear separation of frontend and backend code.
- **.gitignore:** Sensitive and unnecessary files (e.g., `.env`, `__pycache__`, `*.pyc`, `db.sqlite3`) are excluded from version control.
- **README Documentation:** This file provides setup, usage, and contribution guidelines.
- **Responsive Design:** Works well on desktop and mobile devices.
- **Clean Code:** Modular JavaScript, semantic HTML, and organized CSS.
- **User Experience:** Friendly error messages, smooth navigation, and clear feedback.
- **Security:** No sensitive data in codebase; environment variables for secrets.
- **Accessibility:** Semantic HTML and accessible forms.

---

## Contributing

- Garv Mehra
- Yogesh Bhardwaj
- Sarthak Aggarwal
- Harshit Garg

<!-- CONTRIBUTION-STATS:START -->
# Contribution Statistics

## Visual Contribution Chart

[![Contributors](https://contrib.rocks/image?repo=studentGarv/Blog_Master_Pro)](https://github.com/studentGarv/Blog_Master_Pro/graphs/contributors)

## Contribution Breakdown

```mermaid
pie title Contribution by Author
    "Yogesh Bhardwaj": 114
    "Harshit Garg": 13
    "Sarthak Aggarwal": 15
    "Garv Mehra": 62
```

Last updated: Tue May  6 08:46:25 UTC 2025
<!-- CONTRIBUTION-STATS:END -->

---

## License

This project is licensed under the MIT License.

---

