# flash-card-central

## Description

Flash Card Central is a flash cards application to enable learning based on spaced repetition. The application is built with microservices and designed to be deployed to Kubernetes.

Unlike other popular flash card apps, such as Anki, which are primarily designed for self study, Flash Card Central is designed to be used by teachers and learners. *Teachers* are responsible for the creation and management of Flash Cards, while *Learners* are the ones who revise using the Flash Cards.

* Teachers can create and assign flashcards to learners, track learner progress, and see stats related to individual cards (e.g. which cards are learners finding most difficult).
* Learners review the flashcards assigned to them by the teacher based on a spaced repetition algorithm. 

## Potential Use Cases
* A language class teacher assigning vocabulary flashcards to their students. 
  * Flashcards have long been known to be useful to language learning, and providing a structured way for a teacher to assign flashcards could be useful.
* Compliance officer assigning compliance training questions to trainees. 
  * Compliance trainings are known for being things that end users try to get through as quickly as possible and then quickly forget. A spaced repetition algorithm could ensure longer term retention. 

## Architecture (planned)

![Alt text](architecture.png?raw=true "Architecture")