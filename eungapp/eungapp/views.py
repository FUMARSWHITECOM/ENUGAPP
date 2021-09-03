from django.utils import timezone
from django import forms
from django.db.models.query import QuerySet
from django.shortcuts import redirect, render, get_object_or_404
from django.db.models import Q
# Create your views here.

from rest_framework import serializers, viewsets
from rest_framework.response import Response
from rest_framework.decorators import api_view
from eungapp.models import Sensor,CryDetector,User
from eungapp.serializers import UserSerializer, SensorSerializer, CrySerailizer
from .forms import CryDetectorForm, UserCreationForm,SensorForm

from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth import authenticate
from django.http import JsonResponse

from rest_framework.permissions import IsAuthenticated
from rest_framework.decorators import api_view, authentication_classes, permission_classes

from .models import User

class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.values('username','email')
    #filterset_fields = ('id')
    serializer_class = UserSerializer

class SensorViewSet(viewsets.ModelViewSet):
    queryset = Sensor.objects.all()
    #filterset_fields = ('id')
    serializer_class = SensorSerializer

class CryViewSet(viewsets.ModelViewSet):
    queryset = CryDetector.objects.all()
    filterset_fields = ('id','sensor__user__id','sensor__user__username',)
    serializer_class = CrySerailizer


def UserDetail(request, user_id):
    user = get_object_or_404(User, pk=user_id)
    context = {'user':user}

def SensorDetail(request, sensor_id):
    sensor = get_object_or_404(Sensor, pk=sensor_id)
    context = {'sensor':sensor}

def CryDetectDetail(request,cry_id):
    cry_detection = get_object_or_404(CryDetector,pk=cry_id)
    context = {'crying':cry_detection}

def get_sensor_data(request):
    if request.method == "POST":
        temperature = request.POST["temperature"]
        print(temperature)
        pass


@csrf_exempt
def login(request):
    authentication_classes = []
    if request.method == 'POST':
        email = request.POST.get('email')
        password = request.POST.get('password')
        user = authenticate(username=email, password=password)

        if user:
            return JsonResponse({'code': '1001', 'message': 'success', 'username': user.username, 'user_id': user.id}, status=200)

        return JsonResponse({'code': '0000', 'message': 'fail'}, status=200)


@csrf_exempt
def signUp(request):
    if request.method == 'POST':
        username = request.POST.get('username')
        email = request.POST.get('email')
        password = request.POST.get('password')
        try: 
            User.objects.get(username=username)
            context = {'code': '0010', 'message':'fail', 'cause':'username overlap'}
        except:
            try: 
                User.objects.get(email=email)
                context = {'code' : '0011', 'message':'fail', 'cause':'email overlap'}
            except: 
                user = User.objects.create_user(username=username, email=email, password=password)
                context = {'code' : '1001', 'message':'success', 'cause':'none'}

        if context['message'] == 'success':
            print("회원가입 성공!")
            return JsonResponse(context, status=200)
        else:
            print("회원가입 실패")
            return JsonResponse(context, status=200)

@csrf_exempt
def check(request):
    if request.method == 'POST':
        serial_number = request.POST.get('serial_number')
        timestamp = request.POST.get('timestamp')
        
        sensor = Sensor.objects.filter(serial_number=serial_number)
        if len(sensor):
            sensor = sensor[0]
            cry_detection = CryDetector.objects.filter(sensor=sensor, timestamp=timestamp)
            if len(cry_detection):
                cry_detection = cry_detection[0]
                print(cry_detection)
                if cry_detection.checked == False:
                    cry_detection.checked = True
                    cry_detection.save()
                return JsonResponse({"result":True}, status=200)
    return JsonResponse({"result":False}, status=404)


#email 기준으로 삭제
@csrf_exempt
def userDelete(request):
    if request.method == 'POST':
        email = request.POST.get('email')
        queryset = User.objects.filter(email=email)
        queryset.delete()



#sensor의 serial_number 기준으로 삭제
@csrf_exempt
def sensorDelete(request):
    if request.method == 'POST':
        serial_number = request.POST.get('serial_number')
        queryset = Sensor.objects.filter(serial_number = serial_number)
        queryset.delete()



#crydetecter의 timestamp 기준으로 삭제
@csrf_exempt
def cryDelete(request):
    if request.method == 'POST':
        timestamp = request.POST.get('timestamp')
        queryset = CryDetector.objects.filter(timestamp = timestamp)
        queryset.delete()

def crying(request):
    if request.method == 'POST':
        serial_number = request.POST.get('serial_number')
        temperature = request.POST.get('temperature')
        humidity = request.POST.get('humidity')
        smell = request.POST.get('smell')
        sensor = Sensor.objects.filter(serial_number = serial_number).last()
        CryDetector.objects.create(
            timestamp = timezone.now(),
            temperature = temperature,
            humidity=humidity,
            smell = smell,
            sensor = sensor,
        )
        return JsonResponse({'code': '0000', 'message': 'fail'}, status=200)
