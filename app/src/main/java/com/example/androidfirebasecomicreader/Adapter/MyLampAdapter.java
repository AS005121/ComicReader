package com.example.androidfirebasecomicreader.Adapter;

import com.example.androidfirebasecomicreader.Interface.LampScriptsLoad;
import com.mollin.yapi.YeelightDevice;
import com.mollin.yapi.enumeration.YeelightEffect;
import com.mollin.yapi.exception.YeelightResultErrorException;
import com.mollin.yapi.exception.YeelightSocketException;

public class MyLampAdapter implements LampScriptsLoad {
    String ip = "192.168.100.117";
    int port = 55443;
    YeelightEffect effect = YeelightEffect.SMOOTH;
    int effectDuration = 1100;

    public Boolean isWork = true;
    public YeelightDevice device;


    @Override
    public void setMorningMode() {
        try {
            if(device == null)
                device = new YeelightDevice(ip);

            device.setPower(true);
            while (isWork){
                device.setRGB(238, 255, 0);
                //device.setBrightness(100);
                Thread.sleep(1500);
                device.setRGB(128, 0, 255);
                //device.setBrightness(75);
                Thread.sleep(1500);
            }
        } catch (YeelightSocketException | YeelightResultErrorException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setForestMode() {

    }

    @Override
    public void setCandleMode() {

    }

    @Override
    public void setSunsetMode() {

    }

    @Override
    public void setPoliceMode() {

    }

    @Override
    public void setBloodyMode() {

    }
}
