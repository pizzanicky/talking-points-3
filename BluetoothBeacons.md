# Bluetooth Mate Documentation #

[Download it here](http://www.sparkfun.com/datasheets/Wireless/Bluetooth/rn-bluetooth-um.pdf)

# Connecting to and configuring a Bluetooth Mate #

The default mode of Roving modules is an OPEN mode, such that the module does NOT require authentication. However most PC’s will require authentication.

The pass key is a string of alpha or numeric chars, 1 to 16 chars in length. During the initial pairing process, this code is entered on both sides of the Bluetooth connection, and must match to complete the pairing. This passkey is used to create a secure link-key, which is then stored on both devices. Upon subsequent connection attempts, the link-keys are compared and must match before the connection can continue. The default Passkey is “1234”.


  1. Connect to the Bluetooth Mate using your Bluetooth assitant. The passkey to pair is 1234.
  1. Download and install ZTerm and go to Settings->Modem preferences, choose the modem ("Firefly" or something).
  1. Go to Settings->Connection and set the Baud-rate to 9600.
  1. Restart both Zterm and the Bluetooth Mate. Make sure you restart Zterm within 60 seconds after Bluetooth Mate restarted! The green LED should be on.
  1. ommand+K (keyboard buffer) allows you to enter commands (within 60 seconds after Bluetooth Mate restart!). Enter $$$ first. That switches the modem to command mode. The terminal should return CMD?
  1. To change the name of the Bluetooth Mate use this command:

**SN, name** Name of the device, 20 characters maximum.

_Example: “SN,MyDevice”_

**S-, name** Serialized Friendly Name of the device, 15 characters maximum.

This command will automatically append the last 2 bytes of the BT MAC address to
the name. Useful for generating a custom name with unique numbering.
Example: S-,MyDevice will set the name to “MyDevice-ABCD”