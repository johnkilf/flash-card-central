# flash-card-central

## Description

Flash Card Central is a flash cards application to enable learning based on spaced repetition. The application is built with microservices and designed to be deployed to Kubernetes.

Unlike other flash card apps, such as Anki, where both the management of the cards and the learning is handled by a single user, Flash Card Central splits the responsibilities between *Teachers* who are responsible for the creation and management of Flash Cards and *Learners* who are the users of the Flash Cards.

* Teachers can create and assign flashcards to learners, track learner progress, and see stats related to individual cards (e.g. which cards are learners finding most difficult).
* Learners review the flashcards assigned to them by the teacher based on a spaced repetition algorithm. 

## Potential Use Cases
* A language class teacher assigning vocabulary flashcards to their students. 
  * Flashcards have long been known to be useful to language learning, and providing a structured way for a teacher to assign flashcards could be useful.
* Compliance officer assigning compliance training questions to trainees. 
  * Compliance trainings are known for being things that end users try to get through as quickly as possible and then quickly forget. A spaced repetition algorithm could ensure longer term retention. 

## Architecture

![Alt text](architecture.png?raw=true "Architecture")

## Notes (WIP)
Learner UI has its own scheduler. 

Gets the next 10 card ids and revise times.
Notes the revise time for the 10th (or last available) card
If cards are wrong they get rescheduled. 
* If the reschedule is within the revise time of the 10th card they're simply added to the queue
* If the reschedule is outside the revise time of the 10th card they're discarded. - special case when at the end of deck
* When down to 3 remaining cards - get the next 10 cards and revise times