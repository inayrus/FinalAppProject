# FinalAppProject: Note Organization App

## Summary
My name is Valerie Sawirja and this is an app proposal for the final project of the minor programmeren.
The main goal of this app would be to be able to store and organize typed notes and handwritten notes in the same place.
With this app, the user can make new notes, save them in different folders, and edit them.


## Problem statement
People usually write things down to remember it at a later time. This could be a to do list written on a scrap piece of paper, or an overheard quote typed on a phone. The way we make notes isn’t always consistent, even though we sometimes wish it was. It’s easy to print documents and make digital text physical, but it’s a bit more effort to turn handwritten notes digital. Especially in the case of longer texts, like lecture notes.

## Solution
To solve the problem of having to type the notes to make them digital, this app includes a feature that turns handwritten notes into digital texts with a photo.

### Features
These are the main features I would like to implement:
1. let people create new notes:
    - can type a note as normal
    - can upload a photo of text to convert to digital text
2. possible to edit the note: bold, highlights, text sizes.
3. delete a note
4. add tags to a note
5. sort the notes by tag in the overview

#### Optional Features
* Camera feature to retrieve make photos of notes
* A searchbar
* The sidebar
* Add tags in sidebar
* Change layout colours in sidebar
* A date of creation and of modification on the note

### Sketches
These sketches don't yet include views of a photo being taken or chosen to convert. The converted note activity does show what it would look like after the photo has been turned to text.

![overview activity](https://github.com/inayrus/FinalAppProject/blob/master/doc/overview_activity_options.png)
![select photo view](https://github.com/inayrus/FinalAppProject/blob/master/doc/select_photo.png)
![new note activity](https://github.com/inayrus/FinalAppProject/blob/master/doc/Note_View_Empty.png)

![options](https://github.com/inayrus/FinalAppProject/blob/master/doc/options_view.png)
![tag activity](https://github.com/inayrus/FinalAppProject/blob/master/doc/tag_activity.png)
![filled note activity](https://github.com/inayrus/FinalAppProject/blob/master/doc/Note_View_Filled.png)

### Prerequisites
* External components needed to implement certain features:
  * SQLite to save the notes.
  * Microsoft Computer Vision API to convert photos to text.
* I looked into the FairNote as a similar app, and I like the way they use tags instead of folders, and how it's possible to change the colour layout of the app. They do show quite a big part of the note itself in the listview, which is useful when the notes are short, but not really for long texts.
* I think the hardest parts of implementing this application will be figuring out how the Microsoft API works. At this moment I think it could also be a challenge to save and seperate the user's notes from the app settings (labels, colour layout preferences).
