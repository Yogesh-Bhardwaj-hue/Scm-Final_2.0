from django.urls import path
from . import views
from django.contrib.auth import views as auth_views

urlpatterns = [
    path('members/', views.members, name='members'),
    path('', views.item_list, name='home'),
    path('login/', auth_views.LoginView.as_view(template_name='members/login.html'), name='login'),
    path('logout/', auth_views.LogoutView.as_view(next_page='/'), name='logout'),
    path('register/', views.register, name='register'),
    path('item/<int:item_id>/', views.item_detail, name='item_detail'),
    path('api/item/<int:item_id>/bid/', views.place_bid, name='place_bid'),
    path('api/item/<int:item_id>/bids/', views.get_bids, name='get_bids'),
    path('item/<int:item_id>/close/', views.close_bid, name='close_bid'),
]