from django.db import models
from django.contrib.auth.models import (
    BaseUserManager, AbstractBaseUser, PermissionsMixin
)
from django.utils import timezone
from django.utils.translation import ugettext_lazy as _


class UserManager(BaseUserManager):
    def create_user(self, email, username, password=None):
        """
        주어진 이메일, 닉네임, 비밀번호 등 개인정보로 User 인스턴스 생성
        """
        if not email:
            raise ValueError(_('Users must have an email address'))

        user = self.model(
            email=self.normalize_email(email),
            username=username,
        )

        user.set_password(password)
        user.save(using=self._db)
        return user

    def create_superuser(self, email, username, password):
        """
        주어진 이메일, 닉네임, 비밀번호 등 개인정보로 User 인스턴스 생성
        단, 최상위 사용자이므로 권한을 부여한다. 
        """
        user = self.create_user(
            email=email,
            password=password,
            username=username,
        )

        user.is_superuser = True
        user.save(using=self._db)
        return user


class User(AbstractBaseUser, PermissionsMixin):
    email = models.EmailField(
        verbose_name=_('Email address'),
        max_length=255,
        unique=True,
    )
    username = models.CharField(
        verbose_name=_('Username'),
        max_length=30,
        unique=True
    )
    is_active = models.BooleanField(
        verbose_name=_('Is active'),
        default=True
    )
    date_joined = models.DateTimeField(
        verbose_name=_('Date joined'),
        default=timezone.now
    )
    # 이 필드는 레거시 시스템 호환을 위해 추가할 수도 있다.
    salt = models.CharField(
        verbose_name=_('Salt'),
        max_length=10,
        blank=True
    )

    objects = UserManager()

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = ['username', ]

    class Meta:
        verbose_name = _('user')
        verbose_name_plural = _('users')
        ordering = ('-date_joined',)

    def __str__(self):
        return self.username

    def get_full_name(self):        
        return self.username

    def get_short_name(self):
        return self.username

    @property
    def is_staff(self):
        "Is the user a member of staff?"
        return self.is_superuser

    get_full_name.short_description = _('Full name')


class Sensor(models.Model):
    MODEL_CHOICES = (
            ('THERM', '온도계'),
            ('HUMID', '습도 센서'),
            ('GAS', '가스 센서'),
            ('AUX', '마이크'),
            ('ETC', '기타'),
    )
    LOCATION_CHOICES = (
            ('LIVING_ROOM', '거실'),
            ('DINING_ROOM', '부엌'),
            ('BED_ROOM1', '침실1'),
            ('BED_ROOM2', '침실2'),
            ('BED_ROOM3', '침실3'),
            ('ETC', '기타'),
    )
    serial_number = models.CharField(max_length=20)
    location = models.CharField(max_length=20, choices=LOCATION_CHOICES, default='ETC')
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    status = models.BooleanField(default=False)
    model = models.CharField(max_length=20, choices=MODEL_CHOICES, default='ETC')


    def __str__(self):
        return f"{self.model}/{self.location}"


class CryDetector(models.Model):
    timestamp = models.DateTimeField()
    temperature = models.FloatField()
    humidity = models.FloatField(default=0)
    smell = models.BooleanField()
    sensor = models.ForeignKey(Sensor, on_delete=models.CASCADE)
    checked = models.BooleanField(default=False)

    def __str__(self):
        return f"{self.sensor}/{self.timestamp}"