from django.contrib import admin
from .models import UserProfile, Category, Blog, BlogImage, BulletPoint

@admin.register(UserProfile)
class UserProfileAdmin(admin.ModelAdmin):
    list_display = ('user', 'country', 'city', 'registration_date')
    search_fields = ('user__username', 'user__email', 'country', 'city')

@admin.register(Category)
class CategoryAdmin(admin.ModelAdmin):
    list_display = ('name', 'slug')
    prepopulated_fields = {'slug': ('name',)}

class BlogImageInline(admin.TabularInline):
    model = BlogImage
    extra = 1

class BulletPointInline(admin.TabularInline):
    model = BulletPoint
    extra = 1

@admin.register(Blog)
class BlogAdmin(admin.ModelAdmin):
    list_display = ('title', 'author', 'category', 'created_at')
    list_filter = ('category', 'created_at')
    search_fields = ('title', 'short_description', 'long_description')
    inlines = [BlogImageInline, BulletPointInline]