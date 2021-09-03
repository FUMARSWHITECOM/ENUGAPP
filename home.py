import yeelight
import time
bulb = yeelight.Bulb("192.168.31.197")
bulb.set_brightness(100)
while True:
    bulb.turn_on()
    time.sleep(2)
    bulb.turn_off()
    time.sleep(2)

    