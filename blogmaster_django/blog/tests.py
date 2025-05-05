from django.test import TestCase
from django.urls import reverse
from django.contrib.auth.models import User

class BasicTests(TestCase):
    def setUp(self):
        self.user = User.objects.create_user(
            username='testuser',
            email='test@example.com',
            password='testpassword'
        )
    
    def test_home_page_status_code(self):
        response = self.client.get('/')
        self.assertEqual(response.status_code, 200)
    
    def test_login_page_status_code(self):
        response = self.client.get('/login/')
        self.assertEqual(response.status_code, 200)
        
    def test_user_login(self):
        login = self.client.login(
            username='testuser',
            password='testpassword'
        )
        self.assertTrue(login)