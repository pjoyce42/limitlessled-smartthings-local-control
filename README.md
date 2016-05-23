# limitlessled-smartthings-local-control
A SmartThings app that will provide simple, and robust control for Milight / limitlessled / easybulb products using a local server


This Smartthings app is designed to control MiLight / LimitlessLED / EasyBulb without leaving the local network.

The basic goals of this project:

* Provide full compatibility with MiLight / LimitlessLED / EasyBulb RGBW lights
* Be simple to setup, and run robustly. 
* Stay thuroughly documented, so that others can modify the code if/when I stop maintaining things.

The structure of this app+server is as follows:

The App: I will be using brianfreund's HTTP Resuest SmartThings app as the basis of the phone app (https://community.smartthings.com/t/local-wifi-integration-of-milight-limitlessled-easyblub-using-spare-android-phone/31819). I am going to modify his app to use the RGBW Light instead of the simulated switch. I am going to include the python commands in the HTTP requests to simplify the server script.

The server: I will be writing the server in Python (3.X). I am going to use an open source HTTP server to listen for the app commands. I am going to use the Joaquin Casares' python wifi LEDs package (https://github.com/joaquincasares/python-wifi-leds) to send the UDP commands to the MiLight Wifi bridge.

I will update this ReadMe with any future sources as I develop this project.

Thanks,

Patrick Joyce