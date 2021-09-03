from django.db.models import fields
from rest_framework import serializers
from eungapp.models import CryDetector, Sensor, User


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('id', 'username', 'email')
        

class SensorSerializer(serializers.ModelSerializer):

    class Meta:
        model = Sensor
        fields = ('serial_number', 'location','user', 'status', 'model')
        labels = {
            'serial_number' : '제품번호',
            'location' : '장소',
            'user' : '사용자',
            'status' : ' 상태',
            'model' : '모델',
        }
    
    def to_representation(self, instance):
        self.fields['user'] =  UserRepresentationSerializer(read_only=True)
        return super(SensorSerializer, self).to_representation(instance)

class CrySerailizer(serializers.ModelSerializer):
    class Meta:
        order_by = (('timestamp',))
        model = CryDetector
        fields = ('timestamp','temperature', 'humidity', 'smell', 'sensor', 'checked')
        labels = {
            'timestamp' : '시간',
            'temperature' : '아기 온도',
            'humidity' : '습도',
            'smell' : '냄새여부',
            'sensor' : '센서',
            'checked' : '확인여부',
        }
    def to_representation(self, instance):
        self.fields['sensor'] = SensorRepresentationSerializer(read_only = True)
        return super(CrySerailizer,self).to_representation(instance)

class UserRepresentationSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ("id", "username", "email")

class SensorRepresentationSerializer(serializers.ModelSerializer):
    class Meta:
        model = Sensor
        fields = ('serial_number','location','user')
    
    def to_representation(self, instance):
        self.fields['user'] =  UserRepresentationSerializer(read_only=True)
        return super(SensorRepresentationSerializer, self).to_representation(instance)
