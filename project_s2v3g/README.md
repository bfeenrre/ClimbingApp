# Climbing Journal Application

## Overview
**Who is this for?**  <br />
This application is for climbers who want a way to organize and track their climbing goals and progress.
The climbers who use this application can do so by keeping a record of all the routes they have climbed 
in the past (with notes that can contain anything from beta to safety pointers) as well as a list of routes that they
want to climb in the future.

**My Personal Interest in this Project**  <br />
I enjoy climbing, and want a more chic, adaptable, and easily navigable way to track my climbing than a paper
journal. I imagine other climbers have the same desire.

**Basic Components**  <br />
Journal Contains:
- A list of climbed routes
- A list of routes in progress
- A list of routes to climb in the future

Each route has the following data attached to it:
- status (climbed, climbing, to climb)
- name
- latitude and longitude (for implementation with  a map system later)
- Rating (V *or* Yosemite)
- Type of Climb
- Organizable Notes section for each climb (including pictures, if wanted)

One can view their routes in either:
- Map layout
- Journal layout

## Phase 1 User Stories For Grade (>4):
- As a user, I want to be able to add a Route to my Journal (in any list I choose)
- As a user, I want to be able to delete a route
- - As a user, I want to be able to view either past, current, or future routes in list form
- As a user, I want to be able to view either past, current, or future routes filtered according to a...
    - max rating (only routes with rating <= #)
    - min rating (only routes with rating >= #)
    - climb type (only routes with the specified climb type)
    - proximity to my location in kilometers
- As a user, I want to be able to view  either past, current, future, or all routes sorted according to...
    - rating (high to low)
    - rating (low to high)
- As a user, I want to be able to search a list of past, current, or future routes by route name

## Phase 2 User Stories
- As a user, I want the state of my journal to automatically save on quit 
- As a user, I want the most recent saved state of my journal to be loaded automatically when I start the application

## Phase 4 : Task 2
- example event log output (if events occur):
<br />
" 
<br />
EVENT LOG:
<br />
Route (name: Silence) added to Journal (listIndex: climbed)
<br />
Route (name: otherRoute) removed from Journal (listIndex: toClimb)
<br />
"
<br />  <br /> 

- example event log output (if NO events occur):
<br />
"
<br />
EVENT LOG:
<br />
"No events logged - no routes were added or removed from your journal"
<br />
"
 
## Phase 4 : Task 3

If I were to refactor my code, after looking at my design diagram, I would add a class called Display in my ui, and move
into it everything from my current ClimbingApp class except the methods that interact with the journal field. I would 
load, save, add, and remove routes from my journal only in the ClimbingApp class, and use the singleton design pattern
on my ClimbingApp class so that only one app and one journal exist in my program. I would then just make RouteAddUI, 
Display, and JournalUI extend my ClimbingAppClass interact with the ClimbingApp to interact with the Journal.