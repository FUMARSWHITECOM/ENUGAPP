from django.contrib import admin
from .views import User, Sensor, CryDetector
# Register your models here.

admin.site.register(User)
admin.site.register(Sensor)
admin.site.register(CryDetector)
