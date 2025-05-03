from django import forms
from django.contrib.auth.forms import UserCreationForm
from django.contrib.auth.models import User
from .models import UserProfile, Blog, BlogImage, BulletPoint

class SignUpForm(UserCreationForm):
    name = forms.CharField(max_length=100, required=True)
    email = forms.EmailField(required=True)
    
    class Meta:
        model = User
        fields = ['name', 'email', 'password1', 'password2']
        
    def save(self, commit=True):
        user = super().save(commit=False)
        user.username = self.cleaned_data['email']  # Use email as username
        user.email = self.cleaned_data['email']
        user.first_name = self.cleaned_data['name']
        
        if commit:
            user.save()
            # Create user profile
            UserProfile.objects.create(user=user)
        
        return user

class UserProfileForm(forms.ModelForm):
    name = forms.CharField(max_length=100, required=True)
    email = forms.EmailField(required=True)
    
    class Meta:
        model = UserProfile
        fields = ['dob', 'age', 'country', 'city']
        
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        if self.instance and self.instance.user:
            self.fields['name'].initial = self.instance.user.first_name
            self.fields['email'].initial = self.instance.user.email

class BlogForm(forms.ModelForm):
    bullet_points = forms.CharField(
        widget=forms.Textarea(attrs={'rows': 3}),
        required=False,
        help_text="Enter bullet points separated by commas"
    )
    images = forms.FileField(
        widget=forms.ClearableFileInput(attrs={'multiple': True}),
        required=False
    )
    
    class Meta:
        model = Blog
        fields = ['title', 'subtitle', 'short_description', 'long_description', 
                  'category', 'youtube_link']
        
    def save(self, commit=True, author=None):
        blog = super().save(commit=False)
        if author:
            blog.author = author
        
        if commit:
            blog.save()
            
            # Save bullet points
            bullet_points_text = self.cleaned_data.get('bullet_points', '')
            if bullet_points_text:
                points = [point.strip() for point in bullet_points_text.split(',')]
                for point in points:
                    if point:  # Only save non-empty points
                        BulletPoint.objects.create(blog=blog, text=point)
            
        return blog