### Week 1
#### Day 1
I picked between two different API options that can convert handwriting into digital text: Google Mobile Vision and Microsoft Computer Vision. I requested a Microsoft API key, because they offer a trial verion where you don't have to enter a creditcard number. However, this key will be valid for 7 days, so I'd have to request a new one every week, but right now I'm okay with that.

I also looked into a what makes a good interface design (Material Design site) and worked to finish my proposal by sketching some ideas for the user interface. These are the current designs, where the converted note activity visualizes what the app would look like after a photo has been turned to text.

However, I'm still thinking about where to put the option to create a new folder and to put a note in a folder. I also haven't yet included a sketch for the camera and the photo album view.

![converted note activity](https://github.com/inayrus/FinalAppProject/blob/master/doc/converted_note.png)
![overview activity](https://github.com/inayrus/FinalAppProject/blob/master/doc/overview_activity_three_FAB.png)
![new note activity](https://github.com/inayrus/FinalAppProject/blob/master/doc/new_note.png)
![folder activity](https://github.com/inayrus/FinalAppProject/blob/master/doc/folder_activity.png)
![detail activity](https://github.com/inayrus/FinalAppProject/blob/master/doc/detail_activity.png)

#### Day 2
I looked a bit more into how other note apps displayed certain features, such as folders and note options (delete, share). This caused me to redesign the layout of my app. I used this new design to build my design document.

I spend most of the day putting together the advanced sketches and I realized I probably need to do more things than expected to get the app working. Right now, I think I would need two databases, where one is used for the actual notes and one for the app settings, like the tags. I also discovered I probably need to add a camera API for the feature to make an in-app photo.

![design doc sketch](https://github.com/inayrus/FinalAppProject/blob/master/doc/design_doc_sketch_new.png)

#### Day 3
Today was the first stand-up. I asked how they would handle the add tag function in the sidebar and where to save it, considering it isn't linked to a note in the NoteDatabase. My feedback from Pjotr was to try to focus on the minimum viable product features first and to add the tag details later. This caused me to think what else is not strictly necessary to make a functional app. I decided to implement the following features after the basics are working:
* Possibility to create a loose extra tag (not yet linked to a note)
* Camera option (get picture by making a photo)
* The sidebar navigation

I also worked on the "utility modules, classes and functions" part of the design doc and added a new sketch. This select photo activity is added to the readme and incorporated in a new design doc sketch. I think I now only have to look into how to get the keyboard format options (bolding, underline, highlight) and how to remember how edited text is formatted, and then I can start on coding the prototype.

![diagrams](https://github.com/inayrus/FinalAppProject/blob/master/doc/UML.png)

#### Day 4
The goal of the stand up today was to make a list of things we need t research to make our app working. My list consisted of figuring out how to implement the keyboard formatting options, how to save the formatted text, and figuring out how the microsoft API works. My original idea on how to save a formatted word was to bracket this words with HTML tags (<b> bolding </b>), in combination with the fromHTML function. During the stand-up, I got a suggestion from Maarten to look into ascii (Whatsapp's use of * for bolding, _ for italics). Pjotr suggested that I could also use the text attribute in android studio to format the text. I think I will go with whatever option also allows for highlighting in different colours.

Today I mainly worked on putting together basic layouts and making sure they're connected with buttons and listeners. The listviews in the layout aren't working yet.

#### Day 5
Stand up feedback on the layout: filling in your own title sounds useful, could add the creation date/time and the first sentence to the listView of notes.
I've mainly been working on adding a toolbar to the app that supports back navigation and clicking icons.

I believe my prototype right now is okay. It has all the screens, a rough layout of the elements I want in it, and the screens are also connected to each other. Some other layout/connection elements that are still missing: a listener for the listview in main, action provider (pop up) for the label icon in NoteActivity, and a sidebar.

### Week 2
#### Day 1
Pinksteren, I haven't been programming.

#### Day 2
* Created the database, implemented:
  * insert method
  * select all method
* Made a note list adapter for the MainActivity/OverviewActivity
* Linked MainActivity to NoteActivity
* Looked into how to create a horizontal listview, but it says I might need to do that with a recycle view

Tomorrow I want to implement the database update method and create a horizontal listview for the tags.

#### Day 3
New things:
* Implemented the update note method.
* Added the Done button in the NoteActivity.
* Delete button in toolbar is working, provides an alert dialogue.
* Select photo activity sends user to gallery.
* Created a styleguide during the stand-up.
* RecyclerView implemented as recommended by Renske, but the tags aren't shown in horizontal line. If there are two tags, only the first one is shown and the second disappears. I should start with this tomorrow.

I've also been debating whether the back buttons in the NoteActivity should save the note as well, or if they should act as a cancel button (what they do now).

#### Day 4
I've mainly been working on getting the tags in MainActivity to work:
* Fixed the problem with the tags. They're now nested in MainActivity's ListView and displayed in a horizontal line. However, the onClick of the ListView is now broken.
* Natasja suggested that a listview can't be nested, so I tried to programatically add buttons to the listview. I later changed the buttons to a textview because I liked the look better.
* Now the tags are displayed in a horizontal line and the ListView item is still clickable.
* The tags themselves are also clickable, but clicking seems to be a bit difficult.

Three things before the alpha version is complete:
* Linking the API to the SelectActivity so a photo can be converted to digital text.
* Adding a tag to a note in NoteActivity.
* Sorting on a tag in MainActivity.

#### Day 5
I've been working on getting the API to work. The biggest problems were figuring out why the ConvertRequest class could be built but the app could not be installed on the phone, and finding an image format that the API could acceor. Choosing an image from the gallery returns an URI, but the API only accepts URL's and binary image data. I eventually managed to turn the URI into a filepath, which could potentially be a compatible format. However, I haven't been able to test this yet, because I'll have to request a new subscription key first.

### Week 3
#### Day 1
* Android studio reported an error that there is no git repository in this project? After testing: the commits still seem to work, so I think there is no issue.
* Been trying to link an activity to the API. I got a working subscription key and copied quickstart code from the Microsoft Computer Vision website, but running this gave me an NoSuchFieldError for the AllowAllHostnameVerifier apache class. Apparently this class is deprecated.
* Natasja and Marijn helped me find an alternative, so now I'm trying to link my app to the API with an Android client library: https://github.com/microsoft/Cognitive-Vision-Android

#### Day 2
* The link to the API is working! The example code on the Cognitive Vision Android github works well, so now the SelectActivity class can recognize handwriting!
* Improved the layout of the SelectActivity.
* Added the SelectActivity as an option in the NoteActivity, recognizing handwriting doesn't have to start with an empty note; multiple handwriting photos can be converted to text in the same note.
* I also made a start to find a way for the recognized text to be added to the NoteActivity. It's now implemented with an intent, but the downside is that the old NoteActivity is still under it when you press back. Tomorrow I might need to look into the startActivityForResult method.

#### Day 3
* Renske suggested that the problem with multiple stacked NoteActivities (return to NoteActivity after text is recognized from a photo in SelecActivity) could be handled by specifying this activity as a SingleInstance/ SingleTask in the manifest.
* Edited the NoteActivity code so the keyboard only opens after an EditText has been clicked.
* Displayed the tags in the NoteActivity.
* Started research on how to create a pop up menu when an icon in the tool bar is clicked. The idea is for a pop up menu to open when the tags icon is clicked. I want to display all the made tags so far in it, with the extra option to make a new tag. All these option would have a checkbox and are all selectable. At this moment I'm not sure how such a pop up can be achieved.

#### Day 4
* I looked into PopupMenu as a way to display a pop up for choosing tags and put it in my code. Renske suggested that AlertDialogue could also be an option and I have to agree that it does more have the look I was going for. Most of the day I've been trying different layouts that allow for a list of checkbox items (tags), with an extra editText option under it in case the user wants to add a new tag.
* Implementing a layout succeeded, though it's not very pretty to look at yet.

#### Day 5
* Today I wanted to:
  1. Link the Add Tags AlertDialogue to display the right tags.
  2. Change the displayed tags in NoteActivity and MainActivity when different tags are checked and saved, respectively.  
  3. Allow the user to create new tags.
  4. Implement a filter-on-tags option in MainActivity.
* I succeeded in executing the first three points. There does seem to be a bug in the third point, however. If the user unselects all the tags, or when they make a new tag but it has no string (""), the NoteActivity displays a small gray rectangle.

#### Day 6
* Implemented filter function in main.   

### Week 4
#### Day 1
Changed the layout colours to pink/purple and did some bug fixes:
* Creating a new note and converting a photo to text is now able to be saved in the database.
* Unchecking all tags on a note no longer shows/saves an empty tag.
* Made it possible to first add a new tag to a note, apply it, and then click the Add Tag icon again without the app crashing.
* Implemented a horizontalscrollview in NoteaActivity so all chosen tags can be seen if there are a lot of them.
 This solution can not be so easily implemented for the MainActivity tags, however. There the note become unclickable.

#### Day 2
* Cleaned my repository from made, but unused files.
* Fixed a few bugs concerning the tags and converting a photo to text.
* Made the layout more pretty, with sharp icons and more colours.

For tomorrow I need to add a saveInstanceState in the SelectActivity and look if I can improve my code. I also need to rewrite my README, add a PROPOSAL.md, think about Licence, and start on the final report.

#### Day 3
* Did a code review with Levy. She suggested to change the SelectActivity name to something more specifically for the file, so I changed it to RecognizeHandwritingActivity.
* Cleaned the code of all the print statements and out commented code.
* Added a savedInstanceState to every activity.
* Transformed my README to a PROPOSAL.md

Tomorrow I'm planning to make a video, rewrite the readme, and add a license.

#### Day 4
* Changed the app name and added an icon.
* Chose a public domain license (MIT licence) for my app. This app is heavily based on note apps that already existed, and it currently is far from perfect. I worked hard on it, but at this moment I feel like I wouldn't mind if other people improved this app, or used my code as a base for their own projects.
* Wrote the README.
