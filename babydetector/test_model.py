import keras
from keras.models import load_model
import featuring
import soundfile as sf
import numpy as np
import librosa

from record import *

model = load_model('./baby_cry_model.h5')
sound_list = ["아기 울음소리", "사이렌", "남자 목소리"]
for i in range(5):
    print("")

print('-'*150)
'''
for i in range(1,4):
    sig,sr = librosa.load(f'./test{i}.wav',sr=8000)
    
    feature = featuring.make_feature_list(sig,sr)

    X_model = np.stack(feature,axis=0)
    X_model = np.expand_dims(X_model, axis=-1)
    
    y_pred = model.predict(X_model)

    print(f"file{i}: {sound_list[i-1]}")
    print("아기울음소리:  ", int(y_pred[0][0]*100),'%',sep='')
    print("사이렌:  ",int(y_pred[0][1]*100),'%',sep='')
    print("그 외 소음:  ",int(y_pred[0][2]*100),'%',sep='')
'''

def test_file(filename:str, seconds:int):
    print(f"[현재 설정]\n파일이름: {filename}, 음성 녹음 시간: {seconds}초")
    record_audio(filename, seconds)
    sig,sr = librosa.load(f'./{filename}',sr=8000)
    feature = featuring.make_feature_list(sig,sr)
    X_model = np.stack(feature,axis=0)
    X_model = np.expand_dims(X_model, axis=-1)
    y_pred = model.predict(X_model)
    print(f"{filename}")
    print("아기울음소리:  ", int(y_pred[0][0]*100),'%',sep='')
    print("사이렌:  ",int(y_pred[0][1]*100),'%',sep='')
    print("그 외 소음:  ",int(y_pred[0][2]*100),'%',sep='')

test_file("test.wav", 3)

