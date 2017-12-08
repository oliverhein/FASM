#*/usr/bin/bash

#
# FASM Agent Simulator start script
# 2007-03 
# Xining Wang
#

FASM_HOME=.
WORKDIR=$FASM_HOME
JUNGLIBDIR=$FASM_HOME/lib/jung
JADELIBDIR=$FASM_HOME/lib/jade

 CLASSPATH=$JADELIBDIR/jade.jar
 CLASSPATH=$CLASSPATH:$JADELIBDIR/iiop.jar
 CLASSPATH=$CLASSPATH:$JADELIBDIR/Base64.jar
 CLASSPATH=$CLASSPATH:$JADELIBDIR/jadetools.jar
 CLASSPATH=$CLASSPATH:$JUNGLIBDIR/colt.jar
 CLASSPATH=$CLASSPATH:$JUNGLIBDIR/concurrent.jar
 CLASSPATH=$CLASSPATH:$JUNGLIBDIR/commons-collections-3.1.jar
 CLASSPATH=$CLASSPATH:$JUNGLIBDIR/jung-1.7.2.jar
 CLASSPATH=$CLASSPATH:$JUNGLIBDIR/resolver.jar
 CLASSPATH=$CLASSPATH:$JUNGLIBDIR/xercesimpl.jar
 CLASSPATH=$CLASSPATH:$JUNGLIBDIR/xml-apis.jar
 CLASSPATH=$CLASSPATH:$FASM_HOME/lib/jbcl.jar
 CLASSPATH=$CLASSPATH:$FASM_HOME/lib/log4j.jar
 CLASSPATH=$CLASSPATH:$FASM_HOME/etc

 CLASSPATH=$CLASSPATH:$FASM_HOME/lib/fasm.jar

echo "**********************************************************************"
echo "*                                                                    *"
echo "*  This is fasmclient.sh                                             *"
echo "*  fasmclient.sh is used to start AgentSimulator under UNIX          *"
echo "*  An AgentSimulator will manage and simulate some agents.           *"
echo "*  If multi AgentSimulator start parallel, the load of simulation    *"
echo "*  will be distributed to the different simulators. It will only make*"
echo "*  sense when the AgentSimulators are started on different PCS.      *"
echo "*                                                                    *"
echo "*  The current WORKDIR=$WORKDIR                                      *"
echo "*                                                                    *"
echo "**********************************************************************"

echo CLASSPATH=$CLASSPATH
JADEBASICCONFIG=$WORKDIR/etc/jadebasic.cfg

java -noverify -Xms128m -Xmx512m -cp $CLASSPATH -DJADEBASICCONFIG=$JADEBASICCONFIG -DDIALOG=false de.marketsim.agent.trader.BootAgentSimulator

