//아두이노 냄새탐지키트
#include "ESP8266.h"
#include <SoftwareSerial.h>

#define SSID        "303A"  //와이파이 이름    
#define PASSWORD    "01063622074"  //와이파이 비밀번호
#define HOST_NAME   "192.168.31.37"  //무선 LAN 어댑터 Wi-Fi의 IPv4 주소
#define HOST_PORT   (8090)          // 얘는 그냥 가만두면됨
String SERIAL_NUMBER = "12345678";  // 시리얼 넘버(키트 만들때 다 다른 넘버링)
boolean  smell = false;


SoftwareSerial mySerial(2, 3); /* RX:D3, TX:D2 */
ESP8266 wifi(mySerial);
byte buf[128] ={};
void setup(void)
{
    
    Serial.begin(9600);
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
      
        Serial.print("Received:[");
        for(uint32_t i = 0; i < len; i++) {
            Serial.print((char)buffer[i]);
        }
        
        Serial.print("]\r\n");
        int data = analogRead(A0);
        if (data >= 450) smell = true;
        else  smell = false;
        String message = "{'serial_number':'"+SERIAL_NUMBER+"','smell':'"+String(data)+"'}";
        message.getBytes(buf,message.length()+1);
        Serial.println("smell:" + String(data) + ", isSmell? : " + String(smell)+ "}");
        wifi.send(buf, message.length()+1);
        
    }
    
    if (wifi.releaseTCP()) {
        Serial.print("release tcp ok\r\n");
    } else {
        Serial.print("release tcp err\r\n");
    }
    delay(1000);
}
     
