# tableview-phone-directory
My solution for Chapter 13 Exercise 4 of “Introduction to Programming Using Java”.

NOTE: This is a javafx program. It requires the javafx library as a dependency. (See bottom of this README for javafx instructions).

Problem Description:
The sample program PhoneDirectoryFileDemo.java from Subsection 11.3.2 keeps data for
a “phone directory” in a file in the user’s home directory. Exercise 11.5 asked you to
revise that program to use an XML format for the data. Both programs have a simple
command-line user interface. For this exercise, you should provide a GUI interface for the
phone directory data. You can base your program either on the original sample program
or on the modified XML version from the exercise. Use a TableView to hold the data.
The user should be able to edit all the entries in the table. Also, the user should be able
to add and delete rows. Include either buttons or menu commands that can be used to
perform these actions. The delete command should delete the selected row, if any. New
rows should be added at the end of the table.
Your program should load data from the file when it starts and save data to the file
when it ends, just as the two previous programs do. For a GUI program, you need to
save the data when the user closes the window, which ends the program. To do that, you
can add a listener to the program’s Stage to handle the WindowHidden event. For an
example of using that event, the Mandelbrot Viewer program from Section 13.5 uses it to
save preferences when the program ends. For an example of creating an editable table,
see ScatterPlotTableDemo.java.
(I suggest keeping things simple. You not being asked to write a real phone book
application! The point is mostly to make an editable table. My program has text input
boxes for name and number, and an “Add” button for adding a new entry containing the
input in those boxes. My program always saves the data, whether or not the user has
changed it. The interface will be poor: The user has to double-click a cell to edit it and
press return to finish the edit and save the new value. It is possible to make a table with
a better editing interface, but to do that, you need to write a new CellFactory class for the
table.)

Javafx setup instructions:
Download javafx from: https://gluonhq.com/products/javafx/ (I used javafx 12). Save it to a location of your choice.
Unpack the zip folder.
Open my project with your IDE of choice (I use intellij IDEA).
Add the javafx/lib folder as an external library for the project. For intellij, this means going to "project structure" -> "libraries" -> "add library" ->{javafx location}/lib
Add the following as a VM argument for the project: --module-path "{full path to your javafx/lib folder}" --add-modules javafx.controls,javafx.fxml,javafx.base,javafx.graphics,javafx.media,javafx.swing,javafx.web
Build and run the project as normal.
