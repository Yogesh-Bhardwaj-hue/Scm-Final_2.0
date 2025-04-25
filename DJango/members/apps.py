from django.apps import AppConfig


class MembersConfig(AppConfig):
    default_auto_field = "django.db.models.BigAutoField"
    name = "members"
    
    def ready(self):
        # Import and run the function to create the subscribers group
        from .admin import create_subscribers_group
        create_subscribers_group()
