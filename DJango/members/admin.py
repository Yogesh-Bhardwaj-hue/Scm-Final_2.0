from django.contrib import admin
from django.contrib.auth.models import Group, Permission
from django.contrib.contenttypes.models import ContentType
from .models import Item, Bid

# Register your models here.
admin.site.register(Item)
admin.site.register(Bid)

# Create a Subscribers group with add permission for items
def create_subscribers_group():
    # Get the content type for the Item model
    item_content_type = ContentType.objects.get_for_model(Item)
    
    # Get the "add" permission for Item
    add_item_permission = Permission.objects.get(
        content_type=item_content_type,
        codename='add_item'
    )
    
    # Create or get the Subscribers group
    subscribers_group, created = Group.objects.get_or_create(name='Subscribers')
    
    # Add the permission to the group
    subscribers_group.permissions.add(add_item_permission)
    
    return subscribers_group