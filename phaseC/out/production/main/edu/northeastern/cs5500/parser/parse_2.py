#!/usr/bin/python
import sys
import ssl
import socket 

s = socket.socket()


elif sys.argv[1] == "-p" and sys.argv[3] != "-s":
    host= sys.argv[3]
    port= int(sys.argv[2])
    NUID=sys.argv[4]
    s.connect((host, port))
elif sys.argv[1] == "-s":
    host= sys.argv[2]
    port = 27994
    NUID= sys.argv[3]
    s.connect((host, port))
    s = ssl.wrap_socket(s)
else:
    host= sys.argv[1]
    port = 27993
    NUID= sys.argv[2]
    s.connect((host, port)) 

s.send('cs5700fall2016 HELLO %s\n' % NUID)
message=s.recv(1024)
if sys.argv[1] == "-p" and sys.argv[3] == "-s":
    host= sys.argv[4]
    port= int(sys.argv[2])
    NUID=sys.argv[5]
    s.connect((host, port))
    s = ssl.wrap_socket(s)
