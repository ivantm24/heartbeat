# heartbeat
Implementation of heartbeat availability tactic

The files has to be executed in a terminal window in the following order:
1)FaultMonitor.jar
2)FaultDetector.jar
3)TransactionProcessor.jar

java -jar FILENAME.jar

TransactionProcessor might throws an undeterministic exception at anytime.

FaultDetector listens to TransantionProcessor's hearbeats and notifies FaultMonitor in case of reaching the timeout time without receiving a heartbeat.
