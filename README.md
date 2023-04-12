
# Travel Planner 

### *Description*
This application will allow users to plan and organize their travels. Users can use this application to plan their 
itinerary, input information about the date, time, duration, transportation information, travel directions, price,
and any additional information they want to add. Users can edit any information at any time. 


This project interests me because there are many places I want to travel to, but everytime I travel,
I am always disorganized. I lose track of important information and can't decide about what to do during the trip. This travel application will allow me to keep track
of and organize the information so I am better prepared when travelling and have a reference in case I am unsure about anything.



### *User Stories:*


- As a user, I want to be able to add multiple events to my schedule
- As a user, I want to be able to delete events from my schedule
- As a user, I want to be able to view the times I scheduled for a selected activity or event.
- As a user, I want to be able to edit any information about an event
- As a user, I want to be able to save the schedule I have created with all the events and its details
- As a user, I want to be able to open my saved schedule I created earlier


### *Instructions for Grader*

- You can locate my visual component when you first run the application and you are taken to a welcome page. There is 
 a background image
- You can generate the first required action related to adding Xs to a Y by clicking the "Add Event" Button on the main page
 and inputting all the relevant information. Click the "Add event to schedule" to add the event to the schedule. Exit the current
 window to return to the main page
- You can generate the second required action related to adding Xs to a Y by clicking "Remove Event" on the main page. A schedule
 will show up with "Select" buttons under each event. Click the button corresponding with the event you want to remove.
 A confirmation message will show up. 
- To view the updated schedule, go back to the main menu and click "View Schedule"
- You can save the state of my application by clicking "Save Schedule" on the main menu. A window will pop up confirming that the 
 current schedule has been saved
- You can reload the state of my application by clicking "Load Schedule" on the main menu. A window will pop up confirming that the
 schedule has been loaded



### *Phase 4 task 2* ###
*note: the "X" class is "EventX"* 
actions taken: loading, adding, removing, saving

Tue Apr 11 01:17:27 PDT 2023
instantiates a new empty schedule

Tue Apr 11 01:17:28 PDT 2023
instantiates a new empty schedule

Tue Apr 11 01:17:28 PDT 2023
created a new event called event starting at 12.0

Tue Apr 11 01:17:28 PDT 2023
added the event event to the schedule

Tue Apr 11 01:17:28 PDT 2023
created a new event called event2 starting at 9.0

Tue Apr 11 01:17:28 PDT 2023
added the event event2 to the schedule

Tue Apr 11 01:17:55 PDT 2023
created a new event called event3 starting at 13.0

Tue Apr 11 01:17:55 PDT 2023
added the event event3 to the schedule

Tue Apr 11 01:18:03 PDT 2023
removed the event event from the schedule

Tue Apr 11 01:18:06 PDT 2023
converted event event2 to a Json object

Tue Apr 11 01:18:06 PDT 2023
created a json array representing a day schedule

Tue Apr 11 01:18:06 PDT 2023
converted the day schedule to a json object

Tue Apr 11 01:18:06 PDT 2023
converted event event3 to a Json object

Tue Apr 11 01:18:06 PDT 2023
created a json array representing a day schedule

Tue Apr 11 01:18:06 PDT 2023
converted the day schedule to a json object

Tue Apr 11 01:18:06 PDT 2023
created a json array representing a week schedule

Tue Apr 11 01:18:06 PDT 2023
saving schedule by converting the schedule to a Json object



### *Phase 4 Task 3* ###

First, I would refactor the PlanningAppGui to make it more cohesive. The class has too many responsibilities and I would
probably make each functionality its own class. An example would be a class called AddEvent. The constructor would take in
a component. In this case, the component would be the JPanel of the main frame. This class would create the
"Add Event" button, add it to the main frame JPanel, and create the inner ActionListener class that creates the AddEvent frame 
and confirms that the event has been added to the schedule. I would have separate classes for RemoveEvent, SaveSchedule,
and LoadSchedule, and CreateWelcomeFrame with the similar patterns. Furthermore, for the AddEvent and RemoveEvent classes,
I would pull out any methods and fields related to creating the frame into its own class. For example, in the AddEvent 
class, I would pull out all the methods and fields relating to the frame where users input information about an event into
a separate class, and the AddEvent class would instead, have a single field of that class. Additionally, I would also have
a separate class dealing with confirming the event as well. I would do something similar with RemoveEvent as well. The 
other classes seem cohesive on their own. 

Another thing I noticed is that ViewSchedule and RemoveSchedule have a lot of the same methods, since both classes have a
view of the schedule. I would make a separate abstract class that contains methods involving creating the visual schedule. The ViewSchedule
and RemoveSchedule would both extend this class. The abstract method would be the one that involves creating the panel that
contains information about an event (CreateTimePanel). ViewSchedule and RemoveSchedule would provide their own implementations
of this since this is the one part that differs in both classes. This would reduce the unnecessary duplication in these
classes and reduce coupling.

However, all these additional classes need access to the same instance of Schedule, otherwise each action would be performed on a newly
instantiated schedule that is empty. One way this could be fixed is passing a Schedule as a parameter in the constructor
of these additional classes in PlanningAppGui, so each class has access to the same schedule and implementing the Action-Listener
pattern to ensure that if one class makes changes to the schedule, all the classes are notified about this update. However, 
this would mean that most of these classes are both an observer and a subject, which could reduce cohesion and the single
responsibility principle. Another solution is to have the Schedule class be a singleton, so it only gets instantiated
once and every class would have global access to this single instance. A possible downside to this is that tests for EventX
and Schedule would need to change since a new Schedule can't be instantiated anymore. 



