# Generated by Django 3.2.5 on 2021-08-13 10:10

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('eungapp', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='sensor',
            name='model',
            field=models.CharField(choices=[('Thermometer', '온도계'), ('Gas', '가스 센서'), ('Audio', '마이크'), ('ETC', '기타')], default='ETC', max_length=20),
        ),
        migrations.AddField(
            model_name='sensor',
            name='status',
            field=models.BooleanField(default=False),
        ),
    ]
