#!/usr/bin/env python
"""Django's command-line utility for administrative tasks."""
import os
import sys
import django

def check_django_setup():
    try:
        from django.conf import settings
        if not settings.configured:
            from django.core.management import execute_from_command_line
            execute_from_command_line(['manage.py', 'check'])
        return True
    except Exception as e:
        print(f"Django setup error: {e}")
        return False

def main():
    """Run administrative tasks."""
    os.environ.setdefault("DJANGO_SETTINGS_MODULE", "blogmaster_django.settings")
    
    # Verify Django configuration
    if check_django_setup():
        try:
            from django.core.management import execute_from_command_line
            execute_from_command_line(sys.argv)
        except Exception as e:
            print(f"Error executing command: {e}")
    else:
        print("Django configuration check failed")

if __name__ == "__main__":
    main()
