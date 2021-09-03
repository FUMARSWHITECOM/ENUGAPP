import pyaudio
import wave

def record_audio(filename:str, seconds:int):
    chunk = 1024
    sampling_format = pyaudio.paInt16
    sampling_channel = 1
    sampling_rate = 8000#44100
    my_audio = pyaudio.PyAudio()
    print('Recording')

    stream = my_audio.open(format=sampling_format,
                           channels=sampling_channel,
                           rate=sampling_rate,
                           frames_per_buffer=chunk,
                           input=True)
    frames = []

    for i in range(0, int(sampling_rate/ chunk * seconds)):
        data = stream.read(chunk)
        frames.append(data)
    
    stream.stop_stream()
    stream.close()
    my_audio.terminate()

    print('Finished recording')
    wf = wave.open(filename, 'wb')
    wf.setnchannels(sampling_channel)
    wf.setsampwidth(my_audio.get_sample_size(sampling_format))
    wf.setframerate(sampling_rate)
    wf.writeframes(b''.join(frames))
    wf.close()

#record_audio("test.wav", 5)
