::@ECHO OFF
echo ""
set JARPATH=C:/shailendra/eclipse workspace/mergeExcelFiles/lib/*


set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;%JARPATH%;
cd bin
java -Xms128m -Xmx384m -Xnoclassgc MergeExcelFiles
cd ..