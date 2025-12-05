# Daily stand-ups, week 9
## 28-10-2025
### what everyone is going to do
- Mai: fetch polling station controller, and retrieve the data from the database for the frontend. Pivot/morph table research
- Thomas: Documentation migrations and seeder
- Daan: jpa repository national votes
- Lena: election results page front-end, research how the database works


## 30-10-2025
We discussed what we need to do for the expert review to get better feedback. Thomas is making a checklist for us.

Right now:
- each person need to make a part of the erd. Thomas and Mai have already done this.
- each person needs to make DTOs (Mai has already started) and mappers
- Mai wants to implement the OpenAPI so that we get the endpoint documentation
- We need to use Versioning and prefixes such as /api. In controller folder we are going to add folders api and inside v1
- We are already using the SRP (single responsibility principle). For example, our xmlparser is separate from the backend, so we don't have to worry about any errors in the parser messing up our backend
- Some are documenting their code well, everyone needs to do it as well
- We need to implement advanced jpa caching 
- We are not using status codes yet, but we are using custom error messages
- We are not using a custom logger or Advanced error handling with a global exception handler, structured error responses.
- Mai and Thomas have created entities that are properly mapped. Relationships (@OneToMany, @ManyToOne, etc.)
- Components are small and reusable and we already have good folder structure in the frontend. We don't have patterns like smart/dumb components, animations and transitions that enhance UX or strong attention to accessibility
- Everyone needs to make a vertical UML of the front-end, backend and parser
- Mai and Thomas are making multiple small regular and logical commits per day.
- We are using different branches, with semi-consistent naming conventions (e.g., feature/..., hotfix/...). No one uses their own names for the branch; and branch names are clear
- We haven't made any tests yet or used any mocking frameworks. We have to pay extra attention to the "Testing" part in the Expert Review Rubric
- We need to make interfaces for services

### what everyone did yesterday
- Mai: municipality and polling station controllers; made the metadata table polymorphic; dto, mappers, municipality and polling station service; partyvote controller and service; endpoint for polling station, municipality and party votes
- Thomas: 
- Daan: worked on his transformer
- Lena: worked on the election results page service and a mixin for the frontend; started remaking the transformer

### what everyone is going to do
- Mai: OpenAPI docs, further working on the criteria for the expert review rubriek
- Thomas: UML diagram seeder and party list, stream between xml parser and backend (software performance), making global Exception class
- Daan: is going to start his research and maybe further work on the transformer
- Lena: further work on the election results page and showing party results on the page; making controller, service and jpa repository for constituency votes in the backend; remaking the ConstituencyVotestransformer, service and controllers; uncounted votes table in db\migration


## 31-10-2025
### what everyone did yesterday
- Mai: UML class diagram of municipality
- Thomas: worked on the main and sub-questions of the research report and started the UML diagram for the seeder
- Daan: class diagram of forum
- Lena: uncounted votes table in db\migration and adjusted metadata model

### what everyone is going to do
- Mai: research paper; change endpoints, so that they start with /api/v1/; further working on the expert rubriek 
- Thomas: going to finish the UML diagram and start on the UML diagram for the party. Over the weekend, he'll work on the Stream API between the XML parser and the election backend to speed up the retrieval of XML data, and he'll complete the full ERD. In addition, he also wants to finish his entire vertical user story for the party list. If he still has time left, he’ll dockerize the whole project.
- Daan: jpa and transformer for candidates; rejected votes table
- Lena: further work on the election results page and showing party results on the page; remaking the ConstituencyVotestransformer, service and controllers; making controller, service and jpa repository for constituency votes in the backend; class diagram of constituency votes. Whatever is not finished today, will be finished in the weekend.


### 04/11/2025
### what everyone did yesterday
- Thomas: mapper, uml 
- Mai: front-end compare parties
- Daan: party votes jpa repository, national votes
- Lena: constituency votes transformer, constituency votes jpa repository, service, controller; front-end service for fetching the filter list options and parties

### what everyone is going to do
- Thomas: make uml diagram, party list front-end
- Mai: further making front-end compare parties page,
- Daan: the map of the Netherlands
- Lena: front-end election results page and service for fetching the results, uml class diagram of constituency votes

### what we want to have finished before the sprint review
 - Thomas: party list
 - Mai: compare page
 - Lena: election results page
 - Daan: national votes