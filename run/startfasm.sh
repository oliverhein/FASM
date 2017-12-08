#!/usr/bin/bash

echo "*******************************************************************"
echo "*                                                                 *"
echo "*     FASM START SCRIPT FOR UNIX VERSION                          *"
echo "*                                                                 *"
echo "*     2007.03                                                     *"
echo "*                                                                 *"
echo "*******************************************************************"

if [ $# -lt 1 ] 
then 
echo "******************************************************************"
echo "* usage:                                                         *"
echo "* [~/fasm]./startfasm.sh config/<Simulation Configuration File>  *"
echo "*                                                                *"
echo "* Exmaple:                                                       *"
echo "* [~/fasm]./startfasm.sh config/test1.xml                        *"
echo "*                                                                *"
echo "* Tips:                                                          *"
echo "* You can input ./startfasm.sh config/  and then press TAB key,  *"
echo "* all available files will be displayed, then you can input the  *"
echo "* filename.                                                      *"
echo "*                                                                *"
echo "******************************************************************"

else

if [ ! -f $1 ]
then

echo "******************************************************************"
echo "*                                                                *"
echo "* Error:                                                         *"
echo "* Simulation Config $1                                           *"
echo "* does not exist.                                                *"
echo "* Please check the file name                                     *"
echo "******************************************************************"

else

FASM_HOME=.
WORKDIR=$FASM_HOME
JUNGLIBDIR=$FASM_HOME/lib/jung
JADELIBDIR=$FASM_HOME/lib/jade

CLASSPATH=$JADELIBDIR/jade.jar
CLASSPATH=$CLASSPATH:$JADELIBDIR/iiop.jar
CLASSPATH=$CLASSPATH:$JADELIBDIR/Base64.jar
CLASSPATH=$CLASSPATH:$JADELIBDIR/jadetools.jar
CLASSPATH=$CLASSPATH:$JADELIBDIR/colt.jar
CLASSPATH=$CLASSPATH:$JADELIBDIR/concurrent.jar
CLASSPATH=$CLASSPATH:$JADELIBDIR/commons-collections-3.1.jar
CLASSPATH=$CLASSPATH:$JADELIBDIR/jung-1.7.2.jar
CLASSPATH=$CLASSPATH:$JADELIBDIR/resolver.jar
CLASSPATH=$CLASSPATH:$JADELIBDIR/xercesimpl.jar
CLASSPATH=$CLASSPATH:$JADELIBDIR/xml-apis.jar
CLASSPATH=$CLASSPATH:$FASM_HOME/lib/jbcl.jar
CLASSPATH=$CLASSPATH:$FASM_HOME/lib/log4j.jar
CLASSPATH=$CLASSPATH:$FASM_HOME/lib/fasm.jar
CLASSPATH=$CLASSPATH:$FASM_HOME/etc

echo "**********************************************************************"
echo "*                                                                    *"
echo "*  Your current WORKDIR=$WORKDIR                                     *"
echo "*                                                                    *"
echo "**********************************************************************"

echo CLASSPATH=$CLASSPATH

#  Comments
#  Attention: Parameter is seperated by space not comma

JADEBASICCONFIG=$WORKDIR/etc/jadebasic.cfg
AGENTCONFIGPATH=$WORKDIR/config

java -noverify -Xms128m -Xmx1024m -cp $CLASSPATH -DAGENTCONFIGPATH=$AGENTCONFIGPATH -DJADEBASICCONFIG=$JADEBASICCONFIG -DSIMULATIONCONFIG=$1 -DDIALOG=false de.marketsim.gui.StartFrame


fi


fi
