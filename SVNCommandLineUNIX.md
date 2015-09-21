# Adding a New Directory with the SVN Command Line #

SVN repo checkout (read-only)
```
svn checkout http://talking-points-3.googlecode.com/svn/trunk/  talking-points-3-read-only
```

SVN repo checkout (commit privileges)
```
svn checkout https://talking-points-3.googlecode.com/svn/trunk/ talking-points-3 --username your.name
```

Once you make your changes to the code, you need to issue a "svn add" command to add the respective files and/or directories. Note: By default, svn file additions are recursive.
```
svn add "file or directory"

A         POIScanner
A         POIScanner/.classpath
A         POIScanner/.project
A         POIScanner/AndroidManifest.xml
A         POIScanner/assets
A         POIScanner/bin
A  (bin)  POIScanner/bin/classes.dex
A  (bin)  POIScanner/bin/POIScanner.apk
A  (bin)  POIScanner/bin/resources.ap_
...
```
To save these changes to the repository, you need to issue an svn commit command, where the -m argument specifies a comment:

```
svn commit -m "uploading updated Bluetooth Scanner with compass and logging functionality"
```

After you commit your changes, you will be prompted to enter your Google Code password:

```
Authentication realm: <https://talking-points-3.googlecode.com:443> Google Code Subversion Repository

Password for ‘user.name: *********
```

Once you authenticate, svn will verbosely display your uploads to the repository:
```
Adding         POIScanner
Adding         POIScanner/.classpath
Adding         POIScanner/.project
Adding         POIScanner/AndroidManifest.xml
Adding         POIScanner/assets
Adding         POIScanner/bin
Adding  (bin)  POIScanner/bin/POIScanner.apk
Adding  (bin)  POIScanner/bin/classes.dex
Adding  (bin)  POIScanner/bin/resources.ap_
...
Transmitting file data ............................n
Committed revision 46.
```

Note: You have to use your **googlecode password**, not your gmail password.  Please review our project's source page for instructions. In addition, you will **not** be able to commit changes to the repository unless you are a project member (either owner or committer).

Subversion cheat sheet: http://www.abbeyworkshop.com/howto/misc/svn01/