import socket
import time
from tensorflow.keras.models import load_model
import featuring
#import librosa
import numpy as np
import requests
from ast import literal_eval
from record import *
import soundfile as sf

def isCrying(prediction):
    if prediction.argmax() == 0:
        return True
    return False

print(1)
model = load_model('baby_cry_model.h5', compile=False)
url = "http://127.0.0.1:8000/crying/" # input url
print(2)
server_port = 8090
max_users = 5
server_port = [8080, 8090]
print ("Waiting for client on port ",server_port)


while True:
    #---녹음 진행---
    """여기를 라즈베리파이 usb마이크 코드 연결을 하면 될듯"""
    filename = "test.wav"
    record_audio(filename, 5)
    #---녹음 끝---
    #sig,sr = librosa.load(f"./{filename}",sr=8000)
    sig, sr = sf.read(f"./{filename}", dtype='float32')
    feature = featuring.make_feature_list(sig,sr)

    X_model = np.stack(feature,axis=0)
    X_model = np.expand_dims(X_model, axis=-1)

    y_pred = model.predict(X_model)
    
    if isCrying(y_pred):
    #if True:
        '''for i in range(2):
            server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            server_socket.bind(("",server_port[i]))
            server_socket.listen(max_users)
            client_socket, client_address = server_socket.accept()
            print("connection from", client_address)
            sent = client_socket.send(b'data please')
            if sent == 0:
                print("socket connection broken")
            data = client_socket.recv(128)
            if not data:
                break
            str_data = str(data.decode('utf-8')[:-1])
            dict_data = literal_eval(str_data)
            print(dict_data)
            #time.sleep(1)'''
        dict_data = {'se'}
        requests.post(url, data=dict_data)
        
server_socket.close()
