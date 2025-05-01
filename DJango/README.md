# Django Project

## Setup

1. **Create a virtual environment**  
   ```sh
   python -m venv venv
   ```

2. **Activate the virtual environment**  
   - Windows:  
     ```sh
     venv\Scripts\activate
     ```
   - Linux/Mac:  
     ```sh
     source venv/bin/activate
     ```

3. **Install requirements**  
   ```sh
   pip install -r requirements.txt
   ```

4. **Set up `.env` file**  
   Create a `.env` file in the project root with content like:
   ```
   SECRET_KEY=your-secret-key
   DEBUG=True
   DATABASE_URL=sqlite:///db.sqlite3
   ```

5. **Run migrations**  
   ```sh
   python manage.py migrate
   ```

6. **Start the server**  
   ```sh
   python manage.py runserver
   ```

---

## Project Structure & Changes

- **Virtual Environment**: Use a virtual environment for dependencies.
- **requirements.txt**: Added for dependency management.
- **.gitignore**: Added to ignore Python cache, database, static/media, and environment files.
- **.env**: Added for secret keys and environment variables (using `django-environ`).
- **settings.py**:
  - Now loads secrets and config from `.env` using `django-environ`.
  - Static and media file settings added.
  - `members` app included in `INSTALLED_APPS`.
- **Static and Media Folders**:  
  - `STATIC_ROOT` and `MEDIA_ROOT` are set for static and media files.
- **Login Redirects**:  
  - `LOGIN_REDIRECT_URL` and `LOGIN_URL` set for authentication flow.

---

## Example `.gitignore`

```
__pycache__/
*.pyc
*.pyo
*.pyd
*.sqlite3
db.sqlite3
.env
/static/
/media/
```

---

## Example requirements.txt

```
Django>=4.0
django-environ
```

---

## Notes

- Store sensitive information in `.env`, **never** commit it to version control.
- For production, set `DEBUG=False` and configure `ALLOWED_HOSTS` in `.env`.
- You can further split `settings.py` for different environments if needed.

---

<!-- CONTRIBUTION-STATS:START -->
<!-- CONTRIBUTION-STATS:END -->