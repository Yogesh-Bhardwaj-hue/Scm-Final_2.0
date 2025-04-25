from django.db import models
from django.contrib.auth.models import User

class Member(models.Model):
    firstname = models.CharField(max_length=255)
    lastname = models.CharField(max_length=255)

class Item(models.Model):
    STATUS_CHOICES = (
        ('open', 'Open for Bidding'),
        ('closed', 'Bidding Closed'),
    )
    
    name = models.CharField(max_length=255)
    description = models.TextField()
    start_price = models.DecimalField(max_digits=12, decimal_places=2)
    image_url = models.CharField(max_length=255)
    status = models.CharField(max_length=10, choices=STATUS_CHOICES, default='open')
    winner = models.ForeignKey(User, on_delete=models.SET_NULL, null=True, blank=True, related_name='won_items')
    
    def __str__(self):
        return self.name

class Bid(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    item = models.ForeignKey(Item, on_delete=models.CASCADE)
    amount = models.DecimalField(max_digits=12, decimal_places=2)
    timestamp = models.DateTimeField(auto_now_add=True)
    
    class Meta:
        ordering = ['-amount']
    
    def __str__(self):
        return f"{self.user.username}: ${self.amount} on {self.item.name}"