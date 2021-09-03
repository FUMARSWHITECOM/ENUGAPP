from django.urls import path,include,re_path
from . import views

app_name = 'eung_app'
urlpatterns = [
    #path('', include('rest_framework.urls', namespace='rest_framework_category')),
    path('login/', views.login, name='login'),
    path('signup/', views.signUp, name='signup'),
    path('userdelete/', views.userDelete, name='userdelete'),
    path('sensordelete/', views.sensorDelete, name='sensordelete'),
    path('crydelete/', views.cryDelete, name='crydelete'),
    path('get_sensor_data', views.get_sensor_data, name='get_sensor_data'),
    path('crying/', views.crying, name='crying'),
    path('check/', views.check, name='check'),
]