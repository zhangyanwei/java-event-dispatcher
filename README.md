# java-event-dispatcher
A sample to create an event dispatcher in Java.

## JDK version
this project based on JDK1.7, so created new classes to extend the List and Map.  
should delete some classes if upgraded to 1.8.

## How to use it
this is a very simple structure for event, if you want to implement the MQ clustered event, 
you can write your own EventDispatcher implementation.  

And also you can just inject/autowired the EventDispatcher rather than to use the implementation directly will help you
to manage your code in a graceful way. :)