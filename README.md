# CakeManager

Requirements:

* By accessing the root of the server (/) it should be possible to list the cakes currently in the system.  This
 must be presented in an acceptable format for a human to read.

* It must be possible for a human to add a new cake to the server.

* By accessing an alternative endpoint (/cakes) with an appropriate client it must be possible to download a list of
 the cakes currently in the system as JSON data.

* Accessing the /cakes endpoint with a web browser must show the human presentation of the list of cakes.

* The /cakes endpoint must also allow new cakes to be created.


The developer assured us that some of the above is complete, but at the moment accessing the /cakes endpoint
 returns a 404, so getting this working should be the first priority.

There may be other bugs and mistakes, feel free to fix anything you find. Likewise, feel free to re-organise,
 refactor or re-write the project anyway you see fit.

Notes:
Please Ignore Cake.java, CakeStore.java classes as they have been created for initial testing purposes.
Please read ChangeLog.txt for detailed change description
