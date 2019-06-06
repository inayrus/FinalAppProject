## Day 1
I picked between two different API options that can convert handwriting into digital text: Google Mobile Vision and Microsoft Computer Vision. I requested a Microsoft API key, because they offer a trial verion where you don't have to enter a creditcard number. However, this key will be valid for 7 days, so I'd have to request a new one every week, but right now I'm okay with that.

I also looked into a what makes a good interface design (Material Design site) and worked to finish my proposal by sketching some ideas for the user interface. These are the current designs, where the converted note activity visualizes what the app would look like after a photo has been turned to text.

However, I'm still thinking about where to put the option to create a new folder and to put a note in a folder. I also haven't yet included a sketch for the camera and the photo album view.

![converted note activity](https://github.com/inayrus/FinalAppProject/blob/master/doc/converted_note.png)
![overview activity](https://github.com/inayrus/FinalAppProject/blob/master/doc/overview_activity_three_FAB.png)
![new note activity](https://github.com/inayrus/FinalAppProject/blob/master/doc/new_note.png)
![folder activity](https://github.com/inayrus/FinalAppProject/blob/master/doc/folder_activity.png)
![detail activity](https://github.com/inayrus/FinalAppProject/blob/master/doc/detail_activity.png)

## Day 2
I looked a bit more into how other note apps displayed certain features, such as folders and note options (delete, share). This caused me to redesign the layout of my app. I used this new design to build my design document.

I spend most of the day putting together the advanced sketches and I realized I probably need to do more things than expected to get the app working. Right now, I think I would need two databases, where one is used for the actual notes and one for the app settings, like the tags. I also discovered I probably need to add a camera API for the feature to make an in-app photo.

![design doc sketch](https://github.com/inayrus/FinalAppProject/blob/master/doc/design_doc_sketch_new.png)

## Day 3
Today was the first stand-up. I asked how they would handle the add tag function in the sidebar and where to save it, considering it isn't linked to a note in the NoteDatabase. My feedback from Pjotr was to try to focus on the minimum viable product features first and to add the tag details later. This caused me to think what else is not strictly necessary to make a functional app. I decided to implement the following features after the basics are working:
* Possibility to create a loose extra tag (not yet linked to a note)
* Camera option (get picture by making a photo)
* The sidebar navigation

I also worked on the "utility modules, classes and functions" part of the design doc and added a new sketch. This select photo activity is added to the readme and incorporated in a new design doc sketch. I think I now only have to look into how to get the keyboard format options (bolding, underline, highlight) and how to remember how edited text is formatted, and then I can start on coding the prototype.

![diagrams](https://github.com/inayrus/FinalAppProject/blob/master/doc/UML.png)

## Day 4
The goal of the stand up today was to make a list of things we need t research to make our app working. My list consisted of figuring out how to implement the keyboard formatting options, how to save the formatted text, and figuring out how the microsoft API works. My original idea on how to save a formatted word was to bracket this words with HTML tags (<b> bolding </b>), in combination with the fromHTML function. During the stand-up, I got a suggestion from Maarten to look into ascii (Whatsapp's use of * for bolding, _ for italics). Pjotr suggested that I could also use the text attribute in android studio to format the text. I think I will go with whatever option also allows for highlighting in different colours.

Today I mainly worked on putting together basic layouts and making sure they're connected with buttons and listeners. The listviews in the layout aren't working yet.
