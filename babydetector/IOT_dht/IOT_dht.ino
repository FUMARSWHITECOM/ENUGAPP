#include <ESP8266.h>
#include <doxygen.h>

//아두이노 온습도 키트
#include "ESP8266.h"
#include <SoftwareSerial.h>
#include <DHT.h>

#define PIN A0
#define SSID        "303A"  //와이파이 이름    
#define PASSWORD    "01063622074"  //와이파이 비밀번호
#define HOST_NAME   "192.168.31.37"  //무선 LAN 어댑터 Wi-Fi의 IPv4 주소
#define HOST_PORT   (8080)          // 얘는 그냥 가만두면됨
#define DHTTYPE DHT11   // DHT 11
String SERIAL_NUMBER = "123456789";
boolean  smell = false;
DHT dht11(PIN,DHTTYPE);


SoftwareSerial mySerial(2, 3); /* RX:D3, TX:D2 */
ESP8266 wifi(mySerial);
byte buf[128] ={};
void setup(void)
{
    
    Serial.begin(9600);
    dht11.begin();
    Serial.print("setup begin\r\n");
    
    Serial.print("FW Version:");
    Serial.println(wifi.getVersion().c_str());
      
    if (wifi.setOprToStationSoftAP()) {
        Serial.print("to station + softap ok\r\n");
    } else {
        Serial.print("to station + softap err\r\n");
    }
 
    if (wifi.joinAP(SSID, PASSWORD)) {
        Serial.print("Join AP success\r\n");
        Serial.print("IP:");
        Serial.println( wifi.getLocalIP().c_str());       
    } else {
        Serial.print("Join AP failure\r\n");
    }
    
    if (wifi.disableMUX()) {
        Serial.print("single ok\r\n");
    } else {
        Serial.print("single err\r\n");
    }
    
    Serial.print("setup end\r\n");
}
 
void loop(void)
{
    uint8_t buffer[128] = {0};
    
    if (wifi.createTCP(HOST_NAME, HOST_PORT)) {
        Serial.print("create tcp ok\r\n");
    } else {
        Serial.print("create tcp err\r\n");
    }

    uint32_t len = wifi.recv(buffer, sizeof(buffer), 10000);
    if (len > 0) {
        float temp = dht11.readTemperature();
        float humi = dht11.readHumidity();
        Serial.print("Received:[");
        for(uint32_t i = 0; i < len; i++) {
            Serial.print((char)buffer[i]);
        }
        
        Serial.print("]\r\n");
        
        String message = "{'serial_number':'"+SERIAL_NUMBER+"','temperature':'"+String(temp)+"','humidity':'"+String(humi)+"','smell':'"+String(smell)+"'}";
        message.getBytes(buf,message.length()+1);
        wifi.send(buf, message.length()+1);
        
        Serial.println(temp);
        temp += 0.1;
    }
    
    if (wifi.releaseTCP()) {
        Serial.print("release tcp ok\r\n");
    } else {
        Serial.print("release tcp err\r\n");
    }
    delay(1000);
}
     