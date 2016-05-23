/**
 *  HTTP Requests
 *
 *  Copyright 2015 Brian Freund (Credit goes to Patrick Stuart (@pstuart) and Jason E (jasone) for their code examples I copied plus of course the SmartThings Documentation which helped immensely)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
name: "HTTP Requests",
namespace: "brianfreund",
author: "Brian Freund",
description: "A SmartApp that when paired with an Android running Automagic and UDP Sender, can control your Milights naively through UDP commands.",
category: "Convenience",
iconUrl: "http://apk-dl.com/detail/image/com.lierda.wifi-w250.png",
iconX2Url: "http://apk-dl.com/detail/image/com.lierda.wifi-w250.png",
iconX3Url: "http://apk-dl.com/detail/image/com.lierda.wifi-w250.png")

preferences {
section("Execute HTTP Request attached to switch") {
    input "theswitch", "capability.switch", required: true, title: "Which switch?"
}
section("Network Information"){
	input("AndroidIP", "string", title:"Android IP Address", description: "Please enter your Android's IP Address", defaultValue: "192.168.1.195" , required: true, displayDuringSetup: true)
			input("AndroidPort", "string", title:"Android Port", description: "Please enter your Android's Port", defaultValue: 8080 , required: true, displayDuringSetup: true)
    input("PathOn", "string", title:"Path", description: "Enter a path for when the switch turns on", required: false, displayDuringSetup: true)
			input("PathOff", "string", title:"Path", description: "Enter a path for when the switch turns off", required: false, displayDuringSetup: true)
	}
}

def installed() {
	initialize()
}

def updated() {
	unsubscribe()
	initialize()
}

def initialize() {
	subscribe(theswitch, "switch", switchHandler)
}

def switchHandler(evt) {
	if (evt.value == "on") {
		onSwitches()
	} else if (evt.value == "off") {
		offSwitches()
	}
}

def onSwitches() {
def host = AndroidIP
def port = AndroidPort
def hosthex = convertIPtoHex(AndroidIP)
def porthex = convertPortToHex(AndroidPort)
def deviceNetworkId = "$hosthex:$porthex"
def ip = "$AndroidIP:$AndroidPort"
sendHubCommand(new physicalgraph.device.HubAction("""POST $PathOn HTTP/1.1\r\nHOST: $ip\r\n\r\n""", physicalgraph.device.Protocol.LAN, "${deviceNetworkId}"))
log.debug "$ip was sent $PathOn via $deviceNetworkId"
log.debug "host is $host, port is $port, hosthex is $hosthex, porthex is $porthex, deviceNetworkId is     $deviceNetworkId, ip is $ip"
}

def offSwitches() {
def host = AndroidIP
def port = AndroidPort
def hosthex = convertIPtoHex(AndroidIP)
def porthex = convertPortToHex(AndroidPort)
def deviceNetworkId = "$hosthex:$porthex"
def ip = "$AndroidIP:$AndroidPort"
sendHubCommand(new physicalgraph.device.HubAction("""POST $PathOff HTTP/1.1\r\nHOST: $ip\r\n\r\n""", physicalgraph.device.Protocol.LAN, "${deviceNetworkId}"))
log.debug "$ip was sent $PathOff via $deviceNetworkId"
log.debug "host is $host, port is $port, hosthex is $hosthex, porthex is $porthex, deviceNetworkId is     $deviceNetworkId, ip is $ip"
}

private String convertIPtoHex(host) { 
String hosthex = host.tokenize( '.' ).collect {  String.format( '%02x', it.toInteger() ) }.join()
log.debug "the returned host is $hosthex"
return hosthex
}

private String convertPortToHex(port) {
	String porthex = port.toString().format( '%04x', port.toInteger() )
log.debug "the returned port is $porthex"
return porthex
}