#!/bin/bash

BASEDIR=$(dirname $(readlink -f ${0}))
BASENAME=$(basename ${0})

CURR_DIR=${PWD}
cd $BASEDIR

echo "HTTP/1.1 JDK-21"
echo "-------------------------------------------------------------------------------------------------"
export JAVA_HOME=$HOME/Programs/jdk-21
$JAVA_HOME/bin/java -Djavax.net.debug=ssl:handshake -jar H2SNIClient.jar 1.1 wls02 https://100.111.140.126:17703/DukeApp/hello.jsp &> $CURR_DIR/file.h1.jdk21
egrep '"subject"|HTTP' $CURR_DIR/file.h1.jdk21

echo ""
echo "HTTP/1.1 JDK-23"
echo "-------------------------------------------------------------------------------------------------"
export JAVA_HOME=$HOME/Programs/jdk-23
$JAVA_HOME/bin/java -Djavax.net.debug=ssl:handshake -jar H2SNIClient.jar 1.1 wls02 https://100.111.140.126:17703/DukeApp/hello.jsp &> $CURR_DIR/file.h1.jdk23
egrep '"subject"|HTTP' $CURR_DIR/file.h1.jdk23

# echo ""
# echo "HTTP/1.1 JDK-24"
# echo "-------------------------------------------------------------------------------------------------"
# export JAVA_HOME=$HOME/Programs/jdk-24
# $JAVA_HOME/bin/java -Djavax.net.debug=ssl:handshake -jar H2SNIClient.jar 1.1 wls02 https://100.111.140.126:17703/DukeApp/hello.jsp &> $CURR_DIR/file.h1.jdk24
# egrep '"subject"|HTTP' $CURR_DIR/file.h1.jdk24

echo ""
echo "HTTP/1.1 JDK-25"
echo "-------------------------------------------------------------------------------------------------"
export JAVA_HOME=$HOME/Programs/jdk-25
$JAVA_HOME/bin/java -Djavax.net.debug=ssl:handshake -jar H2SNIClient.jar 1.1 wls02 https://100.111.140.126:17703/DukeApp/hello.jsp &> $CURR_DIR/file.h1.jdk25
egrep '"subject"|HTTP' $CURR_DIR/file.h1.jdk25

echo ""
echo "HTTP/2 JDK-21"
echo "-------------------------------------------------------------------------------------------------"
export JAVA_HOME=$HOME/Programs/jdk-21
$JAVA_HOME/bin/java -Djavax.net.debug=ssl:handshake -jar H2SNIClient.jar 2 wls02 https://100.111.140.126:17703/DukeApp/hello.jsp &> $CURR_DIR/file.h2.jdk21
egrep '"subject"|HTTP' $CURR_DIR/file.h2.jdk21

echo ""
echo "HTTP/2 JDK-23"
echo "-------------------------------------------------------------------------------------------------"
export JAVA_HOME=$HOME/Programs/jdk-23
$JAVA_HOME/bin/java -Djavax.net.debug=ssl:handshake -jar H2SNIClient.jar 2 wls02 https://100.111.140.126:17703/DukeApp/hello.jsp &> $CURR_DIR/file.h2.jdk23
egrep '"subject"|HTTP' $CURR_DIR/file.h2.jdk23

# echo ""
# echo "HTTP/2 JDK-24"
# echo "-------------------------------------------------------------------------------------------------"
# export JAVA_HOME=$HOME/Programs/jdk-24
# $JAVA_HOME/bin/java -Djavax.net.debug=ssl:handshake -jar H2SNIClient.jar 2 wls02 https://100.111.140.126:17703/DukeApp/hello.jsp &> $CURR_DIR/file.h2.jdk24
# egrep '"subject"|HTTP' $CURR_DIR/file.h2.jdk24

echo ""
echo "HTTP/2 JDK-25"
echo "-------------------------------------------------------------------------------------------------"
export JAVA_HOME=$HOME/Programs/jdk-25
$JAVA_HOME/bin/java -Djavax.net.debug=ssl:handshake -jar H2SNIClient.jar 2 wls02 https://100.111.140.126:17703/DukeApp/hello.jsp &> $CURR_DIR/file.h2.jdk25
egrep '"subject"|HTTP' $CURR_DIR/file.h2.jdk25
