@echo off

set FASM_HOME=C:\fasmdev-new
set WORKDIR=.
set JUNGLIBDIR=%FASM_HOME%\lib\jung
set JFREECHARTDIR=%FASM_HOME%\lib\jfreechart

echo "**********************************************************************"
echo "* Script for creating chart of the simulation                        *"
echo "* 2007-04                                                            *"
echo "* The current WORKDIR=%WORKDIR%                                      "
echo "**********************************************************************"


set classpath=%FASM_HOME%\lib\fasm.jar

rem set classpath=%FASM_HOME%\classes
set classpath=%CLASSPATH%;%FASM_HOME%\lib\jbcl.jar
set classpath=%CLASSPATH%;%FASM_HOME%\lib\swt\swt.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\colt.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\concurrent.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\commons-collections-3.1.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\jung-1.7.2.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\resolver.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\xercesimpl.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\xml-apis.jar
set classpath=%CLASSPATH%;%JFREECHARTDIR%\jcommon-1.0.12.jar
set classpath=%CLASSPATH%;%JFREECHARTDIR%\jfreechart-1.0.9.jar

echo classpath=%CLASSPATH%

java -Xmx200m  -cp %CLASSPATH% -Djava.library.path=%FASM_HOME%\lib\swt -DFASMWORKDIR=.  de.marketsim.gui.ChartGenerator

