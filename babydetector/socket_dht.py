import socket
import time
from tensorflow.keras.models import load_model
import featuring
import librosa
import numpy as np
import requests
from ast import literal_eval

def isCrying(prediction):
    if prediction.argmax() == 0:
        return True
    return False

model = load_model('baby_cry_model.h5')
url = "http://127.0.0.1:8000/crying/" # input url

server_port = 8080
max_users = 5
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind(("",server_port))
server_socket.listen(max_users)

print ("Waiting for client on port ",server_port)


while True:
    client_socket, client_address = server_socket.accept()
    #소켓 통신이 연결될때까지 대기 함
    print("connection from", client_address)
    #연결되면 어디에 연결됐는지 나옴

    #---녹음 진행---
    """여기를 라즈베리파이 usb마이크 코드 연결을 하면 될듯"""
    '''time.sleep(2)
    #---녹음 끝---
    sig,sr = librosa.load("./test1.wav",sr=8000)
    feature = featuring.make_feature_list(sig,sr)

    X_model = np.stack(feature,axis=0)
    X_model = np.expand_dims(X_model, axis=-1)

    y_pred = model.predict(X_model)'''
    
    #if isCrying(y_pred):
    if  True:
        while True:
            sent = client_socket.send(b'data please')
            #아두이노 쪽으로 데이터를 전송함
            if sent == 0:
                #전송실패시 0을 반환함
                print("socket connection broken")

            data = client_socket.recv(128)
            #데이터 전송받음
            if not data:
                break
            str_data = str(data.decode('utf-8')[:-1])
            #byte 형식이므로 string 형식으로 decode를 해줌
            dict_data = literal_eval(str_data)
            print(dict_data)
            #requests.post(url, data=dict_data)
            #json 형식으로 바꿔준후 post방식으로 request를 보냄
        time.sleep(1)
        
server_socket.close()
