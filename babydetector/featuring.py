import numpy as np
import soundfile as sf
#import librosa, librosa.display
import numpy as np
import matplotlib.pyplot as plt
import os
# 이거 씀
def audio_normalize(sig):
    max_data = np.max(sig)
    min_data = np.min(sig)
    sig = (sig-min_data)/(max_data-min_data+1e-6)
    return sig-0.5

def make_feature(sig,sr):
    sig = audio_normalize(sig)
    if sig.shape[0] < 10*sr:
        sig=np.pad(sig,int(np.ceil((10*sr-sig.shape[0])/2)),mode = 'reflect')
    sig=sig[:10*sr]
    return sig

def make_feature_list(sig,sr):
    feature=[]
    sig = audio_normalize(sig)
    if sig.shape[0] < 10*sr:
        sig=np.pad(sig,int(np.ceil((10*sr-sig.shape[0])/2)),mode = 'reflect')
    sig=sig[:10*sr]
    feature.append(sig)
    return feature
    
if __name__ == "__main__":
    features=[]
    labels=[]
    path_dir = os.getcwd() + '\\data2'
    file_list = os.listdir(path_dir)

    for folder in file_list:
        folder_list = os.listdir(path_dir+'/'+folder)
        for file_name in folder_list:
            sig,sr = librosa.load(path_dir+'/'+folder+'/'+file_name, sr=8000)

            sig = make_feature(sig,sr)
            features.append(sig)

            one_hot = [0,0,0]
            if(folder == "Babycry,infantcry"):
                one_hot[0] = 1
            elif(folder == "Siren"):
                one_hot[1] = 1
            else:
                one_hot[2] = 1
            labels.append(one_hot)

    X = np.stack(features,axis=0)
    X = np.expand_dims(X, axis=-1)
    y = np.stack(labels,axis=0)
    np.save('feating_mono.npy', X)
    np.save('labeling_mono.npy', y)
