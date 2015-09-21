# Introduction #

You need to install three things in order to begin working on talking points:
  1. [Android 2.1 SDK](http://developer.android.com/sdk/android-2.1.html)
  1. [Eclipse Classic 3.5.1](http://www.eclipse.org/downloads/)
  1. Subversion plug-in for Eclipse


# Java JDK #

Install JDK: The Android SDK requires JDK version 5 or version 6. If you already have one of those installed, skip to the next step. In particular, Mac OS X comes with the JDK version 5 already installed, and many Linux distributions include a JDK. If the JDK is not installed, go to and you’ll see a list of Java products to download. You want JDK 6 Update n for your operating system, where n is 6 at the time of this writing.

**Windows (XP and Vista)**
  * Select the distribution for “Windows Offline Installation, Multi-language.”
  * Read, review, and accept Sun’s license for the JDK. (The license has become very permissive, but if you have a problem with it, alternative free JDKs exist.)
  * Once the download is complete, a dialog box will ask you whether you want to run the downloaded executable. When you select “Run,” the Windows Installer will start up and lead you through a dialog to install the JDK on your PC.

**Linux**

  * Select the distribution for “Linux self-extracting file.”
  * Read, review, and accept Sun’s license for the JDK. (The license has become very permissive, but if you have a problem with it, alternative free JDKs exist.)
  * You will need to download the self-extracting binary to the location in which you want to install the JDK on your filesystem. If that is a system-wide directory (such as /usr/local), you will need root access. After the file is downloaded, make it executable (chmod +x jdk-6version-linux-i586.bin), and execute it. It will self-extract to create a tree of directories.

**Mac OS X**

  * Mac OS X comes with JDK version 5 already loaded.


# Installing the Android SDK #

The most recent download can be found here http://developer.android.com/sdk/index.html

To initiate the installation of the Android SDK, please read the SDK Readme.txt contained in the package. You may invoke the SDK Manager and AVD Manager within Eclipse (see below) or via command-line program "tools/android" within the Android SDK package folder.

Note: We recommend using http://dl-ssl.google.com/android/repository/repository.xml (without the https) tool to manage/update your Android SDK client installation. Select which version of the Android SDK you'd like to install (e.g. Google APIs by Google Inc., Android API 7) and accept the EULA.

# Update your environment variables #

Update the environment variables: To make it easier to launch the Android tools, add the tools directory to your path.

**Windows XP**

Click on Start, then right-click on My Computer. In the pop-up menu, click on Properties. In the resulting System Properties dialog box, select the Advanced tab. Near the bottom of the Advanced tab is a button, “Environment Variables,” that takes you to an Environment Variables dialog. User environment variables are listed in the top half of the box, and System environment variables in the bottom half. Scroll down the list of System environment variables until you find “Path”; select it, and click the “Edit” button. Now you will be in an Edit System Variable dialog that allows you to change the environment variable “Path.” Add the full path of the tools directory to the end of the existing Path variable and click “OK.” You should now see the new version of the variable in the displayed list. Click “OK” and then “OK” again to exit the dialog boxes.

**Windows Vista**

Click on the Microsoft “flag” in the lower left of the desktop, then right-click on Computer. At the top of the resulting display, just below the menu bar, click on “System Properties.” In the column on the left of the resulting box, click on “Advanced system settings.” Vista will warn you with a dialog box that says “Windows needs your permission to continue”; click “Continue.” Near the bottom of the System Properties box is a button labeled “Environment Variables” that takes you to an Environment Variables dialog. User environment variables are listed in the top half of the box, and System environment variables in the bottom half. Scroll down the list of System environment variables until you find “Path”; select it, and click the “Edit” button. Now you will be in an Edit System Variable dialog that allows you to change the environment variable “Path.” Add the full path of the tools directory to the end of the existing Path variable, and click “OK.” You should now see the new version of the variable in the displayed list. Click “OK” and then “OK” again to exit the dialog boxes.

**Linux**

The PATH environment variable can be defined in your ~/.bashrc ~/.bash\_profile file. If you have either of those files, use a text editor such as gedit, vi, or Emacs to open the file and look for a line that exports the PATH variable. If you find such a line, edit it to add the full path of the tools directory to the path. If there is no such line, you can add a line like this:

  * export PATH=${PATH}:your\_sdk\_dir/tools
> > where you put the full path in place of your\_sdk\_dir.

**Mac OS X**

Look for a file named .bash\_profile in your home directory (note the initial dot in the filename). If there is one, use an editor to open the file and look for a line that exports the PATH variable. If you find such a line, edit it to add the full path of the tools directory to the path. If there is no such line, you can add a line like this:

  * export PATH=${PATH}:your\_sdk\_dir/tools
> > where you put the full path in place of your\_sdk\_dir.

# Eclipse IDE Installation #

The Android SDK requires a development environment.

While both Eclipse 3.4 (Ganymede) or 3.5 (Galileo) are supported, it is recommended that you use one of the following versions: Eclipse IDE for Java EE Developers, Eclipse IDE for Java Developers, Eclipse for RCP/Plug-in Developers or Eclipse Classic (3.5.1+). The Eclipse JDT plugin is also required, which is included in most Eclipse IDE packages.

As of this writing, our instructions were tested under Eclipse IDE for Java EE Developers 3.5.1 (Galileo):

Once you've installed Eclipse, you need to install the ADT Plugin for Eclipse for Android application development.

# Eclipse ADT Plugin Installation #

  1. Start Eclipse, then select Help > **Install New Software**.
  1. In the Available Software dialog, click **Add**....
  1. In the Add Site dialog that appears, enter a name for the remote site (for example, "Android Plugin") in the "Name" field.
  1. In the "Location" field, enter this URL: https://dl-ssl.google.com/android/eclipse **Note**: If you have trouble acquiring the plugin, you can try using "http" in the URL, instead of "https" (https is preferred for security reasons). **Click OK**.
  1. Back in the Available Software view, you should now see "Developer Tools" added to the list. Select the checkbox next to Developer Tools, which will automatically select the nested tools Android DDMS and Android Development Tools. Click **Next**.
  1. In the resulting Install Details dialog, the Android DDMS and Android Development Tools features are listed. Click Next to read and accept the license agreement and install any dependencies, then click **Finish**.
  1. **Restart** Eclipse.

# Configuring Eclipse to use the Android SDK #

Now modify your Eclipse preferences to point to the Android SDK directory:
  1. **Select** Window > Preferences... to open the Preferences panel (Mac OS X: Eclipse > Preferences).
  1. **Select** Android from the left panel.
  1. For the SDK Location in the main panel, click **Browse**... and locate your downloaded SDK directory. The Android SDK version is determined by the manifest.ini file in your SDK directory
  1. lick **Apply**, then OK.

# Troubleshooting Eclipse WST Server Adapter #

Installing the Android requires WST Server Adapters. If these have not been installed and/or you receive an error message when trying to install the ADT Plugin:

  1. Launch Eclipse
  1. Help -> Install New Software
  1. Click Add
  1. Enter http://download.eclipse.org/releases/galileo under Location
  1. Under Web, XML, and Java EE Development, select WST Server Adapaters