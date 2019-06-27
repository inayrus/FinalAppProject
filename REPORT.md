# Report

My application is a note making app, where the user can create and edit notes, organize notes with tags, and use a handwriting recognition feature.

![overview](https://github.com/inayrus/FinalAppProject/blob/master/doc/Screenshot_overview.png)

The app has three activities: a MainActivity, which is the overview, a NoteActivity, where notes can be edited, and a RecognizeHandwritingActivity, where text in a photo can be converted to digital text. From the MainActivity you can get to NoteActivity, and from NoteActivity you can get to RecognizeHandwritingActivity.

The Note class contains the information that makes up a note, like Title, Content, and Tags. These Notes are all stored in a database, which can only be accessed through the NoteDatabase class.

All the notes in the database are shown in the Mainactivity with a listview. This listview is made with one mold layout and the NoteAdapter class to put every note in that mold. This mold consists of a note title and its tags (see the screenshot above). To account for multiple tags, the TagsAdapter class is made to put the tags nicely in the layout. This TagsAdapter is reused in the NoteActivity.

There are also a few AlertDialogs in the Activities. MainActivity has one so there can be filtered on tags. This feature goes through the database. On create there is a call to select a method to retrieve all the tags used in the app, and when the Filter button is clicked, a select method is called to only get the notes with a certain tag. NoteActivity has two AlertDialogs. One is to add a tag to the note, and the other is a delete confirmation.

The RecognizeHandwritingActivity contains code to make a connection is made with the Microsoft API. The ImageHelper class helps resize the chosen image so it's in the right format for the API. Both of these files are heavily based on code from the microsoft android client library sample.

A few of the challenges I've met are getting a connection to the Microsoft API and getting the TagsAdapter to work. It started with having to choose between a handwriting recognition API from Microsoft or from Google. I choose Microsoft because it allowed trials, but proved quite difficult to implement a request to this API. During the course we've learned about request through volley, and I wasn't sure how to use that with this API. That's why I've tried the sample code on the Microsoft website. However, a connection wasn't happening until we tried the Android Client Library.

The TagsAdapter was an issue because I tried to nest a RecyclerView in a listview, but listviews apparently don't allow nesting. This was solved emptying the tags container and recreating textviews with the tags every time the TagsAdapter was called. This does seem like a good temporary solution to me, but might become an issue when there are a lot more tags. Thinking about it now, nested RecyclerViews could probably have been another solution.

I've also dropped a lot of features since the first proposal, such as the camera feature and adding a keyboard with formatting options. In the ideal world, with more time, I would definitely have looked into it. I would also have added an option to give tags different colours for overview, but I am quite content the way it is now.
