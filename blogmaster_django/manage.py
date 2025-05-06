#!/usr/bin/env python
"""Django's command-line utility for administrative tasks."""
import os
import sys

def main():
    """Run administrative tasks."""
    os.environ.setdefault("DJANGO_SETTINGS_MODULE", "blogmaster_django.settings")
    try:
        from django.core.management import execute_from_command_line
        execute_from_command_line(sys.argv)
    except ImportError as exc:
        print("Couldn't import Django. Are you sure it's installed and available on your PYTHONPATH environment variable?")
        print("Did you forget to activate a virtual environment?")
        raise exc
    except Exception as e:
        print(f"Error executing command: {e}")
        sys.exit(1)

if __name__ == "__main__":
    main()
