from django.shortcuts import render, redirect, get_object_or_404
from django.contrib.auth import login, authenticate, logout
from django.contrib.auth.decorators import login_required
from django.contrib import messages
from django.http import JsonResponse
from django.core.paginator import Paginator
from .forms import SignUpForm, UserProfileForm, BlogForm
from .models import Blog, Category, UserProfile, BlogImage, BulletPoint
import json

# Authentication Views
def login_view(request):
    if request.method == 'POST':
        email = request.POST.get('email')
        password = request.POST.get('password')
        
        # Authenticate against username (which is email)
        user = authenticate(username=email, password=password)
        
        if user is not None:
            login(request, user)
            messages.success(request, "Login successful!")
            return redirect('home')
        else:
            messages.error(request, "Invalid email or password.")
    
    return render(request, 'login.html')

def signup_view(request):
    if request.method == 'POST':
        form = SignUpForm(request.POST)
        if form.is_valid():
            user = form.save()
            # Log the user in
            login(request, user)
            messages.success(request, "Account created successfully!")
            return redirect('home')
        else:
            for field, errors in form.errors.items():
                for error in errors:
                    messages.error(request, f"{field}: {error}")
    else:
        form = SignUpForm()
    
    return render(request, 'signup.html', {'form': form})

@login_required
def logout_view(request):
    logout(request)
    messages.success(request, "You have been logged out.")
    return redirect('login')

# Profile Views
@login_required
def profile_view(request):
    try:
        profile = request.user.profile
    except UserProfile.DoesNotExist:
        profile = UserProfile.objects.create(user=request.user)
    
    if request.method == 'POST':
        form = UserProfileForm(request.POST, instance=profile)
        if form.is_valid():
            # Update user data
            request.user.first_name = form.cleaned_data['name']
            request.user.email = form.cleaned_data['email']
            request.user.save()
            
            # Save profile
            form.save()
            
            messages.success(request, "Profile updated successfully!")
            return redirect('profile')
    else:
        form = UserProfileForm(instance=profile)
    
    return render(request, 'profile.html', {'form': form})

# Blog Views
def home_view(request):
    return render(request, 'index.html')

def blog_list(request):
    category = request.GET.get('category', '')
    search = request.GET.get('search', '')
    
    blogs = Blog.objects.all().order_by('-created_at')
    
    # Apply filters
    if category:
        blogs = blogs.filter(category__name__iexact=category)
    if search:
        blogs = blogs.filter(
            models.Q(title__icontains=search) | 
            models.Q(short_description__icontains=search)
        )
    
    # Pagination
    page = request.GET.get('page', 1)
    paginator = Paginator(blogs, 6)  # 6 blogs per page
    blog_list = paginator.get_page(page)
    
    categories = Category.objects.all()
    
    context = {
        'blogs': blog_list,
        'categories': categories,
        'current_category': category,
        'search_query': search,
    }
    
    return render(request, 'index.html', context)

@login_required
def add_blog(request):
    if request.method == 'POST':
        form = BlogForm(request.POST)
        if form.is_valid():
            blog = form.save(commit=True, author=request.user)
            
            # Handle images
            images = request.FILES.getlist('images')
            for image in images:
                BlogImage.objects.create(blog=blog, image=image)
                
            messages.success(request, "Blog added successfully!")
            return redirect('blog_list')
    else:
        form = BlogForm()
    
    return JsonResponse({'status': 'form_loaded'})

@login_required
def delete_blog(request, blog_id):
    blog = get_object_or_404(Blog, id=blog_id, author=request.user)
    blog.delete()
    return redirect('blog_list')