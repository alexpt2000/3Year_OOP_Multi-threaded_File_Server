To create the JAR file
=================================
jar -cf oop.jar *


To Run the Client
=================================
java -cp oop.jar ie.gmit.sw.client.Client


To Run the Server
=================================
java -cp oop.jar ie.gmit.sw.server.Server 7777 /serverFiles



Falta Concluir
=================================
POISON
Melhorar comentarios
UML
Javadoc
Readme


Anormalias
=================================
A primeira que a aplicacao e iniciada ocorrera um erro devido a falta do arquivo log.txt, mas apartir da segunda utilizacao o erro deixa de existir.



config.xml
=================================

<client-config username="gmit-sw2016">
	<server-host>127.0.0.1</server-host>
	<server-port>7777</server-port>
	<download-dir>C:/Users/Xtribal/Downloads/</download-dir>
</client-config>




log.txt
=================================

[INFO] Connecting requested by 127.0.0.1 at 10:57 AM on 08 January 2017
[ERROR] Connection reset requested by 127.0.0.1 at 11:02 AM on 08 January 2017
[INFO] Connecting requested by 127.0.0.1 at 11:02 AM on 08 January 2017
[INFO] Listing files requested by 127.0.0.1 at 11:02 AM on 08 January 2017
[INFO] End conection requested by 127.0.0.1 at 11:02 AM on 08 January 2017
[INFO] Connecting requested by 127.0.0.1 at 11:05 AM on 08 January 2017
[INFO] Listing files requested by 127.0.0.1 at 11:05 AM on 08 January 2017
[INFO] alex.txt requested by 127.0.0.1 at 11:05 AM on 08 January 2017
[WARNING] file /serverFiles/alex.txt does not exist requested by 127.0.0.1 at 11:05 AM on 08 January 2017
[ERROR] Connection reset requested by 127.0.0.1 at 11:05 AM on 08 January 2017