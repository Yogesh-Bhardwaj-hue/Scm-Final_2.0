from django.urls import path
from . import views

urlpatterns = [
    # Authentication URLs
    path('login/', views.login_view, name='login'),
    path('signup/', views.signup_view, name='signup'),
    path('logout/', views.logout_view, name='logout'),
    
    # Profile URLs
    path('profile/', views.profile_view, name='profile'),
    
    # Blog URLs
    path('', views.home_view, name='home'),
    path('blogs/', views.blog_list, name='blog_list'),
    path('blogs/add/', views.add_blog, name='add_blog'),
    path('blogs/delete/<int:blog_id>/', views.delete_blog, name='delete_blog'),
]