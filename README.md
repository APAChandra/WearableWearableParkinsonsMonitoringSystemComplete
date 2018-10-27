# WearableParkinsonsMonitoringSystem
Movement monitoring using Moto360 watch and other tests for parkinsons.
Here is the full [documentation](http://htmlpreview.github.com/?https://github.com/deshmukhrajvardhan/WearableParkinsonsMonitoringSystem/blob/master/html/index.html).

# Overview
## Wear App
The app for the wearable device 'Moto 360' which is used to record the sensor values when instructed by the mobile phone and store it in the database. <br />
![text](../master/Documentation_Resources/Wear_Options.jpg) <br />
![text2](../master/Documentation_Resources/Accelerometer.jpg) <br />
![text3](../master/Documentation_Resources/Gyroscope.jpg) <br />

The database can be pushed to the phone. <br />
![image](../master/Documentation_Resources/DB.jpg) <br />

Select ```File Transfer``` option and then click on ```Send DB``` to transfer the DB file to the android phone. <br />
![text1](../master/Documentation_Resources/WearOptions.jpg) <br />

Select ```Launch on Mobile``` option to open the app on the Android phone. <br />

## Mobile App
The app for the Android mobile phone which has the following functionalities:
a) Register new user. <br />
![image1](../master/Documentation_Resources/RegisterUser.jpg) <br />
b) Register Medical History of the patient. <br />
![image2](../master/Documentation_Resources/MedicalHistory.jpg) <br />
![image3](../master/Documentation_Resources/MedicalHistory2.jpg) <br />
c) Record Sensor values for Rest Tremor Tests. To start the Android Watch sensor recording, select the ```Play``` button the phone. <br />
![image5](../master/Documentation_Resources/Tremortests.jpg) <br />
![image4](../master/Documentation_Resources/RecordSensor.jpg) <br />
d) Perform Cognitive test. <br /> 
![image6](../master/Documentation_Resources/Cognitive.jpg) <br />
e) Perform Bradykinesia test. <br />  
![image8](../master/Documentation_Resources/Bradykinesiatest.jpg) <br />
![image7](../master/Documentation_Resources/Test.jpg) <br />
f) To get the database file from the Android watch select File Transfer option. <br />
![image9](../master/Documentation_Resources/SelectTest.jpg) <br />

# Flexibility
Both of our apps are independent and can be used individually as they also have different database files. <br />


# Installation
1. ```git clone https://github.com/rzabounidis/WearableWearableParkinsonsMonitoringSystemComplete``` <br />

2. Install [Android studio](https://developer.android.com/studio/index.html) <br />

3. Open Android studio: choose `File->Open File or Project->` <path> ```WclDemoSample``` and ```WearCompanionLibrary``` together. <br />

4. Android App: Enable developer [options](https://developer.android.com/studio/run/device.html) on your phone. <br />


## For Android Wear
 
1. Install the ```Android Wear``` application on your phone from the app store. <br />
2. Connect the watch to phone via bluetooth. <br />
3. [Connect](https://forum.xda-developers.com/moto-360/general/guide-install-apk-moto-360-t3028067) the watch to the phone. <br />
4. Enable the developer [options](https://forums.androidcentral.com/moto-360/436873-how-enable-developer-options.html) on the Android watch. <br />

# Useful Links

https://github.com/googlesamples/android-WclDemoSample <br />
https://github.com/googlesamples/android-WearCompanionLibrary <br />
https://github.com/drejkim/AndroidWearMotionSensors <br />
https://developer.android.com/training/index.html <br />







